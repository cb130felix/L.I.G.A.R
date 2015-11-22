
package middleware.proxy;

import java.util.ArrayList;
import middleware.Address;
import middleware.client.ServiceInfo;

/**
 * Classe que irá cuidar em adicionar, procurar e enviar informações dos serviços
 * 
 * @author arthur
 */
public class ManagerTable {
    
    /**
     * Método para adicionar um serviço na tabela, verificando se o tal serviço
     * já existe e se dentro do serviço existe o determinado IP 
     * 
     * No fim, atualiza o TimeAlive do server
     * 
     * 
     * @param service Arrays de string com os nomes dos serviços
     * @param addressReceived String com IP de quem fez a requisição
     */
    public void addService(String[] service, String addressReceived){
        
        Proxy p = Proxy.getInstance();
        int port = Integer.parseInt(service[1].trim());
        
        for (int i=2; i<service.length; i++){
            
            int indexService = this.searchService(service[i].trim());

            if (indexService!=-1){
        
                int indexIp = this.searchIp(indexService, addressReceived);
            
                if (indexIp!=-1){
                    TimeAlive.getInstance().addTimeAlive(addressReceived);
                    
                } else{
                    Address aux = new Address(addressReceived, port);
                    p.listServices.get(indexService).getAddress().add(aux);
                    
                    TimeAlive.getInstance().addTimeAlive(addressReceived);
                }
                
            } else {
                ArrayList<Address> aux = new ArrayList<>();
                aux.add(new Address(addressReceived, port));
                
                ServiceInfo s = new ServiceInfo(aux, service[i].trim());
                
                p.listServices.add(s);
                
                TimeAlive.getInstance().addTimeAlive(addressReceived);
            }
            
        }
    
    }
    
    /**
     * Método que irá procurar o serviço requisitado (seus ips disponíveis) 
     * e montar/retornar a string que será enviada para o usuário
     * 
     * @param service Parâmetro que informa o serviço que o cliente quer
     * @return String com os Ips e portas dos servidores que tem o serviço
     */
    public String sendService(String[] service){
        
        Proxy p = Proxy.getInstance();
        String answer="";
        
        for(int i=1; i<service.length; i++){
            
            int indexService = this.searchService(service[i].trim());
            
            if (indexService!=-1){
                
                for (int y=0; y<p.listServices.get(indexService).getAddress().size(); y++){
                    
                    answer += p.listServices.get(indexService).getAddress().get(y).getIp() + ":";
                    answer += p.listServices.get(indexService).getAddress().get(y).getPort();
                    if (y<p.listServices.get(indexService).getAddress().size()-1) answer += "||";
                }
                break;
            }
        }
        
        return answer;
    }
    
    
    /**
     * Método que procura o determinado serviço na tabela (x) e retorna o seu índice
     * @param x Termo que irá ser buscado
     * @return Índice do serviço buscado
     */
    public int searchService(String x){
        
        Proxy p = Proxy.getInstance();
        
        for(int i=0; i<p.listServices.size(); i++){
            if (p.listServices.get(i).getService().equals(x)) return i;    
        }
        
        return -1;
    }
    
    /**
     * Método que procura o determinado ip (ip) na lista de um determinado serciço (x)
     * retornando o seu índice
     * 
     * @param x Índice do serviço em que irá fazer a busca
     * @param ip Ip que irá ser buscado
     * @return Índice do ip buscado
     */
    public int searchIp(int x, String ip){
        
        Proxy p = Proxy.getInstance();
        
        for(int i=0; i<p.listServices.get(x).getAddress().size(); i++){
                
            if (p.listServices.get(x).getAddress().get(i).getIp().equals(ip)) return i;
        }
        
        return -1;
    }
    
    
}
