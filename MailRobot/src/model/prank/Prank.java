/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.prank;

import java.util.ArrayList;
import java.util.List;
import model.mail.MailAddress;
import model.mail.Person;

/**
 *
 * @author Norah
 */
public class Prank {

    private Person victimSender;
    private final List<Person> victimRecipients = new ArrayList<>();
    private final List<Person> witnessRecepients = new ArrayList<>();
    private MailAddress message;

    
    /**
     * retourne le victim sender
     * @return 
     */
    public Person getVictimSender() {
        return victimSender;
    }

    /**
     * augmente les victims recepteurs dans la liste 
     * @param victimRecipients 
     */
    public void addVictimRecipients(List<Person> victimRecipients) {
        this.victimRecipients.addAll(victimRecipients);
    }
    
    

    public void addWitnessvictim(List<Person> witnessrecipient) {
        this.witnessRecepients.addAll(witnessrecipient);
    }

    /**
     * retourne le message elu comme message de la plaisanterie
     * @return 
     */
    public MailAddress getMessage() {
        return message;
    }
    
    /**
     * reoturne une liste de victims recipient
     * @return 
     */

    public List<Person> getVictimRecipient() {
        return victimRecipients;
    }

   
    public List<Person> getwitnessrecipient() {
        return witnessRecepients;
    }

    public void setMessage(MailAddress msg) {
        this.message = msg;
    }

    public void setVictimsender(Person victimesender) {
        this.victimSender = victimesender;
    }

}
