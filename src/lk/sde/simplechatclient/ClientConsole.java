package lk.sde.simplechatclient;

// This file contains material supporting section 3.7 of the textbook:

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
import lk.sde.client.ChatClient;
import lk.sde.common.ChatIF;

// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 



/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole 
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge  
 * @author Dr Robert Lagani&egrave;re
 * @version July 2000
 */
public class ClientConsole implements ChatIF 
{
  //Class variables *************************************************
  
  /**
   * The default port to connect on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Instance variables **********************************************
  
  /**
   * The instance of the client that created this ConsoleChat.
   */
  ChatClient client;

  
  //Constructors ****************************************************

  /**
   * Constructs an instance of the ClientConsole UI.
   *
   * @param host The host to connect to.
   * @param port The port to connect on.
   */
  public ClientConsole(String host, int port) 
  {
    try 
    {
      client= new ChatClient(host, port, this);
    } 
    catch(IOException exception) 
    {
      System.out.println("Error: Can't setup connection!"
                + " Terminating client.");
      System.exit(1);
    }
  }

  
  //Instance methods ************************************************
  
  /**
   * This method waits for input from the console.  Once it is 
   * received, it sends it to the client's message handler.
   */
  public void accept() 
  {
    try
    {
      BufferedReader fromConsole = 
        new BufferedReader(new InputStreamReader(System.in));
      String message;

      while (true) 
      {
        message = fromConsole.readLine();
        client.handleMessageFromClientUI(message);
      }
    } 
    catch (Exception ex) 
    {
      System.out.println
        ("Unexpected error while reading from console!");
    }
  }

  /**
   * This method overrides the method in the ChatIF interface.  It
   * displays a message onto the screen.
   *
   * @param message The string to be displayed.
   */
  public void display(String message) 
  {
    System.out.println("> " + message);
  }

  public void update(Observable o, Object o1) {
        display(o1.toString());
  }

  
  //Class methods ***************************************************


  /**
   * This method is responsible for the creation of the Client UI.
   *
   * @param args[0] The host to connect to.
   */
  public static void main(String[] args) 
  {

    String host = "";
    int port = DEFAULT_PORT;  //The port number

    try
    {
        if(args.length<1){
            System.out.println("Please provide your login id as the first command line argument.");
            System.exit(0);
        }

        host = args[1];
        port = Integer.parseInt(args[2]);
    }    
    catch(ArrayIndexOutOfBoundsException e){
      host = "localhost";
    }
    catch(NumberFormatException ex){
        System.out.println(args[1] + " is an invalid port number\n"
                + "Port number has to be a numeric value.\nEstablishing connection on the default port...");
    }

    ClientConsole chat= new ClientConsole(host, port);
    chat.client.setLoginId(args[0]);
    chat.display("Welcome "+chat.client.getLoginId());
    chat.accept();  //Wait for console data
  }


}
//End of ConsoleChat class
