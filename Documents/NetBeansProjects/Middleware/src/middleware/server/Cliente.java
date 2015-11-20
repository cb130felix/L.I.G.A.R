/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.server;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import middleware.Address;
import middleware.ManagerConnection;

/**
 *
 * @author Guto Leoni
 */
public class Cliente {
    
        public static void main(String args[]){
        
            try {
                   ManagerConnection mc = new ManagerConnection(); //inst^ancia da classe
                   Address adress = new Address("localhost", 24251); //inst^ancia de um endereço do servidor

                   mc.connectionServer(adress); //conectando ao servidor do endereço definido anteriormente

                   String mensagem = "2||Detran||kcd-1232"; //mensagem a ser enviada

                   mc.sendData(mensagem.getBytes()); //enviando mensagem

                   //mc.getConnection().shutdownOutput();

                   byte[]dados = mc.getData();

                   try {
                       if(dados == null){System.out.println("eh, fudeu :v");}
                       String msg = new String(dados, "UTF-8");

                       System.out.println(msg);

                   } catch (UnsupportedEncodingException ex) {
                       Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                   }

                   mc.closeConnection();
        
            } catch (Exception e) {
                
                e.printStackTrace();
            }
            
            
        }
        
}
