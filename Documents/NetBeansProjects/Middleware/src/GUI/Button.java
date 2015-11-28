
package GUI;

import Testes.Pergunta;
import Testes.Resposta;
import java.net.SocketException;
import middleware.Middleware;
import middleware.client.DataHandler;

/**
 *
 * @author arthur
 */
public class Button extends Thread{
 
    String message;
    String service;
    javax.swing.JProgressBar load;
    
    public Button(String service, String message, javax.swing.JProgressBar load){
        
        this.message = message;
        this.load = load;
        this.service = service;
    }
    
    public void run (){
        
        try{
            Middleware mid = new Middleware();

            DataHandler handler;
            handler = new DataHandler() {

                @Override
                public void handler(int id, Object o) {

                    Resposta r = (Resposta) o;
                    System.out.println("["+id+"]" + "Consulta: " + r.consulta);
                }
            };

            mid.client.startClient();
            mid.client.sendMessage(new Pergunta(message), service, handler, Resposta.class);
            mid.client.stopClient();

            load.setIndeterminate(false);
            
        } catch (SocketException | InterruptedException ex){
            
            System.out.println("\nErro na mensagem aí bród ;(\n");
            load.setIndeterminate(false);
        }
        
    }
    
}
