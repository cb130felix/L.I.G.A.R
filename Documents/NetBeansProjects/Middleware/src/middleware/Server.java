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
        private int IDRequest;
        
	public boolean addService(ServiceInfo info){
            
            this.servicesList.add(info);
            
            return true;
        }

	
    public void startManagementServer(){
    
        
        ManagerServer mg = new ManagerServer(userCounter,100,servicesList);
        
        mg.start();
    
    
    }
    
        
        public String listenRequest(int port){
        
            byte[] data;
            String mensage = "";
            
            if(mc.listenerTCP(port) == true){
            
                data = mc.receive();
                mensage = data.toString();
                this.IDRequest = (int)mensage.charAt(0);
                mensage = mensage.substring(1);
            }
            
            return mensage;
        }
        
        public void startServer(ServiceInfo info){ // nesse info já tem uma string com a descrição do serviço(String)
                                                   //e o endereço (Adress)
            this.addService(info);
            
            
        }

        
        public boolean removeService(){return true;}
	public void incrementUserCounter(){}
	
	public void keepServerAlive(){}
}