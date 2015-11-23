/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.client;

import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import middleware.Address;
import middleware.ServiceInfo;

import middleware.communication.ConnectionManager;

/**
 *
 * @author Renan
 */
public class DataSender extends Thread {
    
    int id;
    byte[] data;
    ArrayList<ServiceInfo> serviceTable;
    DataHandler dataHandler;
    boolean dataSent;
    String service;
    
    public DataSender(String service,int id, byte[] data, ArrayList<ServiceInfo> serviceTable, DataHandler dataHandler) {
        
        this.id = id;
        this.service = service;
        this.data = data;
        this.serviceTable = serviceTable;
        this.dataHandler = dataHandler;
        this.dataSent = false;
        
    }
         
     
    public synchronized Address getAddress(){
        
        for(int i = 0; i < this.serviceTable.size(); i++){
            
            //se o serviço existir na serviceTable e houver pelo menos um endereço disponível, pega o primeiro endererço
            if((this.serviceTable.get(i).getService().equals(this.service)) && (this.serviceTable.get(i).getAddress().size() > 0)){
                return this.serviceTable.get(i).getAddress().get(0);
            }
            
        }
        
        return null;
        
    }
    
    public synchronized boolean deleteAddress(Address address){
    
        for(int i = 0; i < this.serviceTable.size(); i++){
            if(this.serviceTable.get(i).getService().equals(this.service)){

                this.serviceTable.get(i).getAddress().remove(address);
                
            }
        }
        return true;
        
    }
     
    public void run(){
    
    Address adrs = null;
    ConnectionManager mc = null;
        try {
            mc = new ConnectionManager();
        } catch (SocketException ex) {
            Logger.getLogger(DataSender.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        while(!dataSent){
            
            while(adrs == null){
                
                adrs = this.getAddress();
                if(adrs == null){
                    System.out.println("Serviço desconhecido...");
                }else{
                    System.out.println("Serviço conhecido!");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DataSender.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
            //fica tentando enviar os dados
            
            if(mc.connectionServer(adrs)){
                System.out.println("Conectou ao servidor!");
                mc.sendData(data);
                try {
                    String result = new String(mc.getData(), "UTF-8");
                    this.dataHandler.handler(this.id, result);
                    dataSent = true;
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(DataSender.class.getName()).log(Level.SEVERE, null, ex);
                }
                mc.closeConnection();
                
                
            }else{
                System.out.println("O servidor não foi encontrado...");
            }
        
            
            
        }
        
    
    }
    
    
}
