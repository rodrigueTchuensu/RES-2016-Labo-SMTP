/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.prank;

import configuration.Iconfiguration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import model.mail.Group;
import model.mail.MailAddress;
import model.mail.Person;

/**
 *
 * @author Norah
 */
public class GeneratorPrank {

    private  Iconfiguration configuration;
    private List<Person> availableVictims = new LinkedList<>();

    public GeneratorPrank(Iconfiguration config) {
        this.configuration = config;

    }

    /**
     * Cette methode permet de constitue les differents groups qu'on utilisera
     * pour plaisantrie. a chaque fois qu'augmenter un victim dans un groupe on
     * le supreime de la liste dans le but d'eviter que une personnes se
     * retrouves dans plusierus groups a la fois pour eviter que une personne se
     * retrouve dans plusieurs groupes
     *
     * @param victims
     * @param numberOfGroup
     * @return
     */
    public List<Group> generateGroup(List<Person> victims, int numberOfGroup) {

        Collections.shuffle(victims); //melange les élèments de la liste

        //initialise la liste des map;
        int j = 0;
        for (Person p : victims) {
            availableVictims.add(p);
        }

        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < numberOfGroup; i++) {
            Group group = new Group();
            groups.add(group);
        }

        int index = 0;
        int indexp = 0;

        Group groupeCible;

        for (; availableVictims.size() > 0;) {
            groupeCible = groups.get(index);
            Person victim = availableVictims.remove(0);
            groups.get(index).addMember(victim);
            index = (index + 1) % groups.size();
        }
        return groups;
    }

    /**
     * cette methode permet re genéré plusieurs plaisentrie et de les stocker
     * dans une liste. 
     * pour eviter que le sender ne soit pas en même temp un receiver la personne 
     * elu comme sender est suprimmé de la liste des victims
     *
     * @return
     */
    public List<Prank> generatePranks() {
        List<Prank> pranks = new ArrayList<>();
        List<MailAddress> messages = configuration.getMessage();
        int indexMessage = 0;
        int numberOfGroups = configuration.getNumberOfGroup();
        int numberOfVictims = configuration.getVictim().size();

        
        if (numberOfVictims / numberOfGroups < 3) {
            numberOfGroups = numberOfVictims / 3;
        }
        
        
        List<Group> groups = generateGroup(configuration.getVictim(), numberOfGroups);
        Person sender = null;
        int i = 0;
        
        
        for (Group group : groups) {
            Prank p = new Prank();

            List<Person> victim = group.getmenbers();
            Collections.shuffle(victim);
            
            if (!victim.isEmpty()) {
                sender = victim.remove(0);
            }
            
            p.addVictimRecipients(victim);

            MailAddress message = messages.get(indexMessage);
            indexMessage = (indexMessage + 1) % messages.size();
            p.setVictimsender(sender);
            p.setMessage(message);
            System.out.println(p.getMessage().getBody());
            pranks.add(p);
        }
        return pranks;
    }
}
