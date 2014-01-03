package generadorllistats;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JOptionPane;
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

public class Fitxer {

    static TreeMap<String, TreeMap<String, Estudiant>> assignaturesEstudiant = null;

    /**
     * Mètode que validarà que el fitxer que conté les dades dels alumnes és del
     * format csv i conté capçalera informativa
     *
     * @param fitxer : Fitxer csv
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
     * Mètode que retornarà el conjunt d'estudiants que estan cursant
     * l'assignatura passada per paràmetre
     *
     * @param conjunt : TreeMap que conté tots els estudiants agrupats per
     * assignatures que cursen
     * @param assignatura : Assignatura que volem analitzar
     */
    public static TreeMap<String, Estudiant> obtenirEstudiants(
            TreeMap<String, TreeMap<String, Estudiant>> conjunt,
            String assignatura) {

        TreeMap<String, Estudiant> estudiants = conjunt.get(assignatura);

        // Obtenim els valors de l'assignatura passada per paràmetre del conjunt
        // (TreeMap passat per paràmetre)
        return estudiants;
    }

    /**
     * Mètode que retorna un TreeMap d'assignatures i TreeMap del grup de
     * l'estudiant i Estudiant. Les dades les rep d'un fitxer csv.
     *
     * @param fitxer : Fitxer csv que conté tots els estudiants
     * @return TreeMap d'estudiants ordenats per assignatures
     * @throws IOException
     */
    public static TreeMap<String, TreeMap<String, Estudiant>> obtenirEstudiantsAssignatures(
            File fitxer) throws IOException {

        // Variables per l'Estudiant
        String cognomsNom, grup;

        // Creo el TreeMap que contindrà tots els estudiants ordenats
        // alfabèticament dins de cada pròpia assignatura que cursin
        TreeMap<String, Estudiant> estudiants = new TreeMap<String, Estudiant>();

        // Creo un TreeMap per a obtenir absolutament tots els estudiants
        // ordenats per les assignatures que cursen, tanmateix per ordre
        // alfabètic
        TreeMap<String, TreeMap<String, Estudiant>> assignaturesEstudiant = new TreeMap<String, TreeMap<String, Estudiant>>();

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

            // Mentre llegim linies del fitxer que no siguin res
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

                // Bucle que recorre l'array d'assignatures i afageix segons
                // convingui el valor i la clau en el TreeMap que convingui
                for (String aux : assignatures) {
                    if (assignaturesEstudiant.containsKey(aux)) {
                        assignaturesEstudiant.get(aux).put(grup,estudiant);
                        System.out.println(cognomsNom + " - " + aux);
                    } else {
                        estudiants = new TreeMap<String, Estudiant>();
                        estudiants.put(grup, estudiant);
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

    /**
     * Mètode que a través d'un array d'assignatures i del fitxer que conté les
     * dades dels alumnes crearà les llistes en format XML
     *
     * @param assignatura : array d'assignatures que l'usuari escollirà
     * @param fitxer : fitxer .csv que conté les dades
     */
    public static void crearLlistaXML(String[] assignatura,
            TreeMap<String, TreeMap<String, Estudiant>> assignaturesEstudiant) {
        try {

            // Creem les variables de creació del document XML
            DocumentBuilderFactory docFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // Element arrel del document. S'anomena llistes
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("llistes");
            doc.appendChild(rootElement);

            // Bucle que es farà per a cada assignatura
            for (int i = 0; i < assignatura.length; i++) {

                // Es crea l'String amb el valor assignatura
                String materia = assignatura[i];

                // Es crea l'element llista
                Element llista = doc.createElement("llista");
                // Es crea l'atribut materia de la llista
                llista.setAttribute("materia", materia);
                rootElement.appendChild(llista);

                // Creem un arrayList que contindrà estudiants
                TreeMap<String, Estudiant> estudiant = obtenirEstudiants(
                        assignaturesEstudiant, assignatura[i]);

                // Obtenim els cognoms i el grup de l'estudiant
                ArrayList<Estudiant> estudiants = new ArrayList<Estudiant>(
                        estudiant.values());

                // Bucle que es crearà per acada estudiant
                for (int e = 0; e < estudiants.size(); e++) {

                    String cognomsNom = estudiants.get(e).getCognomsNom();
                    String grup = estudiants.get(e).getGrup();

                    // Creem l'element estudiant a través del mètode
                    // crearEstudiant
                    rootElement.appendChild(crearEstudiant(doc, cognomsNom,
                            grup));
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
            // Les següents línies són per fer-ho "llegible" per pantalla i
            // tambér per arxiu
            // transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
            // "no");
            transformer.transform(source, result);

            System.out.println("La llista s'ha generat satisfactòriament!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    /**
     * Mètode que crea l'element Estudiant del fitxer XML
     *
     * @param doc : Document a on es crearà l'XML
     * @param cognomsNom : cognomsNom de l'alumne
     * @param grup : grup de l'alumne
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
