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

	
    public void startManagementServer(int edge){// esse edge é o limite de clientes simultâneos
    
        ManagerServer mg = new ManagerServer(userCounter,edge,servicesList);
        
        mg.start();
    
    }
    
        public synchronized String listenRequest(int port){
        
            byte[] data;
            String mensage = "";
            
            try {
                if(mc.listenerTCP(port) == true){   
            
                    data = mc.receive();
                    mensage = data.toString();
                    this.IDRequest = (int)mensage.charAt(0);
                    this.userCounter = this.userCounter + 1;
                    mensage = mensage.substring(1);
                    
                }
            } catch (Exception e) {
            }
            
            return mensage;
        }
       
        public synchronized void removeService(String service){
        
            for (int x = 0; x < this.servicesList.size(); x++) {
                
                if(this.servicesList.get(x).getService().equals(service)){
                
                    this.servicesList.remove(x);
                    
                }
                
            }
        
        }
        
        public synchronized void sendReply(String reply){
        
            int tentativas = 0;
            boolean parar = false;
            
            while(!parar){
            
                    try {

                        this.mc.send(reply.getBytes());

                    } catch (Exception e) {

                        try {
                            
                            wait(4000);
                            
                            if(tentativas > 4){
                            
                                parar = true; 
                            
                            }
                            else{
                            
                             tentativas++;
                            }
                            
                        } catch (Exception e1) {}
                }
            
            }
            
            this.userCounter = this.userCounter - 1;
            
            
        }
        
        /*public boolean removeService(){return true;}
	public void incrementUserCounter(){}
	
	public void keepServerAlive(){}*/
}