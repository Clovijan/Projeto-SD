package src;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class InterfaceGraficaRede extends JFrame {

    private JList<String> listaArquivos;
    private DefaultListModel<String> modelLista;

    public InterfaceGraficaRede() {
        super("Exemplo de Interface Gráfica com Arquivos de Rede");

        // Configurações básicas do frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Inicialização dos componentes
        modelLista = new DefaultListModel<>();
        listaArquivos = new JList<>(modelLista);
        JScrollPane scrollPane = new JScrollPane(listaArquivos);

        // Botão para listar arquivos locais
        JButton botaoLocal = new JButton("Listar Arquivos Locais");
        botaoLocal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarArquivosLocais();
            }
        });

        // Botão para listar arquivos de rede
        JButton botaoRede = new JButton("Listar Arquivos de Rede");
        botaoRede.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarArquivosDeRede();
            }
        });

        // Painel para os botões
        JPanel panelBotoes = new JPanel();
        panelBotoes.add(botaoLocal);
        panelBotoes.add(botaoRede);

        // Adicionando os componentes ao frame
        add(panelBotoes, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void listarArquivosLocais() {
        modelLista.clear();

        // Diretório a ser exibido
        File diretorio = new File("C:\\Users\\magda");

        // Verifica se o diretório existe
        if (diretorio.exists() && diretorio.isDirectory()) {
            // Lista os arquivos no diretório
            File[] arquivos = diretorio.listFiles();
            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    modelLista.addElement(arquivo.getName());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Diretório local não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarArquivosDeRede() {
        modelLista.clear();

        try {
            // Lista de computadores na rede
            InetAddress localhost = InetAddress.getLocalHost();
            byte[] ip = localhost.getAddress();
            for (int i = 1; i <= 254; i++) {
                ip[3] = (byte) i;
                InetAddress address = InetAddress.getByAddress(ip);
                if (address.isReachable(1000)) {
                    modelLista.addElement(address.getHostAddress());
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                InterfaceGraficaRede interfaceGrafica = new InterfaceGraficaRede();
                interfaceGrafica.setVisible(true);
            }
        });
    }
}
