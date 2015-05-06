package Client;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import ControllerPck.Controller;
import ControllerPck.Sqlite;


@SuppressWarnings("serial")
public class StartPage extends JFrame {	

	public Controller controller;
	private JPanel contentPane;
	//Player player;
	private JLabel label;
	private JRadioButton radSlotMachine;
	private JRadioButton radBlackJack;
	private JRadioButton radPoker;

	/**
	 * Launch the application.
	 */
	/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartPage window = new StartPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public StartPage(Controller con) {
		controller = con;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			Sqlite dbobj = new Sqlite();
			public void windowClosing(WindowEvent evt) {
				dbobj.sqlUpdateOnLast(controller.player);
			}
		});
		
		setBounds(100, 100, 750, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		InitializeComponents();		
		AssignListeners();
		AddToFrameContent();

		ButtonGroup group = new ButtonGroup();
		group.add(radSlotMachine);
		group.add(radBlackJack);
		group.add(radPoker);

	}

	private void InitializeComponents() {
		label = new JLabel("");
		label.setBounds(29, 215, 379, 22);

		radSlotMachine = new JRadioButton("Slot Machine");	
		radSlotMachine.setBounds(114, 62, 200, 29);

		radBlackJack = new JRadioButton("BlackJack");
		radBlackJack.setBounds(114, 94, 200, 29);

		radPoker = new JRadioButton("Poker: Texas Hold'em");
		radPoker.setBounds(114, 126, 200, 29);
	}

	private void AssignListeners() {
		radSlotMachine.setMnemonic(KeyEvent.VK_C);
		radBlackJack.setMnemonic(KeyEvent.VK_M);
		radPoker.setMnemonic(KeyEvent.VK_P);

		radSlotMachine.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {         
				label.setText("Slot Machine RadioButton: " 
						+ (e.getStateChange()==1?"checked":"unchecked"));
				dispose();
				controller.RadioAction(controller.SLOT_MACHINE_RADIO);
			}           
		});

		radBlackJack.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				label.setText("BlackJack RadioButton: " 
						+ (e.getStateChange()==1?"checked":"unchecked")); 
				dispose();
				controller.RadioAction(controller.BLACKJACK_RADIO);

			}           
		});

		radPoker.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				label.setText("Poker: Texas Hold'em RadioButton: " 
						+ (e.getStateChange()==1?"checked":"unchecked"));
				dispose();
				controller.RadioAction(controller.POKER_RADIO);

			}           
		});		
	}

	private void AddToFrameContent() {
		contentPane.add(label);	
		contentPane.add(radSlotMachine);
		contentPane.add(radBlackJack);
		contentPane.add(radPoker);
		contentPane.add(radSlotMachine);
		contentPane.add(radBlackJack);
		contentPane.add(radPoker); 	

	}
}
