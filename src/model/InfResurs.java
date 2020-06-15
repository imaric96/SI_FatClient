package model;

import java.util.Observable;

public abstract class InfResurs extends Observable {
	private String naziv;
	private String key;
	
	
	
	public String getNaziv() {
		return naziv;
	}



	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}



	public String getKey() {
		return key;
	}



	public void setKey(String kljuc) {
		this.key = kljuc;
	}



	@Override
	public String toString() {
		return naziv ;
	}
	
}
