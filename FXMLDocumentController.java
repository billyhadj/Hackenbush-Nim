/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_i_g;

import java.net.URL;
import static java.time.Clock.system;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
/**
 *
 * @author billel
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    BorderPane BPchoix;
    @FXML
    BorderPane BPprincipal;
    @FXML
    BorderPane BPHackenbush;
    @FXML
    BorderPane BPNim;
    @FXML 
    ChoiceBox choix_couleur ; 
    @FXML
    Pane PHackenbush;
    @FXML
    Pane PNim;
    @FXML 
    ToggleButton Select_sommet ; 
    @FXML 
    ToggleButton Select_arete ;
    @FXML 
    ToggleButton Deplacer ; 
    @FXML 
    ToggleButton Supprimer ; 
    @FXML 
    ChoiceBox Nombre_de_noeuds ; 
    @FXML 
    ChoiceBox Nombre_tiges ; 
    
    
    @FXML
    ToggleButton Ajout_sommet;
    
    @FXML
    void Jouer(ActionEvent event) {
        System.out.println("On va jouer");
        BPchoix.setVisible(true);
        BPprincipal.setVisible(false);
    }
    @FXML
    void instruction(ActionEvent event) {
        System.out.println("Affiche les regles du jeu");
    }
    @FXML
    void Retour(ActionEvent event) {
        BPchoix.setVisible(false);
        BPprincipal.setVisible(true);
        BPHackenbush.setVisible(false);
        BPNim.setVisible(false);
        
    }
    @FXML
    void Relier() {
        System.out.println(les_points.size());
        if(les_points.size()== 2){
            Line l = new Line() ;
            l.startXProperty().bind(les_points.get(0).centerXProperty());
            l.startYProperty().bind(les_points.get(0).centerYProperty());
            l.endXProperty().bind(les_points.get(1).centerXProperty());
            l.endYProperty().bind(les_points.get(1).centerYProperty());
            switch (choix_couleur.getSelectionModel().getSelectedItem().toString()){ 
                case "Bleu" : 
                    l.setStroke(Color.BLUE);
                    break ; 
                    case "Vert" : 
                    l.setStroke(Color.GREEN);
                    break ; 
                    case "Rouge" : 
                    l.setStroke(Color.RED);
                    break ;
                    default:
                        l.setStroke(Color.YELLOW);
            }
            l.setStrokeWidth(3);
            l.setOnMouseClicked((MouseEvent e) -> {
                if(Supprimer.isSelected()){
                    PHackenbush.getChildren().remove(l);
                }
            });
            l.setOnMouseEntered((MouseEvent e) -> {
                l.setStrokeWidth(l.getStrokeWidth()*1.5);
            });
            l.setOnMouseExited((MouseEvent e) -> {
                l.setStrokeWidth(l.getStrokeWidth()/1.5);
            });
           
            
            les_points.get(0).setRadius(Rayon);
            les_points.get(1).setRadius(Rayon);
            les_points.clear();
            PHackenbush.getChildren().add(l);
            

        }
    }
    
    
    @FXML
    void Hackenbush(ActionEvent event) {
        BPchoix.setVisible(false);
        BPprincipal.setVisible(false);
        BPHackenbush.setVisible(true) ; 
        BPNim.setVisible(false);
    }
    @FXML 
    void Fin(ActionEvent event){
        Platform.exit();
    }
    private static final double Rayon = 4;
    private Circle creaSommet(MouseEvent e) {
        Circle vertex = new Circle();
        vertex.setCenterX(e.getX());
        vertex.setCenterY(e.getY());
        vertex.setRadius(Rayon);
        vertex.setFill(Color.BLACK);
        return vertex;
    }
    
    private List<Circle> les_points = new ArrayList<>() ;
  
    
    private EventHandler<MouseEvent> creationPoint = (MouseEvent e) -> {
            Circle vertex = creaSommet(e);
            vertex.setOnMouseClicked((MouseEvent z) -> {
                if(Select_sommet.isSelected()){
                    if(les_points.contains(vertex)){
                        vertex.setRadius(vertex.getRadius()*1.5);
                        les_points.remove(vertex);
                    }else{
                    vertex.setRadius(vertex.getRadius()/1.5);
                    les_points.add(vertex) ; 
                    }
                }
                if(Supprimer.isSelected()){
                    les_points.remove(vertex);
                    PHackenbush.getChildren().remove(vertex);
                }
            });
            vertex.setOnMouseEntered((MouseEvent z) -> {
                vertex.setRadius(vertex.getRadius()*1.5);
            });
            vertex.setOnMouseExited((MouseEvent z) -> {
                vertex.setRadius(vertex.getRadius()/1.5);
            });
            vertex.setOnMouseDragged((MouseEvent x) -> { 
                if(Deplacer.isSelected()){
                    vertex.setCenterX(x.getX());
                    vertex.setCenterY(x.getY());
                }
            });
            PHackenbush.getChildren().add(vertex);
            };
            
    
    @FXML
    void Nim(ActionEvent event) {
        BPchoix.setVisible(false);
        BPprincipal.setVisible(false);
        BPHackenbush.setVisible(false);
        BPNim.setVisible(true);
    }
    
    
    private List<Path> les_tiges = new ArrayList<>() ;
    private List<Circle> les_noeuds = new ArrayList<>(); 
    @FXML 
    void Afficher() { 
        int nombre_de_tiges = (int) Nombre_tiges.getSelectionModel().getSelectedItem();
        int nombre_de_noeuds = (int) Nombre_de_noeuds.getSelectionModel().getSelectedItem();
        System.out.println(nombre_de_tiges) ;
        int i, j ; 
        i =0 ;
        int decalage ;
        decalage = 50 ;
        PNim.getChildren().clear();
        for( j = 0 ; j<nombre_de_tiges ; j++){
            Circle c = new Circle() ;
            c.setRadius(Rayon*2);
            c.setCenterX(j*200+decalage);
            c.setCenterY(PNim.getHeight()-decalage);
            c.setFill(Color.GREEN);
            c.setOnMouseEntered((MouseEvent e)-> {
              c.setRadius(c.getRadius()*1.5);
            });
            c.setOnMouseExited((MouseEvent e)-> {
              c.setRadius(c.getRadius()/1.5);
            });
            c.setOnMouseDragged((MouseEvent e)-> {
              c.setCenterX(e.getX());
              c.setCenterY(e.getY());
            });
            les_noeuds.add(c);
            PNim.getChildren().add(c);
            Path p = new Path();
            p.setStroke(Color.VIOLET);
            p.setStrokeWidth(Rayon/2);
            MoveTo mt = new MoveTo();
            mt.xProperty().bind(c.centerXProperty());
            mt.yProperty().bind(c.centerYProperty());
            p.getElements().add(mt);
            for(i = 0 ; i < nombre_de_noeuds-1 ; i++){
                double rand ; 
                rand = Math.random() ;  
                Circle v = new Circle() ; 
                v.setCenterX((j*200)+70*rand+2*decalage);
                v.setCenterY(PNim.getHeight()-((i*50)+90*rand)-decalage);
                v.setRadius(Rayon);
                v.setFill(Color.BLACK);
                v.setOnMouseEntered((MouseEvent e)-> {
                    v.setRadius(v.getRadius()*1.5);
                });
                v.setOnMouseExited((MouseEvent e)-> {
                    v.setRadius(v.getRadius()/1.5);
                });
                v.setOnMouseDragged((MouseEvent e)-> {
                    v.setCenterX(e.getX());
                    v.setCenterY(e.getY());
                });
                PNim.getChildren().add(v);
                les_noeuds.add(v);
                LineTo lt = new LineTo();
                lt.xProperty().bind(v.centerXProperty());
                lt.yProperty().bind(v.centerYProperty());
                p.getElements().add(lt);
                
            }
            
            PNim.getChildren().add(p);

            
        
        }
        
        
        
    }
    
    @FXML 
    void Appliquer(){
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       choix_couleur.getSelectionModel().selectFirst();
       PHackenbush.onMouseClickedProperty().bind(Bindings.when(Ajout_sommet.selectedProperty()).then(creationPoint).otherwise(((EventHandler<MouseEvent>) null)));
    }    
    
}
