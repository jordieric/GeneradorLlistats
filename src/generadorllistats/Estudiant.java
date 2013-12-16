/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generadorllistats;

import java.util.ArrayList;

/**
 *
 * @author jordi
 */
public class Estudiant {
    
private String nom, cognom, grup;
private ArrayList<String> assignatures;

	public Estudiant(String nom, String cognom, String grup,
			ArrayList<String> assignatures) {
		this.nom = nom;
		this.cognom = cognom;
		this.grup = grup;
		this.assignatures = assignatures;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCognom() {
		return cognom;
	}

	public void setCognom(String cognom) {
		this.cognom = cognom;
	}

	public String getGrup() {
		return grup;
	}

	public void setGrup(String grup) {
		this.grup = grup;
	}

	public ArrayList<String> getAssignatures() {
		return assignatures;
	}

	public void setAssignatures(ArrayList<String> assignatures) {
		this.assignatures = assignatures;
	}

	@Override
	public String toString() {
		return "Estudiant [nom=" + nom + ", cognom=" + cognom + ", grup="
				+ grup + ", assignatures=" + assignatures + "]";
	}
		
}

