
package middleware.proxy;

import java.net.DatagramPacket;
import java.util.regex.Pattern;
import middleware.ManagerConnection;

/**
 * Classe que irá o destino da mensagem recebida
 * m0 para requisições de clientes
 * m1 para manter os servidores ativos na tabela
 * 
 * No final chama o método 'ManagerTable'
 * 
 * @author arthur
 */
public class ManagerProxy extends Thread{
    
    DatagramPacket pckt;
    ManagerConnection mc;
    
    public ManagerProxy(DatagramPacket pckt, ManagerConnection mc){
        this.pckt = pckt;
        this.mc = mc;
    }
    
    @Override
    public void run(){
    
        try{
            
            String addressReceived = pckt.getAddress().getHostAddress();
            addressReceived = addressReceived.replace("/", "");
            
            String dataReceived = new String(pckt.getData(), "UTF-8");            
            String[] data = dataReceived.split(Pattern.quote("||"));
            
            if (data[0].equals("M0")) {
                
                new ManagerTable().addService(data, addressReceived);
                
            } else if (data[0].equals("M1")){
//                String answer = new ManagerTable().sendService(data);
//                mc.sendData(answer.getBytes());
                
            }
            
        } catch (Exception e){
            System.out.println("A:001: " + e);
        }
    }
}
