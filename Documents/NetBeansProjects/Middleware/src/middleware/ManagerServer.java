/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware;

import java.util.ArrayList;

/**
 *
 * @author Guto Leoni
 */
public class ManagerServer extends Thread{
    
    Integer userCounter;
    int edge;
    ManagerConnection mc = new ManagerConnection();
    private ArrayList<ServiceInfo> servicesList = null;
    
    public ManagerServer(Integer userCounter, int edge ,ArrayList<ServiceInfo> list) {
        this.userCounter = userCounter;
        this.edge = edge;
        this.servicesList = list;
    }
    
    public void run(){
    
        String msg = "M0||";
        
        while(true){
        
            try {
                
                    this.wait(5000);

                    if(this.userCounter < this.edge){

                        msg = msg +this.AtualizaServicos();

                        mc.broadcast(msg.getBytes(), 2424);// MUDAR PARA A PORTA DO PROXY

                    }
                
                
            } catch (Exception e) {
                System.out.println("Erro ao tentar mandar mensagem pra o proxy");
            }
        
        }
        
    
    }
    
    public String AtualizaServicos(){
    
        String services="";
        
        for (int x = 0; x < this.servicesList.size(); x++) {
            
            services = services+this.servicesList.get(x).getService();
            
        }
        
        return services;
    }
    
}
