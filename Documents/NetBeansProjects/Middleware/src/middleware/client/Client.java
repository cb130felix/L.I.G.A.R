package middleware.client;


import com.google.gson.Gson;
import java.util.ArrayList;
import middleware.Address;
import middleware.ServiceInfo;

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
    
    /**
     * Configura o client para inciar a troca de mensagens com o servidor
     * @return true ou false, dependendo do sucesso da operação
     */
    
    public boolean startClient(){
        searchService = new SearchService(serviceTable);
        searchService.setName("SearchService");
        searchService.start();
        return true;
    }
    
    /**
     * Espera o fim das transações dos dados solicitados pelo client e então finaliza o Client
     * @return true ou false, dependendo do sucesso da operação
     */
    
    public synchronized boolean stopClient(){
        
        Thread.yield();

        for (int i = 0; i < messageQueue.size(); i++) {
            try{
                messageQueue.get(i).join();
            }catch(Exception ex){
                System.out.println("opa... Erro no join para finalizar a Thread");
            }
        }
        try{
        while(searchService.getState() != Thread.State.WAITING){
            System.out.println("Ela não tava dormindo!");
        }    
        synchronized(searchService){
            searchService.loop = false;
            searchService.notify();
        }
        }catch(Exception ex){}
        return true;
    }

    //@Renan
    //Manda requisição para o servidor de acordo com a serviceTable, se ela estiver vazia, chama o método searchService
    
    
    /**
     * Esse método é responsável pela comunicação de objetos entre o Cliente e o Servidor.
     * O Cliente inicialmente requisita os endereços dos servidores oferecendo o serviço requisitado.
     * Após receber o endereço dos sevidores o Cliente envia e trata o objeto de resposta recebido
     * por meio do método 'handler' da classe que implementa a interface DataHandler. Caso nenhum servidor
     * com o serviço solicitado seja localizado, o client continuar procurando até que consiga enviar seus dados.
     * 
     * Para lidar com os dados recebidos pelo servidor, é necessário implementar uma classe que implementa a interface
     * DataHandler. Esse método recebe o Id da requisição enviada como parâmetro, e o object de resposta que o servidor
     * envia. No corpo do método você deve implementar o código que vai lidar com a resposta recebida pelo servidor.
     * 
     * ATENÇÃO: O método handler recebe um Object como parâmetro. Realize um cast dele para o tipo da classe que
     * é recebida como respsota. Ex:
     * Response r = (Response) o;
     * 
     * 
     * @param question Object enviado ao servidor
     * @param service nome do serviço solicitado
     * @param dataHandler Instancia do Objeto que implementa a classe DataHandler com o método handler sobreescrito
     * @param response O nome da classe do objecto que você recebe como resposta do servidor. Ex: Response.class 
     * @return O id da requisição enviada ao servidor
     */
    
    public int sendMessage(Object question, String service, DataHandler dataHandler, Class response){
        
        Gson gson = new Gson();
        String json = gson.toJson(question);
        String message = service + "||" + json; // adicionando cabeçalho
        return sendMessage(message.getBytes(), service, dataHandler, response);
        
        
    }
    
    private int sendMessage(byte[] message, String service, DataHandler dataHandler, Class c) {

        //Adicionando serviço na serviceTable caso ele não exista
        int i;
        for (i = 0; i < this.serviceTable.size(); i++) {
            if(this.serviceTable.get(i).getService().equals(service)) break;
        }
        if(i == this.serviceTable.size()){
            this.serviceTable.add(new ServiceInfo(new ArrayList<Address>(), service));
        }
        
        int thisId = messageId;
        
        DataSender ds = new DataSender(service, thisId, message, this.serviceTable, dataHandler, searchService, c);
        ds.setName("["+thisId+"]Message");
        messageQueue.add(ds);
        ds.start();
        
        messageId++;
        return thisId;
    
    }
    
    /**
     * Esse método força o Cliente a dessistir de uma determinada conversa com o servidor em andamento,
     * para isso basta informar o id da mensagem enviada ao servidor.
     * @param messadeId numero de Id da mensagem enviada ao servidor, OU -1 para finalizar todas as mensagens em andamento
     * @return 
     */
    public synchronized boolean stopMessage(int messadeId){
    
        ThreadGroup g = new ThreadGroup(null);
        
        
        for (int i = 0; i < this.messageQueue.size(); i++) {
            if((this.messageQueue.get(i).id == messadeId) || (messadeId == -1)){
                this.messageQueue.get(i).dataSent = true;
            }
        }
        
        
        return true;
        
    }

}
