/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testes;

import java.net.SocketException;
import middleware.server.Server;
import services.Detran;
import services.Love;

/**
 *
 * @author iagorichard
 */
public class TestServer2 {
    
      public static void main(String[] args) {
        // TODO code application logic here
    
        Server s = new Server(5,true);
        
        Love serv = new Love();
                
        s.addService("love", serv, Pergunta.class);
        s.setPort(24247);
        s.startServer();
        
        
    }
    
    
}
