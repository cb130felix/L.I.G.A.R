/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware;

/**
 *
 * @author Guto Leoni
 */
public class ManagerServer extends Thread{
    
    Integer userCounter;
    int edge;
    
    public ManagerServer(Integer userCounter, int edge) {
        this.userCounter = userCounter;
        this.edge = edge;
    }
    
    public void run(){
    
    
        while(true){
        
            if(this.userCounter < this.edge){
            
                // AQUI MANDA UMA MENSAGEM PRA O PROXY DIZENDO OS SERVIÇOS QUE 
            
            }
        
        }
        
    
    }
    
}
