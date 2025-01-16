import javax.swing.*;
import java.awt.*;

public class Principal {

    private static final Principal principal = new Principal();
    private final JFrame janela = new JFrame("Jogo 2D");

    public static Principal getInstance() { 
        return principal;
    }

    public static void main(String[] args) {
        Principal.getInstance().start();
    }

    public void start() {
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setResizable(false);

        // Adiciona a tela de início
        TelaInicio telaInicio = new TelaInicio(janela);
        telaInicio.setPreferredSize(new Dimension(Constants.LARGURA_TELA, Constants.ALTURA_TELA));
        janela.getContentPane().add(telaInicio);

        // Configurações de tamanho e centralização
        janela.pack();
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
    }

    // Método para trocar a tela
    public void clear() {
        janela.getContentPane().removeAll();
        janela.pack();
        janela.revalidate();
    }
}