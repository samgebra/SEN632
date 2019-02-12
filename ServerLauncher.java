//-----------------------------------------------------
// SEN 632 Jan 2019 Project
// Student names:
//      Babita Patil
//      Kelsey Kinder
//      Matt Hunter
//      Sam Gebra
//
// File Name: ServerLauncher.java
//
// This file is Class that launches the Client
//  
// Date re-created: Jan 31, 2018
// Source: 28:ServerLauncher.java -- Deitel
//-----------------------------------------------------
import javax.swing.JFrame;

public class ServerLauncher
{
   public static void main(String[] args)
   {
      Server application = new Server(); // create server      
      application.startListening(); // run server application
   } 
}