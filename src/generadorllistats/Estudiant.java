package generadorllistats;

/**
 * Classe que representa un estudiant
 * @author jordi
 */
public class Estudiant {

    //Variables de l'estudiant
    private String cognomsNom, grup;

    //Constructor de l'estudiant
    public Estudiant(String cognomsNom, String grup) {
        this.cognomsNom = cognomsNom;
        this.grup = grup;
    }

    //GETTERS i SETTERS
    public String getCognomsNom() {
        return cognomsNom;
    }

    public void setCognomsNom(String cognomsNom) {
        this.cognomsNom = cognomsNom;
    }

    public String getGrup() {
        return grup;
    }

    public void setGrup(String grup) {
        this.grup = grup;
    }

    //ToString de l'Estudiant
    @Override
    public String toString() {
        return "Estudiant [cognomsNom=" + cognomsNom + ", grup=" + grup + "]";
    }
}
