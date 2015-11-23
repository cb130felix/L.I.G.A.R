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
import middleware.communication.ConnectionManager;
import middleware.ServiceInfo;

public class Server{

    
        ConnectionManager mc = new ConnectionManager();
	public Integer userCounter;
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
            this.port = 34251;
            this.userCounter = new Integer(0);
            mc.startServerTCP(this.port);
        }

        /**
         * Construtor do servidor. Quando não é fornecido o número limte de usuários,
         * o valor padrão é 100.
         *
         */
        public Server() {

            this.edgeClients = 100;
            this.port = 34251;
            this.userCounter = new Integer(0);
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
	public void startServer(){
        
            
            ManagerServer ms = new ManagerServer(this.userCounter, this.edgeClients, this.mapServices,this.port);
            ms.start();
            
            while(true){
            
                System.out.println("Esperando....");
                 
                if(mc.listenerTCP()){
                     //System.out.println("Valor antes: "+this.userCounter);
                    System.out.println("Uma requisicao!");
                    
                    this.IncrementUser();
                    System.out.println("Numero de usuario no servidor: "+this.userCounter);
                    //System.out.println("lol");
                    
                    Service serv = new Service(new ConnectionManager(this.mc),this.processServices,this.mapServices,this.userCounter);
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
        
        public synchronized void IncrementUser(){
        
            this.userCounter = this.userCounter + 1;
        
        }

        
}
