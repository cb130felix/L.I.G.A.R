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
 * @author Leylane e Renan
 */
public class Client {

    //cabeçalho da mensagem(cliente/servidor): id||service||message
    // exemplo prático: 200||A||qualquermerda(em json)
    
    int messageId;
    ArrayList<DataSender> messageQueue;
    public ArrayList<ServiceInfo> serviceTable = null; // IMPORTANTE: serviceTable É UMA REGIÃO CRÍTICA!!!!!!!
    SearchService searchService;

    public Client() throws InterruptedException {
        messageId = 0;
        messageQueue = new ArrayList<>();
        serviceTable = new ArrayList<>();
        
        
        
    }
    
    public boolean startClient(){
        searchService = new SearchService(serviceTable);
        searchService.start();
        return true;
    }
    
    public boolean stopClient(){
        
        for (int i = 0; i < messageQueue.size(); i++) {
            try{
                messageQueue.get(i).join();
            }catch(Exception ex){
                
            }
        }
        synchronized(searchService){
            searchService.loop = false;
            searchService.notify();
        }
        return true;
    }

    //@Renan
    //Manda requisição para o servidor de acordo com a serviceTable, se ela estiver vazia, chama o método searchService
    public int sendMessage(String message, String service, DataHandler dataHandler) {

        //Adicionando serviço na serviceTable caso ele não exista
        int i;
        for (i = 0; i < this.serviceTable.size(); i++) {
            if(this.serviceTable.get(i).getService().equals(service)) break;
        }
        if(i == this.serviceTable.size()){
            this.serviceTable.add(new ServiceInfo(new ArrayList<Address>(), service));
        }
        
        
        message = messageId + "||" + service + "||" + message; // adicionando cabeçalho
        
        DataSender ds = new DataSender(service, messageId, message.getBytes(), this.serviceTable, dataHandler, searchService);
        messageQueue.add(ds);
        ds.start();
        
        messageId++;
        return messageId;
    
    }


    /**
     * Método que manda broadcast para os proxys e preenche a tabela
     * 'serviceTable'
     *
     * @param service - código do serviço a ser solicitado
     * @param nameOfService - nome do serviço a ser solicitado
     * @return int - retorna '0' se preencher o arraylist, retorna '1' se não
     * conseguir realizar o broadcast, retorna '2' em caso de erro na thread de
     * espera, e retorna '3' caso não haja servidor disponível para o serviço
     * requisitado.
     */
//    public int searchService(String nameOfService) {
//        String cab = "M1";
//        try {
//            //fazendo broadcast 
//            String stringOfMessege = cab + "||" + nameOfService;
//            byte[] sendData = stringOfMessege.getBytes();
//            ConnectionManager mc = new ConnectionManager();
////            mc.broadcast(sendData, 24240);
////
////            //recebendo resposta do broadcast
////            DatagramPacket receiveData = mc.listenerUDP(24240); //lembrar de definir taime alte(lembrar que isso significa time out)
////            String messege = Arrays.toString(receiveData.getData());
//            String message = " "; 
//            int counter = 0;
//            do{
//
//                if (counter == 3) {
//                    return 3;
//                }
//                try {
//                    Thread.sleep(3000);
//                    mc.broadcast(sendData, 24240);
//                    DatagramPacket receiveData = mc.listenerUDP(24240);
//                    message = Arrays.toString(receiveData.getData());
//
//                    counter++;
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
//                    return 2;
//                }
//            }while (message.equals(" "));
//
//            Address address;
//            ArrayList<Address> arrayListAddress = new ArrayList<>();
//
//            String[] mTempFirstDivision = message.split(Pattern.quote("||"));
//            String[] mTempSecondDivision;
//
//            for (String mTempFirstDivision1 : mTempFirstDivision) {
//                mTempSecondDivision = mTempFirstDivision1.split(":");
//                address = new Address(mTempSecondDivision[0], Integer.parseInt(mTempSecondDivision[1]));
//                arrayListAddress.add(address);
//            }
//            serviceTable.add(new ServiceInfo(arrayListAddress, nameOfService));
//
//        } catch (Exception e) {
//            return 1;
//        }
//
//        return 0;
//    }

}
