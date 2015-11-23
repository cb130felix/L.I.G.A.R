/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testes;

import java.net.SocketException;
import middleware.Middleware;
import services.Detran;

/**
 *
 * @author Renan
 */
public class TestServer {
    
     public static void main(String[] args) throws SocketException {
        // TODO code application logic here
    
        Detran detran = new Detran();
        Middleware mid = new Middleware();
        mid.server.addService("detran", detran);
        mid.server.startServer();
        
        
    }

}
