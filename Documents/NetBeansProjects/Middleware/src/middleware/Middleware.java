/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware;

import middleware.client.Client;
import middleware.proxy.Proxy;
import middleware.server.Server;

/**
 *
 * @author LIGAR
 */
public class Middleware {
    
    Client client;
    Proxy proxy;
    Server server;

    public Middleware(Client client, Proxy proxy, Server server) {
        this.client = client;
        this.proxy = proxy;
        this.server = server;
    }
    
    
    
}
