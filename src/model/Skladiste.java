package model;

import java.util.ArrayList;

public class Skladiste extends InfResurs{
	
	private ArrayList<PackEnt> deca = new ArrayList<>();

	public ArrayList<PackEnt> getDeca() {
		return deca;
	}

	public void setDeca(ArrayList<PackEnt> deca) {
		this.deca = deca;
	}
	
	
	
}
