/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.client;

import java.util.ArrayList;

/**
 *
 * @author Renan
 */
public class DataSender extends Thread {
    
    byte[] data;
    ArrayList<ServiceInfo> serviceTable;
    DataHandler dataHandler;

    public DataSender(byte[] data, ArrayList<ServiceInfo> serviceTable, DataHandler dataHandler) {
        this.data = data;
        this.serviceTable = serviceTable;
        this.dataHandler = dataHandler;
    }
         
     
     
    public void run(){
    
        
        
    
    }
    
    
}
