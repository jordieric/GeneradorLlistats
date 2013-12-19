package generadorllistats;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

import java.util.TreeMap;

public class ObtenirDadesFitxer {

    public static void main(String[] args) throws IOException {

        // Creo el fitxer que conté les paraules per analitzar
        File fitxer = new File("Assignatures.csv");
        obtenirParaulesFitxer(fitxer);
    }

    public static void obtenirParaulesFitxer(File fitxer)
            throws IOException {
        // Creem el fitxer de lectura i el buffer per a poder llegir-ho
        FileReader lecturaFitxer = new FileReader(fitxer);
        BufferedReader bufferLectura = new BufferedReader(lecturaFitxer);

        // Obtenim la única linia del fitxer per a llegir les paraules
        TreeMap<String, TreeMap<String, Estudiant>> assignaturesEstudiant = new TreeMap<String, TreeMap<String, Estudiant>>();
        TreeMap<String, Estudiant> estudiants = new TreeMap<String, Estudiant>();;

        // Variables per l'Estudiant
        String cognomsNom, grup;
        // Creem un array de les paraules que hi han.
        String linia;
        while ((linia = bufferLectura.readLine()) != null) {

            String[] paraules = linia.split("\"");

            cognomsNom = paraules[1].toString();
            grup = paraules[3].toString();

            String[] assignatures = paraules[5].split(",");

            Estudiant estudiant = new Estudiant(cognomsNom, grup);

            for (String aux : assignatures) {
                if (assignaturesEstudiant.containsKey(aux)) {
                    estudiants.put(estudiant.getGrup(), estudiant);
                    assignaturesEstudiant.put(aux, estudiants);
                } else {
                    estudiants = new TreeMap<String, Estudiant>();
                    estudiants.put(estudiant.getGrup(), estudiant);
                    assignaturesEstudiant.put(aux, estudiants);
                }
            }
        }
        System.out.println(assignaturesEstudiant);
        // Tanquem el fitxer
        bufferLectura.close();
    }

}
