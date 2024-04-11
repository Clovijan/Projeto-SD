public class ShareFiles {
    public static void main(String[] args) {
        try{
            FolderMonitor folderMonitor = new FolderMonitor();
            folderMonitor.start();
            System.out.println("MONITOR ATIVO...");
            Servidor serverListener = new Servidor();
            serverListener.start();
            System.out.println("SERVIDOR OUVINTE ATIVO...");
        }finally{

        }
    }
}
