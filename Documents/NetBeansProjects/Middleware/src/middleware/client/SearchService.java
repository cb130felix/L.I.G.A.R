/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.client;

import java.net.DatagramPacket;
import java.util.ArrayList;
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

    
    public boolean loop = true;
    ArrayList<ServiceInfo> serviceTable;

    public SearchService(ArrayList<ServiceInfo> serviceTable) {
        this.serviceTable = serviceTable;
    }
    

    public void run() {

        
        ArrayList<Address> arrayListAddress = new ArrayList<Address>();
        String[] result = null;
        String[] mTempFirstDivision;
        String[] mTempSecondDivision;
        String nameOfService = "";
        
        while (loop) {

            for (int i = 0; i < serviceTable.size(); i++) {
                System.out.println("ciclo");
                if(serviceTable.get(i).getAddress().size() <= 0){
                    nameOfService = serviceTable.get(i).getService();

                    String header = "M1";
                    try {
                        //fazendo broadcast 
                        String stringOfMessege = header + "||" + nameOfService;
                        byte[] sendData = stringOfMessege.getBytes();
                        
    //            mc.broadcast(sendData, 24240);
    //
    //            //recebendo resposta do broadcast
    //            DatagramPacket receiveData = mc.listenerUDP(24240); //lembrar de definir taime alte(lembrar que isso significa time out)
    //            String messege = Arrays.toString(receiveData.getData());
                        String message = " ";
                        do {
                            System.out.println("->Tentando procurar serviço: ["+nameOfService+"]");
                            ConnectionManager mc = new ConnectionManager();
                            mc.broadcast(sendData, 24240);
                            do{
                                System.out.println("------------------------travei");
                                
                                DatagramPacket receiveData = mc.listenerUDP(6969, true); //precisa colocar um timeout nesse socket aqui aqui
                                System.out.println("----------------------detravei!");
                                if(receiveData != null){
                                    //conseguiu mensagem do proxy
                                    message = new String(receiveData.getData(),"UTF-8");
                                    result = message.split(Pattern.quote("||"), 2);
                                    System.out.println("->conseguiu pegar servico:" + message + " / Service: " + result[0]);
                                }else{
                                    //proxy não respondeu
                                    break;
                                    
                                }
                                
                            }while(!result[0].equals(nameOfService));
                            
                        } while (message.equals(" ") );



                        mTempFirstDivision = result[1].split(Pattern.quote("||"));


                        for (String mTempFirstDivision1 : mTempFirstDivision) {

                            mTempSecondDivision = mTempFirstDivision1.split(Pattern.quote(":"));
                            System.out.println("------> CHEGOU AQUI!!!");
                            System.out.println("------>Total: " + mTempFirstDivision1);
//                            arrayListAddress.add(new Address(mTempSecondDivision[0], Integer.parseInt(mTempSecondDivision[1].trim())));
                            serviceTable.get(i).getAddress().add(new Address(mTempSecondDivision[0], Integer.parseInt(mTempSecondDivision[1].trim())));
                            System.out.println("------>p1: " + mTempSecondDivision[0] + " / p2:|" + Integer.parseInt(mTempSecondDivision[1].trim()) +"|");
                            System.out.println("------>Endereco adicionado ao servico.");

                        }

//                        serviceTable.add(new ServiceInfo(arrayListAddress, nameOfService));

                    } catch (Exception e) {
                        System.out.println("->Erro de alguma coisa...");
                        //return 1;
                    }

                    //return 0;
                }
                System.out.println("ciclo");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(SearchService.class.getName()).log(Level.SEVERE, null, ex);
            }
            synchronized(this){
            
            System.out.println("hey!");
                try {
                    System.out.println("dormindo zZzZzzz");
                    this.wait();

                } catch (InterruptedException ex) {
                    Logger.getLogger(SearchService.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            }
        }

    }

}
