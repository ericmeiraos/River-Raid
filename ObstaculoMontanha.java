import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class ObstaculoMontanha {
    public Image montanhaDir;
    public Image montanhaEsq;
    public int posX, posY_1, posY_2, posY_3;
    public int velY;
    public Rectangle colDir_1, colDir_2, colDir_3;
    public Rectangle colEsq_1, colEsq_2, colEsq_3;

    public ObstaculoMontanha() {
        // Carrega a imagem da montanha
        try {
            montanhaDir = ImageIO.read(getClass().getResource(Constants.PATH_MONTANHA_DIR));
            montanhaEsq = ImageIO.read(getClass().getResource(Constants.PATH_MONTANHA_ESQ));

            Image scaledImage = montanhaDir.getScaledInstance(50, 120, Image.SCALE_SMOOTH);
            Image scaledImage2 = montanhaEsq.getScaledInstance(50, 120, Image.SCALE_SMOOTH);

            montanhaEsq = new BufferedImage(50, 120, BufferedImage.TYPE_INT_ARGB);
            montanhaDir = new BufferedImage(50, 120, BufferedImage.TYPE_INT_ARGB);

            montanhaDir.getGraphics().drawImage(scaledImage, 0, 0, null);
            montanhaEsq.getGraphics().drawImage(scaledImage2, 0, 0, null);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Posição inicial do cenário
        posX = 0;
        posY_1 = Constants.ALTURA_TELA - 845;
        posY_2 = posY_1 - 845;
        posY_3 = posY_2 - 845;
        velY = Cenario.velocidade;

        // Inicializa as caixas de colisão
        colDir_1 = new Rectangle(0, 0, 50, 120);
        colDir_2 = new Rectangle(0, 0, 50, 120);
        colDir_3 = new Rectangle(0, 0, 50, 120);

        colEsq_1 = new Rectangle(0, 0, 50, 120);
        colEsq_2 = new Rectangle(0, 0, 50, 120);
        colEsq_3 = new Rectangle(0, 0, 50, 120);

    }

    // Método para mover a montanha
    public void mover() {
        posY_1 += Cenario.velocidade;
        posY_2 += Cenario.velocidade;
        posY_3 += Cenario.velocidade;

        // Atualiza as caixas para o lado direito
        int xDireita = Constants.LARGURA_TELA - montanhaDir.getWidth(null) - 65;
        colDir_1.setBounds(xDireita, posY_1 + 50, 50, 120-22);
        colDir_2.setBounds(xDireita, posY_2 + 350, 50, 120-22);
        colDir_3.setBounds(xDireita, posY_3 + 150, 50, 120-22);

        // Atualiza as caixas para o lado esquerdo
        int xEsquerda = 65;
        colEsq_1.setBounds(xEsquerda, posY_1 + 350, 50, 120-22);
        colEsq_2.setBounds(xEsquerda, posY_2 + 150, 50, 120-22);
        colEsq_3.setBounds(xEsquerda, posY_3 + 250, 50, 120-22);

    }

    // Método para reposicionar a montanha
    public void reposicionar() {
        if (posY_1 >= Constants.ALTURA_TELA)
            posY_1 = posY_3 - (845 / 2);
        if (posY_2 >= Constants.ALTURA_TELA)
            posY_2 = posY_1 - (845 / 2);
        if (posY_3 >= Constants.ALTURA_TELA)
            posY_3 = posY_2 - (845 / 2);

        // Atualiza as caixas de colisão com os deslocamentos usados no método desenhar
        int xDireita = Constants.LARGURA_TELA - montanhaDir.getWidth(null) - 65;
        colDir_1.setBounds(xDireita+10, posY_1 + 65, 50-15, 120-40);
        colDir_2.setBounds(xDireita+10, posY_2 + 365, 50-15, 120-40);
        colDir_3.setBounds(xDireita+10, posY_3 + 165, 50-15, 120-40);

        int xEsquerda = 65;
        colEsq_1.setBounds(xEsquerda, posY_1 + 375, 50-15, 120-40);
        colEsq_2.setBounds(xEsquerda, posY_2 + 175, 50-15, 120-40);
        colEsq_3.setBounds(xEsquerda, posY_3 + 275, 50-15, 120-40);

    }

    // Método para desenhar a montanha
    public void desenharMontanhaEsq(Graphics g, int larguraCenario, int alturaCenario) {
        int xEsquerda = 65;

        g.drawImage(montanhaEsq, xEsquerda, posY_1 + 350, null);
        g.drawImage(montanhaEsq, xEsquerda, posY_2 + 150, null);
        g.drawImage(montanhaEsq, xEsquerda, posY_3 + 250, null);

        // Desenha as caixas de colisão
        g.setColor(Color.GREEN);
      //  g.drawRect(colEsq_1.x, colEsq_1.y, colEsq_1.width, colEsq_1.height);
       // g.drawRect(colEsq_2.x, colEsq_2.y, colEsq_2.width, colEsq_2.height);
       //g.drawRect(colEsq_3.x, colEsq_3.y, colEsq_3.width, colEsq_3.height);
    }

    // Método para desenhar a montanha
    public void desenharMontanhaDir(Graphics g, int larguraCenario, int alturaCenario) {
        int xDireita = larguraCenario - montanhaDir.getWidth(null) - 65;

        g.drawImage(montanhaDir, xDireita, posY_1 + 50, null);
        g.drawImage(montanhaDir, xDireita, posY_2 + 350, null);
        g.drawImage(montanhaDir, xDireita, posY_3 + 150, null);

        // Desenha as caixas de colisão
        
          g.setColor(Color.RED);
        //  g.drawRect(colDir_1.x, colDir_1.y, colDir_1.width, colDir_1.height);
        //  g.drawRect(colDir_2.x, colDir_2.y, colDir_2.width, colDir_2.height);
        //  g.drawRect(colDir_3.x, colDir_3.y, colDir_3.width, colDir_3.height);
         
    }

    // Método para verificar colisão com a nave
    public boolean verificarColisao(Rectangle naveCaixa) {
        return naveCaixa.intersects(colDir_1) || naveCaixa.intersects(colDir_2) || naveCaixa.intersects(colDir_3) ||
                naveCaixa.intersects(colEsq_1) || naveCaixa.intersects(colEsq_2) || naveCaixa.intersects(colEsq_3);
    }
}
