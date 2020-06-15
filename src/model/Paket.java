package model;

import java.util.ArrayList;

public class Paket extends PackEnt{
	
	
	private ArrayList<PackEnt> deca = new ArrayList<>();

	public ArrayList<PackEnt> getDeca() {
		return deca;
	}

	public void setDeca(ArrayList<PackEnt> deca) {
		this.deca = deca;
	}
	
}
