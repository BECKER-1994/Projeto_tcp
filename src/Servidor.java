import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {
    private static final Logger LOGGER = Logger.getLogger("Servidor");
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        LOGGER.log(Level.INFO,  "Inicianado Servidor");
        ServerSocket servidor = new ServerSocket(40000);

        while (true) {
            LOGGER.log(Level.INFO, "Aguardando conexão");
            Socket conexao = servidor.accept();

            LOGGER.log(Level.INFO, "Criando os objetos de comunicação");
            ObjectOutputStream saida = new ObjectOutputStream(conexao.getOutputStream());
            ObjectInputStream entrada = new ObjectInputStream(conexao.getInputStream());


            LOGGER.log(Level.INFO, "Recebendo requisição");
            Requisicao requisicao = (Requisicao) entrada.readObject();

            LOGGER.log(Level.INFO, "Processando requisição");

            Resposta resposta = new Resposta();
            resposta.setHorario(LocalDateTime.now());

            switch (requisicao.getOperacao()) {
                case "+":
                    resposta.setResultado(requisicao.getX() + requisicao.getY());
                    resposta.setStatus(200);
                    resposta.setMenssagem("A operação de adção foi realizada com sucesso");
                    break;

                default:
                    resposta.setResultado(null);
                    resposta.setStatus(500);
                    resposta.setMenssagem("Operação inválida");

                case "-":
                    resposta.setResultado(requisicao.getX() - requisicao.getY());
                    resposta.setStatus(200);
                    resposta.setMenssagem("A operação de subtração foi realizada com sucesso");
                    break;
                case "*":
                    resposta.setResultado(requisicao.getX() * requisicao.getY());
                    resposta.setStatus(200);
                    resposta.setMenssagem("A operação de multiplicação foi realizada com sucesso");
                    break;
                case "/":
                    resposta.setResultado(requisicao.getX() / requisicao.getY());
                    resposta.setStatus(200);
                    resposta.setMenssagem("A operação de divisão foi realizada com sucesso");
                    break;

            }
            LOGGER.log(Level.INFO, "Enviando resposta");
            saida.writeObject(resposta);
        }
    }
}
