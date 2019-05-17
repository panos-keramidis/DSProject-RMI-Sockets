/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.time.*;
/**
 *
 * @author panos
 */
public class Ptisi implements Serializable{
    private LocalDate imerominia;       //τα στοιχεία μιας πτήσης
    private LocalTime ora;
    private String apo;
    private String pros;
    private int arithmosPtisis;
    private int arithmosEleftherwnThesewn;
    private int timi;
    
    public Ptisi(LocalDate imerominia, LocalTime ora, String apo, String pros, 
            int arithmosPtisis, int arithmosThesewn, int timi){ //constructor
        this.imerominia=imerominia;
        this.ora=ora;
        this.apo=apo;
        this.pros=pros;
        this.arithmosPtisis=arithmosPtisis;
        this.arithmosEleftherwnThesewn=arithmosThesewn;
        this.timi=timi;
    }
    
    public LocalDate getImerominia(){   //getters
        return this.imerominia;
    }
    
    public LocalTime getOra(){
        return this.ora;
    }
       
    public String getApo(){
        return this.apo;
    }
    
    public String getPros(){
        return this.pros;
    }
    
    public int getArithmosPtisis(){
        return this.arithmosPtisis;
    }
    
    public int getArithmosThesewn(){
        return this.arithmosEleftherwnThesewn;
    }
    
    public int getTimi(){
        return this.timi;
    }
    
    public void reduceArithmosThesewn(int epivates){    //συνάρτηση μείωσης των αριθμών θέσεων
        this.arithmosEleftherwnThesewn-=epivates;
    }
}
