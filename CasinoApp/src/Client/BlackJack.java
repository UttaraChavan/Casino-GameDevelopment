package Client;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;

import ControllerPck.Controller;
import ControllerPck.Sqlite;
import Modules.Card;

@SuppressWarnings("serial")
public class BlackJack extends JFrame {

	public Controller controller;
	private JPanel contentPane;
	public boolean[] buttonsEnable = {true, false, false, false, false, false};
	private JLabel lblBlackjack, lblDealersCards, lblYourCards, lblCurrrentBalance;
	JButton btnDeal, btnNewCard, btnStand, btnSplit, btnDouble, btnInsurance;
	ArrayList<JButton> buttons = new ArrayList<JButton>();
	public JLabel lblBetAmount, lblresult;

	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BlackJack frame = new BlackJack();
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
	public BlackJack(Controller con) {
		controller = con;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			Sqlite dbobj = new Sqlite();
			public void windowClosing(WindowEvent evt) {
				dbobj.sqlUpdateOnLast(controller.player);
			}
		});
		
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(34, 139, 34));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblBlackjack = new JLabel("BlackJack");
		lblBlackjack.setForeground(Color.WHITE);
		lblBlackjack.setFont(new Font("Verdana", Font.BOLD, 32));
		lblBlackjack.setBounds(346, 24, 194, 42);		
		
		lblDealersCards = new JLabel("Dealer's Cards");
		lblDealersCards.setForeground(new Color(173, 255, 47));
		lblDealersCards.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDealersCards.setBounds(157, 116, 111, 28);		
		lblDealersCards.setVisible(false);
		
		lblYourCards = new JLabel("Your Cards");
		lblYourCards.setForeground(new Color(173, 255, 47));
		lblYourCards.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblYourCards.setBounds(157, 215, 111, 28);
		lblYourCards.setVisible(false);
		
		lblCurrrentBalance = new JLabel("Currrent Balance: " + controller.player.getBal());
		lblCurrrentBalance.setForeground(new Color(173, 255, 47));
		lblCurrrentBalance.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCurrrentBalance.setBounds(591, 428, 194, 23);
				
		lblBetAmount = new JLabel("Bet Amount: 10");
		lblBetAmount.setForeground(new Color(173, 255, 47));
		lblBetAmount.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblBetAmount.setBounds(591, 462, 194, 23);
	
		lblresult = new JLabel("");
		lblresult.setForeground(new Color(173, 255, 47));
		lblresult.setBounds(72, 517, 713, 33);
		
		btnDeal = new JButton("Deal");
		btnDeal.setForeground(new Color(173, 255, 47));
		btnDeal.setFocusPainted(false);
		btnDeal.setBackground(Color.BLACK);
		btnDeal.setBounds(375, 474, 89, 23);
		btnDeal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act) {
				System.out.println("Action deal");
				controller.ButtonPressAction(controller.BLACKJACK_DEAL);
			}
		});
		buttons.add(btnDeal);
		
		btnNewCard = new JButton("New Card");
		btnNewCard.setForeground(new Color(173, 255, 47));
		btnNewCard.setBackground(new Color(0, 0, 0));
		btnNewCard.setFocusPainted(false);
		btnNewCard.setBounds(157, 431, 89, 23);
		btnNewCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act) {
				controller.ButtonPressAction(controller.BLACKJACK_NEWCARD);
			}
		});
		buttons.add(btnNewCard);
		
		btnStand = new JButton("Stand");
		btnStand.setForeground(new Color(173, 255, 47));
		btnStand.setBackground(new Color(0, 0, 0));
		btnStand.setFocusPainted(false);
		btnStand.setBounds(266, 431, 89, 23);
		btnStand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act) {
				controller.ButtonPressAction(controller.BLACKJACK_STAND);
			}
		});
		buttons.add(btnStand);
		
		btnSplit = new JButton("Split");
		btnSplit.setForeground(new Color(173, 255, 47));
		btnSplit.setBackground(new Color(0, 0, 0));
		btnSplit.setFocusPainted(false);
		btnSplit.setBounds(375, 431, 89, 23);
		btnSplit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act) {
				controller.ButtonPressAction(controller.BLACKJACK_SPLIT);
			}
		});
		buttons.add(btnSplit);
		
		btnDouble = new JButton("Double");
		btnDouble.setForeground(new Color(173, 255, 47));
		btnDouble.setBackground(new Color(0, 0, 0));
		btnDouble.setFocusPainted(false);
		btnDouble.setBounds(157, 474, 89, 23);
		btnDouble.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act) {
				controller.ButtonPressAction(controller.BLACKJACK_DOUBLE);
			}
		});
		buttons.add(btnDouble);
		
		btnInsurance = new JButton("Insurance");
		btnInsurance.setForeground(new Color(173, 255, 47));
		btnInsurance.setFocusPainted(false);
		btnInsurance.setBackground(Color.BLACK);
		btnInsurance.setBounds(266, 474, 91, 23);
		btnInsurance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act) {
				controller.ButtonPressAction(controller.BLACKJACK_INSURANCE);
			}
		});
		buttons.add(btnInsurance);			
					
		EnableButons(buttonsEnable);
		AddToContentPane();							
	
	}

	private void AddToContentPane() {
		contentPane.add(lblBlackjack);
		contentPane.add(lblDealersCards);
		contentPane.add(lblYourCards);
		contentPane.add(lblCurrrentBalance);
		contentPane.add(lblBetAmount);
		contentPane.add(btnNewCard);
		contentPane.add(btnStand); 
		contentPane.add(btnSplit);
		contentPane.add(btnDouble);
		contentPane.add(btnInsurance);
		contentPane.add(btnDeal);
		contentPane.add(lblresult);
	}

	public void EnableButons(boolean[] startEnabling) {
		for (int i=0; i<startEnabling.length; i++){
			buttons.get(i).setEnabled(startEnabling[i]);
		}
	}
	
	public void SetResultLabel(String str){
		lblresult.setVisible(true);
		lblresult.setText(str);
		EnableButons(buttonsEnable);
		lblDealersCards.setVisible(false);
		lblYourCards.setVisible(false);
	}
	
	public void DisplayBoardChanges(boolean[] blkjckDisplyChoices, ArrayList<Card> dealCards, ArrayList<ArrayList<Card>> plyCard, int currBal){
		EnableButons(blkjckDisplyChoices);
		lblDealersCards.setVisible(true);
		lblYourCards.setVisible(true);
		
		int deal_x=391, deal_y=116, player_x=157, player_y=269, cardsize_x=71, cardsize_y=96; 
		Stack<JLabel> stackForDealerCards = new Stack<JLabel>();
		Stack<JLabel> stackForPlayerCards = new Stack<JLabel>();
		
		stackForDealerCards.push(new JLabel(""));
		stackForDealerCards.peek().setBounds(deal_x, deal_y, cardsize_x, cardsize_y);
		stackForDealerCards.peek().setIcon(new ImageIcon(this.getClass().getResource("/Cards/"+dealCards.get(0).toString()+".png")));
		
		if (dealCards.size() == 1){
			stackForDealerCards.push(new JLabel(""));
			stackForDealerCards.peek().setBounds(deal_x+20, deal_y, cardsize_x, cardsize_y);	
			stackForDealerCards.peek().setIcon(new ImageIcon(this.getClass().getResource("/Cards/unknown.png")));
		} else {
			for (int d=1; d<dealCards.size(); d++){
				stackForDealerCards.push(new JLabel(""));
				stackForDealerCards.peek().setBounds(deal_x+20, deal_y, cardsize_x, cardsize_y);
				deal_x = deal_x+20;
				stackForDealerCards.peek().setIcon(new ImageIcon(this.getClass().getResource("/Cards/"+dealCards.get(d).toString()+".png")));
			}		
		}
		RefreshScreenForNextRound();
		while (!stackForDealerCards.isEmpty()){
			contentPane.add(stackForDealerCards.pop());
		}
		System.out.println();
		
		//int i=1;
		for (ArrayList<Card> listOfCards : plyCard) {
			stackForPlayerCards.push(new JLabel(""));
			stackForPlayerCards.peek().setBounds(player_x, player_y, cardsize_x, cardsize_y);	
			stackForPlayerCards.peek().setIcon(new ImageIcon(this.getClass().getResource("/Cards/"+listOfCards.get(0).toString()+".png")));
			
			for (int p=1; p<listOfCards.size(); p++){
				stackForPlayerCards.push(new JLabel(""));
				stackForPlayerCards.peek().setBounds(player_x+20, player_y, cardsize_x, cardsize_y);
				player_x = player_x+20;
				stackForPlayerCards.peek().setIcon(new ImageIcon(this.getClass().getResource("/Cards/"+listOfCards.get(p).toString()+".png")));
			}
			
			while (!stackForPlayerCards.isEmpty()){
				contentPane.add(stackForPlayerCards.pop());
			}
			System.out.println();
			//i++;
			player_x=539; player_y=269;
		}
		lblCurrrentBalance.setText("Currrent Balance: " + currBal);
	}
	
	public void RefreshScreenForNextRound(){
		contentPane.removeAll();
		AddToContentPane();
	}	
		
}
