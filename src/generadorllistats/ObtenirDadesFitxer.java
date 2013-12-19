package generadorllistats;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;

import java.util.TreeMap;

public class ObtenirDadesFitxer {

    public static void main(String[] args) throws IOException {

        // Creo el fitxer que conté les paraules per analitzar
        File fitxer = new File("Assignatures.csv");
        String[] paraules = obtenirParaulesFitxer(fitxer);

        for (int i = 0; i < paraules.length; i++) {
            System.out.println(paraules[i]);

        }

    }

    public static String[] obtenirParaulesFitxer(File fitxer)
            throws IOException {
        // Creem el fitxer de lectura i el buffer per a poder llegir-ho
        FileReader lecturaFitxer = new FileReader(fitxer);
        BufferedReader bufferLectura = new BufferedReader(lecturaFitxer);

        // Obtenim la única linia del fitxer per a llegir les paraules
        String linia = bufferLectura.readLine();

        /* 
         while ((bufferLectura.readLine())!=null){
         }
         */
        TreeMap<String, TreeMap<String, Estudiant>> assignaturesEstudiant = new TreeMap<String, TreeMap<String, Estudiant>>();
        TreeMap<String, Estudiant> estudiants = new TreeMap<String, Estudiant>();
        
        // Variables per l'Estudiant
        String cognomsNom, grup;
        // Creem un array de les paraules que hi han.
        String[] paraules = linia.split("\"");

        cognomsNom = paraules[1].toString();
        grup = paraules[3].toString();
      
        String[] assignatures = paraules[5].split(",");

        Estudiant estudiant = new Estudiant(cognomsNom, grup);

        for (String aux : assignatures){
            if (assignaturesEstudiant.containsKey(aux)) {
                estudiants.put(estudiant.getGrup(), estudiant);
                assignaturesEstudiant.put(aux, estudiants);
            }
            else{
                
            }
        }
        
        /* for (String i : paraules) {
         if (comptParaules.containsKey(i)) {
         comptParaules.put(i,0 comptParaules.get(i) + 1);
         percentatge.put(i, (double) comptParaules.get(i) * 100
         / paraules.length + "%");

         } else {
         comptParaules.put(i, 1);
         percentatge.put(i, (double) comptParaules.get(i) * 100
         / paraules.length + "%");
         }
         }
         */
        // Tanquem el fitxer
        bufferLectura.close();

        return arrayAssig;
    }

}
