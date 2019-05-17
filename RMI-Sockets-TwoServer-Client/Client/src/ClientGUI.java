/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.collections.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author panos
 */
public class ClientGUI extends Application {        //το GUI
    private Stage primaryStage;         //primaryStage
    private static Client client;       //ένα αντικείμενο τύπου client
    
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;   //το παραθυρο
               
        this.primaryStage.setMinHeight(350);    //σετάρω το παράθυρο να έχει συγκεκριμένο μέγεθος
        this.primaryStage.setMinWidth(500);
        this.primaryStage.setMaxHeight(350);
        this.primaryStage.setMaxWidth(500);
        
        primaryStage.setTitle("Σύστημα Πτήσεων");   //ο τίτλος
        set_primary_stage();
    }

    
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        client = new Client();      //το αντικείμενο client
        launch(args);           //ξεκινάνε
    }

    private void set_primary_stage() {
        Pane pane = new Pane();         //ο πίνακας
        pane.setPrefSize(500, 350);

        Text text1 = new Text();            //εισαγωγικό κείμενο
        text1.setText("Συμπληρώστε τα στοιχεία της πτήσης");

        text1.setX(115);            //η διαρύθμισή του
        text1.setY(20);

        TextField apo = new TextField();     //τα πεδία συμπλήρωσης
        apo.setPromptText("Από");    //έχουν προσυμπληρωμένο το τι θέλουμε να συμπληρώσει ο χρήστης
        apo.setFocusTraversable(false);  

        apo.setLayoutX(10);      //η διαρύθμισή τους
        apo.setLayoutY(30);

        TextField pros = new TextField();
        pros.setPromptText("Προς");
        pros.setFocusTraversable(false);

        pros.setLayoutX(300);
        pros.setLayoutY(30);

        TextField afixi = new TextField();
        afixi.setPromptText("Άφιξη (yyyy-mm-dd)");
        afixi.setFocusTraversable(false);

        afixi.setLayoutX(10);
        afixi.setLayoutY(80);

        TextField anaxorisi = new TextField();
        anaxorisi.setPromptText("Αναχώρηση (yyyy-mm-dd)");
        anaxorisi.setFocusTraversable(false);

        anaxorisi.setLayoutX(300);
        anaxorisi.setLayoutY(80);

        TextField epivates = new TextField();
        epivates.setPromptText("Αριθμός επιβατών");
        epivates.setFocusTraversable(false);

        epivates.setLayoutX(10);
        epivates.setLayoutY(130);

        Button btn2 = new Button(); //το κουμπί αναζήτησης
        btn2.setText("Αναζήτηση");
        
        
        btn2.setOnAction(e -> { //όταν πατείται έχουμε την αναζήτηση η οποία γίνεται με βάση τα στοιχεία που δίνει ο χρήστης
            try {
                anazitisi(client.send(LocalDate.parse(afixi.getText(), DateTimeFormatter.ISO_LOCAL_DATE), LocalDate.parse(anaxorisi.getText(), DateTimeFormatter.ISO_LOCAL_DATE),
                        apo.getText(), pros.getText(),Integer.parseInt(epivates.getText())));
            } catch (RemoteException ex) {
                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btn2.setLayoutX(210);  //η διαρύθμισή του
        btn2.setLayoutY(257.5);  

        StackPane root = new StackPane();   //ο root
        pane.getChildren().add(apo);     //προσθέτω τα πάντα σε αυτόν
        pane.getChildren().add(pros);
        pane.getChildren().add(afixi);
        pane.getChildren().add(anaxorisi);
        pane.getChildren().add(epivates);
        pane.getChildren().add(btn2);
        pane.getChildren().add(text1);

        root.getChildren().add(pane);

        primaryStage.setScene(new Scene(root, 500, 350));
        primaryStage.show();
    }
    
    private void anazitisi(ArrayList<ArrayList<Ptisi>> ar){     //αφού επιστρέψει ο σέρβερ την arraylist 
        ListView<String> m_listViewAf;
        ListView<String> m_listViewAn;
        Group root = new Group();

	// οριζόντιο panel για λίστα 
	HBox listViewPanel = new HBox();
	listViewPanel.setSpacing(10);

	// το κείμενο μετά την επιλογή
	Text label = new Text("Επιλέξτε έναν αριθμό πτήσης για να δείτε τα στοιχεία του");
	label.setFont(Font.font(null, FontWeight.BOLD, 16));
        
        Text label1 = new Text();
        label1.setFont(Font.font(null, FontWeight.BOLD, 16));
        
        ArrayList<String> arIDAf = new ArrayList();  //arraylist που περιέχει τα id για τις αφίξεις
        ArrayList<String> arIDAn = new ArrayList();  //arraylist που περιέχει τα id για τις αναχωρήσεις
        for(Ptisi tmp : ar.get(0)){             //τον γεμίζει
            arIDAf.add(Integer.toString(tmp.getArithmosPtisis()));
        }
        
        for(Ptisi tmp : ar.get(1)){             //και αυτόν
            arIDAn.add(Integer.toString(tmp.getArithmosPtisis()));
        }	
        
	
	m_listViewAf = new ListView<String>(FXCollections.observableArrayList(arIDAf));    //η λίστα των κωδικών αφίξεων
        m_listViewAn = new ListView<String>(FXCollections.observableArrayList(arIDAn));    //η λίστα των κωδικών αναχωρήσεων
        m_listViewAf.prefWidth(50);
        m_listViewAn.prefWidth(50);
	m_listViewAf.setMaxWidth(100);
        m_listViewAn.setMaxWidth(100);
	m_listViewAf.getSelectionModel().selectedItemProperty().addListener((   //όταν πατάς ένα αντικείμενο της λίστας
                ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            int i = Integer.parseInt(newValue);
            Ptisi check=null;
            for(Ptisi t : ar.get(0)){           //ψαχνει να βρει την πτήση που αντιστοιχεί στον κωδικό αυτό
                if(i==t.getArithmosPtisis()){
                    check=t;            //και την βαζει στην check
                }
            }
            //εκτυπωνει τα στοιχεια της
            label1.setText("Kodikos Ptisis " + newValue + "\nDate:" +  check.getImerominia() +  "\nTime:" + check.getOra() +
                    "\nApo:" + check.getApo() +  "\nPros:" + check.getPros() +
                    "\nArithmos Thesewn:" + check.getArithmosThesewn() +  "\nTimi:"
                    + check.getTimi());
            
            //προβολή των στοιχείων
        });
        
        m_listViewAn.getSelectionModel().selectedItemProperty().addListener((       //ομοίως με τη λίστα για τις αφίξεις
                ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            int i = Integer.parseInt(newValue);
            Ptisi check=null;
            
            for(Ptisi t : ar.get(1)){
                if(i==t.getArithmosPtisis()){
                    check=t;
                }
            }
            label1.setText("Kodikos Ptisis " + newValue + "\nDate:" +  check.getImerominia() +  "\nTime:" + check.getOra() +
                    "\nApo:" + check.getApo() +  "\nPros:" + check.getPros() +
                    "\nArithmos Thesewn:" + check.getArithmosThesewn() +  "\nTimi:"
                    + check.getTimi());
            
            //προβολή των στοιχείων
        });

	listViewPanel.getChildren().addAll(m_listViewAf, m_listViewAn, label1);     //τα βαζω στο panel
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);           //οριζω ενα VBox
        Button next = new Button();
        next.setText("Πατήστε για να προχωρήσετε στην κράτηση");        //βάζω κάποια κουμπιά
        next.setOnAction(e -> { kratisi(); });          //ή για την κράτηση
        
        Button cancel = new Button();
        cancel.setText("Άκυρο");                    //ή για την ακύρωση που σε πάει ξανά στην αρχή
        cancel.setOnAction(e -> { set_primary_stage(); });
        
        box.getChildren().addAll(label, next, cancel, listViewPanel);
	root.getChildren().addAll(box);

	Scene scene = new Scene(root);
	primaryStage.setTitle("Αποτελέσματα Αναζήτησης");
	primaryStage.setScene(scene);
	primaryStage.show();
    }
    
    public void kratisi(){
        Pane pane = new Pane(); //ο πίνακας
        pane.setPrefSize(500, 350);

        Text text1 = new Text();    //εισαγωγικό κείμενο
        text1.setText("Συμπληρώστε τον αριθμό πτήσης για άφιξη και αναχώριση");

        text1.setX(115);    //η διαρύθμισή του
        text1.setY(20);

        TextField apo = new TextField();     //τα πεδία συμπλήρωσης
        apo.setPromptText("Αριθμός Π. Άφιξης");    //έχουν προσυμπληρωμένο το τι θέλουμε να συμπληρώσει ο χρήστης
        apo.setFocusTraversable(false);  

        apo.setLayoutX(10);      //η διαρύθμισή τους
        apo.setLayoutY(30);

        TextField pros = new TextField();
        pros.setPromptText("Αριθμός Π. Αναχώρισης");
        pros.setFocusTraversable(false);

        pros.setLayoutX(300);
        pros.setLayoutY(30);
        
        TextField epivates = new TextField();
        epivates.setPromptText("Αριθμός Επιβατών");
        epivates.setFocusTraversable(false);

        epivates.setLayoutX(10);
        epivates.setLayoutY(60);

        

        Button btn2 = new Button(); //το κουμπί κράτησης
        btn2.setText("Κράτηση");
        
        
        btn2.setOnAction(e -> {
            try {           //στελνει στον server την book και με βαση το boolean που θα επιστρέψει εκτελείται η τελική καρτέλα
                end(client.book(Integer.parseInt(apo.getText()), Integer.parseInt(pros.getText()), Integer.parseInt(epivates.getText())));
            } catch (RemoteException ex) {
                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btn2.setLayoutX(210);  //η διαρύθμισή του
        btn2.setLayoutY(257.5);  

        StackPane root = new StackPane();   //ο root
        pane.getChildren().add(apo);     //προσθέτω τα πάντα σε αυτόν
        pane.getChildren().add(pros);
        pane.getChildren().add(epivates);
        pane.getChildren().add(btn2);
        pane.getChildren().add(text1);

        root.getChildren().add(pane);

        primaryStage.setScene(new Scene(root, 500, 350));
        primaryStage.show();
    }
    
    public void end(boolean b){
        Pane pane = new Pane(); //ο πίνακας
        pane.setPrefSize(500, 350);

        Text text1 = new Text();    //εισαγωγικό κείμενο που είναι και η τελική ενημέρωση του εάν έγινε σωστά η κράτηση ή όχι
        if(b){
            text1.setText("OK");
        }
        else{
            text1.setText("NOT OK");
        }
        

        text1.setX(115);    //η διαρύθμισή του
        text1.setY(20);

        
        StackPane root = new StackPane();   //ο root
        pane.getChildren().add(text1);

        root.getChildren().add(pane);

        primaryStage.setScene(new Scene(root, 500, 350));
        primaryStage.show();
    }
}
