/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDate;

/**
 *
 * @author panos
 */
public class Client {           //η κλάση που καλεί τις συναρτήσεις του first server
    
    private static FirstInterface first;        //ενα αντικειμενο τυπου first server για να υλοποιει συναρτησεις
    
    
    public Client() throws RemoteException, NotBoundException, MalformedURLException {                    
        first = (FirstInterface) Naming.lookup("//localhost/first");    //σύνδεση με τον first server
    }
    
    public ArrayList<ArrayList<Ptisi>> send(LocalDate imerominiaAf, LocalDate imerominiaAn, String apo, String pros, int epivates) throws RemoteException {
        return first.send(imerominiaAf, imerominiaAn, apo, pros, epivates);     //η send που καλει τη send του first server
    }
    
    public boolean book(int pigaine, int ela, int epivates) throws RemoteException {
        return first.book(pigaine, ela, epivates);          //η book που καλει τη book του first server
    }
}
