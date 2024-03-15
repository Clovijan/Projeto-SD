import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 8888;
    private static List<InetAddress> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(PORT);
            serverSocket.setBroadcast(true);

            System.out.println("Servidor aguardando solicitações...");

            while (true) {
                byte[] requestData = new byte[1024];
                DatagramPacket requestPacket = new DatagramPacket(requestData, requestData.length);
                serverSocket.receive(requestPacket);

                String requestMessage = new String(requestPacket.getData(), 0, requestPacket.getLength());
                System.out.println("Mensagem recebida do cliente: " + requestMessage);

                if (requestMessage.equals("LIST_FILES")) {
                    clients.add(requestPacket.getAddress());

                    // Envia a lista de arquivos de todos os clientes conectados
                    String fileList = getAllClientsFiles();
                    byte[] responseData = fileList.getBytes();

                    for (InetAddress client : clients) {
                        DatagramPacket responsePacket = new DatagramPacket(
                                responseData,
                                responseData.length,
                                client,
                                PORT
                        );
                        serverSocket.send(responsePacket);
                    }

                    System.out.println("Lista de arquivos enviada a todos os clientes.");
                }
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getAllClientsFiles() {
        StringBuilder fileListBuilder = new StringBuilder();

        // Obtém a lista de arquivos de cada cliente
        for (InetAddress client : clients) {
            fileListBuilder.append("Arquivos do cliente ").append(client.toString()).append(":\n");
            File folder = new File("/caminho/do/diretorio/" + client.getHostAddress());
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        fileListBuilder.append(file.getName()).append("\n");
                    }
                }
            }
            fileListBuilder.append("\n");
        }

        return fileListBuilder.toString();
    }
}

