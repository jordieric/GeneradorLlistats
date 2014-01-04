package generadorllistats;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;

import javax.swing.JOptionPane;

public class Fitxer {

	// Variable estàtica que contindrà les dades de tots els estudiants i les
	// seves assignatures
	static TreeMap<String, TreeMap<String, Estudiant>> assignaturesEstudiant = null;

	/**
	 * Mètode que validarà que el fitxer que conté les dades dels alumnes és del
	 * format csv i conté capçalera informativa
	 * 
	 * @param fitxer
	 *            : Fitxer csv
	 * @return True o False segons si el fitxer és correcte o no ho és
	 * @throws IOException
	 */
	public static boolean validarFitxer(File fitxer, String capcaleraFitxer)
			throws IOException {

		// Variable que conté la capçalera que hauria de tenir el fitxer
		String capcalera = " #,\"00_NOM\",\"01_GRUPSCLASSE\",\"02_MATRICULADES\"";

		// Comprovem extensió del fitxer
		if (fitxer.getName().endsWith(".csv")) {
			// Comprovem que la capçalera és exactament igual que la variable
			// capcalera declarada
			if (capcaleraFitxer.equals(capcalera)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Mètode que retorna un TreeMap d'assignatures i TreeMap del grup de
	 * l'estudiant i Estudiant. Les dades les rep d'un fitxer csv. Es pot dir
	 * que és el GET i el SET de la variable assignaturesEstudiant
	 * 
	 * @param fitxer
	 *            : Fitxer csv que conté tots els estudiants
	 * @return TreeMap d'estudiants ordenats per assignatures
	 * @throws IOException
	 */
	public static TreeMap<String, TreeMap<String, Estudiant>> obtenirEstudiantsAssignatures(
			File fitxer) throws IOException {

		// Variables d'informació de cada Estudiant
		String cognomsNom, grup;

		// Creo el TreeMap que contindrà tots els estudiants ordenats
		// alfabèticament dins de cada pròpia assignatura que cursin
		TreeMap<String, Estudiant> estudiants = new TreeMap<String, Estudiant>();

		// Creo un TreeMap per a obtenir absolutament tots els estudiants
		// ordenats per les assignatures que cursen, tanmateix per ordre
		// alfabètic
		assignaturesEstudiant = new TreeMap<String, TreeMap<String, Estudiant>>();

		// Declarem les variables de lectura i tractament (Buffer) del fitxer
		// que conté les llistes dels alumnes i les seves assignatures, així com
		// el seu grup
		FileReader lecturaFitxer = new FileReader(fitxer);
		BufferedReader bufferLectura = new BufferedReader(lecturaFitxer);

		// Creem la variable linia que contindrà la primera linia del fitxer
		// (Contindrà la capcalera informatica del fitxer). D'aquesta manera
		// podrem validar el fitxer
		String linia = bufferLectura.readLine();

		// Cridem el mètode validarFitxer
		if (validarFitxer(fitxer, linia)) {

			// Mentre llegim linies del fitxer que continguin estudiant
			while (((linia = bufferLectura.readLine())) != null) {

				// Creem un array d'String que contindrà les paraules de la
				// linia llegida
				String[] paraules = linia.split("\"");

				// Obtenim el nom, cognoms i grup dels estudiants a través de
				// l'array creat
				cognomsNom = paraules[1].toString();
				grup = paraules[3].toString();

				// Guardem les assignatures de l'estudiant en l'array
				// assignatures
				String[] assignatures = paraules[5].split(",");

				// Creem l'estudiant a través de les dades obtingudes
				Estudiant estudiant = new Estudiant(cognomsNom, grup);

				// Bucle que recorre l'array d'assignatures i afageix, segons
				// convingui, el valor i la clau en el TreeMap que convingui
				for (String aux : assignatures) {
					if (assignaturesEstudiant.containsKey(aux)) {
						// Creem el Treemap amb les dades que ja conté i li
						// afegim el nou estudiant
						estudiants = new TreeMap<String, Estudiant>(
								assignaturesEstudiant.get(aux));
						estudiants.put(cognomsNom, estudiant);

						// Afegim el treemap estudiants acabat de crear al
						// treemap general
						assignaturesEstudiant.put(aux, estudiants);
					} else {
						// Creem un nou treemap i li afegim l'estudiant que
						// estem tractant
						estudiants = new TreeMap<String, Estudiant>();
						estudiants.put(cognomsNom, estudiant);

						// Afegim el treemap creat al treemap general
						assignaturesEstudiant.put(aux, estudiants);
					}
				}
			}
		} else {
			// Si el fitxer no és valid ho indiquem a l'usuari
			JOptionPane.showMessageDialog(null, "Aquest fitxer no és vàlid",
					"Error", JOptionPane.ERROR_MESSAGE);
		}

		// Tanquem el buffer de lectura
		try {
			bufferLectura.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Retornem el TreeMap que com a clau conté les assignatures, i com a
		// valor un altre TreeMap format per la clau grup de l'estudiant i com a
		// valor Estudiant
		return assignaturesEstudiant;
	}
}
