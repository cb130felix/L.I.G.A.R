/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.server;

import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Pattern;
import middleware.ManagerConnection;

/**
 *
 * @author Guto Leoni
 */
public class Service extends Thread{
    
    ManagerConnection mc;
    ArrayList<MapService> mapServices = new ArrayList<MapService>();
    ArrayList<ServiceProcess> processServices = new ArrayList<ServiceProcess>();
    Integer userCounter;

    
    /**
     * Construtor do Serviço.
     * @param mc O gerenciador de conexões para poder utilizar o socket criado.
     * @param process Array com os objetos cujos métodos dos serviços estão implementados
     * @param map Array com os mapeamentos entre nome do serviço e o ID do mesmo
     * @param user Inteiro que conta quantos usuários estão conectados ao servidor
     */
    public Service(ManagerConnection mc,ArrayList<ServiceProcess> process,ArrayList<MapService> map,Integer user) {
        this.mc = mc;
        this.processServices = process;
        this.mapServices = map;
        this.userCounter = user;
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
        byte[] reply;
        boolean check = false;
        int count = 1;
        //System.out.println("ola");
        
        while(check == false){
       
            try {
            
                    data = mc.getData();
                    msg = new String(data, "UTF-8");// 2||Detran||kcd-1232
                    mensages = this.TratarString(msg);
                    
                    idServico = this.descobreIndiceServico(mensages[1]);// NESSE CASO ELE VAI ESTAR PASSANDO Detran
                    
                   
                    if(idServico != -1){

                        reply =  this.processServices.get(idServico).process(mensages[2].getBytes());

                        msg = mensages[0] + "||" + mensages[1] + "||" + new String(reply,"UTF-8");
                        
                        //System.out.println("Olha a mensagem enviada: "+msg);
                        reply = msg.getBytes();
                        
                        if(mc.sendData(reply)){
                        
                            System.out.println("Mandou!");
                        }

                        //this.mc.closeConnection();
                        this.decrementsUserCounter();
                      
                    }
                    
                    else{
                        
                        this.mc.sendData("Servico nao encontrado...".getBytes());
                        
                        //this.decrementsUserCounter();
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
                    
                        check = true;
                        System.out.println("Cliente indisponivel!");
                    
                    }
                    
                }
       
       }// fim do while
    
    
    }// fim do método run
    
    /**
     * Método que decrementa o número de usuários
     */
    public synchronized void decrementsUserCounter(){
    
        this.userCounter = this.userCounter - 1;
        
    }
    
    
    /**
     * Método que quebra as mensagens enviadas pelo cliente
     * @param msg Mensagem a ser quebrada. Padrão: Id_da_mensagem||nome_do_serviço||mensagem. Exemplo: 2||Detran||kcd-1232
     * @return Um vetor de String com os componentes do cabeçalho e a mensagem. Exemplo: mensagens[0] == 2, mensagens[1] == Detran
     * mensagens[3] == kcd-1232
     */
    public String[] TratarString(String msg){
    
        String[] mensages = msg.split(Pattern.quote("||"));
        
        return mensages;
    }
    
    /**
     * Método que, dado o nome do serviço, retorna o índice desse serviço no vetor processServices.
     * @param nome Nome do serviço
     * @return o índice do serviço no vetor. Caso não ache, ele retorna -1
     */
    public int descobreIndiceServico(String nome){
    
        for (int x = 0; x < this.mapServices.size(); x++) {
                
                //System.out.println("Nome do servico procurado: "+nome+" servicos disponiveis: "+this.mapServices.get(x).name);
            
                if(this.mapServices.get(x).name.equals(nome)){
                
                    return x;
                
                }
                
            }
        return -1;
    }
}
