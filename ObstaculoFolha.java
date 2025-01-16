import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ObstaculoFolha {
    public Image folha;
    public int posX, posY_1;
    public int velY;
    public Rectangle col_1;
    private Random random;

    public ObstaculoFolha() {
        // Carrega a imagem da folha
        try {
            folha = ImageIO.read(getClass().getResource(Constants.PATH_FOLHA_DIR));
            Image scaledImage = folha.getScaledInstance(100, 50, Image.SCALE_SMOOTH);

            folha = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
            folha.getGraphics().drawImage(scaledImage, 0, 0, null);

        } catch (IOException e) {
            e.printStackTrace();
        }
        random = new Random();

        // Posição inicial do cenário
        posX = gerarPosicaoX();
        posY_1 = random.nextInt(200);
        velY = Cenario.velocidade;

        // Inicializa as caixas de colisão
        col_1 = new Rectangle(posX, posY_1, folha.getWidth(null), folha.getHeight(null));
    }

    // Método para gerar a posição X da folha
    private int gerarPosicaoX() {
        int minX = 200;
        int maxX = 350;
        return random.nextInt(maxX - minX) + minX;
    }

    // Método para mover a folha
    public void mover() { // Mover a montanha
        posY_1 += Cenario.velocidade;

        // Atualiza as caixas de colisão
        col_1.setBounds(posX+8, posY_1+8, 100-20, 50-8);

    }

    // Método para reposicionar a folha
    public void reposicionar() { // Reposicionar a montanha
        if (posY_1 >= Constants.ALTURA_TELA) {
            posY_1 = -random.nextInt(300);
            posX = gerarPosicaoX();
        }

        // Atualiza as caixas de colisão com os deslocamentos usados no método desenhar
        col_1.setBounds(posX+8, posY_1+8, 100-20, 50-8);

    }

    // Método para desenhar a folha
    public void desenharFolha(Graphics g, int larguraCenario, int alturaCenario) {

        g.drawImage(folha, posX, posY_1, null);

        // Desenha as caixas de colisão
        g.setColor(Color.yellow);
        //g.drawRect(col_1.x, col_1.y, col_1.width, col_1.height);

    }

    // Método para verificar colisão com a nave
    public boolean verificarColisao(Rectangle naveCaixa) {
        return naveCaixa.intersects(col_1);
    }

}