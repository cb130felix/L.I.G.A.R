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
    
        private ManagerConnection mc = new ManagerConnection();
	private ArrayList<ServiceInfo> servicesList = null;
	private int userCounter;

	public boolean addService(ServiceInfo info){
            
            this.servicesList.add(info);
            
            return true;
        }
	
        
        public void startServer(ServiceInfo info){ // nesse info já tem uma string com a descrição do serviço(String)
                                                   //e o endereço (Adress)
            this.addService(info);
            // ok, aqui vem o método listenTCP de Iago
        
        }

        
        public boolean removeService(){return true;}
	public void incrementUserCounter(){}
	public void listenRequest(){}
	public void keepServerAlive(){}
}