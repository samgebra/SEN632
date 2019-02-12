//-----------------------------------------------
//SEN 632 Jan 2019 Project
//The Grocery Helper
//Student names:
//	Kelsey Kinder
//	Sam Gebra
//
//File name: readme.txt
//This file was written with the intent to provide a //simple readme document to accompany our Grocery //Helper application, in an attempt to provide a //simple run-down of how to operate the application //we've built.
//
//Date created: Feb 10, 2019
//-----------------------------------------------



Please group the following java files on the same directory on the client computer (ClientLauncher.java, Client.java, Item.java, Umbrella.java, DatabaseLoginInfo.java), on another computer (the server), have MySQL installed and set the database username and password to "root" and "1234", then group the following java files on the same directory as well (ServerLauncher.java, Server.java, Item.java, Umbrella.java, DatabaseLoginInfo.java, DB.java). Then run the ServerLauncher.java (using Eclipse IDE).

If the connection is via the internet find the server's IP address, but if the connection is from within the local network find the private IP address (note, if the connection is from the internet and the server computer is connected to the internet via a router please navigate through the router's setting to port-forwarding to set the port that our server and client are communicating through.

Now back to the client computer, after modifying the ClientLauncher.java code and set the server's IP address on the code line 22 (use the public IP if connecting from the internet, or private if connecting from within the local area network), please note that if the port chosen during the router port-forwarding step is different than "10247" then please also change the port in the Client.java and Server.java codes to reflect the new port.

Now execute the ServerLauncher.java on the server, then the ClientLauncher.java on the client, enter the database username and password (which should be "root" and "1234") and the connection should be established between the server and the client for the user to start entering Item objects or simply end the connection.
