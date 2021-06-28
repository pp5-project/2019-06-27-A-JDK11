package it.polito.tdp.crimes.model;

public class Adiacenza implements Comparable<Adiacenza>{
	private String uno;
	private String due;
	private int peso;
	
	
	public Adiacenza(String uno, String due, int peso) {
		super();
		this.uno = uno;
		this.due = due;
		this.peso = peso;
	}


	public String getUno() {
		return uno;
	}


	public void setUno(String uno) {
		this.uno = uno;
	}


	public String getDue() {
		return due;
	}


	public void setDue(String due) {
		this.due = due;
	}


	public int getPeso() {
		return peso;
	}


	public void setPeso(int peso) {
		this.peso = peso;
	}


	@Override
	public int compareTo(Adiacenza arg0) {
		// TODO Auto-generated method stub
		return -(this.peso-arg0.peso);
	}


	@Override
	public String toString() {
		return "Adiacenza [uno=" + uno + ", due=" + due + ", peso=" + peso + "]"+"\n";
	}
	
	

}
