/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testes;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import middleware.server.ServiceProcess;

/**
 *
 * @author Guto Leoni
 */
public class Servico implements ServiceProcess {

    
    public byte[] process(byte[] data) {
        
        for (int i = 0; i < 10000000; i++) {
            try {
                sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Servico.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "Ola mundo".getBytes();
    
    }
    
}
