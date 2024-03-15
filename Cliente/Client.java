import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    public static void main(String[] args) {
        try {
            // Criar um socket para envio de broadcast
            DatagramSocket clientSocket = new DatagramSocket();
            clientSocket.setBroadcast(true);

            // Preparar a mensagem de solicitação
            String requestMessage = "LIST_FILES";
            byte[] requestData = requestMessage.getBytes();

            // Enviar a mensagem de broadcast para o servidor
            DatagramPacket requestPacket = new DatagramPacket(
                requestData,
                requestData.length,
                InetAddress.getByName("255.255.255.255"),
                8888
            );
            clientSocket.send(requestPacket);

            System.out.println("Solicitação de lista de arquivos enviada.");

            // Aguardar a resposta do servidor
            byte[] responseData = new byte[1024];
            DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length);
            clientSocket.receive(responsePacket);

            // Extrair e exibir a lista de arquivos recebida do servidor
            String fileList = new String(responsePacket.getData(), 0, responsePacket.getLength());
            System.out.println("Lista de arquivos recebida do servidor:");
            System.out.println(fileList);

            // Fechar o socket
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
