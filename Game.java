import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game extends JPanel {
    private final Cenario cenario;
    private final Nave nave;
    // private ObstaculoMontanha montanha;
    // private ObstaculoFolha folha;
    // private ObstaculoCaixa caixa;
    // private ObstaculoNuvem nuvem;
    private boolean cima = false, baixo = false, esquerda = false, direita = false;

    private final List<Object> obstaculos = new ArrayList<Object>();

    private long iniciaTempo;
    private long tempoDecorrido;
    private boolean gameOver = false;

    private JButton reiniciaButton;
    private JButton sairButton;
    private TelaInicio play;

    public Game() {
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> cima = true;
                    case KeyEvent.VK_DOWN -> baixo = true;
                    case KeyEvent.VK_LEFT -> esquerda = true;
                    case KeyEvent.VK_RIGHT -> direita = true;
                    case KeyEvent.VK_E -> Cenario.velocidade = Cenario.velocidade + 5;
                    case KeyEvent.VK_Q -> {
                        if ((Cenario.velocidade - 5) > 0) {
                            Cenario.velocidade = Cenario.velocidade - 5;
                        }
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> cima = false;
                    case KeyEvent.VK_DOWN -> baixo = false;
                    case KeyEvent.VK_LEFT -> esquerda = false;
                    case KeyEvent.VK_RIGHT -> direita = false;
                    case KeyEvent.VK_E -> Cenario.velocidade = Cenario.velocidade + 5;
                    case KeyEvent.VK_Q -> {
                        if ((Cenario.velocidade - 5) > 0) {
                            Cenario.velocidade = Cenario.velocidade - 5;
                        }
                    }
                }
            }
        });
        // Instancia os objetos
        cenario = new Cenario();
        nave = new Nave();
        // Adiciona os obstáculos
        obstaculos.add(new ObstaculoMontanha());
        obstaculos.add(new ObstaculoFolha());
        obstaculos.add(new ObstaculoNuvem());
        obstaculos.add(new ObstaculoCaixa());

        // Adiciona a tela de início
        play = new TelaInicio(null);

        iniciaTempo = System.currentTimeMillis(); // Inicializa o tempo do jogo
        iniciarButton(); // Inicializa os botões

        new Thread(this::gameloop).start(); // Thread para o loop do jogo
    }

    // Gameloop --------------------------------------------------------
    public void gameloop() {
        while (true) {
            if (!gameOver) {
                handlerEvents();
                update();
            }
            render();
            try {
                Thread.sleep(17); // Aproximadamente 60 FPS
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void handlerEvents() {
        // Movimentação da inicial da nave
        nave.velX = 0;
        nave.velY = 0;

        // Movimentação da nave com as teclas
        if (cima)
            nave.velY = -5;
        if (baixo)
            nave.velY = 5;
        if (esquerda)
            nave.velX = -5;
        if (direita)
            nave.velX = 5;
    }

    public void update() {
        if (!gameOver) {
            Cenario.velocidade = 5;
            tempoDecorrido = (System.currentTimeMillis() - iniciaTempo) / 1000; // Para calcular o tempo decorrido
            Cenario.velocidade = 5 + (int) (tempoDecorrido / 2); // Ajuste a VELOCIDADE conforme o tempo
        }
        // Move a Nave e o cenario
        nave.mover();
        cenario.mover();
        cenario.reposicionar();

        // Atualiza os obstáculos
        for (Object obj : obstaculos) {
            if (obj instanceof ObstaculoMontanha) {
                ((ObstaculoMontanha) obj).mover();
                ((ObstaculoMontanha) obj).reposicionar();
            } else if (obj instanceof ObstaculoFolha) {
                ((ObstaculoFolha) obj).mover();
                ((ObstaculoFolha) obj).reposicionar();
            } else if (obj instanceof ObstaculoNuvem) {
                ((ObstaculoNuvem) obj).mover();
                ((ObstaculoNuvem) obj).reposicionar();
            } else if (obj instanceof ObstaculoCaixa) {
                ((ObstaculoCaixa) obj).mover();
                ((ObstaculoCaixa) obj).reposicionar();
            }
        }

        testeColisoes();
    }

    public void render() {
        repaint();
    }

    // Outros Metodos --------------------------------------------------
    public void testeColisoes() {
        // Verificar se a nave saiu da tela
        if (nave.posX < 65) {
            nave.posX = 65;
            gameOver();
        }
        if (nave.posX > Constants.LARGURA_TELA - 65 - nave.nave.getWidth()) {
            nave.posX = Constants.LARGURA_TELA - 65 - nave.nave.getWidth();
            gameOver();
        }
        if (nave.posY < 0)
            nave.posY = 0;
        if (nave.posY > Constants.ALTURA_TELA - nave.nave.getHeight())
            nave.posY = Constants.ALTURA_TELA - nave.nave.getHeight();

        // Verificar colisão com os obstáculos
        for (Object obj : obstaculos) {

            if (obj instanceof ObstaculoMontanha && ((ObstaculoMontanha) obj).verificarColisao(nave.naveColisao)) {
                gameOver();
            } else if (obj instanceof ObstaculoFolha && ((ObstaculoFolha) obj).verificarColisao(nave.naveColisao)) {
                gameOver();
            } else if (obj instanceof ObstaculoNuvem && ((ObstaculoNuvem) obj).verificarColisao(nave.naveColisao)) {
                gameOver();
            } else if (obj instanceof ObstaculoCaixa && ((ObstaculoCaixa) obj).verificarColisao(nave.naveColisao)) {
                gameOver();
            } 
        }

    }

    // Caso tenha colião, o jogo acaba
    public void gameOver() {
        gameOver = true;

        // Capturar o nome do jogador
        String nomeJogador = JOptionPane.showInputDialog("Digite seu nome:");

        // Salvar a pontuação no arquivo
        if (nomeJogador != null && !nomeJogador.trim().isEmpty()) {
            salvarPontuacao(nomeJogador, (int) tempoDecorrido);
        }
        // Exibir os botões
        reiniciaButton.setVisible(true);
        sairButton.setVisible(true);

    }

    // Inicializa os botões
    public void iniciarButton() {
        setLayout(null);
        reiniciaButton = new JButton("Reiniciar");
        reiniciaButton.setBounds(230, 380, 120, 40);
        reiniciaButton.setVisible(false);
        reiniciaButton.addActionListener(e -> playGame());

        sairButton = new JButton("Sair");
        sairButton.setBounds(360, 380, 120, 40);
        sairButton.setVisible(false);
        sairButton.addActionListener(e -> System.exit(0));

        add(reiniciaButton);
        add(sairButton);
    }

    // Reinicia o jogo
    public void playGame() {

        Principal.getInstance().clear();
        Principal.getInstance().start();
        ;
        if (play.play == true) {
            gameOver = false;
        }

        iniciaTempo = System.currentTimeMillis();
    }

    // Salva a pontuação no arquivo
    private void salvarPontuacao(String nome, int pontuacao) {
        String arquivo = "record.txt";
        try (BufferedWriter escrever = new BufferedWriter(new FileWriter(arquivo, true))) {
            escrever.write(nome + " - " + pontuacao + " pontos\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metodo Sobrescrito --------------------------------------------------
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.LIGHT_GRAY);

        // Desenha o cenário
        g.drawImage(cenario.img1, cenario.posX, cenario.posY_1, null);
        g.drawImage(cenario.img1, cenario.posX, cenario.posY_2, null);
        g.drawImage(cenario.img1, cenario.posX, cenario.posY_3, null);

        // Desenha a nave
        g.drawImage(nave.nave, nave.posX, nave.posY, null);
        nave.desenhar(g);

        // Desenha os obstáculos
        for (Object obj : obstaculos) {
            if (obj instanceof ObstaculoMontanha) {
                // Desenha a montanha
                ((ObstaculoMontanha) obj).desenharMontanhaDir(g, Constants.LARGURA_TELA, Constants.ALTURA_TELA);
                ((ObstaculoMontanha) obj).desenharMontanhaEsq(g, Constants.LARGURA_TELA, Constants.ALTURA_TELA);
            } else if (obj instanceof ObstaculoFolha) {
                // Desenha a folha
                ((ObstaculoFolha) obj).desenharFolha(g, Constants.LARGURA_TELA, Constants.ALTURA_TELA);
            } else if (obj instanceof ObstaculoNuvem) {
                // Desenha a nuvem
                ((ObstaculoNuvem) obj).desenhar(g, Constants.LARGURA_TELA, Constants.ALTURA_TELA);
            } else if (obj instanceof ObstaculoCaixa) {
                // Desenha a caixa
                ((ObstaculoCaixa) obj).desenhar(g, Constants.LARGURA_TELA, Constants.ALTURA_TELA);
            }
        }

        // Exibe o tempo (pontuação)
        if (!gameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.BOLD, 20));
            g.drawString("Pontuação: " + tempoDecorrido + " s", 75, 40);
        } else {
            // Exibe o tempo final quando o jogo acabar
            g.setColor(Color.black);
            g.setFont(new Font("Monospaced", Font.BOLD, 40));
            g.drawString("Game Over!Pontuação: " + tempoDecorrido + "s", 90, 365);

        }
    }

}
