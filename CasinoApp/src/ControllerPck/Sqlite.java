package ControllerPck;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;

import Modules.Player;

public class Sqlite {
	final String DatabasePath = "jdbc:sqlite:D:\\Uttara\\Login.sqlite";
	Connection connectionObj = null;
	String currStatus = "";
	String user = "";
	String balance = "";
	
	public Sqlite() {
		try {
			Class.forName("org.sqlite.JDBC");
			connectionObj = DriverManager.getConnection(DatabasePath);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	public boolean Login(String id, String pass){
		boolean returnflag = false;
		String query;
		PreparedStatement pst;
		ResultSet rs;
		int count;

		try {
			query = "SELECT * FROM Casinodb WHERE userid=? AND pass=? ";
			pst = connectionObj.prepareStatement(query);
			pst.setString(1, id);
			pst.setString(2, pass);
			rs = pst.executeQuery();

			count=0;
			
			while (rs.next()) {
				count++;
				user=rs.getString(1);
				balance=rs.getString(3);
			}
			if (count==0) {
				JOptionPane.showMessageDialog(null, "No match found");
				returnflag = false;
			} else {
				query = "SELECT * FROM Casinodb WHERE userid=? AND isplaying='false' ";
				pst = connectionObj.prepareStatement(query);
				//JOptionPane.showMessageDialog(null, "welcome " + user + "!");
				pst.setString(1, id);
				rs = pst.executeQuery();

				count=0;
				while (rs.next()) {
					count++;
				}
				if (count==0) {
					JOptionPane.showMessageDialog(null, "You are already logged in");
					returnflag = false;
				} else {
					//JOptionPane.showMessageDialog(null, "Welcome " + user + "!!!\n Your Current balance is: " + balance);
					currStatus = "Welcome " + user + "!!!\n Your Current balance is: " + balance;
					query = "UPDATE Casinodb SET isplaying='true' WHERE userid=? ";
					pst = connectionObj.prepareStatement(query);
					pst.setString(1, id);
					pst.executeUpdate();
					
					returnflag = true;
				}						
			}

			rs.close();
			pst.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			returnflag = false;
		}
		
		return returnflag;
	}
	
	public void UpdateMoney(String id, String money){
		try{
			String query = "UPDATE Casinodb SET money=? WHERE userid=? ";
			PreparedStatement pst = connectionObj.prepareStatement(query);
			pst.setString(1, money);
			pst.setString(2, id);
			pst.executeUpdate();
			balance = money;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Sorry, Could not update amount");
		}
		
	}
	
	public void sqlUpdateOnLast(Player player){
		try{
			String query = "UPDATE Casinodb SET money=?, isplaying='false', iswaitingforpoker='false' WHERE userid=? ";
			PreparedStatement pst = connectionObj.prepareStatement(query);
			pst.setString(1, String.valueOf(player.getBal()));
			pst.setString(2, player.getName());
			pst.executeUpdate();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Sorry, Could not update last session");
		}
	}
	
	public ArrayList<String> getListOfPlayersWaitingForPoker(){
		ArrayList<String> playersWaiting = new ArrayList<String>();
		int count=0;
		try{
			String query = "SELECT * FROM Casinodb WHERE iswaitingforpoker='true' ";
			PreparedStatement pst = connectionObj.prepareStatement(query);
			ResultSet rs;
			pst = connectionObj.prepareStatement(query);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				count++;
				playersWaiting.add(rs.getString(1)+","+rs.getString(3));
			}
			
			System.out.println("result count: " + count);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Sorry, Could not update last session");
		}
		
		System.out.println("result count: " + count);
		if(count == 0)
			return null;
		else 
			return playersWaiting;
	}
	
	public void updatePokerStatusWait(Player p, String status){
		try{
			String query = "UPDATE Casinodb SET iswaitingforpoker=? WHERE userid=? ";
			PreparedStatement pst = connectionObj.prepareStatement(query);
			pst.setString(1, status);
			pst.setString(2, String.valueOf(p.getName()));
			pst.executeUpdate();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Sorry, Could not update last session");
		}
	}

}
