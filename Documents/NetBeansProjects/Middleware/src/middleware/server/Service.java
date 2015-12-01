/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.server;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.regex.Pattern;
import middleware.communication.ConnectionManager;

/**
 *
 * @author Guto Leoni
 */
public class Service extends Thread{
    
    ConnectionManager mc;
    ArrayList<MapService> mapServices = new ArrayList<MapService>();
    ArrayList<ServiceProcess> processServices = new ArrayList<ServiceProcess>();
    public UserCounter userCounter;
    public ArrayList<Class> classObject = new ArrayList<Class>();

    
    /**
     * Construtor do Serviço.
     * @param mc O gerenciador de conexões para poder utilizar o socket criado.
     * @param process Array com os objetos cujos métodos dos serviços estão implementados
     * @param map Array com os mapeamentos entre nome do serviço e o ID do mesmo
     * @param user Inteiro que conta quantos usuários estão conectados ao servidor
     */
    public Service(ConnectionManager mc,ArrayList<ServiceProcess> process,ArrayList<MapService> map,UserCounter user,ArrayList<Class> obj) {
        this.mc = mc;
        this.processServices = process;
        this.mapServices = map;
        this.userCounter = user;
        this.classObject = obj;
    }

    /**
     * Método que recebe a mensagem do servidor, faz todo o processamento processamento da requisição e depois retorna a resposta
     * para o cliente.
     */
    public void run(){
    
        byte[] data;
        String msg;
        String[] mensages;
        int idServico;
        String reply;
        boolean check = false;
        int count = 1;
        //System.out.println("ola");
        
        while(check == false){
       
            try {
            
                    data = mc.getData();
                    msg = new String(data, "UTF-8");//Detran||kcd-1232
                    mensages = this.SeparateString(msg);
                    
                    idServico = this.discoveryIndexService(mensages[0]);// NESSE CASO ELE VAI ESTAR PASSANDO Detran
                    
                    if(idServico != -1){
                        
                        reply = this.ConvertStringToObject(mensages[1], idServico);// Passando o Json e o id do serviço
                        
                        //System.out.println("Olha a mensagem enviada: "+reply);
                        
                        if(mc.sendData(reply.getBytes())){
                        
                            this.decrementsUserCounter();
                            check = true;
                            //System.out.println("contador: "+this.userCounter.cont);
                            
                        }

                      
                    }
                    
                    else{
                        
                        this.mc.sendData("Servico nao encontrado...".getBytes());
                        this.decrementsUserCounter();
                        check = true;
                        
                    }
            
                }catch (Exception e) {
        
                    //System.out.println("Erro ao processar a requisicao");
                    if(count <= 3){
                    
                        System.out.println("Tentativa "+count+" de enviar a resposta");
                        count++;
                        
                        try {
                            sleep(3000);
                        } catch (Exception e1) {
                        }
                    }
                    else{
                    
                        this.decrementsUserCounter();
                        check = true;
                        System.out.println("Cliente indisponivel!");
                    
                    }
                    
                }
            
       }// fim do while
        
        
        this.mc.closeConnection();
    
    }// fim do método run
    
    /**
     * Método que decrementa o número de usuários
     */
    public synchronized void decrementsUserCounter(){
    
        this.userCounter.cont = this.userCounter.cont - 1;
        
    }
    
    
    /**
     * Método que quebra as mensagens enviadas pelo cliente
     * @param msg Mensagem a ser quebrada. Padrão: Id_da_mensagem||nome_do_serviço||mensagem. Exemplo: 2||Detran||kcd-1232
     * @return Um vetor de String com os componentes do cabeçalho e a mensagem. Exemplo: mensagens[0] == 2, mensagens[1] == Detran
     * mensagens[3] == kcd-1232
     */
    public String[] SeparateString(String msg){
    
        String[] mensages = msg.split(Pattern.quote("||"));
        
        return mensages;
    }
    
    /**
     * Método que, dado o nome do serviço, retorna o índice desse serviço no vetor processServices.
     * @param nome Nome do serviço
     * @return o índice do serviço no vetor. Caso não ache, ele retorna -1
     */
    public int discoveryIndexService(String nome){
    
        for (int x = 0; x < this.mapServices.size(); x++) {
                
                //System.out.println("Nome do servico procurado: "+nome+" servicos disponiveis: "+this.mapServices.get(x).name);
            
                if(this.mapServices.get(x).name.equals(nome)){
                
                    return x;
                
                }
                
            }
        return -1;
    }
    
    /**
     * Método que recebe o json, o converte em um objeto e o passa para o serviço que o servidor oferece.
     * Depois ele pega o objeto retornado pelo serviço e o transforma em um json, que será retornado ao cliente.
     * @param mensage json do objeto recebido pelo cliente
     * @param id id do serviço que irá receber o objeto
     * @return o json do objeto que será enviao ao cliente
     */
    public String ConvertStringToObject(String mensage,int id){
    
                        Gson gson = new Gson();
                        
                        Object object = gson.fromJson(mensage, this.classObject.get(id));
                        
                        object =  this.processServices.get(id).process(object);
                        
                        return gson.toJson(object);
    }
    
}
