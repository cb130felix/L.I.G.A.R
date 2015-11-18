/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware;

/**
 *
 * @author LIGAR
 */
public class ServiceInfo {
    
    int port;
    private String service;

    public int getPort(){
    
        return this.port;
    }
    
    public void setPort(int port){
    
        this.port = port;
        
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
        
    
    
}
