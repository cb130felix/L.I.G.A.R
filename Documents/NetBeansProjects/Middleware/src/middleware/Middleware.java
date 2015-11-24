/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware;

import java.net.SocketException;
import middleware.client.Client;
import middleware.proxy.Proxy;
import middleware.server.Server;

/**
 *
 * @author LIGAR
 */
public class Middleware {
    
    public Client client;
    public Proxy proxy;
    public Server server;

    public Middleware() throws SocketException, InterruptedException {
        this.client = new Client();
        this.proxy = Proxy.getInstance();
        this.server = new Server();
    }
    
    
    
}
