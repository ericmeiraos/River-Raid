import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class TelaRanking extends JPanel {
    private static final String RECORD_FILE = "record.txt";
    private final JFrame janelaPrincipal; 

    public TelaRanking(JFrame janelaPrincipal) { 
        this.janelaPrincipal = janelaPrincipal;
        setLayout(new BorderLayout());

        // Carregar e ordenar os rankings
        List<String> rankings = carregarRankings();

        //exibir rankings
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        for (String linha : rankings) {
            textArea.append(linha + "\n");
        }

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Botão para voltar
        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(e -> voltarTela());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(voltarButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    // Método para carregar os rankings
    private List<String> carregarRankings() {
        List<String> rankings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(RECORD_FILE))) {
            String linha;
            List<Pontuacao> listaPontuacoes = new ArrayList<>();
            while ((linha = reader.readLine()) != null) { 
                String[] partes = linha.split(" - "); 
                if (partes.length == 2) {  
                    String nome = partes[0].trim(); 
                    int pontuacao = Integer.parseInt(partes[1].replace(" pontos", "").trim()); 
                    listaPontuacoes.add(new Pontuacao(nome, pontuacao));
                }
            }

            // Ordenar as pontuações do maior para o menor
            listaPontuacoes.sort((p1, p2) -> Integer.compare(p2.getPontuacao(), p1.getPontuacao()));

            // Montar a lista de strings
            for (Pontuacao p : listaPontuacoes) {
                rankings.add(p.getNome() + " - " + p.getPontuacao() + " pontos");
            }

        } catch (IOException | NumberFormatException e) {
            rankings.add("Nenhuma pontuação disponível.");
            e.printStackTrace();
        }
        return rankings;
    }
    // Método para voltar à tela anterior
    private void voltarTela() {
        SwingUtilities.getWindowAncestor(this).dispose();
    }

    // Classe para representar uma pontuação
    private static class Pontuacao {
        private final String nome;
        private final int pontuacao;

        public Pontuacao(String nome, int pontuacao) {
            this.nome = nome;
            this.pontuacao = pontuacao;
        }

        public String getNome() {
            return nome;
        }

        public int getPontuacao() {
            return pontuacao;
        }
    }
}
