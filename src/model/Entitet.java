package model;

import java.util.ArrayList;

public class Entitet extends PackEnt {
	private ArrayList<Atribut> atributi = new ArrayList<>();
	private ArrayList<Relacija> relacije = new ArrayList<>();
	
	public ArrayList<Atribut> getAtributi() {
		return atributi;
	}
	public void setAtributi(ArrayList<Atribut> atributi) {
		this.atributi = atributi;
	}
	public ArrayList<Relacija> getRelacije() {
		return relacije;
	}
	public void setRelacije(ArrayList<Relacija> relacije) {
		this.relacije = relacije;
	}
	
}
