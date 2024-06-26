
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class InterfaceGrafica extends JFrame {

    private static DefaultListModel<String> modelListaLocal;
    private DefaultListModel<String> modelListaRede;
    private DefaultListModel<String> modelListaSolicitados;
    private Map<String, String> arquivosSolicitadosMap;
    private static final String FOLDERPATHMONITOR = System.getProperty("user.home") + "\\OneDrive\\Área de Trabalho\\Compartilhamento_Arquivos";
    private static final String FOLDERLISTFILE = System.getProperty("user.dir") + "/src/FileList/ListArquivo.ser";

    public InterfaceGrafica() {
        super("Projeto de SD - UFSDrive");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Inicializa o mapa de arquivos solicitados
        arquivosSolicitadosMap = new HashMap<>();

        JPanel painelPrincipal = new JPanel(new GridLayout(1, 2));

        JPanel panelLocal = new JPanel(new BorderLayout());
        modelListaLocal = new DefaultListModel<>();
        JList<String> listaArquivosLocal = new JList<>(modelListaLocal);
        JScrollPane scrollPaneLocal = new JScrollPane(listaArquivosLocal);
        JButton botaoLocal = new JButton("Reload Pasta Arquivos");

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

        panelSolicitados.add(new JLabel("Arquivos Desejados", SwingConstants.CENTER), BorderLayout.NORTH);
        panelSolicitados.add(scrollPaneSolicitados, BorderLayout.CENTER);
        
        painelPrincipal.add(panelLocal);
        painelPrincipal.add(panelRede);

        botaoLocal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarArquivosLocais(FOLDERPATHMONITOR);
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

        // Adicionar ação ao selecionar um arquivo solicitado
        listaArquivosSolicitados.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedFile = listaArquivosSolicitados.getSelectedValue();
                if (selectedFile != null) {
                    liberarArquivoSolicitado(selectedFile);
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

    private static void listarArquivosLocais(String url) {
        modelListaLocal.clear();
        File diretorio = new File(url);
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
            ServerClient client = new ServerClient();
            String listaArquivo = "thisList.ser";
            client.requestFileList(listaArquivo);
            List<Arquivo> arquivosRede = loadData(FOLDERLISTFILE);
            for (Arquivo arquivo : arquivosRede) {
                if(!arquivo.getName().equals("recebido"))
                    modelListaRede.addElement(arquivo.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
	@SuppressWarnings("unchecked")
	private static ArrayList<Arquivo> loadData(String path) throws IOException{
        ArrayList<Arquivo> listFile = new ArrayList<Arquivo>();
        Path filePath = Paths.get(path);
        if(Files.exists(filePath) && Files.size(filePath) > 0){
            try{
                ObjectInputStream arquivo = new ObjectInputStream(new FileInputStream(filePath.toFile()));
                listFile = (ArrayList<Arquivo>) arquivo.readObject();
                arquivo.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            listFile = new ArrayList<Arquivo>();
        }

        return listFile;
    }

    private void adicionarArquivoSolicitado(String nomeArquivo) {
        String pathArquivo = "C:\\Users\\Magda\\Projeto-SD" + nomeArquivo; // Substituir pelo caminho real do arquivo da rede
        arquivosSolicitadosMap.put(nomeArquivo, pathArquivo);
        modelListaSolicitados.addElement(nomeArquivo);
    }

    //Para liberar arquivo, basta clicar no mesmo
    private void liberarArquivoSolicitado(String nomeArquivo) {
        int opcao = JOptionPane.showConfirmDialog(null, "Deseja ter acesso ao arquivo '" + nomeArquivo + "' para download?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (opcao == JOptionPane.YES_OPTION) {
            //antes de liberar o arquivo, tem que verificar se é o proprietário do mesmo. Falta implementar isso
            String pathArquivo = arquivosSolicitadosMap.get(nomeArquivo);
            ServerClient client = new ServerClient();
            System.out.println(nomeArquivo);
            client.requestFile(nomeArquivo);

            if (pathArquivo != null) {
                salvarArquivoLocal(pathArquivo);
                arquivosSolicitadosMap.remove(nomeArquivo);
                modelListaSolicitados.removeElement(nomeArquivo);
                JOptionPane.showMessageDialog(null, "Arquivo '" + nomeArquivo + "' baixado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao baixar o arquivo '" + nomeArquivo + "'.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void salvarArquivoLocal(String pathArquivo) {
        // Implementar o código para realizar download do arquivo localmente
        System.out.println("Arquivo baixado em: " + pathArquivo);
    }

  
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FolderMonitor folder = new FolderMonitor();
                folder.start();
                
                InterfaceGrafica interfaceGrafica = new InterfaceGrafica();
                interfaceGrafica.setVisible(true);
                listarArquivosLocais(FOLDERPATHMONITOR);

                Servidor serverListener = new Servidor();
                serverListener.start();
                
            }
        });
    }
}