/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testes;

import middleware.server.Server;

/**
 *
 * @author Guto Leoni
 */
public class Servidor {
    
    public static void main(String args[]){
    
        
        Servico serv = new Servico();
        Server s = new Server();
        
        Detran serv2 = new Detran();

        
        s.addService("outro", serv);
        s.addService("Detran", serv2);
        s.startServer();
    
    }
    
    
}
