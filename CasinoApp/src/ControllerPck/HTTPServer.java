package ControllerPck;

import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Client.StartPage;
import Modules.Player;


public class HTTPServer extends Thread{

	Controller controller;

	static final String HTML_START = 
			"<html>" +
					"<title>HTTP Server in java</title>" +
					"<body>";

	static final String HTML_END = 
			"</body>" +
					"</html>";

	Socket connectedClient = null;	
	BufferedReader inFromClient = null;
	DataOutputStream outToClient = null;


	public HTTPServer(Socket client, Controller con) {
		connectedClient = client;
		controller = con;
	}

	@Override
	public void run() {

		try {

			System.out.println( "The Client "+
					connectedClient.getInetAddress() + ":" + connectedClient.getPort() + " is connected");

			inFromClient = new BufferedReader(new InputStreamReader (connectedClient.getInputStream()));                  
			outToClient = new DataOutputStream(connectedClient.getOutputStream());

			StringBuffer responseBuffer = new StringBuffer();
			responseBuffer.append("<b> This is the HTTP Server Home Page.... </b><BR>");
			responseBuffer.append("The HTTP Client request is ....<BR>");

		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	public void sendResponse (int statusCode, String responseString, boolean isFile) throws Exception {

		String statusLine = null;
		String serverdetails = "Server: Java HTTPServer";
		String contentLengthLine = null;
		String fileName = null;		
		String contentTypeLine = "Content-Type: text/html" + "\r\n";
		FileInputStream fin = null;

		if (statusCode == 200)
			statusLine = "HTTP/1.1 200 OK" + "\r\n";
		else
			statusLine = "HTTP/1.1 404 Not Found" + "\r\n";	

		if (isFile) {
			fileName = responseString;			
			fin = new FileInputStream(fileName);
			contentLengthLine = "Content-Length: " + Integer.toString(fin.available()) + "\r\n";
			if (!fileName.endsWith(".htm") && !fileName.endsWith(".html"))
				contentTypeLine = "Content-Type: \r\n";	
		}						
		else {
			responseString = HTTPServer.HTML_START + responseString + HTTPServer.HTML_END;
			contentLengthLine = "Content-Length: " + responseString.length() + "\r\n";	
		}			

		outToClient.writeBytes(statusLine);
		outToClient.writeBytes(serverdetails);
		outToClient.writeBytes(contentTypeLine);
		outToClient.writeBytes(contentLengthLine);
		outToClient.writeBytes("Connection: close\r\n");
		outToClient.writeBytes("\r\n");		

		if (isFile) sendFile(fin, outToClient);
		else outToClient.writeBytes(responseString);

		outToClient.close();
	}

	public void sendFile (FileInputStream fin, DataOutputStream out) throws Exception {
		byte[] buffer = new byte[1024] ;
		int bytesRead;

		while ((bytesRead = fin.read(buffer)) != -1 ) {
			out.write(buffer, 0, bytesRead);
		}
		fin.close();
	}

	@SuppressWarnings("resource")
	public static void main (String args[]) throws Exception {

		ServerSocket Server = new ServerSocket (5000);

		System.out.println ("TCPServer Waiting for client on port 5000");

		while(true) {	                	   	      	
			Socket connected = Server.accept();

			Controller controller = new Controller();

			StartPage StartPageFrame = new StartPage(controller);
			StartPageFrame.setVisible(true);
			
			Sqlite SqliteObj = new Sqlite();
			if (LoginPopup(SqliteObj)) {
				StartPageFrame.controller.player = new Player(SqliteObj.user, Integer.valueOf(SqliteObj.balance));
				(new HTTPServer(connected, controller)).start();
			} else {
				StartPageFrame.dispose();
			}
		} 

	}

	@SuppressWarnings("deprecation")
	private static boolean LoginPopup(Sqlite SqliteObj) {

		String[] options = {"Login", "Cancel"};
		JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
		JLabel lblUserName = new JLabel("User Name: ");
		JTextField uname = new JTextField(15);
		JLabel lblPass = new JLabel("Password: ");
		JPasswordField pass = new JPasswordField(15);
		panel.add(lblUserName);
		panel.add(uname);
		panel.add(lblPass);
		panel.add(pass);
		int selectedOption;

		while (true){	
			selectedOption = JOptionPane.showOptionDialog(null, panel, "Identify Yourself", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);
			if (selectedOption == 0){		
				
				if (SqliteObj.Login(uname.getText(), pass.getText())) {
					
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, SqliteObj.currStatus + "\nWant to update money? ", "Warning", dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION){
						String[] optionsToUpdate = {"Update", "Cancel"};
						JPanel panelUpdate = new JPanel();
						JLabel lblMoneyTxt = new JLabel("Enter Amount: ");
						JTextField money = new JTextField(10);					
						panelUpdate.add(lblMoneyTxt);
						panelUpdate.add(money);
						
						int selectedOptionUpdate;
						selectedOptionUpdate = JOptionPane.showOptionDialog(null, panelUpdate, "Update Money?", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, optionsToUpdate , optionsToUpdate[0]);
						if (selectedOptionUpdate == 0){	
							SqliteObj.UpdateMoney(uname.getText(), money.getText());
						} 
					}
					return true;
				} else {
					return false;
				}
			} else if (selectedOption == 1) {
				return false;
			}
		}


	}
}
