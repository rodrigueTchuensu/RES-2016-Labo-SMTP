/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import java.io.IOException;
import java.util.List;
import model.mail.MailAddress;
import model.mail.Person;

/**
 *
 * @author Norah
 */
public interface Iconfiguration {

    public List<MailAddress> loadDataFromFile(String fileName) throws IOException;

    public List<Person> loadAddressFromFile(String fileName) throws IOException;

    public void loadPropertie(String fileName) throws IOException;

    public String getServerAddress();

    public int getServerport();

    public List<Person> getVictim();

    public List<MailAddress> getMessage();

    public int getNumberOfGroup();

    public String getSubject();

   
}
