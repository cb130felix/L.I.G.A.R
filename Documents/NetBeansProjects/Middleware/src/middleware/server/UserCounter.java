/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.server;

/**
 *
 * @author Guto Leoni
 */
public class UserCounter {
    
    public int cont;

    public UserCounter() {
    }

    public UserCounter(int cont) {
        this.cont = cont;
    }
    
    public synchronized void IncrementUser(){
    
    
        this.cont++;
        
    }
    
    synchronized void decrementsUserCounter(){
    
        this.cont--;
        
    }
    
}
