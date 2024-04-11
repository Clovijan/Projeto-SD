import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;

public class Arquivo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name; // Agora é uma String
    private ArrayList<ClientData> listAuthorized;

    public Arquivo(Path name) {
        this.name = name.toString(); // Convertendo o caminho para uma String
        this.listAuthorized = new ArrayList<ClientData>();
    }

    public String getName() {
        return name;
    }

    public void setName(Path name) {
        this.name = name.toString(); // Convertendo o caminho para uma String
    }

    public ArrayList<ClientData> getListAuthorized() {
        return listAuthorized;
    }

    public void setListAuthorized(ClientData client) {
        listAuthorized.add(client);
    }

    public void setListAuthorized(ArrayList<ClientData> listAuthorized) {
        this.listAuthorized = listAuthorized;
    }
}