/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testes;

import java.net.SocketException;
import middleware.Middleware;

/**
 *
 * @author Renan
 */
public class TestProxy {
    
     public static void main(String[] args) throws SocketException, InterruptedException {
        // TODO code application logic here
    
        Middleware mid = new Middleware();
        
        mid.proxy.startProxy();

        
    }
    
    
}
