/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.server;

import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import middleware.ManagerConnection;

/**
 *
 * @author Guto Leoni
 */
public class Service extends Thread{
    
    ManagerConnection mc;
    ArrayList<MapService> mapServices = new ArrayList<MapService>();
    ArrayList<ServiceProcess> processServices = new ArrayList<ServiceProcess>();

    public Service(ManagerConnection mc,ArrayList<ServiceProcess> process,ArrayList<MapService> map) {
        this.mc = mc;
        this.processServices = process;
        this.mapServices = map;
    }

    public void run(){
    
        byte[] data;
        String msg;
        String[] mensages;
        int idServico;
        byte[] reply;
        
        try {
            
            data = mc.getData();
            msg = new String(data, "UTF-8");// 2||Detran||kcd-1232
            mensages = this.TratarString(msg);
            idServico = this.descobreIndiceServico(mensages[1]);
            
            if(idServico != -1){
            
              reply =  this.processServices.get(idServico).process(mensages[2].getBytes());
              
              msg = mensages[0] + "||" + mensages[2] + "||" + new String(reply,"UTF-8");
              
              reply = msg.getBytes();
              
              this.mc.sendData(reply);
              
            }
            
        }catch (Exception e) {
        
        }
    
    
    }// fim do método run
    
    public String[] TratarString(String msg){
    
        String[] mensages = msg.split("||");
        
        return mensages;
    }
    
    public int descobreIndiceServico(String mensage){
    
        for (int x = 0; x < this.mapServices.size(); x++) {
                
                if(this.mapServices.get(x).name.equals(mensage)){
                
                    return x;
                
                }
                
            }
        return -1;
    }
}
