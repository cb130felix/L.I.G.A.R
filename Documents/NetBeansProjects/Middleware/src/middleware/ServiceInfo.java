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
    
    Address address;
    private String service;

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Address getAddress() {
        return address;
    }    
}
