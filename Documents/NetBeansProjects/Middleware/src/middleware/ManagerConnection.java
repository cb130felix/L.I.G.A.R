package middleware;

import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Classe responsável por realizar todo o controle de conexões.
 *
 * @author Iago
 */
public class ManagerConnection {

    /**
     * Servidor TCP.
     */
    private ServerSocket serverSocket;

    /**
     * Conexão TCP do cliente para o servidor.
     */
    private Socket connection;

    /**
     * Pacote UDP a ser enviado via broadcast.
     */
    private DatagramSocket broadcast;

    /**
     * Método para envio de pacote UDP via broadcast.
     *
     * @param data - Mensagem a ser enviada no pacote.
     * @param port - Porta de destino do pacote.
     * @return boolean - Tratamento de possíveis erros.
     */
    public boolean broadcast(byte[] data, int port) {

        try {
            this.broadcast = new DatagramSocket();

            InetAddress ina = InetAddress.getByName("255.255.255.255"); //InetAdress para o broadcast.
            DatagramPacket udpPacket = new DatagramPacket(data, data.length, ina, port);

            this.broadcast.send(udpPacket);

            return true;
        } catch (Exception e) {
            System.out.println("Erro ao tentar enviar um pacote em broadcast.");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Método para conexão a um servidor via TCP.
     *
     * @param serverAdress - Contém as informações do servidor que irá ser
     * contactado: IP e Porta.
     * @return boolean - Tratamento de possíveis erros.
     */
    public boolean connectionServer(Adress serverAdress) {

        try {
            this.connection = new Socket(serverAdress.getIp(), serverAdress.getPort());

            return true;
        } catch (Exception e) {
            System.out.println("Não foi possível estabelecer uma conexão com o servidor.");
            System.out.println("Erro:");
            e.printStackTrace();

            return false;
        }
    }

    //Esse método fica escutando mensagens na porta informada e atualiza
    //o socket dessa classe de acordo com o endereço da máquina que fez a requisição
    /**
     * Método que faz com que um lado da comunicação fique escutando mensagens
     * em uma porta
     *
     * @param port - Porta que ficará escutando.
     * @return boolean - Tratamento de possíveis erros.
     */
    public DatagramPacket listenerUDP(int port) {

        try {
            this.broadcast = new DatagramSocket(port);
            byte receivedData[] = new byte[1024];

            DatagramPacket buffer = new DatagramPacket(receivedData, receivedData.length);
            this.broadcast.receive(buffer);
            
            return buffer;
        } catch (Exception e) {
            System.out.println("Erro no listener.");
            e.printStackTrace();
            
            return null;
        }
    }

    /**
     * Método responsável pelo envio de mensagens.
     *
     * @param data - Dados a serem enviados.
     * @return boolean - Tratamento de possíveis erros.
     */
    public boolean send(byte[] data) {

        try {
            OutputStream os = this.connection.getOutputStream();
            os.write(data);
            
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao tentar enviar os dados para o servidor.");
            return false;
        }
    }

    /**
     * Método responsável pelo recebimento de mensagens.
     *
     * @return byte[] - Os dados recebidos pela rede serão o retorno do método.
     */
    public byte[] receive() {

        try {
            Scanner s = new Scanner(this.connection.getInputStream());
            byte[] data = s.next().getBytes();

            return data;
        } catch (Exception e) {
            System.out.println("Erro ao tentar receber os dados.");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Checa se há conexão com a Internet.
     *
     * @return boolean - Se há conexão com a Internet o retorno será 'true',
     * caso contrário será 'false'.
     */
    public boolean checkConnection() {
        try {
            URL url = new URL("http://www.google.com/");
            URLConnection connection = url.openConnection();
            connection.connect();
            System.out.println("Conectado");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Sem conexão com a internet.");

            return false;
        }
    }
    

}

// Fluxo de uma aplicação com essa classe:
/*

Cliente:
ManagerConnection mc = new ManagerConnection();
byte[] data;
mc.connection(endereco);
mc.send(data);
mc.listen();
data = mc.receive();

Server:
ManagerConnection mc = new ManagerConnection();
byte[] data;
mc.listen();
data = mc.receive();
*processa dadados*
mc.send(data);

 */
