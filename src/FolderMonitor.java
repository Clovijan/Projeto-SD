import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class FolderMonitor {

    public static void main(String[] args) {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();

            // DIRETÓRIO A SER MONITORADO
            Path directory = Paths.get("C:/Users/clovi/OneDrive/Área de Trabalho/FileServer");

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
                        kind == StandardWatchEventKinds.ENTRY_DELETE||
                        kind == StandardWatchEventKinds.ENTRY_MODIFY){

                        WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        Path fileName = ev.context();
                        System.out.println(kind + ": " + fileName);
                    } 

                }
                boolean valid = key.reset();
                if(!valid){
                    break;
                }
                System.out.println("---------------------------------------------------");
            } 
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}