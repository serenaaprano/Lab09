
package it.polito.tdp.borders;

import java.net.URL;

import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    
    @FXML // fx:id="cmbStati"
    private ComboBox<Country> cmbStati; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaStati"
    private Button btnCercaStati; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult2"
    private TextArea txtResult2; // Value injected by FXMLLoader


    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	
    	
    	txtResult.clear();
    	int anno=0;
    	try {
    		
    		anno=Integer.parseInt(txtAnno.getText());
    		model.creaGrafo(anno);
    		txtResult.appendText("Grafo dei confini creato!\n");
    		for(Country c: model.getCountStatiConfinanti().keySet()) {
    			
    			txtResult.appendText("Stato: "+c.getName()+": "+model.getCountStatiConfinanti().get(c));
    			txtResult.appendText("\n");
    			
    		}
    		
    		

    		txtResult.appendText("Numero di componenti connesse del grafo: "+model.getComponentiConnesse());
    		
    		
    		
    		
    	}catch(NumberFormatException e ) {
    		
    		txtResult.setText("Inserire un anno valido");
    		return ;
    	}

    }
    
    
    @FXML
    void doCercaStatiRaggiungibili(ActionEvent event) {
    	
    	
    	
    	txtResult2.clear();
    	Country selected=cmbStati.getValue();
    	if(selected==null) {
    		
    		txtResult2.setText("Selezionare una città");
    		return ;
    		
    	}
    	
    	try {
    	
    	for(Country c: model.getReachableCountries(selected)) {
    		
    		
    		txtResult2.appendText(c.getName()+"\n");
    		
    	}
    	
    	}catch(RuntimeException e) {
    		txtResult2.setText("La città selezionata non ha confini");
    	}
    	
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbStati != null : "fx:id=\"cmbStati\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaStati != null : "fx:id=\"btnCercaStati\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult2 != null : "fx:id=\"txtResult2\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	cmbStati.getItems().addAll(model.getCountries());
    }
}
