/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Model;
import it.polito.tdp.imdb.model.Simulatore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;
	private Simulatore simulatore;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimili"
    private Button btnSimili; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimulazione"
    private Button btnSimulazione; // Value injected by FXMLLoader

    @FXML // fx:id="boxGenere"
    private ComboBox<String> boxGenere; // Value injected by FXMLLoader

    @FXML // fx:id="boxAttore"
    private ComboBox<Actor> boxAttore; // Value injected by FXMLLoader

    @FXML // fx:id="txtGiorni"
    private TextField txtGiorni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doAttoriSimili(ActionEvent event) {
    	this.txtResult.clear();
    	
    	Actor attore = this.boxAttore.getValue();
    	if(attore==null) {
    		this.txtResult.appendText("Devi selezionare prima un attore!!!");
    		return;
    	}
    	
    	if(!this.model.getGenre().equals(this.boxGenere.getValue())) {
    		this.txtResult.appendText("Devi prima creare il grafo!!!");
    		return;
    	}
    	
    	List<Actor> attoriSimili = this.model.getAttoriSimili(attore);
    	
    	this.txtResult.appendText("ATTORI SIMILI A: "+attore.toString()+"\n");
    	for(Actor a : attoriSimili) 
    		this.txtResult.appendText("- "+a.toString()+"\n");
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	
    	String genre = this.boxGenere.getValue();
    	if(genre==null) {
    		this.txtResult.appendText("Devi selezionare prima un genere!!!");
    		return;
    	}
    	
    	this.model.creaGrafo(genre);
    	
    	Integer nArchi = this.model.getNumArchi();
    	Integer nVertici = this.model.getNumVertici();
    	
    	this.txtResult.appendText(String.format("Creato un grafo con:\n#VERTICI %d\n#ARCHI %d", nVertici, nArchi));
    	this.boxAttore.setDisable(false);
    	this.btnSimili.setDisable(false);

    	List<Actor> attori = new ArrayList<>(this.model.getAllVertex());
    	Collections.sort(attori);
    	this.boxAttore.getItems().clear();
    	this.boxAttore.getItems().addAll(attori);
    	
    	this.btnSimulazione.setDisable(false);
    	this.txtGiorni.setDisable(false);
    }

    @FXML
    void doSimulazione(ActionEvent event) {
    	this.txtResult.clear();
    	this.simulatore = new Simulatore();
    	
    	Integer numGiorni;
    	try {
    		numGiorni = Integer.parseInt(this.txtGiorni.getText());
    	}
    	catch(NumberFormatException e) {
    		this.txtResult.appendText("Devi inserire un numero INTERO!!!");
    		return;
    	}
    	
    	this.simulatore.init(model, numGiorni);
    	this.simulatore.simula();
    	List<Actor> attoriIntervistati = this.simulatore.getAttoriIntervistati();
    	
    	this.txtResult.appendText(String.format("Simluazione effettuata!\n\n#PAUSE %d\n\nAttori intervistati:\n", this.simulatore.getNumPause()));
    	for(Actor a : attoriIntervistati) 
    		this.txtResult.appendText("- "+a.toString()+"\n");

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimili != null : "fx:id=\"btnSimili\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimulazione != null : "fx:id=\"btnSimulazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxGenere != null : "fx:id=\"boxGenere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAttore != null : "fx:id=\"boxAttore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtGiorni != null : "fx:id=\"txtGiorni\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	this.boxGenere.getItems().addAll(this.model.getAllGenres());
    }
}
