/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.mail;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Norah
 */
public class MailAddress {
    
    private String from;
    private String address;
     private final List<String> to = new ArrayList<>();
    private final List<String> cc = new ArrayList<>();
    String Subject;
    String body ;
    String realName;
   
/**
 * retourne l'adresse de le l'emmeteur
 * @return 
 */
 public String getFrom(){
     return this.from;
 }
 
 /**
  * retourne le coprs du message
  * @return 
  */
 
 public String getBody(){
     return this.body;
 }
 
 
 /**
  * retourne la valeur d' l'adresse do
  * @param return to 
  */
    public List<String> getTo(){
        return to;
    }
  
  /**
   * @param retourne le subject
   */
  
  public  String getSubject(){
      return Subject;
  }
  /**
   * retourne le coprs du devoir 
   * @param 
   */
 public void setTo(List<String> s){
     this.to.addAll(s);
 }         
 
 /**
  * modifie le nom de l'expediteur
  * @param from 
  */
 public void SetFrom(String from){
     this.from = from;
 }
 
 /**
  * modifie la liste des destinataires
  * @param s 
  */
 public void setCc(List<String> s){
     this.cc.addAll(s);
 }
 
 /**
  * modifie le coprs du message
  * @param body 
  */
 public void setbody(String body){
     this.body = body;
 }
 
 public void setSubject(String subject){
     this.Subject = subject;
 }
 
 /**
 * retourne l'adresse email de l'utilisateur.
 * @return l'adresse email
 */

public String getAddress(){
return address;
}

/**
 * modifier le champs adresse 
 * 
 *@param address email
 */

public void setAdress(int index){
    this.address = to.get(index);
}

/**
 * modifie le nom de la vie réel
 * 
 * @param realName real life name
 */
public void setRealName(String realName){
    this.realName = realName;
}


/**
 * return le nom reel 
     * @return 
 */

public String getRealName(){
    return realName;
}

 /**
  * 
  * @return 
  */
 
   
 public String toString(){
    StringBuffer buffer = new StringBuffer();
    
    if(realName != null){
        buffer.append("\"" + realName + "\"" );
    }
    if(address != null){
        buffer.append("<" + address + ">");
    }
    else{
        return null;
    }
    return buffer.toString();
}
 
}
