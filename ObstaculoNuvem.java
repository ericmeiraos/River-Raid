import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class ObstaculoNuvem {
    public int posX, posY_1, posY_2, posY_3;
    public int velY;
    public Image nuvem;
    public Rectangle col_1, col_2, col_3;
    private Random random;

    public ObstaculoNuvem() {
        // Carrega a imagem da nuvem
        try {

            nuvem = ImageIO.read(getClass().getResource(Constants.PATH_NUVEM));

            BufferedImage tempNuvem = new BufferedImage(80, 50, BufferedImage.TYPE_INT_ARGB);
            Graphics g = tempNuvem.getGraphics();
            g.drawImage(nuvem.getScaledInstance(80, 50, Image.SCALE_SMOOTH), 0, 0, null);
            g.dispose();
            nuvem = tempNuvem;

        } catch (IOException e) {
            e.printStackTrace();
        }
        random = new Random();

        // Define posições iniciais
        posX = gerarPosicaoX();
        posY_1 = random.nextInt(100); 
        velY = Cenario.velocidade;

        // Inicializa as caixas de colisão
        col_1 = new Rectangle(posX, posY_1, 80, 50);

    }

    // Método para gerar a posição X da nuvem
    public int gerarPosicaoX() {
        int minX = 400;
        int maxX = 500;
        return random.nextInt(maxX - minX) + minX; 
    }

    // Método para mover a nuvem
    public void mover() {
        posY_1 += Cenario.velocidade;

        // Atualiza as caixas de colisão
        col_1.setBounds(posX+8, posY_1+8, 80-20, 50-8);

    }

    // Método para reposicionar a nuvem
    public void reposicionar() {
        if (posY_1 >= Constants.ALTURA_TELA) {
            posY_1 = -random.nextInt(300);
            posX = gerarPosicaoX();
        }

        // Atualiza as caixas de colisão
        col_1.setBounds(posX+8, posY_1+8, 80-20, 50-8);

    }

    // Método para desenhar a nuvem
    public void desenhar(Graphics g, int larguraCenario, int alturaCenario) {
        // Desenha as nuvens
        g.drawImage(nuvem, posX, posY_1, null);

        // Desenha as caixas de colisão
        g.setColor(Color.BLACK);
       // g.drawRect(col_1.x, col_1.y, col_1.width, col_1.height);

    }

    // Método para verificar colisão
    public boolean verificarColisao(Rectangle naveCaixa) {
        return naveCaixa.intersects(col_1);
    }

}
