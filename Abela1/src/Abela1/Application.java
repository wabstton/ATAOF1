package Abela1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Livro {
    String titulo;
    String autor;
    int ano;

    public Livro(String titulo, String autor, int ano) {
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
    }

    @Override
    public String toString() {
        return "Título: " + titulo + ", Autor: " + autor + ", Ano: " + ano;
    }
}

public class Application {
    private Livro[] livros;
    private int count;
    private JTextArea textArea;

    public Application(int capacidade) {
        livros = new Livro[capacidade];
        count = 0;
    }

    public void adicionarLivro(String titulo, String autor, int ano) {
        if (count < livros.length) {
            livros[count] = new Livro(titulo, autor, ano);
            count++;
            atualizarLista();
        } else {
            JOptionPane.showMessageDialog(null, "Capacidade máxima atingida!");
        }
    }

    public void listarLivros() {
        textArea.setText("");
        if (count == 0) {
            textArea.append("Nenhum livro no acervo.\n");
            return;
        }
        for (int i = 0; i < count; i++) {
            textArea.append(livros[i].toString() + "\n");
        }
    }

    public void ordenarPorTitulo() {
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - i - 1; j++) {
                if (livros[j].titulo.compareToIgnoreCase(livros[j + 1].titulo) > 0) {
                    Livro temp = livros[j];
                    livros[j] = livros[j + 1];
                    livros[j + 1] = temp;
                }
            }
        }
        listarLivros();
    }

    public void ordenarPorAutor() {
        quickSortAutor(0, count - 1);
        listarLivros();
    }

    private void quickSortAutor(int inicio, int fim) {
        if (inicio < fim) {
            int posicaoPivo = partition(inicio, fim);
            quickSortAutor(inicio, posicaoPivo - 1);
            quickSortAutor(posicaoPivo + 1, fim);
        }
    }

    private int partition(int inicio, int fim) {
        Livro pivo = livros[fim];
        int i = inicio - 1;
        for (int j = inicio; j < fim; j++) {
            if (livros[j].autor.compareToIgnoreCase(pivo.autor) < 0) {
                i++;
                Livro temp = livros[i];
                livros[i] = livros[j];
                livros[j] = temp;
            }
        }
        Livro temp = livros[i + 1];
        livros[i + 1] = livros[fim];
        livros[fim] = temp;
        return i + 1;
    }

    private void atualizarLista() {
        listarLivros();
    }

    public void criarInterface() {
        JFrame frame = new JFrame("Sistema de Livros");
        frame.setSize(720, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));
        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Adicionar Novo Livro"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel tituloLabel = new JLabel("Título:");
        JTextField tituloField = new JTextField(20);
        JLabel autorLabel = new JLabel("Autor:");
        JTextField autorField = new JTextField(20);
        JLabel anoLabel = new JLabel("Ano de Publicação:");
        JTextField anoField = new JTextField(5);

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(tituloLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(tituloField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(autorLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(autorField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(anoLabel, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(anoField, gbc);
        
        JButton adicionarButton = new JButton("Adicionar Livro");
        adicionarButton.setBackground(new Color(46, 204, 113));
        adicionarButton.setForeground(Color.WHITE);
        
        gbc.gridx = 1; gbc.gridy = 3; gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(adicionarButton, gbc);

        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = tituloField.getText();
                String autor = autorField.getText();
                
                // Verifica se o ano é um número válido
                try {
                    int ano = Integer.parseInt(anoField.getText());
                    adicionarLivro(titulo, autor, ano);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, insira um ano de publicação válido (número inteiro).");
                }
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton ordenarTituloButton = new JButton("Ordenar por Título");
        ordenarTituloButton.setBackground(new Color(52, 152, 219));
        ordenarTituloButton.setForeground(Color.WHITE);

        JButton ordenarAutorButton = new JButton("Ordenar por Autor");
        ordenarAutorButton.setBackground(new Color(155, 89, 182));
        ordenarAutorButton.setForeground(Color.WHITE);

        buttonsPanel.add(ordenarTituloButton);
        buttonsPanel.add(ordenarAutorButton);

        ordenarTituloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ordenarPorTitulo();
            }
        });

        ordenarAutorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ordenarPorAutor();
            }
        });

        textArea = new JTextArea(10, 40);
        textArea.setEditable(false);
        textArea.setFont(new Font("Serif", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(textArea);

        frame.add(formPanel, BorderLayout.NORTH);
        frame.add(buttonsPanel, BorderLayout.SOUTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Application app = new Application(100); // capacidade máxima de 100 livros
        app.criarInterface();
    }
}
