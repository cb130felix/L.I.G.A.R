/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testes;

import java.net.SocketException;
import java.util.ArrayList;
import middleware.Middleware;
import middleware.client.DataHandler;

/**
 *
 * @author LIGAR
 */
public class TestClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException, InterruptedException {
    
        Middleware mid = new Middleware();

        //Handler do client
        DataHandler handler;
        handler = new DataHandler() {
            
            @Override
            public void handler(int id, Object o) {
                
                Resposta r = (Resposta) o;
                System.out.println("["+id+"]" + "Consulta: " + r.consulta);
                
            }
        };
        
        mid.client.startClient();

        mid.client.sendMessage(new Pergunta("pej3163"), "detran", handler, Resposta.class);
        mid.client.sendMessage(new Pergunta("kim2639"), "detran", handler, Resposta.class);

        mid.client.stopClient();

    }
    
}
