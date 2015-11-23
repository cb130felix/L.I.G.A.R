/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testes;

import java.net.SocketException;
import middleware.communication.ConnectionManager;

/**
 *
 * @author Renan
 */
public class Server2 {
    
    public static void main(String args[]) throws SocketException{
    
        ConnectionManager mc = new ConnectionManager();
        mc.startServerTCP(24246);
        System.out.println("escutando na porta... sei la");
        while(true){

            System.out.println("esperando");
            mc.listenerTCP();
            byte[] data = mc.getData();
            mc.sendData(data);
        }
        
    }
    
    
}
