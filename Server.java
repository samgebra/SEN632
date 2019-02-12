//-----------------------------------------------------
// SEN 632 Jan 2019 Project 
// Student names:
//      Babita Patil
//      Kelsey Kinder
//      Matt Hunter
//      Sam Gebra
//
// File Name: Server.java
//
// This file is the class for the server and its GUI
//  
// Date re-created: Jan 31, 2018
// Source: SEN632 Prject Team
//-----------------------------------------------------
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Server extends JFrame 
{   
   private JTextArea feedBack; // display information to user
   private Font feedbackFont;
   private ObjectOutputStream output; // output stream to client
   private ObjectInputStream input; // input stream from client
   private ServerSocket serverSocket; // server socket
   private Socket connection; // connection to client
   private boolean anotherConnection = false; //flag for single/multi-connection, false for single
   private DatabaseLoginInfo incAuthInfo;
   private Item incItem; // placeholder for incoming item
   private Item[] ItemArray; // placeholder for incoming items to be migrated to the DB later
   private int itemCounter = 0;
   private Umbrella incomingUmbrellas;
   private boolean done = false; // flag for the 'while' loop

   // Executing the GUI with class constructor 
   public Server()
   {
      super("Database Server");      
      feedbackFont = new Font("Arial", Font.BOLD, 12);
      feedBack = new JTextArea(); // create displayArea
      feedBack.setEditable(false); // making the JTextField read-only
      feedBack.setFont(feedbackFont);
      feedBack.setForeground(Color.YELLOW);
      feedBack.setBackground(Color.BLUE);
      add(new JScrollPane(feedBack), BorderLayout.CENTER);
      setSize(500, 300); // set size of window
      setVisible(true); // show window
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
   // set up and run server 
   public void startListening()
   {
	  try // set up server to receive connections
      {
    	  serverSocket = new ServerSocket(10247, 100); // create ServerSocket
    	  feedBack.append("Waiting for " + (anotherConnection?"another ":"") + "connection\n");
      	  connection = serverSocket.accept();
      	  feedBack.append("Connection received from:\n " + connection.getInetAddress().getHostName());
    	  input = new ObjectInputStream(connection.getInputStream());
      	  output = new ObjectOutputStream(connection.getOutputStream());      
      	  output.flush(); // flush output buffer to send header information
      	  
    	  while(!done)
            	{
            	// Receiving the Umbrella class
            	// and decide what to do based on
            	// its switcher attribute
    		  	this.incomingUmbrellas = new Umbrella();
            	this.incomingUmbrellas = (Umbrella)input.readObject();
            	switch(this.incomingUmbrellas.getSwitcher())
            	{
            	case 1: this.incItem = new Item();
            			this.incItem = this.incomingUmbrellas.getEmbeddedItem();
    					ItemArray[itemCounter] = this.incItem;
        				itemCounter++;
        				feedBack.append("\nItem Received and stored to the array!\n");
        				this.incomingUmbrellas = new Umbrella(); // resetting Umbrella values
        				done = false; // enable item entering loop
        				/////////////////////////////////////////////////////
                break;
            	case 2: this.incAuthInfo = new DatabaseLoginInfo();
            			this.incAuthInfo = this.incomingUmbrellas.getEmbeddedDatabaseLoginInfo();
            			if(this.incAuthInfo.getUID().contentEquals("root") && 
            					this.incAuthInfo.getPW().contentEquals("1234"))
            				{
            					feedBack.append("\nAuthentication ");
            					transmitObject("succeeded");
            					feedBack.append("!!\n");
            					this.incomingUmbrellas = new Umbrella(); // resetting Umbrella values
            					done = false; // enable item entering loop
            				}
            			else
            				{
            					transmitObject("failed");
            					done = true; // disable item entering loop
            					this.incomingUmbrellas = new Umbrella(); // resetting Umbrella values
            					endSession();
            				}
            			/////////////////////////////////////////////////////
                break;
            	case 3: transmitObject("\nSession ended.\n");
            			endSession();
						done = true;
						/////////////////////////////////////////////////////
                break;
       			default:feedBack.append("\nUnknown input, retry please.\n");
       					done = false; // continue looping
       					this.incomingUmbrellas = new Umbrella(); // resetting Umbrella values
                break;
            	}
            	}
      }
	catch (IOException | ClassNotFoundException e) 
      {
		feedBack.append("\nServer terminated connection");
		e.printStackTrace();
      }
   }   
   // send message to client
   private void transmitObject(String message)
   {
      try // send object to client
      {
         this.output.writeObject(message);
         this.output.flush(); // flush output to client
         this.feedBack.append(message);
      } 
      catch (IOException ioException) 
      {
    	  this.feedBack.append("\nError writing object");
      } 
   } // end of transmitObject function
   
   // close streams and socket
   private void endSession() 
   {
      try 
      {
    	  this.output.close(); // close output stream
    	  this.input.close(); // close input stream
    	  this.connection.close(); // close socket
    	  this.feedBack.append("\nTerminating connection\n");
      } 
      catch (IOException ioException) 
      {
         ioException.printStackTrace();
      } 
   }
}