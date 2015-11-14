/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware;

/**
 *
 * @author Arthur
 */

import java.util.ArrayList;

public class Proxy{


	ArrayList<ServiceInfo> listServices = null;

	public boolean listenServer(){return true;}
	public boolean listenClient(){return true;}


}