/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware;

import java.util.ArrayList;

/**
 *
 * @author Lylane e Renan
 */
public class Client{

    
    //cabeçalho da mensagem(cliente/servidor): id;service;message
    
    public ArrayList<ServiceInfo> serviceTable = null;
    public ArrayList<String> sendQueue = null;
    public ArrayList<String> receiveQueue = null;
    
    //@Renan
    //Manda requisição para o servidor de acordo com a serviceTable, se ela estiver vazia, chama o método searchService
    public boolean sendMessage(String message, String service){
        return true;
    }
    
    //@Renan
    //Recebe a mensagem dos servidores, de acordo com as requisições enviadas
    public boolean receiveMessage(){
        return true;    
    }
    
    //Leylane
    //Manda broadcast para os e proxys preenche a tabela 'serviceTable'
    public String searchService(String service){
        return null;
    }
    
    
    
}
