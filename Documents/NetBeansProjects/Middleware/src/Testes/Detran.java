/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import middleware.server.ServiceProcess;

/**
 *
 * @author Guto Leoni
 */
public class Detran implements ServiceProcess {
    
    public byte[] process(byte[] data) {
        
        String placa ="";
        try {
            placa = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Detran.class.getName()).log(Level.SEVERE, null, ex);
        }
;
        String importante="";
        ArrayList<String> aux = new ArrayList<>();
        boolean legend=false;
        URL url;
        
        try {
            
            url = new URL("http://online4.detran.pe.gov.br//ServicosWeb/Veiculo/frmDetalhamentoDebitos.aspx?pPlaca="+ placa.replace("-", "") +"&pExtrato=N&pTerceiros=I&pPlacaOutraUF=N");
            
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();             
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            for (String line; (line = reader.readLine()) != null;) {
                
                if ((legend && !line.contains("value=\"Imprimir Detalhamento de Débitos\"")) || line.contains("<legend>Detalhamento de Débitos")){
                    legend=true;
                    
                    for (int i=0; i<line.length(); i++){
                        
                        if (!line.substring(i, i+1).equals("<")){
                            
                            importante = importante + line.substring(i, i+1);
                        }
                        
                        else if (line.substring(i, i+1).equals("<")){
                            
                            while(true){
                                
                                i++;
                                if (line.substring(i, i+1).equals(">")) break;
                            }
                            
                            aux.add(importante);
                            importante="";
                        }
                    }
                }
                
                for (int i=0; i<aux.size(); i++){
                    if (aux.get(i).contains("   ") || aux.get(i).contains("&nbsp") || aux.get(i).contains("\t") || aux.get(i).length() < 2)                         
                        aux.remove(i);
                    else aux.set(i, removeAcentos(aux.get(i)));
                }  
            } 
            
            if (!legend) return "Placa nao existe na base de dados do Detran".getBytes();
                    
        } catch (Exception ex) {
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
        
        
        return importante.getBytes();
    
    }
    
    
    public static String removeAcentos(String str) {
 
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
    
}
