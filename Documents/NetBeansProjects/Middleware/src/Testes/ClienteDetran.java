/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testes;

import java.io.DataInputStream;
import java.io.UnsupportedEncodingException;
import static java.lang.Thread.sleep;
import java.net.Socket;
import middleware.Address;
import middleware.ManagerConnection;

/**
 *
 * @author Guto Leoni
 */
public class ClienteDetran {
 
    public static void main(String args[]){
    
        try {
                   ManagerConnection mc = new ManagerConnection(); //inst^ancia da classe
                   Address adress = new Address("localhost", 24251); //inst^ancia de um endereço do servidor

                   if(mc.connectionServer(adress)){
                   
                       System.out.println("Conectou com o servidor!");
                   } //conectando ao servidor do endereço definido anteriormente

                   String mensagem = "4||Detran||peu0189"; //mensagem a ser enviada
                   
                   System.out.println("Menagem: "+mensagem);
                   if(mc.sendData(mensagem.getBytes())){
                   
                       System.out.println("Mandei");
                   
                   } //enviando mensagem
                   
                   //mc.getConnection().shutdownInput();

                   /*Socket s = mc.getConnection();
                   DataInputStream dis = new DataInputStream(s.getInputStream());
                   int tamanho = dis.readInt();
                   byte[] dados = new byte[tamanho];
                   dis.read(dados, 0, tamanho);*/
                   
                  byte[]dados = mc.getData();

                   try {
                       String msg="mensagem de resposta vazia...";
                       if(dados != null){
                        msg = new String(dados, "UTF-8");
                       }
                       System.out.println("Mensagem recebida: "+msg);

                   } catch (UnsupportedEncodingException ex) {
                       System.out.println("Mensagem vazia...");
                   }

                   mc.closeConnection();
        
            } catch (Exception e) {
                
               e.printStackTrace();
            }
            
    
    
    }
    
}
