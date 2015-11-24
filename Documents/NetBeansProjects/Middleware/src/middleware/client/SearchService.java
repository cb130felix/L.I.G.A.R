/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.client;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import middleware.Address;
import middleware.ServiceInfo;
import middleware.communication.ConnectionManager;

/**
 *
 * @author Leylão
 */
public class SearchService extends Thread {

    ArrayList<ServiceInfo> serviceTable;

    public SearchService(ArrayList<ServiceInfo> serviceTable) {
        this.serviceTable = serviceTable;
    }

    public void run() {

        String[] result = null;
        String[] mTempFirstDivision;
        String[] mTempSecondDivision;
        String nameOfService = "";

        while (true) {

            for (int i = 0; i < serviceTable.size(); i++) {
                nameOfService = serviceTable.get(i).getService();

                String header = "M1";
                try {
                    //fazendo broadcast 
                    String stringOfMessege = header + "||" + nameOfService;
                    byte[] sendData = stringOfMessege.getBytes();
                    ConnectionManager mc = new ConnectionManager();
//            mc.broadcast(sendData, 24240);
//
//            //recebendo resposta do broadcast
//            DatagramPacket receiveData = mc.listenerUDP(24240); //lembrar de definir taime alte(lembrar que isso significa time out)
//            String messege = Arrays.toString(receiveData.getData());
                    String message = " ";
                    int counter = 0;
                    do {

                        System.out.println("->Tentando procurar serviço["+nameOfService+"] tentativa: "+counter);

                        if (counter == 3) {
                            //return 3;
                        }
                        try {
                            
                            Thread.sleep(3000);
                            mc.broadcast(sendData, 24240);
                            
                            do{
                                DatagramPacket receiveData = mc.listenerUDP(6969);
                                message = new String(receiveData.getData(),"UTF-8");
                                result = message.split(Pattern.quote("||"), 2);
                                 
                                 
                                System.out.println("->conseguiu pegar servico:" + message + " / Service: " + result[0]);
                            }while(!result[0].equals(nameOfService));
                            
                            counter++;
                            
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                            System.out.println("->Treta na hora de pegar serviço...");
                        //return 2;
                        }
                    } while (message.equals(" "));

                    Address address;
                    ArrayList<Address> arrayListAddress = new ArrayList<>();

                    mTempFirstDivision = result[1].split(Pattern.quote("||"));
                    

                    for (String mTempFirstDivision1 : mTempFirstDivision) {
                        mTempSecondDivision = mTempFirstDivision1.split(":");
                        address = new Address(mTempSecondDivision[0], Integer.parseInt(mTempSecondDivision[1]));
                        arrayListAddress.add(address);
                    }
                    serviceTable.add(new ServiceInfo(arrayListAddress, nameOfService));
                    for (int j = 0; j < 10; j++) {
                        System.out.println("Adicionando servico; Nome:" + nameOfService + " / endereco: " + arrayListAddress.get(j).getIp() + "/" + arrayListAddress.get(j).getPort());
                    }
                } catch (Exception e) {
                    System.out.println("->Erro de alguma coisa...");
                    //return 1;
                }

                //return 0;
            }
        }

    }

}
