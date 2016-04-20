/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.mail;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Norah
 */
public class Group {
   private  List<Person> members = new LinkedList<Person>();
   
   
   /**
    * cette methide permet d'augmente une personne à un group
    * @param p 
    */
   public void addMember(Person p){
       members.add(p);
   }
   
   
   /**
    * retourne les menbres d'un group*
    * @return 
    */
   
   public List<Person> getmenbers(){
       return members;
   }
}
