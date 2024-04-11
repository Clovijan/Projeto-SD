import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Iterator;

public class FolderMonitor extends Thread{

    private static final String FOLDERPATHMONITOR = System.getProperty("user.home") + "/OneDrive/Área de Trabalho/Compartilhamento_Arquivos";
    private static final String FOLDERLISTFILE = System.getProperty("user.dir") + "/src/FileList";
    private static final String LISTFILE = FOLDERLISTFILE + "/thisList.ser";

    public void run(){
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path directory = configInitial();
            System.out.println("SISTEMA CONFIGURADO ...");

            if(Files.exists(directory)){
                directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY );
                while(true){
                    System.out.println("MONITORAMENTO EM ANDAMENTO...");
                    WatchKey key;
                    try {
                        key = watchService.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }

                    for(WatchEvent<?> event : key.pollEvents()){
                        WatchEvent.Kind<?> kind = event.kind();
    
                        if(kind == StandardWatchEventKinds.ENTRY_CREATE || 
                            kind == StandardWatchEventKinds.ENTRY_DELETE){
    
                            WatchEvent<Path> ev = (WatchEvent<Path>) event;
                            Path fileName = ev.context();
                            System.out.println(kind + ": " + fileName);

                            updateThisListFile(kind, fileName);
                        } 
                    }
                    boolean valid = key.reset();
                    if(!valid){
                        break;
                    }
                    System.out.println("---------------------------------------------------");
                } 
            }else{
                System.out.println("ENTRE EM CONTATO COM O ADMNISTRADOR DO SISTEMA!");
            }   
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    private static boolean updateThisListFile(WatchEvent.Kind<?> kind, Path fileName ) throws IOException{     
        if(kind == StandardWatchEventKinds.ENTRY_CREATE){
            ArrayList<Arquivo> listFile = loadData();
            Arquivo addFile = new Arquivo(fileName);
            listFile.add(addFile);
            saveData(listFile);
            showFileList();
            System.out.println("Arquivo adicionado a lista!");
        }
        if(kind == StandardWatchEventKinds.ENTRY_DELETE){
            ArrayList<Arquivo> listFile = loadData();
            Iterator<Arquivo> iterator = listFile.iterator();
            while (iterator.hasNext()) {
                Arquivo arquivo = iterator.next();
                if (arquivo.getName().equals(fileName.toString())) {
                    iterator.remove(); // Remove o arquivo usando o iterador
                    saveData(listFile);
                    System.out.println("Arquivo removido da lista!");
                }
            }
        } 
        return true;
    }

    private static void saveData(ArrayList<Arquivo> listFile){
        Path filePath = Paths.get(LISTFILE);
        try(ObjectOutputStream arquivo = new ObjectOutputStream(new FileOutputStream(filePath.toFile()))){
           arquivo.writeObject(listFile);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("ERRO AO SALVAR O ARQUIVO");
        }
    }

    @SuppressWarnings("unchecked")
    private static ArrayList<Arquivo> loadData() throws IOException{
        ArrayList<Arquivo> listFile = new ArrayList<Arquivo>();
        Path filePath = Paths.get(LISTFILE);
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

    private static Path configInitial() throws IOException{
        
        Path directory = Paths.get(FOLDERPATHMONITOR);
        Path directoryList = Paths.get(FOLDERLISTFILE);
        Path directList = Paths.get(LISTFILE);
        Path directoryRec = Paths.get(FOLDERPATHMONITOR+"/recebido");

        if(!Files.exists(directoryList)){
            try{
                Files.createDirectories(directoryList);
                System.out.println("Pasta Compartilhada criada com Sucesso!");
            }catch(Exception e){
                e.printStackTrace();
                System.out.println("Erro ao criar pasta compartilhada!");
            }  
        }
        if(!Files.exists(directory)){
            try{
                Files.createDirectories(directory);
                System.out.println("Pasta da Lista de arquivos criada com Sucesso!");
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }
        if(!Files.exists(directoryRec)){
            try{
                Files.createDirectories(directoryRec);
                System.out.println("Pasta de Recebido criada com Sucesso!");
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }
        if(!Files.exists(directList)){
            try{
                Files.createFile(directList);
                System.out.println("Lista de arquivos criada com sucesso!");
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }
        return directory;
    }

    private static void showFileList() throws IOException {
        ArrayList<Arquivo> listFile = loadData();
        if (listFile.isEmpty()) {
            System.out.println("A lista de arquivos está vazia.");
        } else {
            System.out.println("Lista de Arquivos:");
            for (Arquivo arquivo : listFile) {
                System.out.println("Nome: " + arquivo.getName());
                System.out.println("Clientes autorizados:");
                for (ClientData client : arquivo.getListAuthorized()) {
                    System.out.println("- " + client.getEndereco() + " (Autorizado: " + client.isAutorizado() + ")");
                }
                System.out.println();
            }
        }
    }
}