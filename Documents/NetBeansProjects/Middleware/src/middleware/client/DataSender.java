/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.client;

import com.google.gson.Gson;
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
    public boolean dataSent;
    String service;
    SearchService ss;
    Class c;
    
    public DataSender(String service,int id, byte[] data, ArrayList<ServiceInfo> serviceTable, DataHandler dataHandler, SearchService ss, Class c) {
        
        this.id = id;
        this.service = service;
        this.data = data;
        this.serviceTable = serviceTable;
        this.dataHandler = dataHandler;
        this.dataSent = false;
        this.ss = ss;
        this.c = c;
    }
         
     
    public synchronized Address getAddress(){

        
        for(int i = 0; i < this.serviceTable.size(); i++){
            
            //se o serviço existir na serviceTable e houver pelo menos um endereço disponível, pega o primeiro endererço
            if((this.serviceTable.get(i).getService().equals(this.service)) && (this.serviceTable.get(i).getAddress().size() > 0)){
                Address adrs = new Address(this.serviceTable.get(i).getAddress().get(0).getIp(), this.serviceTable.get(i).getAddress().get(0).getPort());
                return adrs;
            }
            
        }
        return null;
        
    }
    
    public synchronized boolean deleteAddress(Address address){
    
        for(int i = 0; i < this.serviceTable.size(); i++){
            if(this.serviceTable.get(i).getService().equals(this.service)){
                for (int j = 0; j < this.serviceTable.get(i).getAddress().size(); j++) {
                    if(this.serviceTable.get(i).getAddress().get(j).getIp().equals(address.getIp()) && this.serviceTable.get(i).getAddress().get(j).getPort() == address.getPort()){
                        this.serviceTable.get(i).getAddress().remove(j);
                    }
                }
                //this.serviceTable.get(i).getAddress().remove(address);
                
            }
        }
        return true;
        
    }
    
    public synchronized void wakeSearchService(){
        
        if(ss.getState().WAITING == Thread.State.WAITING){
            synchronized(this.ss){
                System.out.println("Acordando!!!!!");
                ss.notify();
            }
            
        }
    
    }
    
    public void run(){
    
    int attempt=0;
    Address adrs = null;
    ConnectionManager mc = null;
    mc = new ConnectionManager();
            
        while(!dataSent){
            while(adrs == null){
                
                //checa se a SearchServico tá dormindo, se não estiver, acorda ele
                System.out.println("Datasent: " + dataSent);
                if(dataSent == true) break;
                wakeSearchService();
                System.out.println("Tenando pegar algo...");
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
            
            if(dataSent != true){
                while(attempt < 3){
                    System.out.println("Tentando conectar ao servidor...("+attempt+")");
                    try{

                        mc.connectionServer(adrs);
                        mc.sendData(this.data);
                        byte[] data2 = mc.getData();
                        String result = new String(data2, "UTF-8");
                        mc.closeConnection();

                        dataSent = true;
                        Gson gson = new Gson();
                        Object o = gson.fromJson(result, c);
                        dataHandler.handler(id, o);
                        break;

                    }catch(Exception ex){
                        System.out.println("O servidor não foi encontrado...");
                        attempt++;
                    }

                }
                if(attempt >= 3){
                    System.out.println("Deletando servidor da lista de serviços...");
                    deleteAddress(adrs);
                    adrs = null;
                    attempt = 0;
                }
            }
            
            
        }
        
    
    }
    
    
}
