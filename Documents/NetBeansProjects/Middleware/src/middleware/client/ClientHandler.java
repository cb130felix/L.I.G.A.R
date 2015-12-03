/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.client;

/**
 *
 * @author Renan
 */
public interface ClientHandler {
   
    /**
     * ATENÇÃO: O método handler recebe um Object como parâmetro. Realize um cast dele para o tipo da classe que
     * é recebida como respsota. Ex:
     * Resposta r = (Resposta) o
     * @param id
     * @param o 
     */
    public void handler(int id, Object o);    
    
}
