/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testes;

import java.net.SocketException;
import middleware.Middleware;
import middleware.client.DataHandler;

/**
 *
 * @author LIGAR
 */
public class TestClient {

    /**
     * @param args the command line arguments
     * @throws java.net.SocketException
     * @throws java.lang.InterruptedException
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

        while(true){
            int retorno = mid.client.sendMessage(new Pergunta("pej3163"), "detran", handler, Resposta.class);
            System.out.println(retorno);
            Thread.sleep(3000);
         //   mid.client.sendMessage(new Pergunta("kim2639"), "detran", handler, Resposta.class);
        }
        //mid.client.stopMessage(-1);
        //teste
//        mid.client.stopClient();

    }
    
}
