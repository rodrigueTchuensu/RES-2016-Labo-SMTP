# HEIGVD-RES-2016-Labo-SMTP 
 
Author: Rodrigue Tchuensu  && Thibaut Togue Kamga  
Date: 2016-02-23
[liens du repos](https://github.com/rodrigueTchuensu/RES-2016-Labo-SMTP)

## AIM
Develop a SMTP client application that automatically plays pranks on groups of e-mail addresses (list of victims).  
A confuguration package is made available with 3 files.   
There is a file which the user can edit to define a list of e-mail adresses(victims).  
Another file that can be edited to specify a list of prank messages(define a list of e-mail messages) delimited from one another by " **:::** " sequence. This mail would be sent to all group recipients, from the address of the group sender. In other words, the recipient victims would be lead to believe that the sender victim has sent them the mail.    
The 3rd file can also be edited by the user, it contains the server adresse, the number of groups he whishes to form within his list of e-mail adresses and some other configuration properties.In every group of victims, there should be 1 sender and at least 2 recipients (i.e. the minimum size for a group is 3).

## Objectives  
In this lab, we have develop a client application (TCP) in Java. This client application uses the Socket API to communicate with a SMTP server. Our code includes a **partial implementation of the SMTP protocol**.  
The objectives of this lab is to:  
  1 - Make practical experiments to become familiar with the **SMTP protocol**, that is to be able to use a command line tool to **communicate with a SMTP server** and to send well-formed messages to the server, in order to send emails to the address of your choice.  

  2 - Permit us **See how easy it is to send forged e-mails**, which appear to be sent by certain people but in reality are issued by malicious users.  
  
  3 - Let us understand what it means to **implement the SMTP protocol** and be able to send e-mail messages, by working directly on top of the Socket API it should be noted that the use of the SMTP library was not allowed.  

  4 - Initiate us to the notions of **test double** and **mock server**, which are useful when developing and testing a client-server application. The **mock server** simulates a smtp server and prevents conjugestion of the network by the multiple prank mails.

  5 - Make us **Design a simple object-oriented model** to implement the functional requirements of the task specification. The webcast proposed by our teacher was of great help. You can browse the following links to the webcasts.  
  [Youtube Webcast part 1](https://youtu.be/ot-bDyqgTtk)  
  [Youtube Webcast part 2](https://youtu.be/Nj34XicS6JM)  
  [Youtube Webcast part 3](https://youtu.be/LoFKsT9Rj10)  
  [Youtube Webcast part 4](https://youtu.be/OrSdRCt_6YQ)   

## Class Diagrams  
![Diagrame de classe](figures\Diagram_de_classe.jpg)

### Description of principal points:
See anotation on the class diagram 

## Description of the implementation
We choosed to define a SmtpMail class which play the role of our smpt client. This class contains an internal class called smtpException which derives from exception whose role is to throw an exception each time we have a negative response from the server, reason why we store the last command send by the user.   This determines why the exception was thrown.  
We have configuration package into which we have a class named Configuration whose role is to read the content of the configuration files.  
In the package model.mail we have 3 classes.  
class Group is a data structure that will receive instances of class Person also found in this package. Groups of person(victims) are stored in this data structure in the form of a list.  
class Person defines a person by making available it's personal information.
class MailAdresse on its own has all the header information required to send a mail. It's field members are initialised from the configuration files.  
In package model.prank   
we have 2 classes.  
class GeneratorPrank which generate group of person and a list of pranks
class Prank contains all the information required to make a prank mmessage  
## Installation and usage 
Download the files in this respiratory.  
Edit the configuration files as stated in the *AIM - Section*. Compile the  project with a Netbeans IDE and run it to your satisfaction.  
NB: Each time you edit a configuration file you have to re-compile the project before running it. 
## Installing and Using a mock SMTP server  
We used the MockMock server. The setup is availaible [here](https://github.com/tweakers-dev/MockMock). Copy the link to the github respiratory to the clipboard. To the directory into wich you want to store the setup files open a bash shell and type in the command `git clone `, then paste the link to the respiratory and hit enter. Upon doing this, mockmock program files are transferred to your computer in the current directory. The next thing to do is to run the `mvn clean install` command to compile the project or to open the project in Netbeans and compile it there. When the compilation is successfully done, two jars are produced in the target directory.  
Now we can run the mock server by opening a bash shell in the target directory and running the command `java -jar MockMock.jar`. This will run MockMock on the default ports 25 (for SMTP) and 8282 (the web interface).  After it is started you can use your internet browser to navigate to http://localhost:8282.  
To run MockMock on another port, you can start it with the  *-p* parameter to indicate the **SMTP** port and the *-h* to indicate the **HTTP** port. e.g
`java -jar MockMock.jar -p 2525 -h 8080`  
This will run MockMock on **SMTP** port 2525 and http port 8080.  

_**What is a Mock Server ?**_  
This is an application that offers the services of a **SMTP Server** on your local machine when it is running. The server runs on a local port and http service to on another. 
 