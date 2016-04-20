/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import model.mail.MailAddress;
import model.mail.Person;

/**
 *
 * @author Norah
 */
public class Configuration implements Iconfiguration {

    private String smtpServerAddress;
    private int serverport;
    private final List<Person> victimes;
    private final List<MailAddress> messages;
    private int numberOfGroup;
    private String subject = null;

    public Configuration() throws IOException {
        victimes = loadAddressFromFile("victims.utf8");
        messages = loadDataFromFile("messages.utf8");
        loadPropertie("configuration.properties");

    }

    public void loadPropertie(String fileName) throws IOException {
        FileInputStream f = new FileInputStream(fileName);
        Properties properpties = new Properties();
        properpties.load(f);

        this.smtpServerAddress = properpties.getProperty("smtpserverAddress");
        this.serverport = Integer.parseInt(properpties.getProperty("smtpServerport"));
        this.numberOfGroup = Integer.parseInt(properpties.getProperty("numberOfGroup"));
        this.subject = properpties.getProperty("Subject");
    }

    /**
     * Lorsqu'on désire modifier une chaine de caractères ou alors en construire
     * une petit à petit, l'utilisation des String peut couter cher en terme de
     * performance étant donné le caractère immuable des String. La création
     * d'objets est en effet une opération qui peut prendre du temps. Afin de
     * pallier a ces problèmes, la librairie standard Java contient la classe
     * StringBuffer qui représente également une chaine de caractères mais les
     * objets de type StringBuffer sont mutables. Dès lors, les opérations
     * dessus seront plus rapides.
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public List<MailAddress> loadDataFromFile(String fileName) throws IOException {
        List<MailAddress> resultat;
       
        // String tmp;
        try (FileInputStream f = new FileInputStream(fileName)) {
            InputStreamReader fls = new InputStreamReader(f, "UTF-8");
            try (BufferedReader reader = new BufferedReader(fls)) {
                resultat = new LinkedList<>();
                String line = reader.readLine();
                while (line != null) {
                    MailAddress m = new MailAddress();
                    
                    while ((line != null) && (!line.equals(":::"))) {
                         StringBuilder body = new StringBuilder();
                        if (line.indexOf("subject") != -1) {
                            String str[] = line.split(":");
                            m.setSubject(str[1]);
                        } else {
                            body.append(line);
                            body.append("\r\n");
                        }
                         m.setbody(body.toString());
                        line = reader.readLine();
                    }
                    resultat.add(m);
                    line = reader.readLine();
                }
                return resultat;
            }
        }
    }

    /**
     * Cette methode retourne une liste de personne stocker dans le fichier de
     * configuration pour extraire le nom et le prenom de l'utilisateur nous
     * avons adopte la norma utilise par la Heig pour formé les adresses email
     * du genre prenom.nom@heig-vd.ch donc pour extraire les differentes
     * parties: 
     *    - on utilise la methode split pour extaire le nom et prenom en
     *      passant an paramètre @ 
     *    - on utilise a nouveau le split sur le tableau  String retourne par 
     *      le premier splite en passant en paramètre le . *
     * notre premier element sera le nom * deuxième element sera le prèenom
     *
     * @param fileName le nom du fichier contenant les addresses des victims
     * @return
     * @throws IOException
     */
    
    
    public List<Person> loadAddressFromFile(String fileName) throws IOException {
        List<Person> result;
        String tmp = null;
        
        
        try (FileInputStream f = new FileInputStream(fileName)) {
            InputStreamReader fls = new InputStreamReader(f, "UTF-8");
            
            
            try (BufferedReader reader = new BufferedReader(fls)) {
                result = new LinkedList<>();
                String address = reader.readLine();
                while (address != null) {
                    String str[] = address.split("@");
                    str = str[0].split("\\.");
                    
                    
                    if (str.length == 2) {
                        tmp = str[1];
                    }
                    result.add(new Person(address, str[0], tmp));
                    address = reader.readLine();
                }
            }
        }
        return result;
    }
    
    /**
     * Cette methode retourne l'adresse du serveur
     * @return 
     */
    

    public String getServerAddress() {
        return smtpServerAddress;
    }

    /**
     * cette methode retourne le numero de port du serveur
     * @return 
     */
    
    public int getServerport() {
        return serverport;
    }

    /**
     * retourne une liste de personne contenant les victims
     * @return 
     */
    public List<Person> getVictim() {
        return victimes;
    }

    /**
     * retourne le nombre de group
     * @return 
     */
    public int getNumberOfGroup() {
        return numberOfGroup;
    }

    /**
     * retourne une liste de message contenant la liste des message
     * @return 
     */
    public List<MailAddress> getMessage() {
        return messages;
    }

 /**
  * return le sujet du mail
  * @return 
  */   
    public String getSubject() {
        return subject;
    }
   
}
