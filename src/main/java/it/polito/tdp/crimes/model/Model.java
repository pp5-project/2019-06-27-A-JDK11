package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	private EventsDao dao;
	private SimpleWeightedGraph<String, DefaultWeightedEdge> grafo;
	private Map<Integer, String> idMap;
	private int bestPeso;
	private List<String> bestPercorso;

		
	public Model(){
		dao=new EventsDao();
		idMap=new HashMap<Integer,String>();
		//dao.listAllMatches(idMap);
		
	}
	
	
	public void CreaGrafo(int anno, String categoria) {
		this.grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		//aggiungo vertici
		Graphs.addAllVertices(grafo,dao.getVertici(anno,categoria));
		//aggiungo archi
		for(Adiacenza a:dao.getArchi(anno, categoria)) { 
				Graphs.addEdge(grafo, a.getUno(), a.getDue(), a.getPeso());
			}
					
	}
	
	public Set<String> getVertici() {
		return this.grafo.vertexSet();
	}
	
	public String grafoRecap() {
		return "GRAFO CREATO" +"\n"+" N VERTICI: "+this.grafo.vertexSet().size()+"\n"+"N ARCHI: "+this.grafo.edgeSet().size()+"\n";
	}
	public List<String> getReati(){
		return dao.listaReati();
	}
	public List<Integer> getGG(){
		return dao.listaYear();
	}
	
	public List<Adiacenza> massimo(int giorno, String categoria){
		List<Adiacenza> bella=new LinkedList<Adiacenza>(dao.getArchi(giorno, categoria));
		Collections.sort(bella);
		List<Adiacenza> fine=new LinkedList<Adiacenza>();
		for(Adiacenza a:bella) {
			if(a.getPeso()>=bella.get(0).getPeso())
				fine.add(a);
		}
		return fine;
	}
	
	public List<String> Ricorsione(Adiacenza a) {
		String uno=a.getUno();
		String due=a.getDue();
		this.bestPeso=1000;
		this.bestPercorso=new ArrayList<>();
		List <String> parziale=new ArrayList<>();
		//List <String> daToccare=new ArrayList<>(this.grafo.vertexSet());
		parziale.add(uno);
		cerca(due, 0, parziale);
		return this.bestPercorso;
		
	}


	public int getBestPeso() {
		return bestPeso;
	}


	private void cerca(String due, int peso, List<String> parziale) {
		// CASO TERMINALE
		if(parziale.get(parziale.size()-1).equals(due) && parziale.size()==this.grafo.vertexSet().size()) {
			if(this.bestPeso>peso) {
				this.bestPercorso=new ArrayList<>(parziale);
				this.bestPeso=peso;
				return;
			}
		}
		//GENERAZIONE PERCORSO
		for(String p:Graphs.neighborListOf(grafo, parziale.get(parziale.size()-1))) {
			int pesoAggiunto=(int) grafo.getEdgeWeight(grafo.getEdge(parziale.get(parziale.size()-1), p));
			if(!parziale.contains(p)) {
				parziale.add(p);
				peso+=pesoAggiunto;
			//	daToccare.remove(p);
				cerca(due,peso, parziale);
				peso-=pesoAggiunto;
				parziale.remove(p);
				//daToccare.add(p);
							}
		}
		
	}
	
	public List<String> ricorsione2(Adiacenza a){
		this.bestPercorso=new ArrayList<>();
		List<String> parziale=new ArrayList<>();
		String uno=a.getUno();
		String due=a.getDue();
		parziale.add(uno);
		cerca(parziale,0,due);
		return this.bestPercorso;
	}


	private void cerca(List<String> parziale, int livello, String due) {
		//TERMINALE
		if(parziale.size()>this.bestPercorso.size() && parziale.get(parziale.size()-1).equals(due)) {
			this.bestPercorso=new ArrayList<>(parziale);
		}
		//PERCORSO
		for(String s:Graphs.neighborListOf(grafo, parziale.get(parziale.size()-1))){
			if(!parziale.contains(s)) {
			parziale.add(s);
			cerca(parziale,livello+1,due);
			parziale.remove(s);
		}
		
	}
	}
	
	
	
}
