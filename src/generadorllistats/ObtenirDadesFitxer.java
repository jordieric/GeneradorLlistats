import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


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

		// Creem un array de les paraules que hi han
		String[] paraules = linia.split("\"");
		String[] patata = paraules[5].split(",");
		
		// Tanquem el fitxer
		bufferLectura.close();

		return paraules;
	}
	
}
