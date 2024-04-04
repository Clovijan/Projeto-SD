package src;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class InterfaceGrafica extends JFrame {

    private JTextField textField1;
    private JTextField textField2;
    private JComboBox<String> fileComboBox;

    public InterfaceGrafica() {
        setTitle("Interface Gráfica Java");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout
        setLayout(new BorderLayout());

        // Painel para o topo
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        // Caixa de seleção de arquivo
        fileComboBox = new JComboBox<>();
        fileComboBox.setPreferredSize(new Dimension(200, 30));
        topPanel.add(fileComboBox);
        fileComboBox.setEditable(false);

        // Botão para selecionar arquivo
        JButton selectButton = new JButton("Selecionar Arquivo");
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    fileComboBox.addItem(selectedFile.getAbsolutePath());
                }
            }
        });
        topPanel.add(selectButton);

        add(topPanel, BorderLayout.NORTH);

        // Painel para as caixas de texto
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new FlowLayout());

        JLabel label2 = new JLabel("Arquivos locais:");
        // Segunda caixa de texto
        textField1 = new JTextField();
        textField1.setPreferredSize(new Dimension(100, 50)); 
        textPanel.add(label2);
        textPanel.add(textField1);

        // Caixa de texto 2
        JLabel label3 = new JLabel("Arquivos compartilhados:");
        textField2 = new JTextField();
        textField2.setPreferredSize(new Dimension(100, 50)); 
        textPanel.add(label3);
        textPanel.add(textField2);

        add(textPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new InterfaceGrafica();
            }
        });
    }
}