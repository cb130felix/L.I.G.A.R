/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testes;

import java.net.SocketException;
import java.util.ArrayList;
import middleware.Middleware;
import middleware.client.DataHandler;

/**
 *
 * @author LIGAR
 */
public class TestClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException, InterruptedException {
        // TODO code application logic here
    
        Middleware mid = new Middleware();
        DataHandler handler;
        handler = new DataHandler() {
            
            @Override
            public void handler(int id, String message) {
                System.out.println(message);
            }
        };
        
//        while(true){

            mid.client.sendMessage("pej3163", "detran", handler);
            Thread.sleep(1000);
        
//        }
        
    }
    
}
