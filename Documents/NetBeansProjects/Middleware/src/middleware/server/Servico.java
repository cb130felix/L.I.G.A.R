/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.server;

import middleware.server.ServiceProcess;

/**
 *
 * @author Guto Leoni
 */
public class Servico implements ServiceProcess {

    
    public byte[] process(byte[] data) {
        
        return "ola mundo".getBytes();
        
    
    }
    
}
