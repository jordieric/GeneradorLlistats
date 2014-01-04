package generadorllistats;

import java.io.File;
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

/**
 * Classe que creara el fitxer XML amb les dades dels alumnes
 *
 * @author jordi
 */
public class FitxerXML {

    //Array d'strings que seran les assignatures
    private String[] assignatura;
    //TreeMap que contindra les dades dels estudiants i les assignatures
    private static TreeMap<String, TreeMap<String, Estudiant>> assignaturesEstudiant;

    //Constructor que rep com a argumens l'array d'assignatures i el TreeMap
    public FitxerXML(String[] assignatura,
            TreeMap<String, TreeMap<String, Estudiant>> assignaturesEstudiant) {
        this.assignatura = assignatura;
        FitxerXML.assignaturesEstudiant = assignaturesEstudiant;
    }

    //GETTERS i SETTERS
    public String[] getAssignatura() {
        return assignatura;
    }

    public void setAssignatura(String[] assignatura) {
        this.assignatura = assignatura;
    }

    public TreeMap<String, TreeMap<String, Estudiant>> getAssignaturesEstudiant() {
        return assignaturesEstudiant;
    }

    public void setAssignaturesEstudiant(
            TreeMap<String, TreeMap<String, Estudiant>> assignaturesEstudiant) {
        FitxerXML.assignaturesEstudiant = assignaturesEstudiant;
    }

    /**
     * Mètode que retornarà el conjunt d'estudiants que estan cursant
     * l'assignatura passada per paràmetre
     *
     * @param assignatura : Assignatura que volem analitzar
     */
    public static TreeMap<String, Estudiant> obtenirEstudiants(String assignatura) {

        TreeMap<String, Estudiant> estudiants = assignaturesEstudiant.get(assignatura);

        // Obtenim els valors de l'assignatura passada per paràmetre del conjunt
        // (TreeMap passat per paràmetre)
        return estudiants;
    }

    /**
     * Mètode que a través d'un array d'assignatures i del fitxer que conté les
     * dades dels alumnes crearà les llistes en format XML
     *
     * @param assignatura : array d'assignatures que l'usuari escollirà
     */
    public static void crearLlistaXML(String[] assignatura) {
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
                //Diem que l'element llista sera fill de llistes
                rootElement.appendChild(llista);

                // Creem un arrayList que contindrà estudiants
                TreeMap<String, Estudiant> estudiant = obtenirEstudiants(assignatura[i]);

                // Obtenim els cognoms i el grup de l'estudiant
                ArrayList<Estudiant> estudiants = new ArrayList<Estudiant>(
                        estudiant.values());

                // Bucle que es crearà per a cada estudiant
                for (int e = 0; e < estudiants.size(); e++) {

                    String cognomsNom = estudiants.get(e).getCognomsNom();
                    String grup = estudiants.get(e).getGrup();

                    // Creem l'element estudiant a través del mètode
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

            transformer.transform(source, result);

            //Informem a l'usuari de la creacio satisfactoria del fitxer XML
            JOptionPane.showMessageDialog(null, "El fitxer XML ja ha estat generat!",
                    "Fitxer Generat", JOptionPane.INFORMATION_MESSAGE);

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
