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
        int edgeClients;
        
       /**
        * Construtor do servidor
        * @param edgeClient Limite de clientes simultâneos no servidor
        */
        public Server(int edgeClient) {
            
            this.edgeClients = edgeClient;
            this.port = 24251;
            this.userCounter = 0;
            mc.startServerTCP(this.port);
        }

        public Server() {

            this.edgeClients = 100;
            this.port = 24251;
            this.userCounter = 0;
            mc.startServerTCP(this.port);
        }
        
        
        
        /**
         * Método para adicionar um novo servico provido pelo servidor.
         * @param description nome do serviço
         * @param service Objeto que possui os métodos do serviço oferecido pelo servidor.
         * @return 
         */
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

        /**
         * Método para inicializar o servidor. Esse método inicia o gerenciador do servidor e faz com que ele comece a escutar
         * requisições.
         */
	public synchronized void startServer(){
        
            
            //ManagerServer ms = new ManagerServer(userCounter, edgeClients, servicesList,this.port);
           // ms.start();
            
            while(true){
            
                System.out.println("Esperando...");
                 
                if(mc.listenerTCP()){
                    System.out.println("Uma requisicao!");
                    this.userCounter = this.userCounter + 1;
                    //System.out.println("lol");
                    
                    Service serv = new Service(new ManagerConnection(this.mc),this.processServices,this.mapServices,this.userCounter);
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