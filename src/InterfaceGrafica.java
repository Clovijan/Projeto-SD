package src;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileSystemView;

public class InterfaceGrafica extends JFrame {

    private DefaultListModel<String> modelListaLocal;
    private DefaultListModel<String> modelListaRede;
    private DefaultListModel<String> modelListaSolicitados;

    public InterfaceGrafica() {
        super("Projeto de SD - UFSDrive");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        JPanel painelPrincipal = new JPanel(new GridLayout(1, 2));

        JPanel panelLocal = new JPanel(new BorderLayout());
        modelListaLocal = new DefaultListModel<>();
        JList<String> listaArquivosLocal = new JList<>(modelListaLocal);
        JScrollPane scrollPaneLocal = new JScrollPane(listaArquivosLocal);
        JButton botaoLocal = new JButton("Selecionar Pasta Local");

        panelLocal.add(new JLabel("Arquivos Locais", SwingConstants.CENTER), BorderLayout.NORTH);
        panelLocal.add(scrollPaneLocal, BorderLayout.CENTER);
        panelLocal.add(botaoLocal, BorderLayout.SOUTH);
        
        JPanel panelRede = new JPanel(new BorderLayout());
        modelListaRede = new DefaultListModel<>();
        JList<String> listaArquivosRede = new JList<>(modelListaRede);
        JScrollPane scrollPaneRede = new JScrollPane(listaArquivosRede);
        JButton botaoRede = new JButton("Listar Arquivos de Rede");
 
        JPanel panelArquivosRede = new JPanel(new BorderLayout());
        panelArquivosRede.add(new JLabel("Arquivos de Rede", SwingConstants.CENTER), BorderLayout.NORTH);
        panelArquivosRede.add(scrollPaneRede, BorderLayout.CENTER);

        panelRede.add(panelArquivosRede, BorderLayout.CENTER);

        panelRede.add(botaoRede, BorderLayout.SOUTH);
       
        JPanel panelSolicitados = new JPanel(new BorderLayout());
        modelListaSolicitados = new DefaultListModel<>();
        JList<String> listaArquivosSolicitados = new JList<>(modelListaSolicitados);
        JScrollPane scrollPaneSolicitados = new JScrollPane(listaArquivosSolicitados);

        panelSolicitados.add(new JLabel("Arquivos Solicitados", SwingConstants.CENTER), BorderLayout.NORTH);
        panelSolicitados.add(scrollPaneSolicitados, BorderLayout.CENTER);
        
        painelPrincipal.add(panelLocal);
        painelPrincipal.add(panelRede);
        
        botaoLocal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selecionarPastaLocal();
            }
        });
        
        listaArquivosRede.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedFile = listaArquivosRede.getSelectedValue();
                if (selectedFile != null) {
                    adicionarArquivoSolicitado(selectedFile);
                }
            }
        });

        listaArquivosSolicitados.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedFile = listaArquivosSolicitados.getSelectedValue();
                if (selectedFile != null) {
                    adicionarArquivoAprovado(selectedFile);
                }
            }
        });
     
        botaoRede.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarArquivosDeRede();
            }
        });
       
        add(painelPrincipal, BorderLayout.CENTER);
        add(panelSolicitados, BorderLayout.SOUTH);
       
        setLocationRelativeTo(null);
    }

    private void selecionarPastaLocal() {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            listarArquivosLocais(selectedFile);
        }
    }

    private void listarArquivosLocais(File diretorio) {
        modelListaLocal.clear();

        if (diretorio.exists() && diretorio.isDirectory()) {
            File[] arquivos = diretorio.listFiles();
            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    modelListaLocal.addElement(arquivo.getName());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Diretório local não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarArquivosDeRede() {
        modelListaRede.clear();
        
        try {
           
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void aprovarArquivosSolicitados(String pastaCompartilhada) {
        //Percorrer lista de arquivos solicitados adicionados em modelListaSolicitados linha 158
        try {
            File pastaArquivo = new File(pastaCompartilhada);

            if (pastaArquivo.exists() && pastaArquivo.isDirectory()) {
                File[] arquivos = pastaArquivo.listFiles();
                if (modelListaSolicitados != null) {
                    for (File arquivo : arquivos) {
                        modelListaLocal.addElement(" - " + arquivo.getName());
                    }
                }
            } else {
                System.out.println("Compartilhamento não encontrado ou não é um diretório válido.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void adicionarArquivoSolicitado(String nomeArquivo) {
        modelListaSolicitados.addElement(nomeArquivo);
    }

    private void adicionarArquivoAprovado(String nomeArquivo) {
        modelListaLocal.addElement(nomeArquivo);
    }
  
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                InterfaceGrafica interfaceGrafica = new InterfaceGrafica();
                interfaceGrafica.setVisible(true);
            }
        });
    }
}
