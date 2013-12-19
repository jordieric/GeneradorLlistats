package generadorllistats;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

import java.util.SortedMap;
import java.util.TreeMap;

public class ObtenirDadesFitxer {

	
	public static void main(String[] args) throws IOException {

		// Creo el fitxer que conté les paraules per analitzar
		File fitxer = new File("Assignatures.csv");
		String[] paraules = obtenirParaulesFitxer(fitxer);
		
		for (int i = 0; i < paraules.length; i++){
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
              
               /* String capcalera = " #,\"00_NOM\",\"01_GRUPSCLASSE\",\"02_MATRICULADES\"";
               
                int nlinies = 0;
                if (linia.equals(capcalera)){
                    System.out.println("Correcte");
                }
                while ((bufferLectura.readLine())!=null){
                   nlinies++; 
               }
                System.out.println(nlinies);
		*/// Creem un array de les paraules que hi han
                String cognomsNom, grup;
		String[] paraules = linia.split("\"");
                
                cognomsNom = paraules[1].toString();
                grup = paraules[3].toString();
		String[] assignatures = paraules[5].split(",");
                
                Estudiant estudiant = new Estudiant(cognomsNom, grup, assignatures);
                
                System.out.println(estudiant.getCognomsNom() + estudiant.getGrup() + estudiant.getAssignatures().toString());
                
                //SortedMap<String, Estudiant> alumnesAssignatures = new TreeMap<String, Estudiant>();
                
		// Tanquem el fitxer
		bufferLectura.close();

		return assignatures ;
	}
	
}
