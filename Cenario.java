import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Cenario {
    public BufferedImage img1;
    public int posX;
    public int posY_1, posY_2, posY_3;
    public int velY;
    public static int velocidade = 5;

    public Cenario() {
        // Carregue a imagem do cenário
        try {

            img1 = ImageIO.read(getClass().getResource(Constants.PATH_CENARIO));

        } catch (Exception e) {
            System.out.println("Erro ao carregar a imagem do cenário!");
            e.printStackTrace();
        }

        // Posição inicial do cenário
        posX = 0;
        posY_1 = Constants.ALTURA_TELA - 845;
        posY_2 = posY_1 - 845;
        posY_3 = posY_2 - 845;
        velY = velocidade;
    }

    public void mover() { // Mover o cenário
        posY_1 += Cenario.velocidade;
        posY_2 += Cenario.velocidade;
        posY_3 += Cenario.velocidade;
    }

    public void reposicionar() { // Reposicionar o cenário
        if (posY_1 >= Constants.ALTURA_TELA)
            posY_1 = posY_3 - 845;
        if (posY_2 >= Constants.ALTURA_TELA)
            posY_2 = posY_1 - 845;
        if (posY_3 >= Constants.ALTURA_TELA)
            posY_3 = posY_2 - 845;
    }
}
