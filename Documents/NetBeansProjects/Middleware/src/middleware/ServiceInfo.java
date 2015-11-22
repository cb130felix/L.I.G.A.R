/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware;

import java.util.ArrayList;
import middleware.Address;

/**
 *
 * @author LIGAR
 */
public final class ServiceInfo {
    
    ArrayList <Address> address;
    private String service;
    
    public ServiceInfo(ArrayList<Address> address, String service){
        this.address = address;
        this.service = service;
        
        setAddress(address);
        setService(service);
    }

    public void setAddress(ArrayList<Address> address) {
        this.address = address;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public ArrayList<Address> getAddress() {
        return address;
    }
   
}
