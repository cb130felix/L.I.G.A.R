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
    public ArrayList<ServiceInfo> serviceTable = null; // IMPORTANTE: serviceTable É UMA REGIÃO CRÍTICA!!!!!!!
    

    public Client() {
        messageId = 0;
        serviceTable = new ArrayList<>();
        
        
        //teste de serviços
        Address address = new Address("128.0.0.1", 24251);
        ArrayList<Address> lista = new ArrayList<>();
        lista.add(address);
        ServiceInfo sf = new ServiceInfo(lista, "detran");
        this.serviceTable.add(sf);
        
        
        
    }

    //@Renan
    //Manda requisição para o servidor de acordo com a serviceTable, se ela estiver vazia, chama o método searchService
    public int sendMessage(String message, String service, DataHandler dataHandler) {
        
        message = service + "||" + message; // adicionando cabeçalho
        
        DataSender ds = new DataSender(service, messageId, message.getBytes(), this.serviceTable, dataHandler);
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
    public int searchService(String service, String nameOfService) {
        try {
            //fazendo broadcast 
            String stringOfMessege = service + "||" + nameOfService;
            byte[] sendData = stringOfMessege.getBytes();
            ConnectionManager mc = new ConnectionManager();
            mc.broadcast(sendData, 24240);

            //recebendo resposta do broadcast
            DatagramPacket receiveData = mc.listenerUDP(24240);
            String messege = Arrays.toString(receiveData.getData());

            int counter = 0;
            while (messege.equals(" ")) {
                if (counter == 3) {
                    return 3;
                }
                try {
                    Thread.sleep(3000);
                    mc.broadcast(sendData, 24240);
                    receiveData = mc.listenerUDP(24240);
                    messege = Arrays.toString(receiveData.getData());

                    counter++;
                } catch (InterruptedException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    return 2;
                }
            }

            Address address;
            ArrayList<Address> arrayListAddress = new ArrayList<>();

            String[] mTempFirstDivision = messege.split(Pattern.quote("||"));
            String[] mTempSecondDivision;

            for (String mTempFirstDivision1 : mTempFirstDivision) {
                mTempSecondDivision = mTempFirstDivision1.split(":");
                address = new Address(mTempSecondDivision[0], Integer.parseInt(mTempSecondDivision[1]));
                arrayListAddress.add(address);
            }
            serviceTable.add(new ServiceInfo(arrayListAddress, service));

        } catch (Exception e) {
            return 1;
        }

        return 0;
    }

}
