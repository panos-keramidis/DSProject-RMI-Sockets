/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author panos
 */
public class First extends UnicastRemoteObject implements FirstInterface {

    /**
     * @param args the command line arguments
     */
    private static First first;     //ένα αντικείμενο τύπου first server

    public First() throws RemoteException {
        super();        //constructor του πατέρα UnicastRemoteObject
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException {
        Registry r = LocateRegistry.createRegistry(1099);       //RMI για εκτέλεση netbeans
        first = new First();        //αρχικοποιώ το first server
        
        Naming.rebind("//localhost/first", first);      //δηλώνοντας σε ποια πόρτα ακούμε
    }
    
    
    @Override
    public ArrayList<ArrayList<Ptisi>> send(LocalDate imerominiaAf, LocalDate imerominiaAn, String apo, String pros, int epivates) {    //η send
        //δεχεται ορίσματα ημερομηνία αφιξης, αναχώρισης, από, προς και επιβάτες
        Socket s;   //ένα νεο socket
        try {
            s = new Socket("localhost", 5555);  //αρχικοποιείται localhost
            ObjectOutputStream outstream = new ObjectOutputStream(s.getOutputStream());  //τα streams
            ObjectInputStream instream = new ObjectInputStream(s.getInputStream());
            
            Message m1 = new Message(imerominiaAf, imerominiaAn, apo, pros, epivates);  //ξεκινάμε με check
            outstream.writeObject(m1);  //το στέλνουμε
            outstream.flush();
            Message m2 = (Message) instream.readObject();   //διαβαζουμε
            if(m2.getAnagnoristiko().equals("SEND")){       //αν μας σταλεί SEND
                return m2.getPtisis();          //η send επιστρέφει arraylist με τα αποτελεσματα που ταιριαζουν με την αναζητηση
            }           
            instream.close();   //κλείνω τη σύνδεση
            outstream.close();
            s.close();
        } catch (IOException ex) {
            Logger.getLogger(First.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(First.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean book(int pigaine, int ela, int epivates) {       //η book δέχεται δυο κωδικους εναν για αφιξη έναν για αναχώρηση και τον αριθμό επιβατών
        Socket s;   //socket
        try {
            s = new Socket("localhost", 5555);      //localhost
            ObjectOutputStream outstream = new ObjectOutputStream(s.getOutputStream());  //τα streams
            ObjectInputStream instream = new ObjectInputStream(s.getInputStream());

            Message m1 = new Message(pigaine, ela, epivates);       //BOOK
            outstream.writeObject(m1);          //το στέλνουμε
            outstream.flush();
            Message m2 = (Message) instream.readObject();   //διαβαζουμε απάντηση
            if(m2.getAnagnoristiko().equals("OK")){     //αν ειναι OK
                return true;        //επιστρέφουμε true 
            }
            else if(m2.getAnagnoristiko().equals("NOT OK")){
                return false;           //αλλιώς false
            }
            instream.close();   //κλείνω τη σύνδεση
            outstream.close();
            s.close();
            
        } catch (IOException ex) {
            Logger.getLogger(First.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(First.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
