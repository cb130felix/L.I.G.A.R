
package services;

import Testes.Pergunta;
import Testes.Resposta;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import static java.lang.Thread.sleep;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import middleware.server.ServiceProcess;

/**
 *
 * @author Guto Leoni
 */
public class Detran implements ServiceProcess {
    
    
    @Override
    public Object process(Object obj) {
        
        Pergunta perg = (Pergunta) obj;
        Resposta r ;
        
        /*String placa ="";

        try {
            placa = new String(data, "UTF-8");
        } catch (Exception ex) {
            System.out.println("S:erro " + ex);
        }*/
        
        String importante="";
        ArrayList<String> aux = new ArrayList<>();
        boolean legend=false;
        URL url;
        
        try {
            Resposta resp = new Resposta("Sport Campeão de 1987");
            return resp;
            
//            url = new URL("http://online4.detran.pe.gov.br//ServicosWeb/Veiculo/frmDetalhamentoDebitos.aspx?pPlaca="+ perg.placa.replace("-", "") +"&pExtrato=N&pTerceiros=I&pPlacaOutraUF=N");
//            
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();             
//            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            
//            for (String line; (line = reader.readLine()) != null;) {
//                
//                if ((legend && !line.contains("value=\"Imprimir Detalhamento de Débitos\"")) || line.contains("<legend>Detalhamento de Débitos")){
//                    legend=true;
//                    
//                    for (int i=0; i<line.length(); i++){
//                        
//                        if (!line.substring(i, i+1).equals("<")){
//                            
//                            importante = importante + line.substring(i, i+1);
//                        }
//                        
//                        else if (line.substring(i, i+1).equals("<")){
//                            
//                            while(true){
//                                
//                                i++;
//                                if (line.substring(i, i+1).equals(">")) break;
//                            }
//                            
//                            aux.add(importante);
//                            importante="";
//                        }
//                    }
//                }
//                
//                for (int i=0; i<aux.size(); i++){
//                    if (aux.get(i).contains("   ") || aux.get(i).contains("&nbsp") || aux.get(i).contains("\t") || aux.get(i).length() < 2)                         
//                        aux.remove(i);
//                    else aux.set(i, removeAcentos(aux.get(i)));
//                }  
//            } 
//            
//            if (!legend) return "Placa nao existe na base de dados do Detran".getBytes();
                    
        } catch (Exception ex) {
            try {
            
                sleep(500);
                r = new Resposta("sem internet =[");
                
               
            } catch (Exception e) {
            }
            
        }
        
        if (aux.isEmpty()) return "semnet".getBytes();
        
        for (int i=0; i<aux.size(); i++){
            
            if (aux.get(i).contains("Detalhamento de Debitos")) importante += "\tDetalhamento de Debitos - Placa: " + aux.get(i+1) + "\n";
            
            else if (aux.get(i).contains("Restricao")){
                if (aux.get(i+1).contains("LICENCIAMENTO")) {
                    importante += "\tRESTRICAO: NADA CONSTA\n"; i--;
                }
                else importante += "\tRESTRICAO: "+ aux.get(i+1) +"\n";
            }
            
            else if (aux.get(i).contains("LICENCIAMENTO")) {
                if(!aux.get(i+1).contains("NADA CONSTA")) importante += "\t" + aux.get(i).replace(":", "") + ": HA DEBITOS\n";
                else importante += "\t" + aux.get(i).replace(":", "") + ": " + aux.get(i+1) + "\n";
            }
            
            else if (aux.get(i).contains("TAXAS DETRAN")) importante += "\t" + aux.get(i).replace(":", "") + ": " + aux.get(i+1) + "\n";
            
            else if (aux.get(i).contains("Debitos")) {
                importante += "\n\tDebitos\n"; i--;
            }
            
            else if (aux.get(i).contains("MULTAS")) importante += "\t" + aux.get(i).replace(":", "") + ": " + aux.get(i+1) + "\n";
            
            else if (aux.get(i).contains("Total em Cota Unica")) {
                importante += "\t" + aux.get(i).replace(":", "") + ": " + aux.get(i+1) + "\n"; i--;
            }
            
            i++;
        }
        
        Resposta resp = new Resposta(importante);
        return resp;
    }
    
    
    public static String removeAcentos(String str) {
 
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
    
}
