package Diretorios;

public class Explorer {
    public static void main(String[] args) {
        // Criando diretórios e arquivos
        Diretorio raiz = new Diretorio("Raiz");
        
        Diretorio pasta1 = new Diretorio("Pasta1");
        Diretorio pasta2 = new Diretorio("Pasta2");
        
        Arquivo arquivo1 = new Arquivo("Arquivo1.txt");
        Arquivo arquivo2 = new Arquivo("Arquivo2.txt");
        Arquivo arquivo3 = new Arquivo("Arquivo3.txt");
        
        // Adicionando arquivos e subdiretórios
        raiz.adicionarDiretorio(pasta1);
        raiz.adicionarDiretorio(pasta2);
        raiz.adicionarArquivo(arquivo1);
        
        pasta1.adicionarArquivo(arquivo2);
        pasta2.adicionarArquivo(arquivo3);
        
        // Exibindo estrutura de diretórios e arquivos
        exibirDiretorio(raiz, "");
    }

    public static void exibirDiretorio(Diretorio diretorio, String prefixo) {
        System.out.println(prefixo + diretorio.getNome() + "/");
        prefixo += "  ";
        for (Arquivo arquivo : diretorio.getArquivos()) {
            System.out.println(prefixo + arquivo.getNome());
        }
        for (Diretorio subDir : diretorio.getSubDiretorios()) {
            exibirDiretorio(subDir, prefixo);
        }
    }
}