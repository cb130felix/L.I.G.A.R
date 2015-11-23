/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testes;

import java.util.ArrayList;
import middleware.Middleware;
import middleware.client.DataHandler;

/**
 *
 * @author LIGAR
 */
public class TesClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    
        Middleware mid = new Middleware();
        DataHandler handler;
        handler = new DataHandler() {
            
            @Override
            public void handler(int id, String message) {
                System.out.println("recebeu mensagem!");
            }
        };
        mid.client.sendMessage("pej3163", "detran", handler);
        //teste
        
        
    }
    
}
