/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.server;

import middleware.server.Server;

/**
 *
 * @author Guto Leoni
 */
public class Servidor {
    
   public static void main(String args[]){
   
   
        Server s = new Server();
        Servico serv = new Servico();
        
        s.addService("Detran", serv);
        s.startServer();
   
   
   }
    
    
}
