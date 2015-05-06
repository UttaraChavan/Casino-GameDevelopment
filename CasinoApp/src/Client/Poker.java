package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ControllerPck.Controller;
import ControllerPck.Sqlite;
import Modules.Card;

import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class Poker extends JFrame {

	public Controller controller;
	private JPanel contentPane;
	public boolean[] buttonsEnable = {true, true, true, true, false, false};
	public JLabel lblPoker, lblCardsOnTable, lblYourCards, lblCurrrentBalance, lblDealer, lblOtherPlayersMove;
	JButton btnCall, btnRaise, btnAllIn, btnFold, btnBet, btnCheck;
	ArrayList<JButton> buttons = new ArrayList<JButton>();
	ArrayList<JLabel> dealcards = new ArrayList<JLabel>();
	public JLabel lblMoneyInPot, lblresult;


	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Poker frame = new Poker();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public Poker(Controller con) {
		controller = con;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			Sqlite dbobj = new Sqlite();
			public void windowClosing(WindowEvent evt) {
				dbobj.sqlUpdateOnLast(controller.player);
				dbobj.sqlUpdateOnLast(controller.otherPlayer);
			}
		});
		
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(34, 139, 34));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblPoker = new JLabel("Poker Texas Hold'em");
		lblPoker.setForeground(Color.WHITE);
		lblPoker.setFont(new Font("Verdana", Font.BOLD, 32));
		lblPoker.setBounds(242, 24, 384, 42);	

		lblCardsOnTable = new JLabel("Cards on table");
		lblCardsOnTable.setForeground(new Color(173, 255, 47));
		lblCardsOnTable.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCardsOnTable.setBounds(157, 116, 134, 28);		

		lblYourCards = new JLabel("");
		lblYourCards.setForeground(new Color(173, 255, 47));
		lblYourCards.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblYourCards.setBounds(157, 238, 134, 28);

		lblCurrrentBalance = new JLabel("Currrent Balance: " + controller.player.getBal());
		lblCurrrentBalance.setForeground(new Color(173, 255, 47));
		lblCurrrentBalance.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCurrrentBalance.setBounds(591, 428, 194, 23);

		lblMoneyInPot = new JLabel("Money in pot: 10");
		lblMoneyInPot.setForeground(new Color(173, 255, 47));
		lblMoneyInPot.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblMoneyInPot.setBounds(591, 462, 194, 23);

		lblOtherPlayersMove = new JLabel("Other player's move");
		lblOtherPlayersMove.setForeground(new Color(173, 255, 47));
		lblOtherPlayersMove.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblOtherPlayersMove.setBounds(585, 299, 200, 101);

		lblresult = new JLabel("");
		lblresult.setForeground(new Color(173, 255, 47));
		lblresult.setBounds(72, 517, 713, 33);

		lblDealer = new JLabel("");
		lblDealer.setHorizontalAlignment(SwingConstants.CENTER);
		lblDealer.setForeground(new Color(173, 255, 47));
		lblDealer.setBounds(234, 75, 392, 14);

		btnCall = new JButton("Call");
		btnCall.setForeground(new Color(173, 255, 47));
		btnCall.setFocusPainted(false);
		btnCall.setBackground(Color.BLACK);
		btnCall.setBounds(375, 474, 89, 23);
		btnCall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act) {
				System.out.println("Action deal");
				controller.ButtonPressAction(controller.POKER_CALL_BUTTON);
			}
		});
		buttons.add(btnCall);

		btnRaise = new JButton("Raise");
		btnRaise.setForeground(new Color(173, 255, 47));
		btnRaise.setBackground(new Color(0, 0, 0));
		btnRaise.setFocusPainted(false);
		btnRaise.setBounds(157, 431, 89, 23);
		btnRaise.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act) {
				controller.ButtonPressAction(controller.POKER_RAISE_BUTTON);
			}
		});
		buttons.add(btnRaise);

		btnAllIn = new JButton("All in");
		btnAllIn.setForeground(new Color(173, 255, 47));
		btnAllIn.setBackground(new Color(0, 0, 0));
		btnAllIn.setFocusPainted(false);
		btnAllIn.setBounds(266, 431, 89, 23);
		btnAllIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act) {
				System.out.println("Action performed");
				controller.ButtonPressAction(controller.POKER_ALLIN_BUTTON);
			}
		});
		buttons.add(btnAllIn);

		btnFold = new JButton("Fold");
		btnFold.setForeground(new Color(173, 255, 47));
		btnFold.setBackground(new Color(0, 0, 0));
		btnFold.setFocusPainted(false);
		btnFold.setBounds(375, 431, 89, 23);
		btnFold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act) {
				System.out.println("Action performed");
				controller.ButtonPressAction(controller.POKER_FOLD_BUTTON);
			}
		});
		buttons.add(btnFold);

		btnBet = new JButton("Bet");
		btnBet.setForeground(new Color(173, 255, 47));
		btnBet.setBackground(new Color(0, 0, 0));
		btnBet.setFocusPainted(false);
		btnBet.setBounds(157, 474, 89, 23);
		btnBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act) {
				System.out.println("Action performed");
				controller.ButtonPressAction(controller.POKER_BET_BUTTON);
			}
		});
		buttons.add(btnBet);

		btnCheck = new JButton("Check");
		btnCheck.setForeground(new Color(173, 255, 47));
		btnCheck.setFocusPainted(false);
		btnCheck.setBackground(Color.BLACK);
		btnCheck.setBounds(266, 474, 91, 23);
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act) {
				System.out.println("Action performed");
				controller.ButtonPressAction(controller.POKER_CHECK_BUTTON);
			}
		});
		buttons.add(btnCheck);
	
		AddToContentPane();			
	}

	private void AddToContentPane() {		
		contentPane.add(lblPoker);
		contentPane.add(lblCardsOnTable);
		contentPane.add(lblYourCards);
		contentPane.add(lblCurrrentBalance);
		contentPane.add(lblMoneyInPot);
		contentPane.add(btnCall);
		contentPane.add(btnRaise); 
		contentPane.add(btnAllIn);
		contentPane.add(btnFold);
		contentPane.add(btnBet);
		contentPane.add(btnCheck);
		contentPane.add(lblresult);
		contentPane.add(lblOtherPlayersMove);
		contentPane.add(lblDealer);

	}

	private void EnableButons(boolean[] startEnabling) {
		for (int i=0; i<startEnabling.length; i++){
			buttons.get(i).setEnabled(startEnabling[i]);
		}
	}

	public void SetResultLabel(String str){
		lblresult.setVisible(true);
		lblresult.setText(str);
		EnableButons(buttonsEnable);
		System.out.println("ResultLabel Set end");
	}

	public void RefreshScreenForNextRound(){
		contentPane.removeAll();
		AddToContentPane();	
		dealcards = new ArrayList<JLabel>();
	}

	public void inactivateButtons(){
		btnCall.setEnabled(false);
		btnRaise.setEnabled(false);
		btnAllIn.setEnabled(false);
		btnFold.setEnabled(false);
		btnBet.setEnabled(false);
		btnCheck.setEnabled(false);
	}
	public void ActivateButtons(boolean[] buttons){
		btnCall.setEnabled(buttons[0]);
		btnRaise.setEnabled(buttons[1]);
		btnAllIn.setEnabled(buttons[2]);
		btnFold.setEnabled(buttons[3]);
		btnBet.setEnabled(buttons[4]);
		btnCheck.setEnabled(buttons[5]);
	}

	public void AddPlayerCards(ArrayList<Card> playersCards){
		JLabel lblPlayerCard1 = new JLabel("");
		lblPlayerCard1.setBounds(314, 253, 71, 96);

		JLabel lblPlayerCard2 = new JLabel("");
		lblPlayerCard2.setBounds(405, 253, 71, 96);

		lblPlayerCard1.setIcon(new ImageIcon(this.getClass().getResource("/Cards/"+playersCards.get(0)+".png")));
		lblPlayerCard2.setIcon(new ImageIcon(this.getClass().getResource("/Cards/"+playersCards.get(1)+".png")));

		contentPane.add(lblPlayerCard1);
		contentPane.add(lblPlayerCard2);

	}

	public void AddDealerCards(ArrayList<Card> dealerCards){
		
		JLabel lblTableCard1 = new JLabel("");
		lblTableCard1.setBounds(314, 116, 71, 96);
		dealcards.add(lblTableCard1);

		JLabel lblTableCard2 = new JLabel("");
		lblTableCard2.setBounds(405, 116, 71, 96);
		dealcards.add(lblTableCard2);

		JLabel lblTableCard3 = new JLabel("");
		lblTableCard3.setBounds(496, 116, 71, 96);
		dealcards.add(lblTableCard3);

		JLabel lblTableCard4 = new JLabel("");
		lblTableCard4.setBounds(587, 116, 71, 96);
		dealcards.add(lblTableCard4);

		JLabel lblTableCard5 = new JLabel("");
		lblTableCard5.setBounds(678, 116, 71, 96);
		dealcards.add(lblTableCard5);

		int i=0;
		for (Card d : dealerCards){
			dealcards.get(i).setIcon(new ImageIcon(this.getClass().getResource("/Cards/"+d.toString()+".png")));
			i++;
		}

		contentPane.add(lblTableCard1);
		contentPane.add(lblTableCard2);
		contentPane.add(lblTableCard3);
		contentPane.add(lblTableCard4);
		contentPane.add(lblTableCard5);
	}
}
