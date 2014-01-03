package generadorllistats;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class FitxerXML {

	private String[] assignatura;

	public FitxerXML(String[] assignatura,
			TreeMap<String, TreeMap<String, Estudiant>> assignaturesEstudiant) {
		this.assignatura = assignatura;
	}

	public String[] getAssignatura() {
		return assignatura;
	}

	public void setAssignatura(String[] assignatura) {
		this.assignatura = assignatura;
	}

	/**
	 * M�tode que retornar� el conjunt d'estudiants que estan cursant
	 * l'assignatura passada per par�metre
	 * 
	 * @param conjunt
	 *            : TreeMap que cont� tots els estudiants agrupats per
	 *            assignatures que cursen
	 * @param assignatura
	 *            : Assignatura que volem analitzar
	 */
	public static TreeMap<String, Estudiant> obtenirEstudiants(
			TreeMap<String, TreeMap<String, Estudiant>> conjunt,
			String assignatura) {

		TreeMap<String, Estudiant> estudiants = conjunt.get(assignatura);

		// Obtenim els valors de l'assignatura passada per par�metre del conjunt
		// (TreeMap passat per par�metre)
		return estudiants;
	}

	/**
	 * M�tode que a trav�s d'un array d'assignatures i del fitxer que cont� les
	 * dades dels alumnes crear� les llistes en format XML
	 * 
	 * @param assignatura
	 *            : array d'assignatures que l'usuari escollir�
	 * @param fitxer
	 *            : fitxer .csv que cont� les dades
	 */
	public static void crearLlistaXML(String[] assignatura,
			TreeMap<String, TreeMap<String, Estudiant>> assignaturesEstudiant) {
		try {

			// Creem les variables de creaci� del document XML
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// Element arrel del document. S'anomena llistes
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("llistes");
			doc.appendChild(rootElement);

			// Bucle que es far� per a cada assignatura
			for (int i = 0; i < assignatura.length; i++) {

				// Es crea l'String amb el valor assignatura
				String materia = assignatura[i];

				// Es crea l'element llista
				Element llista = doc.createElement("llista");
				// Es crea l'atribut materia de la llista
				llista.setAttribute("materia", materia);
				rootElement.appendChild(llista);

				// Creem un arrayList que contindr� estudiants
				TreeMap<String, Estudiant> estudiant = obtenirEstudiants(
						assignaturesEstudiant, assignatura[i]);

				// Obtenim els cognoms i el grup de l'estudiant
				ArrayList<Estudiant> estudiants = new ArrayList<Estudiant>(
						estudiant.values());

				// Bucle que es crear� per acada estudiant
				for (int e = 0; e < estudiants.size(); e++) {

					String cognomsNom = estudiants.get(e).getCognomsNom();
					String grup = estudiants.get(e).getGrup();

					// Creem l'element estudiant a trav�s del m�tode
					// crearEstudiant
					llista.appendChild(crearEstudiant(doc, cognomsNom, grup));
				}

			}

			// Enregistrar el contingut a disc
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(
					"llistaGenerada.xml"));

			// Si es vol veure el resultat per la consola, enlloc d'enviar a
			// arxiu
			// StreamResult result = new StreamResult(System.out);
			// Les seg�ents l�nies s�n per fer-ho "llegible" per pantalla i
			// tamb�r per arxiu
			// transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			// transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
			// "no");
			transformer.transform(source, result);

			System.out.println("La llista s'ha generat satisfact�riament!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	/**
	 * M�tode que crea l'element Estudiant del fitxer XML
	 * 
	 * @param doc
	 *            : Document a on es crear� l'XML
	 * @param cognomsNom
	 *            : cognomsNom de l'alumne
	 * @param grup
	 *            : grup de l'alumne
	 * @return L'element Alumne
	 */
	static private Element crearEstudiant(Document doc, String cognomsNom,
			String grup) {
		// Creem l'element alumne
		Element alumne = doc.createElement("alumne");

		// Elements de l'alumne (Cognom i grup)
		Element cognom = doc.createElement("cognomsNom");
		cognom.appendChild(doc.createTextNode(cognomsNom));
		alumne.appendChild(cognom);

		Element classe = doc.createElement("grup");
		classe.appendChild(doc.createTextNode(grup));
		alumne.appendChild(classe);

		// Retorna l'element alumne acabat de crear
		return alumne;
	}

}
