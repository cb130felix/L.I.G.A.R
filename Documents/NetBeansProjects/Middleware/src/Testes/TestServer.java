/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testes;

import java.net.SocketException;
import middleware.server.Server;
import services.Detran;

/**
 *
 * @author Renan
 */
public class TestServer {
    
     public static void main(String[] args) throws SocketException {
        // TODO code application logic here
    
        Server s = new Server();
        
        Detran serv2 = new Detran();
                
        s.addService("detran", serv2,Pergunta.class);
        s.setPort(24246);
        s.startServer();
        
        
    }

}
