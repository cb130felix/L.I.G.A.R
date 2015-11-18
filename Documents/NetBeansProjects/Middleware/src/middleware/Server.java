/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package middleware;

/**
 *
 * @author Guto
 */

import java.util.ArrayList;

public class Server{

        //cabeçalho da mensagem(cliente/servidor): id;service;message
        // exemplo prático: 200;A;qualquermerda(em json)
    
        ManagerConnection mc = new ManagerConnection();
	private ArrayList<ServiceInfo> servicesList = null;
	private Integer userCounter;
        private ArrayList<MapService> services = new ArrayList<MapService>();
        int port;
        
	public boolean addService(String description,Service service){
        
            return true;
        }

	public void startServer(){
        
            
        }
    
        public void setPort(int port){
        
            this.port = port;
        
        }
        /*public boolean removeService(){return true;}
	public void incrementUserCounter(){}
	
	public void keepServerAlive(){}*/
}