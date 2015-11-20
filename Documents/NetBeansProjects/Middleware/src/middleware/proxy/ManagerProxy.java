
package middleware.proxy;

import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 *
 * @author arthur
 */
public class ManagerProxy extends Thread{
    
    String dataReceived;
    InetAddress addressReceived;
    DatagramPacket pckt;
    
    public ManagerProxy(DatagramPacket pckt){
        this.pckt = pckt;
    }
    
    @Override
    public void run(){
    
        try{
            dataReceived = new String(pckt.getData(), "UTF-8"); 
            addressReceived = pckt.getAddress();
            
        } catch (Exception e){
        
        }
        
    }
}
