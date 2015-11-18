/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware;

/**
 *
 * @author Guto Leoni
 */
public interface ServiceProcess {
    
    public byte[] process(byte[] data);
    
}
