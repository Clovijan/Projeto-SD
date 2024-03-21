package Diretorios;
import java.util.ArrayList;
import java.util.List;

class Diretorio {
    private String nome;
    private List<Diretorio> subDiretorios;
    private List<Arquivo> arquivos;

    public Diretorio(String nome) {
        this.nome = nome;
        this.subDiretorios = new ArrayList<>();
        this.arquivos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public List<Diretorio> getSubDiretorios() {
        return subDiretorios;
    }

    public List<Arquivo> getArquivos() {
        return arquivos;
    }

    public void adicionarDiretorio(Diretorio diretorio) {
        subDiretorios.add(diretorio);
    }

    public void adicionarArquivo(Arquivo arquivo) {
        arquivos.add(arquivo);
    }
}