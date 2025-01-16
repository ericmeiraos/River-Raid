import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ObstaculoCaixa {
    public Image caixa;
    public int posX, posY_1;
    public int velY;
    public Rectangle col_1;

    private Random random;

    public ObstaculoCaixa() {
        // Carrega a imagem da caixa
        try {
            caixa = ImageIO.read(getClass().getResource(Constants.PATH_CAIXA));

            Image scaledImage = caixa.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

            caixa = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
            caixa.getGraphics().drawImage(scaledImage, 0, 0, null);

        } catch (IOException e) {
            e.printStackTrace();
        }
       
        random = new Random();

        // Define posições iniciais
        posX = gerarPosicaoX();
        posY_1 = -random.nextInt(300);
        velY = Cenario.velocidade;

        // Inicializa as caixas de colisão
        col_1 = new Rectangle(posX, posY_1, caixa.getWidth(null), caixa.getHeight(null));
    }
    // Método para gerar a posição X da caixa
    private int gerarPosicaoX() {
        int minX = 85;
        int maxX = 150;
        return random.nextInt(maxX - minX) + minX;
    }

    // Método para mover a caixa
    public void mover() {
        posY_1 += Cenario.velocidade;

        // Atualiza as caixas de colisão
        col_1.setBounds(posX, posY_1, 40, 4050);

    }
    // Método para reposicionar a caixa
    public void reposicionar() {
        if (posY_1 >= Constants.ALTURA_TELA) {
            posY_1 = -random.nextInt(300);
            posX = gerarPosicaoX();
        }

        // Atualiza as caixas de colisão
        col_1.setBounds(posX, posY_1, 50, 50);

    }
    // Método para desenhar a caixa
    public void desenhar(Graphics g, int larguraCenario, int alturaCenario) {
        g.drawImage(caixa, posX, posY_1, null);

        // Desenhar as caixas de colisão
        g.setColor(Color.green);
       g.drawRect(col_1.x, col_1.y, col_1.width, col_1.height);
    }

    // Método para verificar colisão com a nave
    public boolean verificarColisao(Rectangle naveCaixa) {
        return naveCaixa.intersects(col_1);
    }
}
