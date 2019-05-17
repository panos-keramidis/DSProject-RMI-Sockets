/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
import java.io.*;
import java.time.LocalDate;
/**
 *
 * @author panos
 */

/*η σειρά είναι -CHECK->
                <-SEND-
                -BOOK->
                <-OK/NOT OK-
*/
public class Message implements Serializable{
    private String anagnoristiko;       //τα στοιχεία που πιθανόν να ανταλλαχθούν μέσω του Message
    private String apo;
    private String pros;
    private LocalDate imerominiaAf;
    private LocalDate imerominiaAn;
    private int pigaine;
    private int ela;
    private int epivates;
    private ArrayList<ArrayList<Ptisi>> ptisis;
    
    public Message(int pigaine, int ela, int epivates){         //constructors ανάλογα με τον τύπο των μηνυμάτων
        this.anagnoristiko="BOOK";              //εδώ βλέπουμε της κράτησης που θέλει ο χρήστης
        this.pigaine=pigaine;
        this.ela=ela;
        this.epivates=epivates;
    }
    
    public Message(ArrayList<ArrayList<Ptisi>> ptisis){ //εδώ βλέπουμε της αποστολής της arraylist της αναζητησης
        this.anagnoristiko="SEND";
        this.ptisis=ptisis;
    }
    
    public Message(LocalDate imerominiaAf, LocalDate imerominiaAn, String apo, String pros, int epivates){
        this.anagnoristiko="CHECK";         //εδώ βλέπουμε της αναζήτησης που θέλει ο χρήστης
        this.imerominiaAf=imerominiaAf;
        this.imerominiaAn=imerominiaAn;
        this.apo = apo;
        this.pros = pros;
        this.epivates=epivates;
    }
    
    public Message(boolean done){   //εδώ βλέπουμε της τελικής ενημέρωσης μιας κράτησης
        if(done){
            this.anagnoristiko="OK";
        }
        else{
            this.anagnoristiko="NOT OK";
        }
       
    }
    
    public String getAnagnoristiko(){       //getters
        return this.anagnoristiko;
    }
    
    public String getApo(){
        return this.apo;
    }
    
    public String getPros(){
        return this.pros;
    }
    
    public int getPigaine(){
        return this.pigaine;
    }
    
    public int getEla(){
        return this.ela;
    }
    
    public int getEpivates(){
        return this.epivates;
    }
    
    public LocalDate getImerominiaAf(){
        return this.imerominiaAf;
    }
    
    public LocalDate getImerominiaAn(){
        return this.imerominiaAn;
    }
    
    public ArrayList<ArrayList<Ptisi>> getPtisis(){
        return this.ptisis;
    }
}
