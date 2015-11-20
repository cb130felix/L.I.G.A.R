
package middleware.proxy;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Classe que irá gerenciar a tabela de servidores ativos
 * @author arthur
 */
public class TimeAlive extends Thread{

    ArrayList<ArrayList> tableTime = new ArrayList<>();
    
    // Singleton
    private TimeAlive() {}
    
    public static TimeAlive getInstance() {
        return timeAliveInstance.INSTANCE;
    }
    
    private static class timeAliveInstance {
        private static final TimeAlive INSTANCE = new TimeAlive();
    }
    
    
    // Thread que a cada X segundos chama o método 'rmTimeAlive'
    @Override
    public void run(){
        
        try {
            
            while(true){
                Thread.sleep(10000);
                this.rmTimeAlive();
            }
            
        } catch (InterruptedException ex) {
            System.out.println("A:002");
        }
    }
    
    
    /**
     * Método que faz a busca na tabela de ativos verificando os segundos da última 
     * resposta do servidor, caso seja maior que o limite, o remove da lista
     * @return 
     */
    public synchronized boolean rmTimeAlive(){
    
        Calendar rightNow = Calendar.getInstance();
        
        for(int i=tableTime.size()-1; i==0; i--){
        
            long saved = (long)tableTime.get(i).get(1);
            long now = rightNow.getTimeInMillis();
            
            if ((now-saved)>11000){
                tableTime.remove(i);
            }
        }
        
        return true;
    }
    
    
    /**
     * Método que busca o ip na tabela de ativos
     * Se existir tal ip, apenas atualiza seu registro
     * Se não existir, adiciona esse novo ip e seu registro atual à lista
     * 
     * @param ip
     * @return 
     */
    @SuppressWarnings("unchecked")
    public synchronized boolean addTimeAlive(String ip){
        
        Calendar rightNow = Calendar.getInstance();
        
        for (int i=0; i<tableTime.size(); i++){
        
            if (tableTime.get(i).get(0).equals(ip)){
                
                tableTime.get(i).set(1, rightNow.getTimeInMillis());
                return true;
            }
        }
        
        ArrayList<Object> novo = new ArrayList<>();
        novo.add(ip);
        novo.add(rightNow.getTimeInMillis());
        
        tableTime.add(novo);
        
        return true;
    }
}
