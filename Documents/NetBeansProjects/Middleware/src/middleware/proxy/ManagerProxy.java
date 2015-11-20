
package middleware.proxy;

import java.net.DatagramPacket;

/**
 * Classe que irá o destino da mensagem recebida.
 * m0 para requisições de clientes
 * m1 para manter os servidores ativos na tabela
 * 
 * No final chama o método 'ManagerTable'
 * 
 * @author arthur
 */
public class ManagerProxy extends Thread{
    
    DatagramPacket pckt;
    
    public ManagerProxy(DatagramPacket pckt){
        this.pckt = pckt;
    }
    
    @Override
    public void run(){
    
        try{
    
            String addressReceived = pckt.getAddress().getHostAddress();
            addressReceived = addressReceived.replace("/", "");
            
            String dataReceived = new String(pckt.getData(), "UTF-8");
            
            String[] data = dataReceived.split("||");
            
            if (data[0].equals("m0")) {
                new ManagerTable().addService(data, addressReceived);
                
            } else if (data[0].equals("m1")){
                new ManagerTable().sendService(data);
            
            }
            
        } catch (Exception e){
            System.out.println("A:001");
        }
    }
}
