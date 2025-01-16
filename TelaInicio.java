import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class TelaInicio extends JPanel {
    private final JFrame janela;
    private final ImageIcon inicio;
    public boolean play = false;
    
    public TelaInicio(JFrame janela) { 
        this.janela = janela;

        // Carregar a imagem de fundo
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(getClass().getResource(Constants.PATH_RIVERRAID));
        } catch (IOException e) {
            e.printStackTrace();
        }
        inicio = new ImageIcon(bufferedImage);

        setLayout(null);

        // Botão para iniciar o jogo
        JButton iniciarButton = new JButton("Iniciar Jogo");
        estilizarBotao(iniciarButton);
        iniciarButton.setBounds((Constants.LARGURA_TELA - 200) / 2, 300, 200, 50);
        iniciarButton.addActionListener(this::iniciarJogo);
        add(iniciarButton);

        // Botão para exibir o recorde
        JButton recordButton = new JButton("Exibir Recorde");
        estilizarBotao(recordButton);
        recordButton.setBounds((Constants.LARGURA_TELA - 200) / 2, 380, 200, 50);
        recordButton.addActionListener(e -> exibirRecorde());
        add(recordButton);

        // Botão para sair do jogo
        JButton sairButton = new JButton("Sair");
        estilizarBotao(sairButton);
        sairButton.setBounds((Constants.LARGURA_TELA - 200) / 2, 460, 200, 50);
        sairButton.addActionListener(e -> System.exit(0));
        add(sairButton);
    }

    private void estilizarBotao(JButton botao) {
        botao.setFont(new Font("Monospaced", Font.PLAIN, 20));
        botao.setFocusPainted(false);
        botao.setBackground(new Color(50, 44, 255));
        botao.setForeground(Color.WHITE);
        botao.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
    }

    // Método para iniciar o jogo
    private void iniciarJogo(ActionEvent e) {
        Game game = new Game();
        play = true;

        // Configurar o painel do jogo
        game.setPreferredSize(new Dimension(Constants.LARGURA_TELA, Constants.ALTURA_TELA));

        // Trocar o conteúdo da janela
        janela.getContentPane().removeAll();
        janela.getContentPane().add(game);
        janela.pack();
        janela.revalidate();

        SwingUtilities.invokeLater(game::requestFocusInWindow); //foco no painel
    }

    // Método para exibir o recorde
    private void exibirRecorde() {
        JFrame janelaRanking = new JFrame("Ranking");
        janelaRanking.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janelaRanking.setSize(500, 400);
        janelaRanking.add(new TelaRanking(this.janela));
        janelaRanking.setLocationRelativeTo(null);
        janelaRanking.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(inicio.getImage(), 0, 0, Constants.LARGURA_TELA, Constants.ALTURA_TELA, this);
    }
}
