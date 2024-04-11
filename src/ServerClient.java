import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ServerClient {

    final static String SERVER_ADDRESS = "127.0.0.1";
    final static int SERVER_PORT = 6789;
    final static String DOWNLOAD_DIR = System.getProperty("user.dir") + "/src/FileList";
    private static final String FOLDERPATHMONITOR = System.getProperty("user.home") + "/OneDrive/Área de Trabalho/Compartilhamento_Arquivos/recebido";

    public void requestFileList(String nameFile) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            
            String fileName = nameFile +"\n";
            DataOutputStream outToServer =
					new DataOutputStream(socket.getOutputStream());
            outToServer.writeBytes(fileName);


            FileOutputStream fileOut = new FileOutputStream(DOWNLOAD_DIR + "/ListArquivo.ser");

            InputStream socketIn = socket.getInputStream();

            byte[] cbuffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = socketIn.read(cbuffer)) != -1) {
				fileOut.write(cbuffer, 0, bytesRead);
			}
			fileOut.close();

			socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void requestFile(String nameFile) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            
            String fileName = nameFile +"\n";
            DataOutputStream outToServer =
					new DataOutputStream(socket.getOutputStream());
            outToServer.writeBytes(fileName);


            FileOutputStream fileOut = new FileOutputStream(FOLDERPATHMONITOR + "/" + nameFile);

            InputStream socketIn = socket.getInputStream();

            byte[] cbuffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = socketIn.read(cbuffer)) != -1) {
				fileOut.write(cbuffer, 0, bytesRead);
			}
			fileOut.close();

			socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
