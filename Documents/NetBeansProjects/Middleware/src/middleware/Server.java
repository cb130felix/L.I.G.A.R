/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package middleware;

/**
 *
 * @author Guto
 */

import java.util.ArrayList;

public class Server{

	private ArrayList<ServiceInfo> servicesList = null;
	private int userCounter;

	public boolean addService(){return true;}
	public boolean removeService(){return true;}
	public void incrementUserCounter(){}
	public void listenRequest(){}
	public void keepServerAlive(){}

}