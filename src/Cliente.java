import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {
    private static final Logger LOGGER = Logger.getLogger("Cliente");
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Requisicao requisicao = new Requisicao();
        Scanner teclado = new Scanner(System.in);
        System.out.println("X: ");
        requisicao.setX(teclado.nextDouble());
        System.out.println("X: ");
        requisicao.setY(teclado.nextDouble());
        System.out.println("Operação: ");
        requisicao.setOperacao(teclado.next());

        LOGGER.log(Level.INFO, "Conectando ao servidor");
        Socket conexao = new Socket("127.0.0.1", 40000);

        LOGGER.log(Level.INFO, "Criando os objetos de comunicação");
        ObjectInputStream entrada = new ObjectInputStream(conexao.getInputStream());
        ObjectOutputStream saida = new ObjectOutputStream(conexao.getOutputStream());

        LOGGER.log(Level.INFO, "Enviando resposta");
        saida.writeObject(requisicao);

        LOGGER.log(Level.INFO, "Recebendo resposta");
        Resposta resposta = (Resposta) entrada.readObject();

        System.out.println("X: " + requisicao.getX());
        System.out.println(requisicao.getOperacao());
        System.out.println("Y: " + requisicao.getY());

        System.out.println(resposta.getResultado());
    }
}
