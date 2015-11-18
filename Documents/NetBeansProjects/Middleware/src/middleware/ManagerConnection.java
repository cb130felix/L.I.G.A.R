package middleware;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

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
     * @return boolean - Retorna 'true' se enviar o pacote com sucesso, retorna
     * 'false' se der algum erro.
     */
    public boolean broadcast(byte[] data, int port) {

        try {
            this.broadcast = new DatagramSocket();

            InetAddress ina = InetAddress.getByName("255.255.255.255");
            DatagramPacket udpPacket = new DatagramPacket(data, data.length, ina, port);

            this.broadcast.send(udpPacket);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método para um cliente se conectar a um servidor via TCP.
     *
     * @param serverAdress - Contém as informações do servidor que irá ser
     * contactado: IP e Porta.
     * @return boolean - Retorna 'true' se conseguir conectar-se ao servidor,
     * retorna 'false' se der algum erro.
     */
    public boolean connectionServer(Address serverAdress) {

        try {
            this.connection = new Socket(serverAdress.getIp(), serverAdress.getPort());

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método que faz com que um lado da comunicação fique escutando pacotes via
     * UDP em uma porta.
     *
     * @param port - Porta que ficará escutando.
     * @return DatagramPacket - O pacote recebido será o retorno, se der erro
     * irá retornar a 'null'.
     */
    public DatagramPacket listenerUDP(int port) {

        try {
            this.broadcast = new DatagramSocket(port);
            byte receivedData[] = new byte[1024];

            DatagramPacket buffer = new DatagramPacket(receivedData, receivedData.length);
            this.broadcast.receive(buffer);

            return buffer;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Método que vai fazer com que o servidor espere algum cliente se conectar
     * via TCP.
     *
     * @return boolean - Se não ocorrer falhas, retorna a 'true', caso contrário
     * retorna a 'false'.
     */
    public boolean listenerTCP(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            this.connection = this.serverSocket.accept();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método responsável pelo envio de dados via TCP.
     *
     * @param data - Dados a serem enviados.
     * @return boolean - Se não ocorrer falhas, retorna a 'true', caso contrário
     * retorna a 'false'.
     */
    public boolean sendData(byte[] data) {

        try {
            DataOutputStream dos = new DataOutputStream(this.connection.getOutputStream());
            dos.writeInt(data.length);
            dos.write(data);
            dos.close();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método responsável pelo recebimento de mensagens via TCP.
     *
     * Observação importante: para converter a saída dos bytes recebidos para
     * String de volta, é só seguir este modelo de código abaixo: String
     * resultado = new String(arrayDeBytes, "UTF-8");
     *
     * @return byte[] - Os dados recebidos pela rede serão o retorno do método,
     * se der erro irá retornar a 'null'.
     */
    public byte[] getData() {

        try {
            DataInputStream dis = new DataInputStream(this.connection.getInputStream());
            int dataLength = dis.readInt();
            byte[] data = new byte[dataLength];
            dis.read(data, 0, dataLength);
            dis.close();

            return data;
        } catch (Exception e) {
            return null;
        }
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

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método que encerra a conexão TCP entre um cliente e servidor.
     *
     * @return boolean - Retorna 'true' se encerrar, se não encerrar retorna
     * 'false'.
     */
    public boolean closeConnection() {
        try {
            this.connection.close();
            return true;
        } catch (Exception e) {
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
