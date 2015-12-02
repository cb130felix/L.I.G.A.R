/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.server;

import java.net.SocketException;
import java.util.ArrayList;
import middleware.communication.ConnectionManager;

/**
 * Classe que fará o gerenciamento do servidor
 * @author Guto Leoni
 */
public class ManagerServer extends Thread{
    
    public UserCounter userCounter;
    int edgeClients;
    ConnectionManager mc;
    private ArrayList<MapService> Services = new ArrayList<MapService>();
    int portServer;
    
    public ManagerServer(UserCounter userCounter, int edge ,ArrayList<MapService> list, int port){
        this.mc = new ConnectionManager();
        this.userCounter = userCounter;
        this.edgeClients = edge;
        this.Services = list;
        this.portServer = port;
    }
    
    /**
     * Método que irá verificar o nome de clientes conectados ao servidor. Se for maior que o limite de clientes, ele não irá mandar
     * mensagem para o proxy com suas informações. Caso contrário, ele mandará suas informações (Serviços oferecidos)
     */
    public void run(){
    
        this.sendMensageToProxy();
        // teste
    
    }
    
    public synchronized void sendMensageToProxy(){
    
        String msg = "M0||"+this.portServer;// CABEÇALHO DA MENSAGEM PRO PROXY
        
        while(true){
        
            try {
                
                    sleep(5000);// Madará mensagens de 5 em 5 segundos
                    //System.out.println("Olha aí no manager "+this.userCounter.cont);
                    if(this.userCounter.cont < this.edgeClients){

                        msg = msg +this.AtualizaServicos();

                        if(mc.broadcast(msg.getBytes(), 24240)){// PORTA DO PROXY
                            
                            msg = "M0||"+this.portServer;
                        }

                    }
                
                
            } catch (Exception e) {
                System.out.println("Erro ao tentar mandar mensagem pra o proxy");// ISSO VAI MUDAR :V
            }
        
        }
    
    }
    
    
    /**
     * Método que concatena todos os serviços oferecidos pelo servidor numa string para mandar para o proxy.
     * @return String com todos os serviços oferecidos, separados com "||".
     */
    public String AtualizaServicos(){
    
        String services="";
        
        for (int x = 0; x < this.Services.size(); x++) {
            
            services = services+"||"+this.Services.get(x).name;
            
        }
        
        return services;
    }
    
}
