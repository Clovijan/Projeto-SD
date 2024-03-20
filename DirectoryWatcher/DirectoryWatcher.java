package DirectoryWatcher;
import static java.nio.file.StandardWatchEventKinds.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class DirectoryWatcher {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Diretório que será monitorado
        Path directory = Paths.get("C:/Users/Magda");

        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            directory.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

            System.out.println("Assistindo diretório: " + directory);

            while (true) {
                WatchKey key;
                try {
                    key = watchService.take();
                } catch (InterruptedException ex) {
                    break;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    if (kind == OVERFLOW) {
                        continue;
                    }

                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path filename = ev.context();
                    Path child = directory.resolve(filename);

                    if (kind == ENTRY_CREATE) {
                        System.out.println(child + " criado.");
                    } else if (kind == ENTRY_DELETE) {
                        System.out.println(child + " deletado.");
                    } else if (kind == ENTRY_MODIFY) {
                        System.out.println(child + " modificado.");
                    }
                }

                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
        } catch (IOException ex) {
            System.err.println("IOException: " + ex.getMessage());
        }
    }
}

