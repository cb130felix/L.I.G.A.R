/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testes;

import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import middleware.Address;
import middleware.communication.ConnectionManager;

/**
 *
 * @author Renan
 */
public class Client2 {
 
    public static void main(String args[]) throws SocketException, UnsupportedEncodingException{
     
        Address ad = new Address("127.0.0.1", 24246);
        ConnectionManager mc = new ConnectionManager();
        try{
            mc.connectionServer(ad);
            System.out.println("1");

            mc.sendData("teste".getBytes());
            System.out.println("2");

            byte[] data2 = mc.getData();
            System.out.println("3");

            String resultado = new String(data2, "UTF-8");
            System.out.println("4");

            mc.closeConnection();
            System.out.println("5");

            System.out.println("resultado:" + resultado);
        
        }catch(Exception ex){
            System.out.println("deu ruim");
        }
        
        
        
        
        
    
    }
    
    
}
