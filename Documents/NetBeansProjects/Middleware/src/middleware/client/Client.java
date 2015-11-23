package middleware.client;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import middleware.Address;
import middleware.communication.ConnectionManager;

/**
 *
 * @author Leylane e Renan
 */
public class Client {

    //cabeçalho da mensagem(cliente/servidor): id;service;message
    // exemplo prático: 200;A;qualquermerda(em json)
    public ArrayList<ServiceInfo> serviceTable = null;
    public ArrayList<String> sendQueue = null;
    public ArrayList<String> receiveQueue = null;

    public Client() {
        serviceTable = new ArrayList<>();
    }

    //@Renan
    //Manda requisição para o servidor de acordo com a serviceTable, se ela estiver vazia, chama o método searchService
    public boolean sendMessage(String message, String service) {
        return true;
    }

    //@Renan
    //Recebe a mensagem dos servidores, de acordo com as requisições enviadas
    public boolean receiveMessage() {
        return true;
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
