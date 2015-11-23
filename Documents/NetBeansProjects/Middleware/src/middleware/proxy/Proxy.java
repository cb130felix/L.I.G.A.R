
package middleware.proxy;

/**
 * Classe Proxy (Balanceador de Carga)
 * @author arthur
 */

import java.net.DatagramPacket;
import java.net.SocketException;
import java.util.ArrayList;
import middleware.communication.ConnectionManager;
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
    public void startProxy() throws SocketException{
        TimeAlive.getInstance().start();
        this.listener();        
    }
    
    // Método para escutar uma porta UDP. 
    // Quando conectar, chama o método 'ManagerProxy' e depois volta a escutar
    private void listener() throws SocketException{
        
        ConnectionManager mc = new ConnectionManager();
        
        while (true){
                        
//            System.out.println("Esperando conexão...");
            DatagramPacket pckt = mc.listenerUDP(24240);
            
            if (pckt!=null) {
//                System.out.println("Pacote recebido de: " + pckt.getAddress().getHostAddress() + "\n");
                new ManagerProxy(pckt, mc).start();
            }
            
        }
    }    
}