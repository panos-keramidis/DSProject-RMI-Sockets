/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;
import java.time.LocalDate;
/**
 *
 * @author panos
 */
public interface FirstInterface extends Remote{   //υλοποίηση του Interface του πρώτου Server
    
    public ArrayList<ArrayList<Ptisi>> send(LocalDate imerominiaAf, LocalDate imerominiaAn, String apo, String pros, int epivates) throws RemoteException; //η συνάρτηση send
    
    public boolean book(int pigaine, int ela, int epivates) throws RemoteException; //η συνάρτηση book
}
