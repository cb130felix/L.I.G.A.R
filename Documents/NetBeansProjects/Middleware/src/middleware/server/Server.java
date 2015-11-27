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

import java.net.SocketException;
import java.util.ArrayList;
import middleware.communication.ConnectionManager;
import middleware.ServiceInfo;

public class Server{

        
        public ConnectionManager mc;
	public UserCounter userCounter;
        private ArrayList<MapService> mapServices = new ArrayList<MapService>();
        private int port;
        public ArrayList<ServiceProcess> processServices = new ArrayList<ServiceProcess>();
        public int edgeClients;
        private boolean enableProxy;
        
       /**
        * Construtor do servidor
        * @param edgeClient Limite de clientes simultâneos no servidor
        * @param proxy variável que habilita o proxy
        */
        public Server(int edgeClient, boolean proxy){
        this.mc = new ConnectionManager();
            
            this.edgeClients = edgeClient;
            this.port = 24246;
            this.userCounter = new UserCounter(0);
            this.enableProxy = proxy;
            //mc.startServerTCP(this.port);
        }

        /**
         * Construtor do servidor. Quando não é fornecido o número limte de usuários,
         * o valor padrão é 100 e o proxy é ativado por padrão.
         *
         */
        public Server() throws SocketException {
        this.mc = new ConnectionManager();

            this.edgeClients = 100;
            this.port = 24246;
            this.userCounter = new UserCounter(0);
            this.enableProxy = true;
            //mc.startServerTCP(this.port);
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
	public void startServer() throws SocketException{
        
            if(mc.startServerTCP(this.port)){
            
                System.out.println("Servidor pronto!");
            }
            
            if(this.enableProxy == true){
            
                ManagerServer ms = new ManagerServer(this.userCounter, this.edgeClients, this.mapServices,this.port);
                ms.start();
                
            }
            
            while(true){
            
                System.out.println("Esperando....");
                 
                if(mc.listenerTCP()){
                     //System.out.println("Valor antes: "+this.userCounter);
                    System.out.println("Uma requisicao!");
                    
                    this.IncrementUser();
                    System.out.println("Numero de usuario no servidor: "+this.userCounter.cont);
                    System.out.println("lol");
                    
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
        
            this.userCounter.cont++;
        
        }

        
}
