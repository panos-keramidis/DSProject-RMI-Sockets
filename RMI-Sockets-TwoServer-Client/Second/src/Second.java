/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author panos
 */
public class Second {
    private ArrayList<Ptisi> ptisis;    //η βάση δεδομένων μας
    private ServerSocket server;        //ένα server socket 
    
    /**
     * @param args the command line arguments
     */
    
    public Second(){  
        ptisis = new ArrayList<Ptisi>();    //αρχικοποιώ την arraylist μας
        //βαζω καποιες συγκεκριμένες πτήσεις
        ptisis.add(new Ptisi(LocalDate.of(2019,Month.AUGUST,16), LocalTime.of(10,15), "Samos", "Athina", 11, 20, 80));
        ptisis.add(new Ptisi(LocalDate.of(2019,Month.AUGUST,16), LocalTime.of(12,15), "Samos", "Athina", 12, 20, 80));
        ptisis.add(new Ptisi(LocalDate.of(2019,Month.SEPTEMBER,16), LocalTime.of(10,15), "Samos", "Athina", 13, 20, 80));
        ptisis.add(new Ptisi(LocalDate.of(2019,Month.SEPTEMBER,16), LocalTime.of(12,15), "Samos", "Athina", 14, 20, 90));
    }
    
    public void listen() throws IOException{
        server = new ServerSocket(5555,50);    //localhost
        
        while(true){    
            Socket s = server.accept();         //ξεκιναω να δεχομαι στο socket
            
            new Thread(new Runnable(){      //ξεκινάω τον threaded server
                public void run(){
                    try {
                        ObjectOutputStream outstream = new ObjectOutputStream(s.getOutputStream());  //τα streams
                        ObjectInputStream instream = new ObjectInputStream(s.getInputStream());
                        
                        Message m1 = (Message) instream.readObject();   //διαβαζω το πρώτο μήνυμα
                        Message m2;
                        if(m1.getAnagnoristiko().equals("CHECK")){  //αν ο χρήστης θέλει να ξεκινήσει την αναζήτηση
                            //βάζω ό,τι μου δωσε στο availability το οποίο είναι μια μέθοδος που επιστρέφει arraylist με τα αποτελέσματα των συναρτήσεων
                            m2 = new Message(availability(m1.getImerominiaAf(), m1.getImerominiaAn(), m1.getApo(), m1.getPros(), m1.getEpivates()));
                            outstream.writeObject(m2);      //το στέλνω
                            outstream.flush();
                        }
                        if(m1.getAnagnoristiko().equals("BOOK")){       //αν ο χρήστης θέλει να κάνει κράτηση
                            m2 = new Message(book(m1.getPigaine(), m1.getEla(), m1.getEpivates())); //διαβαζω ό,τι μου στειλε και το βαζω στην book
                            //η book είναι συνάρτηση που δέχεται τους κωδικούς των πτήσεων του χρήστη και τον αριθμό ατόμων και κάνει την κράτηση επιστρέφοντας
                            //ένα boolean για OK/NOT OK αντιστοιχα
                            outstream.writeObject(m2);  //το στέλνω
                            outstream.flush();
                        }
                        instream.close();   //κλείνω τη σύνδεση
                        outstream.close();
                        s.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Second.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Second.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).start();
        }
    }
    
    public ArrayList<ArrayList<Ptisi>> availability(LocalDate imerominiaAf, LocalDate imerominiaAn, String apo, String pros, int epivates){ //η availability
        ArrayList<ArrayList<Ptisi>> diathesima = new ArrayList<ArrayList<Ptisi>>();     //δήλωση της τελικής λίστας
        diathesima.add(new ArrayList<Ptisi>());     //αρχικοποιώ τις επιμέρους λίστες μες στη λίστα
        diathesima.add(new ArrayList<Ptisi>());     //η λίστα στη θεση 0 είναι για τις αφίξεις και η λίστα στη θέση 1 για τις αναχωρήσεις
        synchronized(this){         //συγχρονίζω τα κομμάτια της αναζητησης για να επιτρέπεται παράλληλη διέλευση
            for (Ptisi ptisi : ptisis) {    //για κάθε πτήση
                if(epivates<=ptisi.getArithmosThesewn() && ptisi.getImerominia().compareTo(imerominiaAf)==0
                        && ptisi.getApo().equals(apo) && ptisi.getPros().equals(pros)){
                    diathesima.get(0).add(ptisi);       //ελέγχω αν ταιριάζει με τα κριτήρια αναζήτησης για τις αφίξεις και τη βάζω στη θεση 0
                    
                }
            }
        }
        synchronized(this){
            for (Ptisi ptisi : ptisis) {
                if(epivates<=ptisi.getArithmosThesewn() && ptisi.getImerominia().compareTo(imerominiaAn)==0 
                        && ptisi.getApo().equals(apo) && ptisi.getPros().equals(pros)){
                    diathesima.get(1).add(ptisi);   //παρόμοια με τις αναχωρήσεις και τη βάζω στην 1
                }
            }
        }
        return diathesima;      //επιστρέφω την arraylist
    }
    
    public boolean book(int pigaine, int ela, int epivates){        //η book παίρνει τον κωδικό για την άφιξη, τον κωδικό για την αναχώρηση και τον αριθμό επιβατών
        int i=0;
        synchronized(this){     //συγχρονίζω το κομμάτι της αναζήτησης
            for (Ptisi ptisi : ptisis) {
                if(ptisi.getArithmosPtisis()==pigaine){     //ψάχνω βάση αριθμού πτήσης
                    ptisi.reduceArithmosThesewn(epivates);      //μειώνω τους διαθέσιμους επιβάτες
                    i++;        //και σηματοδοτώ την εύρεση
                }
                if(ptisi.getArithmosPtisis()==ela){     //παρόμοια για τις αφίξεις
                    ptisi.reduceArithmosThesewn(epivates);
                    i++;
                }
            }
        }
        return (i==2);      //επιστρέφει true αν βρήκε και για αναχώρηση και για άφιξη
    }
    
    public static void main(String[] args) throws IOException {
        
        new Second().listen();      //ξεκινάει την υλοποίηση του server
    }
    
}
