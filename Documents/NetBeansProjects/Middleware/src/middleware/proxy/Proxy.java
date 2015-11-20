
package middleware.proxy;

/**
 *
 * @author Arthur
 */

import java.util.ArrayList;
import middleware.ServiceInfo;

public class Proxy{


    ArrayList<ServiceInfo> listServices = null;

    public boolean listenServer(){return true;}
    public boolean listenClient(){return true;}


}