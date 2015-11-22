
package middleware.proxy;

import services.Detran;
import middleware.server.Server;

/**
 *
 * @author arthur
 */
public class MainServ {

    public static void main(String[] args) {
        
        Server s = new Server(10);
        Detran servico1 = new Detran();
        
        s.addService("Detran", servico1);
        s.startServer();
         
    }
    
}
