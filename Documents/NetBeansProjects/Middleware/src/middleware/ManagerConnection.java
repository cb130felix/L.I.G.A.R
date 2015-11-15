/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware;

import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Iago
 */
public class ManagerConnection {

    private ServerSocket serverSocket;
    private Socket connection;
    private DatagramSocket broadcast;

    //manda mensagem em broadcast
    public boolean broadcast() {
        return true;
    }

    //Se conecta a uma máquina de acordo com o endereço passado
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
    public boolean listener(int port) {
        return true;
    }

    //método para envio e recebimento de dados
    public boolean send(byte[] data) {
        return true;
    }

    public byte[] receive() {
        return null;
    }

    //checa se há conexão com a internet
    public boolean checkConnection() {
        return true;
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
