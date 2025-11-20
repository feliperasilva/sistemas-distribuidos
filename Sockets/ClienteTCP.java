import java.io.*;
import java.net.*;

public class ClienteTCP {
    private static final String ENDERECO_SERVIDOR = "localhost";
    private static final int PORTA = 12345;

    public static void main(String[] args) {
        try (
            
            Socket socket = new Socket(ENDERECO_SERVIDOR, PORTA);
            
            BufferedReader inputServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outputServidor = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader inputConsole = new BufferedReader(new InputStreamReader(System.in));
        ) {
            System.out.println("Conectado ao servidor TCP em " + ENDERECO_SERVIDOR + ":" + PORTA);

            String respostaInicial = inputServidor.readLine();
            System.out.println("Servidor: " + respostaInicial);

            String mensagem;
            while (true) {
                System.out.print("Digite uma mensagem (ou 'sair'): ");
                mensagem = inputConsole.readLine();

                if (mensagem == null || mensagem.trim().isEmpty()) continue;


                outputServidor.println(mensagem);

                String respostaServidor = inputServidor.readLine();
                System.out.println("Servidor: " + respostaServidor);

                if ("sair".equalsIgnoreCase(mensagem)) {
                    break;
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("Host desconhecido: " + ENDERECO_SERVIDOR);
        } catch (IOException e) {
            System.err.println("Erro de I/O na conexão TCP: " + e.getMessage());
        }
    }
}