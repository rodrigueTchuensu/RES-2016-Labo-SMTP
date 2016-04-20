/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.mail;

/**
 *
 * @author Norah
 */
public class Person {
    private String address;
    private String firstname;
    private String lastname;
  public Person(String addr, String firstname, String lastname){
      this.address =  addr;
      this.firstname = firstname;
      this.lastname = lastname;
  }
  
 /**
  * retourne l'adresse
  * @return 
  */
  public String getAddr(){
      return address;
  }
  
  /**
   * retourne le prenom 
   * @return 
   */
  public String getfirstname(){
      return firstname;
  }
  
  /**
   * returne le nom
   * @return 
   */
  public String getlastname(){
      return lastname;
  }
  
}
