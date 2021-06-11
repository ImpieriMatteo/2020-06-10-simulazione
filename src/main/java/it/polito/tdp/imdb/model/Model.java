package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {

	private ImdbDAO dao;
	private String genre;
	private SimpleWeightedGraph<Actor, DefaultWeightedEdge> grafo;
	
	public Model() {
		this.dao = new ImdbDAO();
	}
	
	public void creaGrafo(String genre) {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.genre = genre;
		
		Map<Integer, Actor> idMapActor = new HashMap<>();
		this.dao.getActorsPerGenre(genre, idMapActor);
		
		Graphs.addAllVertices(this.grafo, idMapActor.values());
		
		List<EdgePerGenre> archi = this.dao.getAllCouples(genre, idMapActor);
		for(EdgePerGenre e : archi) 
			Graphs.addEdge(this.grafo, e.getAttore1(), e.getAttore2(), e.getFilmInsieme());
		
	}

	public List<String> getAllGenres() {
		List<String> result = this.dao.getAllGenres();
		Collections.sort(result);
		return result;
	}

	public Integer getNumArchi() {
		return this.grafo.edgeSet().size();
	}

	public Integer getNumVertici() {
		return this.grafo.vertexSet().size();
	}

	public Set<Actor> getAllVertex() {
		return this.grafo.vertexSet();
	}

	public List<Actor> getAttoriSimili(Actor attore) {
		DepthFirstIterator<Actor, DefaultWeightedEdge> dfv = new DepthFirstIterator<>(this.grafo, attore);
		List<Actor> result = new ArrayList<>();
		
		while(dfv.hasNext()) {
			Actor actor = dfv.next();
			result.add(actor);
		}
		result.remove(0);
		Collections.sort(result, new ActorLastNameComparator());;
	
		return result;
	}
	
	public Map<Actor, Integer> getAttoriViciniConPeso(Actor attore) {
		Map<Actor, Integer> result = new HashMap<>();
		
		for(Actor a : Graphs.neighborListOf(this.grafo, attore))
			result.put(a, (int)this.grafo.getEdgeWeight(this.grafo.getEdge(attore, a)));
	
		return result;
	}
	
	public String getGenre() {
		return this.genre;
	}

}
