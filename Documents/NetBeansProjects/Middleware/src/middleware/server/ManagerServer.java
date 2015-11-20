/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.server;

import java.util.ArrayList;
import middleware.ManagerConnection;
import middleware.ServiceInfo;

/**
 * Classe que fará o gerenciamento do servidor
 * @author Guto Leoni
 */
public class ManagerServer extends Thread{
    
    Integer userCounter;
    int edgeClients;
    ManagerConnection mc = new ManagerConnection();
    private ArrayList<ServiceInfo> servicesList = null;
    
    public ManagerServer(Integer userCounter, int edge ,ArrayList<ServiceInfo> list) {
        this.userCounter = userCounter;
        this.edgeClients = edge;
        this.servicesList = list;
    }
    
    /**
     * Método que irá verificar o nome de clientes conectados ao servidor. Se for maior que o limite de clientes, ele não irá mandar
     * mensagem para o proxy com suas informações. Caso contrário, ele mandará suas informações (Serviços oferecidos)
     */
    public void run(){
    
        String msg = "M0";// CABEÇALHO DA MENSAGEM PRO PROXY
        
        while(true){
        
            try {
                
                    this.sleep(5000);// Madará mensagens de 5 em 5 segundos

                    if(this.userCounter < this.edgeClients){

                        msg = msg +this.AtualizaServicos();

                        mc.broadcast(msg.getBytes(), 24240);// PORTA DO PROXY

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
        
        for (int x = 0; x < this.servicesList.size(); x++) {
            
            services = services+"||"+this.servicesList.get(x).getService();
            
        }
        
        return services;
    }
    
}
