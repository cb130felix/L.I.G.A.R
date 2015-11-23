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
    boolean dataSent;

    public DataSender(byte[] data, ArrayList<ServiceInfo> serviceTable, DataHandler dataHandler) {
        this.data = data;
        this.serviceTable = serviceTable;
        this.dataHandler = dataHandler;
        this.dataSent = false;
        
    }
         
     
    public synchronized ServiceInfo getServiceInfo(){
        
        if(this.serviceTable.size() > 0){
            return this.serviceTable.get(0);
        }else{
            return null;
        }
        
    }
    
    public synchronized boolean deleteServiceInfo(ServiceInfo serviceInfo){
    
        this.serviceTable.remove(serviceInfo);
        return true;
        
    }
     
    public void run(){

        while(!dataSent){
            
            //fica tentando enviar os dados
            
        }
        
    
    }
    
    
}
