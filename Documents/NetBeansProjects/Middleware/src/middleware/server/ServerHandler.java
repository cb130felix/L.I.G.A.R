/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.server;

/**
 * Interface a ser implementada pela classe que tem os métodos do serviço.
 * @author Guto Leoni
 */
public interface ServerHandler {
    
    public Object handler(Object o);
    
}
