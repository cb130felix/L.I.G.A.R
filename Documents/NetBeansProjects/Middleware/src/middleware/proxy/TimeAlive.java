
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
                
                this.estadoProxy();
                
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
     * 
     * @return Retorna True ou False
     */
    public synchronized boolean rmTimeAlive(){
    
        Calendar rightNow = Calendar.getInstance();
        
        for(int i=tableTime.size()-1; i>=0; i--){

            long saved = (long)tableTime.get(i).get(1);
            long now = rightNow.getTimeInMillis();
            
            if ((now-saved)>11000){
                this.rmIpService(this.tableTime.get(i));
                this.tableTime.remove(i);
            }
        }
                
        return true;
    }
    
    
    /**
     * Método que busca o ip na tabela de ativos
     * Se existir tal ip, apenas atualiza seu registro
     * Se não existir, adiciona esse novo ip e seu registro atual à lista
     * 
     * @param ip Parâmetro que informa o Ip qie será atualizado ou adicionado
     * @return Retorna True ou False
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
    
    /**
     * Método que irá remover determinado ip da lista de serviços
     * 
     * @param ip Parâmetro que identifica o ip que será retirado
     * @return Retorna True ou False
     */
    public synchronized boolean rmIpService(ArrayList ip){ 
        
        Proxy p = Proxy.getInstance();
        
        for(int i=p.listServices.size()-1; i>=0; i--){
            for(int y=p.listServices.get(i).getAddress().size()-1; y>=0; y--){
                
                if (p.listServices.get(i).getAddress().get(y).getIp().equals(ip.get(0))){
                    p.listServices.get(i).getAddress().remove(y);
                }
            }
            
            if(p.listServices.get(i).getAddress().isEmpty()){
                        
                p.listServices.remove(i);
            }
        }
        
        return true;
    }
    
    
    /**
     * Imprime a lista de serviços do proxy
     */
    public synchronized void estadoProxy(){

        Proxy p = Proxy.getInstance();
        
        System.out.println("\n###### Estado dos Serviços do Proxy ######");
        
        if (p.listServices.size()>0){
            
            for(int i=0; i<p.listServices.size(); i++){

                System.out.println("Serviço: " + p.listServices.get(i).getService());

                for(int y=0; y<p.listServices.get(i).getAddress().size(); y++){
                    System.out.println("   - "+p.listServices.get(i).getAddress().get(y).getIp()+":"+p.listServices.get(i).getAddress().get(y).getPort());
                }

            }
            
        } else{
        
            System.out.println("Não há serviços no momento.");    
        }
        
        System.out.println("##########################################\n");
    }
}

