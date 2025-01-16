import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Nave {
    public BufferedImage nave;
    public int posX, posY;
    public int velX, velY;
    public Rectangle naveColisao;

    public Nave() {
        try {
            // Carrega a imagem da nave
            nave = ImageIO.read(getClass().getResource(Constants.PATH_NAVE));

            Image scaledImage = nave.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

            nave = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
            nave.getGraphics().drawImage(scaledImage, 0, 0, null);

        } catch (Exception e) {
            System.out.println("Erro ao carregar a imagem da nave!");
            e.printStackTrace();
        }

        // Posição inicial da nave
        posX = 300;
        posY = 700;
        velX = 0;
        velY = 0;

        // Inicializa a naveColisao e atualiza com a nova posição
        naveColisao = new Rectangle(posX, posY, nave.getWidth(null), nave.getHeight(null));
    }

    // Método para mover a nave
    public void mover() {
        posX += velX;
        posY += velY;
        naveColisao.setLocation(posX, posY);
    }

    // Desenahr caixa de colisão
    public void desenhar(Graphics g) {
        g.setColor(Color.BLUE);
        //g.drawRect(naveColisao.x, naveColisao.y, naveColisao.width, naveColisao.height);

    }

}
