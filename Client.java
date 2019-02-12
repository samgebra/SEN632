//-----------------------------------------------------
// SEN 632 Jan 2019 Project
// Student names:
//      Babita Patil
//      Kelsey Kinder
//      Matt Hunter
//      Sam Gebra
//
// File Name: Client.java
//
// This file is Class that tests the Client
//  
// Date created: Jan 31, 2018
// Source: SEN632 Prject Team
//-----------------------------------------------------
import java.io.EOFException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.*;

public class Client extends JFrame implements ActionListener
{   
   //General
   private String serverFeedback = "null"; // placeholder for any incoming message from server
   private int authenticationTriesCounter = 0; // placeholder for how many failed attempts a user would do
   private ObjectOutputStream outgoingObject; // used to send data to server
   private ObjectInputStream incomingObject; // used to receive data from server
   public static final int WIDTH = 400; // The width of the SwingCalculator window
   public static final int HEIGHT = 300; // The height of the Swing
   private String serverIP; // host server for this application   
   private String UID; // placeholder for the entered user ID 
   private String PW; // placeholder for the entered user password
   // this object below combine UID and PW to be sent to server for authentication
   private DatabaseLoginInfo credentials;
   private JFrame hintPopupWindow;
   private Socket communicationSocket; // socket to communicate with server
   // Top (Authentication) JPanel
   private JPanel login = new JPanel();
   private GridLayout loginLayout = new GridLayout(2,2);   
   private JButton loginButton;
   private JButton hint;
   private JTextField enterUserIDTextField;
   private JPasswordField enterUserPWTextField;
   private JLabel usernameLabel;
   private JLabel passwordLabel;
   // Middle (Notification space) JTextArea
   private JTextArea notificationArea; // display information to user
   private Font font; // to change the notificationArea font color for a better visibility
   // Bottom (Data Entry) JPanel
   private JPanel itemEnteringPanel = new JPanel(); // where the data entry buttons will be
   private GridLayout itemEnteringLayout = new GridLayout(2,2); // the above JPanel's grid buttons dimensions
   private JButton newItem = new JButton("Enter credentials first!");
   private JButton closeConnection = new JButton("Enter credentials first!");
   // This JPanel and its JTextFields is for the Item object data entry
   // it will be used in conjunction with JOptionPane
   private JPanel jpanelForItemObjectEntry;
   private JTextField userIDTextField;
   private JTextField nameTextField;
   private JTextField priceTextField;
   private JTextField calorieTextField;
   private JTextField quantityTextField;
   private JTextField dayTextField;
   private JTextField monthTextField;
   private JTextField yearTextField;
   // this object is like a joker, it contain all three (Item, DBI and String)
   // The purpose of it is to combine the available objects in one and transmit that one
   // after setting its flags for whichever Class it is containing at any given time
   private Umbrella umbrellaItem;
   private Umbrella umbrellaDBLI;
   private Umbrella umbrellaString;

   // initializing Grocery Helper and set-up GUI
   public Client(String address)
   {
	  super("SEN632 - Grocery Helper"); // Setting up the window title
      serverIP = address; // set server to which this client connects
      //------------------------------
      //Top part of the Main JFrame
      loginLayout.setHgap(0);
      loginLayout.setVgap(0);
      login.setLayout(loginLayout);
      enterUserIDTextField = new JTextField("  Enter credentials"); // enter UID to login
      enterUserIDTextField.setEditable(true);      
      // For a more use-friendly UI
      // Making the displayed message in 'enterUserID' disappear
      // upon clicking inside the TextField				
      enterUserIDTextField.addMouseListener(new MouseAdapter() {
    	  @Override
    	  public void mouseClicked(MouseEvent evt) {
    		  enterUserIDTextField.setText("");
    		  }
    	  });
      enterUserPWTextField = new JPasswordField("*************"); // enter UID to login
      enterUserPWTextField.setEditable(true);				
      enterUserPWTextField.addMouseListener(new MouseAdapter() {
    	  @Override
    	  public void mouseClicked(MouseEvent evt) {
    		  enterUserPWTextField.setText("");
    		  }
    	  });      
      loginButton = new JButton("Enter");
      hint = new JButton("Hint!");
      usernameLabel = new JLabel("Username:");
      passwordLabel = new JLabel("Password:");
      
      //Adding the action listener to the buttons
      loginButton.addActionListener(this);
      hint.addActionListener(this);
            
      login.add(usernameLabel);      
      login.add(enterUserIDTextField);
      login.add(loginButton);
      login.add(passwordLabel);
      login.add(enterUserPWTextField);
      login.add(hint);
      
      add(login, BorderLayout.NORTH);
      //------------------------------
      //Middle Part of the Main JFrame
      notificationArea = new JTextArea(); // create displayArea
      font = new Font("Arial", Font.BOLD, 14);      
      notificationArea.setFont(font);
      notificationArea.setEditable(false); // making the JTextField read-only
      notificationArea.setForeground(Color.BLUE);
      notificationArea.setBackground(Color.LIGHT_GRAY);
      add(new JScrollPane(notificationArea), BorderLayout.CENTER);      
      //------------------------------
      //Bottom Part of the Main JFrame     
      //Adding the action listener to the buttons
      newItem.addActionListener(this);
      newItem.setEnabled(false);
      closeConnection.addActionListener(this);
      closeConnection.setEnabled(false);
      
      itemEnteringPanel.setLayout(itemEnteringLayout);
      itemEnteringPanel.add(newItem);
      closeConnection.setBackground(Color.GRAY);
      itemEnteringPanel.add(closeConnection);
      add(itemEnteringPanel, BorderLayout.SOUTH);
      // For a better visual and emphasis on the 'End Session' button
      // Making the background colored RED upon mouse hovering
      closeConnection.addMouseListener(new java.awt.event.MouseAdapter()
      {
    	  @Override
    	  public void mouseEntered(java.awt.event.MouseEvent evt)
    	  {
    		  closeConnection.setBackground(Color.RED);
    		  closeConnection.setForeground(Color.YELLOW);}
    	  @Override
    	  public void mouseExited(java.awt.event.MouseEvent evt)
    	  {
    		  closeConnection.setBackground(Color.GRAY);
    		  closeConnection.setForeground(Color.BLACK);}});
      //------------------------------
      // Finally --
      // Setting up the JPanel for multiple input
      // to be used in enter the Item Object via
      // JOptionPane class after pressing "New Item? Press here!" button      
      userIDTextField = new JTextField(10);
      nameTextField = new JTextField(10);
      priceTextField = new JTextField(10);
      calorieTextField = new JTextField(10);
      quantityTextField = new JTextField(10);
      dayTextField = new JTextField(10);
      monthTextField = new JTextField(10);
      yearTextField = new JTextField(10);
      jpanelForItemObjectEntry = new JPanel();
      
      jpanelForItemObjectEntry.add(new JLabel("User ID:"));
      jpanelForItemObjectEntry.add(userIDTextField);
      jpanelForItemObjectEntry.add(new JLabel("Name:"));
      jpanelForItemObjectEntry.add(nameTextField);
      jpanelForItemObjectEntry.add(new JLabel("Price:"));
      jpanelForItemObjectEntry.add(priceTextField);
      jpanelForItemObjectEntry.add(new JLabel("Calorie: "));
      jpanelForItemObjectEntry.add(calorieTextField);
      jpanelForItemObjectEntry.add(new JLabel("Quantity: "));
      jpanelForItemObjectEntry.add(quantityTextField);
      jpanelForItemObjectEntry.add(new JLabel("Day:"));
      jpanelForItemObjectEntry.add(dayTextField);
      jpanelForItemObjectEntry.add(new JLabel("Month:"));
      jpanelForItemObjectEntry.add(monthTextField);
      jpanelForItemObjectEntry.add(new JLabel("Year:"));
      jpanelForItemObjectEntry.add(yearTextField); 

      // Setting window's size
      setSize(WIDTH, HEIGHT);
      setVisible(true); // show window
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
   } // End of Client class constructor
   
   // adding actionListner to the buttons   
   @Override
   public void actionPerformed(ActionEvent evt) {
		try
		{
     	//-----------------------------------
		//   ENTER button - VERIFYING USER'S UID AND PW
     	//   THEN CONNECT TO SERVER
		//-----------------------------------
		if(evt.getSource() == loginButton)
			{
				this.UID = enterUserIDTextField.getText();
				// convert the char[] array coming from the JPasswordText into String
				this.PW = new String(enterUserPWTextField.getPassword());
				initiateConnecting();				
				authenticate(UID, PW);
				enterUserIDTextField.setText("OFF during logon");
				enterUserPWTextField.setText("");
				enterUserIDTextField.setEditable(false);
				enterUserPWTextField.setEditable(false);
			}// End of loginButton (or 'Enter') ActionListner      

		//-----------------------------------
		//             HINT
		//-----------------------------------
		if(evt.getSource() == hint)
			{
				JOptionPane.showMessageDialog(hintPopupWindow,
						"The username is \"root\" " +
				"and the password is \"1234\" lol");
			}// End of 'hint' button's ActionListner
		//-----------------------------------
		//       ENTER ITEM
		//-----------------------------------
		if(evt.getSource() == newItem)
			{
				int result = JOptionPane.showConfirmDialog(null, jpanelForItemObjectEntry,
						"Please enter your Items details", JOptionPane.OK_CANCEL_OPTION);
				if(result == JOptionPane.OK_OPTION)
				{
					Item newItem = new Item((String)userIDTextField.getText(),
							Double.parseDouble(priceTextField.getText()),
							Integer.parseInt(quantityTextField.getText()),
							(String)nameTextField.getText(),
							Integer.parseInt(calorieTextField.getText()),
							Integer.parseInt(yearTextField.getText()),
							Integer.parseInt(monthTextField.getText()),
							Integer.parseInt(dayTextField.getText()));
					System.out.println(newItem.toString());
					this.umbrellaItem = new Umbrella();
					this.umbrellaItem.setEmbeddedItem(newItem);
					
					System.out.println("Switcher is: " + umbrellaItem.getSwitcher());
					transmitUmbrella(this.umbrellaItem);					
					notificationArea.append("\nItem sent!\n");
				}
				
			}// End of 'Enter Item'
		//-----------------------------------
		//       END SESSION
		//-----------------------------------
		if(evt.getSource() == closeConnection)
			{
				// sending the "EOF" string will signal the server to end session too
				enterUserIDTextField.setEditable(true);
				enterUserPWTextField.setEditable(true);
			    
				this.umbrellaString = new Umbrella();
				umbrellaString.setStringExistance(true);
				transmitUmbrella(umbrellaString);
				endSession(); // close connection
			}// End of 'End Session'
		} //end of try block
		catch(NumberFormatException e)
		{
			notificationArea.append("\nSomething went wrong!\n");
			e.printStackTrace();
		} // end of catch block
	} //end of actionPerformed  
   
   // connect to server and process messages from server
   private void initiateConnecting() 
   {
      try // connect to server, get streams, process connection
      {
    	 // create a Socket to make connection
    	// create Socket to make connection to server
    	communicationSocket = new Socket(InetAddress.getByName(serverIP), 10247);
    	// display connection information
    	notificationArea.append("Successfully connected to the server\nIP:PORT  => " +
    	communicationSocket.getInetAddress().getHostName() + ":10247\n\n");
        // set up output stream for objects
    	outgoingObject = new ObjectOutputStream(communicationSocket.getOutputStream());      
    	outgoingObject.flush(); // flush output buffer to send header information

        // set up input stream for objects
    	incomingObject = new ObjectInputStream(communicationSocket.getInputStream());

      } 
      catch (IOException e) 
      {
    	  notificationArea.append("\nClient terminated connection");
    	  e.printStackTrace();
      } 

   } // end of initiateConnecting()
   
   // Verify user's login info over to the server
   private void authenticate(String UID, String PW)
   {
	   
	   this.credentials = new DatabaseLoginInfo(UID, PW);
	   this.umbrellaDBLI = new Umbrella();
	   this.umbrellaDBLI.setEmbeddedDatabaseLoginInfo(credentials);
	   this.umbrellaDBLI.setDBLIExistance(true);	   
	   transmitUmbrella(this.umbrellaDBLI);
	   listening();
	   if(this.serverFeedback.contentEquals("succeeded"))
	   {
		   notificationArea.append("\nAuthentication succeeded!\n");
		   newItem.setText("New Item? Press here!");
		   closeConnection.setText("End Session");
		   newItem.setEnabled(true);
		   closeConnection.setEnabled(true);
		   authenticationTriesCounter++;
	   }
	   else if(authenticationTriesCounter == 0)
	   {
		   notificationArea.append("\nAuthentication failed!\nTry again ...\n");
		   authenticationTriesCounter++;
	   }
	   else if(authenticationTriesCounter == 1)
	   {
		   notificationArea.append("\n\nTry harder!!\n");
		   authenticationTriesCounter++;
	   }
	   else if(authenticationTriesCounter == 2)
	   {
		   notificationArea.append("\n\n\nLAST CHANCE!\n");
		   authenticationTriesCounter++;
	   }
	   else
	   {
		   notificationArea.append("\n\n\n\nYou failed to enter the right UN/PW combo\n");
		   notificationArea.append("\nClosing server conneciton!\n");
		   authenticationTriesCounter = 0;
		   endSession();
	   }
   } // end of authenticate()   

   
   // receive data from server
   // this data will always be a String object
   // because by design, the Server class will not
   // send anything but feedback in the form
   // of String object  
   private void listening()
   {

         try
         {
        	// read the incoming server message and display it
        	this.serverFeedback = (String)incomingObject.readObject(); 
        	notificationArea.append("\n" + this.serverFeedback + "\n");      	
         } 
         catch (IOException | ClassNotFoundException e) 
         {
        	 notificationArea.append("\nUnknown object type received");
        	// The below command is useful because when error happens
        	// the printed stack trace will help debug the problem
        	 e.printStackTrace();
         }

   }// end of receiveMsgFromServer function
   
   // Transmission Method   
   private void transmitUmbrella(Umbrella Object)
   {
      try
      {
    	 this.outgoingObject.writeObject(Object);
    	 this.outgoingObject.flush(); // flush data to output
      } 
      catch (IOException ioException)
      {
    	  notificationArea.append("\nError writing object\n");
      }
   }// End of DatabaseLoginInfo object transmitter
   
   // This function will end connection to server and close socket
   private void endSession() 
   {
	  notificationArea.append("\nClosing connection ...\n");
      try 
      {
    	 outgoingObject.close(); // close output stream
    	 incomingObject.close(); // close input stream
         communicationSocket.close(); // close socket
         notificationArea.append(" Session closed!\n");
      } 
      catch (IOException ioException) 
      {
         ioException.printStackTrace();
      } 
   } // end of endSession()   
} 