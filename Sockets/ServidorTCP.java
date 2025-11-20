import java.io.*;
import java.net.*;

public class ServidorTCP {
    private static final int PORTA = 12345;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORTA)) {
            System.out.println("Servidor TCP iniciado e escutando na porta " + PORTA);

            
            while (true) {
                
                Socket clientSocket = serverSocket.accept(); 
                System.out.println("Novo cliente conectado: " + clientSocket.getInetAddress().getHostAddress());
                
                
                new ClientHandler(clientSocket).start(); 
            }
        } catch (IOException e) {
            System.err.println("Erro ao iniciar ou executar o Servidor TCP: " + e.getMessage());
        }
    }
}


class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try (
           
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true); 
        ) {
            output.println("Bem-vindo ao Servidor TCP! Envie uma mensagem.");

            String clientMessage;
            
            while ((clientMessage = input.readLine()) != null) {
                System.out.println(Thread.currentThread().getName() + " recebeu: " + clientMessage);
                
                if ("sair".equalsIgnoreCase(clientMessage)) {
                    output.println("Conexão encerrada. Adeus!");
                    break;
                }

                
                output.println("Servidor Eco: " + clientMessage.toUpperCase());
            }
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName() + " (Erro de I/O ou conexão fechada): " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                System.out.println(Thread.currentThread().getName() + " fechou a conexão.");
            } catch (IOException e) {
                
            }
        }
    }
}