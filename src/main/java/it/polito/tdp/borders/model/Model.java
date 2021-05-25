package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private SimpleGraph<Country, DefaultEdge> grafo;
	private BordersDAO dao;
	private Map<Integer, Country>idMap;
	private List<Country> stati;
	

	public Model() {
		
		
		
		dao=new BordersDAO();
		idMap=new HashMap<Integer, Country>();
		dao.loadAllCountries(idMap);
		
		
		
		
		
	}
	
	
	public void creaGrafo(int anno) {
		
		
		this.grafo=new SimpleGraph<>(DefaultEdge.class);
		
		
		List<Border> confini= dao.getCountryPairs(anno, idMap);
		if(!confini.isEmpty()) {
			
			for(Border b: confini) {
				
				this.grafo.addVertex(b.getC1());
				this.grafo.addVertex(b.getC2());
				this.grafo.addEdge(b.getC1(), b.getC2());
				
			}
			
		}
		
		stati=new LinkedList<Country>(this.grafo.vertexSet());
		
	}
	
	
	
	public Map<Country,Integer> getCountStatiConfinanti(){
		
		Map<Country,Integer> statoECount=new HashMap<Country, Integer>();
		
		if(this.grafo==null) {
			
			
			throw new RuntimeException("Grafo non esistente");
		}
		
		for(Country country: this.grafo.vertexSet()) {
			
			statoECount.put(country, this.grafo.degreeOf(country));
			
		}
		
		return statoECount;
		
		
	}
	
	
	public int getComponentiConnesse() {
		
		if(this.grafo==null) {
		
			throw new RuntimeException("Grafo non esistente");
		}
		
		ConnectivityInspector <Country,DefaultEdge> ci=new ConnectivityInspector<Country, DefaultEdge>(this.grafo);
		return ci.connectedSets().size();
	}
	
	public List<Country> getReachableCountries(Country selected){
		
		
		if(!this.grafo.vertexSet().contains(selected)) {
			throw new RuntimeException("Selezionare un paese che ha almeno un confine con un altro stato!");
		}
		
		List<Country>result=this.trovaReachableCountries(selected);
		
		return result;
		
	}


	private List<Country> trovaReachableCountries(Country selected) {
		//uso il depthfirstiterator
		
		List<Country>visitate=new LinkedList<Country>();
		
		GraphIterator<Country,DefaultEdge> dfi= new DepthFirstIterator<Country, DefaultEdge>(this.grafo, selected);
		
		while(dfi.hasNext()) {
			visitate.add(dfi.next());
		}
		
		return visitate;
	}
	
	public List<Country>getCountries(){
		
		return dao.getAllCountries();
	}
	
	

}
