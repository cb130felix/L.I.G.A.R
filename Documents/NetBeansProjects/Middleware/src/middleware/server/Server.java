/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package middleware.server;

/**
 *
 * @author Guto
 */

import java.util.ArrayList;
import middleware.ManagerConnection;
import middleware.ServiceInfo;

public class Server{

        //cabeçalho da mensagem(cliente/servidor): id;service;message
        // exemplo prático: 200;A;qualquermerda(em json)
    
        ManagerConnection mc = new ManagerConnection();
	private ArrayList<ServiceInfo> servicesList = null;
	private Integer userCounter;
        private ArrayList<MapService> mapServices = new ArrayList<MapService>();
        private int port;
        ArrayList<ServiceProcess> processServices = new ArrayList<ServiceProcess>();
        
        public Server() {

            this.port = 24241;

        }
        
        
        public boolean addService(String description,ServiceProcess service){
        
            try {
                
                MapService ms = new MapService();
                ms.name = description;
                ms.ID = this.mapServices.size();
                this.mapServices.add(ms);
                this.processServices.add(service);
                return true;
                
            } catch (Exception e) {
            
                System.out.println("Erro ao adicionar servico");
                return false;
            }
            
        }

	public synchronized void startServer(){
        
        
            while(true){
            
                if(mc.listenerTCP(this.port)){
                
                    this.userCounter = this.userCounter + 1;
                    Service serv = new Service(this.mc,this.processServices,this.mapServices,this.userCounter);
                    serv.start();
                }
                
            
            }// fim do while
            
        }
    
        public void setPort(int port){
        
            this.port = port;
        
        }
        

        public int getPort() {
            return port;
        }
        
                
        /*public boolean removeService(){return true;}
	public void incrementUserCounter(){}
	
	public void keepServerAlive(){}*/

        
}