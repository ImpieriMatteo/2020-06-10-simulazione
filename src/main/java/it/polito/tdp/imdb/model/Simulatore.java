package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Simulatore {
	
	private Model model;
	
	private Integer N;
	
	private List<Actor> attori;
	private Actor precedente;
	private boolean pausa;
	private boolean flag;
	
	private Integer numPause;
	private List<Actor> attoriIntervistati;
	
	public void init(Model model, Integer N) {
		this.model = model;
		
		this.attori = new ArrayList<>(this.model.getAllVertex());
		this.precedente = null;
		this.pausa = false;
		this.flag = false;
		
		this.N = N;
		
		this.numPause = 0;
		this.attoriIntervistati = new ArrayList<>();
	}
	
	public void simula() {
		
		for(int i=0; i<this.N; i++) {
			
			if(!pausa) {

				Integer probabilita = (int)(Math.random()*100);

				if(i==0 || probabilita<60 || this.flag) {
					
					int probabilitaAttore = (int)(Math.random()*this.attori.size());
					Actor attore = this.attori.remove(probabilitaAttore);
					this.attoriIntervistati.add(attore);
					
					if(i!=0 && !this.flag) {
						if(attore.getGender().equals(this.precedente.getGender())) {
							Integer prob = (int)(Math.random()*100);
							if(prob<90) {
								this.pausa = true;
								this.flag = true;
							}
						}
					}
					this.flag = false;

					this.precedente = attore;
				}
				else {
					Map<Actor, Integer> vicini = this.model.getAttoriViciniConPeso(precedente);

					if(vicini.isEmpty()) {
						int probabilitaAttore = (int)(Math.random()*this.attori.size());
						Actor attore = this.attori.remove(probabilitaAttore);
						this.attoriIntervistati.add(attore);
						
						if(attore.getGender().equals(this.precedente.getGender())) {
							Integer prob = (int)(Math.random()*100);
							if(prob<90) {
								this.pausa = true;
								this.flag = true;
							}
						}	

						this.precedente = attore;
					}
					else {
						Integer gradoMax = 0;
						List<Actor> attoriMax = new ArrayList<>();

						for(Actor a : vicini.keySet()) {
							if(vicini.get(a)>gradoMax)
								gradoMax = vicini.get(a);
						}

						for(Actor a : vicini.keySet()) {
							if(vicini.get(a)==gradoMax)
								attoriMax.add(a);
						}

						int probabilitaAttore = (int)(Math.random()*attoriMax.size());

						Actor attore = attoriMax.get(probabilitaAttore);
						this.attori.remove(attore);
						this.attoriIntervistati.add(attore);
						
						if(attore.getGender().equals(this.precedente.getGender())) {
							Integer prob = (int)(Math.random()*100);
							if(prob<90) {
								this.pausa = true;
								this.flag = true;
							}
						}

						this.precedente = attore;
					}
				}
			}
			else {
				this.pausa = false;
				this.numPause++;
			}
		}
	}
	
	public List<Actor> getAttoriIntervistati(){
		Collections.sort(this.attoriIntervistati, new ActorLastNameComparator());
		return this.attoriIntervistati;
	}
	
	public Integer getNumPause() {
		return this.numPause;
	}

}
