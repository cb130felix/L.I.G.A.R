
package middleware.proxy;

/**
 * Classe Proxy (Balanceador de Carga)
 * @author arthur
 */

import java.net.DatagramPacket;
import java.util.ArrayList;
import middleware.ManagerConnection;
import middleware.ServiceInfo;

public class Proxy{

    ArrayList<ServiceInfo> listServices = new ArrayList<>();
    
    // Singleton
    private Proxy() {}
    
    public static Proxy getInstance() {
        return ProxyInstance.INSTANCE;
    }
    
    private static class ProxyInstance {
        private static final Proxy INSTANCE = new Proxy();
    } 
    
    
    // Iniciando proxy
    public void startProxy(){
        this.listener();
        TimeAlive.getInstance().start();
    }
    
    // Método para escutar uma porta UDP. 
    // Quando conectar, chama o método 'ManagerProxy' e depois volta a escutar
    private void listener(){
        
        ManagerConnection mc = new ManagerConnection();
        
        while (true){

            DatagramPacket pckt = mc.listenerUDP(24240);

            if (pckt!=null) {
                new ManagerProxy(pckt, mc).start();
            }
        }
    }
}