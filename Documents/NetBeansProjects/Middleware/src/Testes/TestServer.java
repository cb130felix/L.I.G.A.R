/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testes;

import java.net.SocketException;
import middleware.Middleware;
import middleware.server.Server;
import middleware.server.ServiceProcess;
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
                
        s.addService("detran", serv2);
        s.setPort(24245);
        s.startServer();
        
        
    }

}
