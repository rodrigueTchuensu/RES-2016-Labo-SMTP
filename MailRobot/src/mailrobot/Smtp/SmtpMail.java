
package mailrobot.Smtp;

import configuration.Configuration;
import configuration.Iconfiguration;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import model.mail.MailAddress;
import model.mail.Person;
import model.prank.GeneratorPrank;
import model.prank.Prank;


public class SmtpMail {

    private static Object MailAddress;

    public SmtpMail() {
        super();
    }

    /**
     * @param serverName adresse of SMTP server (numerique ou par le nom)
     * @param port le numéro de port du serveur mail
     * @param sender Addresse email dde l'emmeteur
     * @param prank
     */
    /**
     * envoyer le message
     *
     * l'envoi ce fait via la sequence suivante connect to server EHLO MAIL FROM
     * RCPT TO DATA .....data...... QUIT
     *
     * toutes les défaillances sont considérées comme des erreurs , même si
     * certaines peuvent être récupérés
     *
     * @exception IOException erreur pendant la communication
     * @exception SmtpException erreur returned by SMTP server
     *
     *
     *
     */
    public void simpleSend(String serverName, int port, Prank p)
            throws SmtpException, UnknownHostException, IOException {
        serverAddress = InetAddress.getByName(serverName);
        this.pranks = p;
        serverport = port;
        mail = pranks.getMessage();
        message = mail.getBody();
        sender = pranks.getVictimSender();
        this.subject = mail.getSubject();

        send();
    }

    
    protected void send()
            throws SmtpException, IOException {

        Socket sock = null;

        try {
            //setup connection 
            trace("trying to connect to server");
            sock = new Socket(serverAddress, serverport);
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream(), "UTF-8"));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(sock.getOutputStream(), "UTF-8"));

            int returnCode = getReponse(in);

            if (returnCode / 100 != 2) {
                throw new SmtpException("CONNECT", returnCode, lastcommande);
            }
            //connect pas un bonjoiur

            sendcommand(out, "EHLO " + InetAddress.getLocalHost().getHostName(), in, 2);
            sendcommand(out, "MAIL from: " + sender.getAddr(), in, 2);

            for (int i = 0; i < pranks.getVictimRecipient().size(); ++i) {
                sendcommand(out, "RCPT TO: " + pranks.getVictimRecipient().get(i).getAddr(), in, 2);
            }
            sendcommand(out, "DATA ", in, 3);

            sendata(out);
            returnCode = getReponse(in);
            if (returnCode / 100 != 2) {
                throw new SmtpException("Send data", returnCode, lastcommande);
            }

            sendcommand(out, "Quit", in, 2);
        } catch (IOException e1) {
            if (sock != null) {
                sock.close();
            }
            throw e1;
        } catch (SmtpException e2) {
            if (sock != null) {
                sock.close();
            }
            throw e2;
        }

    }

    
    
    
    /**
     * Chaque ligne de la commande smtp lors de la connexion à un code. C'est avec
     * ce code qu'on vérifie si le serveur est d'accord ou pas d'initier une
     * conversation avec le client. Le but de cette methode est de retourner
     * ce code et le dernier message qui sera stocker dans String est la dernier 
     * message envoyé par le serveur .
     */
    protected int getReponse(BufferedReader in) throws IOException {
        int code;
        boolean plusDeligne;
        String line;
        StringBuffer text = new StringBuffer();

        do {
            line = in.readLine();
            trace("response: " + line);

            plusDeligne = (line.charAt(3) == '-');
            text.append((line.substring(4, line.length())));
        } while (plusDeligne);

        code = Integer.parseInt(line.substring(0, 3));

        lastcommande = text.toString();

        return code;

    }

    
    
    /**
     * Lève une exception de type SMTPException si le code n'est pas valide
     * c'est à dire le premier caractére retourner par le serveur
     *
     * @param out Outputstream pour serveur SMPT
     * @param cmd commande envoyer
     * @param in Inputstream pour serveur smtp
     * @exception SmtpException propage une exeception losrque le serveur àn
     * retourne un code d'erreur
     * @exception IOException erreur pendant la communication
     */
    protected void sendcommand(PrintWriter out, String cmd, BufferedReader in, int okCode)
            throws SmtpException, IOException {

        trace("sending: " + cmd);
        out.write(cmd);
        out.write("\r\n");
        out.flush();

        int returnCode = getReponse(in);

        if (returnCode / 100 != okCode) {
            throw new SmtpException(cmd, okCode, lastcommande);
        }

    }

    /**
     * envoyer le message au serveur une entête simple qui contient <From> et
     * <To>
     * la suit avec <subject>.
     *
     * @param out Outputstream
     */
    protected void sendata(PrintWriter out) {
        // entête du mail

        out.write("From: " + sender.getAddr() + "\r\n");
        out.write("TO: " + pranks.getVictimRecipient().get(0).getAddr());
        
        for (int i = 0; i < pranks.getVictimRecipient().size(); ++i) {
            out.write("," + pranks.getVictimRecipient().get(i).getAddr());
        }
        
        out.write("\r\n");
        out.write("Cc: " + pranks.getVictimRecipient().get(0).getAddr());
        
        for (int i = 0; i < pranks.getVictimRecipient().size(); ++i) {
            out.write("," + pranks.getVictimRecipient().get(i).getAddr());
        }
        
        out.write("\r\n");
        out.write("Subject: " + mail.getSubject() + "\r\n");
        out.write("\r\n"); // fin de l'entête

        //message a envoyer
        String data = msg2data(message);
        trace(data);
        out.write(data);

        //fin du message marque par <CR/LF>.<CR/LF>
        trace(".");
        out.write("\r\n.\r\n");
        out.flush();
    }

    /**
     * convertir le String au format compris par le serveur. Le format de message
     * attendu est le suivant: les lignes séparées par saut de ligne Le format
     * de données SMTP est : lignes séparées by <lt> <LF> <LF>;
     *
     * @param message message a convertir
     * @return le message convertir
     */
    protected String msg2data(String message) {
        StringBuffer buffer = new StringBuffer();
        String line;
        int start = 0;
        int end = 0;
        if (message != null) {
            buffer.ensureCapacity(message.length() + 100);
            do {
                end = message.indexOf('\n', start);
                if (end == -1) {
                    line = message.substring(start);
                } else {
                    line = message.substring(start, end);
                    end++;
                }

                if (line.length() > 0 && line.charAt(0) == '.') {
                    buffer.append('.');
                }

                buffer.append(line);
                if (end != -1) {
                    buffer.append("\r\n");
                }

                start = end;
            } while (end != -1);
        }

        return buffer.toString();
    }

    public void debugMode(boolean debug) {
        this.debug = debug;
    }

    /**
     * Outputs the String to stderr if debug is turned on.
     *
     * @param t String to trace.
     */
    protected void trace(String t) {
        if (debug) {
            System.err.println("D:" + t);
        }
    }
    private boolean debug;

    /**
     * l'adresse su serveur smtp
     */
    private InetAddress serverAddress = null;

    /**
     * le port utilise par le client smpt
     */
    private int serverport = -1;

    /**
     * l'adresse source pour l'envoi des messages
     */
    private Person sender;
    /**
     * listre des mail recus;
     *
     */

    private MailAddress mail = new MailAddress();
    private Prank pranks = null;
    private String message = null;

    /**
     * ce String va nous permettre de stocker la dernière valeur de la reponse
     * du serveur.
     */
    String lastcommande = null;

    /**
     * l'objet du mail
     */
    private String subject = null;

    public static void main(String[] argv) throws IOException {
        
        
        //recupere les données neccessaire pour genere un prank  
        Iconfiguration conf = new Configuration();
        
        //liste contenant les pranks
        List<Prank> pranks = new LinkedList<Prank>();

        
        GeneratorPrank gPranks = new GeneratorPrank(conf);
        //génération de la liste de panks 
       
        pranks = gPranks.generatePranks();
        
        //le nomù du serveur 
        String serverName = conf.getServerAddress();

        //le numèro du port 
        int serverPort = conf.getServerport();

        SmtpMail smtp = new SmtpMail();
        smtp.debugMode(true);
        try {
            for (Prank p : pranks) {
                smtp.simpleSend(serverName, serverPort, p);
            }
        } catch (Exception e) {
            System.err.println("Error while sending: " + e.toString());
            System.exit(1);
        }

        System.exit(0);
    }
}

class SmtpException extends Exception {

    /**
     *
     * cree une nouvelle exception
     *
     * @param lastCmd la dernière commande envoyer avent l'erreur.
     * @param errorCode le code d'erreur retourne par le serveur.
     * @param errorMsg le message d'erreur.
     */
    public SmtpException(String lastCmd, int errorCode, String errorMsg) {
        this.lastCmd = lastCmd;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    /**
     * convertir l'exception en String
     *
     * @return String representation of Exception
     */
    public String toString() {
        StringBuffer buff = new StringBuffer();

        buff.append("Error while executing cmd " + lastCmd + ":"
                + errorCode + "-" + errorMsg);

        return buff.toString();
    }

    String lastCmd = null;
    int errorCode = -1;
    String errorMsg = null;
}
