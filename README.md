# Servidor de Compartilhamento de Arquivos em Java

Este é um projeto de servidor de compartilhamento de arquivos desenvolvido em Java. O servidor permite que os usuários compartilhem e acessem arquivos de forma simples e segura em uma rede local.

## Funcionalidades

- **Compartilhamento de Arquivos**: Os usuários podem compartilhar arquivos em um diretório específico do servidor.
- **Download de Arquivos**: Outros usuários podem fazer o download dos arquivos compartilhados.
- **Segurança**: O servidor é projetado para garantir a segurança dos arquivos compartilhados, implementando autenticação e controle de acesso.

## Requisitos

- **Java**: O servidor é desenvolvido em Java, portanto, é necessário ter o ambiente Java instalado na máquina onde o servidor será executado.

## Como Usar

1. **Clonar o Repositório**: Clone este repositório em sua máquina local.

2. **Configurar o Servidor**: Edite as configurações do servidor conforme necessário, incluindo o diretório onde os arquivos serão armazenados e as opções de segurança.

3. **Compilar o Código**: Compile o código-fonte do servidor.

4. **Executar o Servidor**: Execute o servidor.
5.  
6. **Conectar ao Servidor**: Os clientes podem se conectar ao servidor utilizando o endereço IP e a porta especificados.

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests para melhorias, correções de bugs ou novas funcionalidades.

## Desenvolvimento (Passo a Passo)

1. Criar um método que monitora uma pasta de compartilhada de arquivos;
2. Criar um servidor que envie na rede todas as mudanças desta pasta;
3. Criar um Método que receba esta mensagem a guarde em array;
4. Criar um método que envia resposta para o servidor dizendo se quer ou não o arquivo;
5. Criar um método que envie este arquivo para o outro servidor;

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).

---

*Este README é apenas um exemplo. Sinta-se à vontade para modificá-lo conforme necessário para o seu projeto.*

## Requisitos
1. Servidor arguandando Solicitação.
2. Criar uma lista com o endereço de cada pc e os arquivos q ele possui.
3. Caso o clinte queira o arquivo ele vai pedir permissão para o endereço que possui o arquivo caso ele libere já envia o arquivo junto.



