import java.io.Serializable;

public class ClientData implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private String Endereco;
    private boolean autorizado;
    
    public String getEndereco() {
        return Endereco;
    }
    public void setEndereco(String endereco) {
        Endereco = endereco;
    }
    public boolean isAutorizado() {
        return autorizado;
    }
    public void setAutorizado(boolean autorizado) {
        this.autorizado = autorizado;
    }    
}
