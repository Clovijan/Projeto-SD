import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Servidor extends Thread{
    private static final int PORT = 6789;
    private static final String FOLDERPATHMONITOR = System.getProperty("user.home") + "/OneDrive/Área de Trabalho/Compartilhamento_Arquivos";
    private static final String FOLDERLISTFILE = System.getProperty("user.dir") + "/src/FileList";
    private static final String LISTFILE = FOLDERLISTFILE + "/thisList.ser";

    public void run(){
        try{
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Servidor Aguardando conexões...");

           while(true){
                Socket clienSocket = server.accept();
                System.out.println("Conexão estabelecida com" + clienSocket.getInetAddress());
                
                BufferedReader inFromClient  = new BufferedReader(new InputStreamReader(clienSocket.getInputStream()));
                String request = inFromClient.readLine();

                if(request.equals("thisList.ser")){
                    Path filePath = Paths.get(LISTFILE);
                    if(Files.exists(filePath)){
                       FileInputStream fileIn = new FileInputStream(LISTFILE);
                       OutputStream socketOut = clienSocket.getOutputStream();
                       byte[] cbuffer = new byte[1024];
                       int bytesRead;
                       while ((bytesRead = fileIn.read(cbuffer)) != -1) {
                        socketOut.write(cbuffer, 0, bytesRead);
                        socketOut.flush();
                        }
                        fileIn.close();
                        clienSocket.close();
                    }
                }else{
                    Path filePath = Paths.get(FOLDERPATHMONITOR + "/" + request);
                    if(Files.exists(filePath)){
                        FileInputStream fileIn = new FileInputStream(FOLDERPATHMONITOR + "/" + request);
                        OutputStream socketOut = clienSocket.getOutputStream();
                        byte[] cbuffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = fileIn.read(cbuffer)) != -1) {
                         socketOut.write(cbuffer, 0, bytesRead);
                         socketOut.flush();
                         }
                         fileIn.close();
                         clienSocket.close(); 
                    }
                }
           }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
