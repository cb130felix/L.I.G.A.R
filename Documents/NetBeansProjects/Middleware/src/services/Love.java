/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import Testes.Pergunta;
import Testes.Resposta;
import middleware.server.ServiceProcess;

/**
 *
 * @author iagorichard
 */
public class Love implements ServiceProcess{

    @Override
    public Object process(Object o) {
        Pergunta p = (Pergunta) o;
        
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }
        
        return new Resposta("s2");
        
    }
    
}
