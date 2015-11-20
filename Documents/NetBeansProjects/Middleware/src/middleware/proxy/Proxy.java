
package middleware.proxy;

/**
 *
 * @author arthur
 */

import java.net.DatagramPacket;
import java.util.ArrayList;
import middleware.ManagerConnection;
import middleware.ServiceInfo;

public class Proxy{

    ArrayList<ServiceInfo> listServices;
    
    public Proxy(){
        this.listServices = new ArrayList<>();
        this.listener();
    }
    
    private void listener(){
        
        ManagerConnection mc = new ManagerConnection();
        
        while (true){

            DatagramPacket pckt = mc.listenerUDP(24240);

            if (pckt!=null) {
                new ManagerProxy(pckt).start();
            }
        }
    }

}