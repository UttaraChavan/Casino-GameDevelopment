package Client;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JButton;

import ControllerPck.Controller;
import ControllerPck.Sqlite;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class SlotMachineStart extends JFrame {
	
	public Controller controller;
	private JPanel contentPane;
	private JTextField textField;

	public static int[] linesSelected = {0, 0, 0, 0, 0};
	public static int seedAmount = 5;

/*	*//**
	 * Launch the application.
	 *//*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SlotMachineStart frame = new SlotMachineStart();
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
	public SlotMachineStart(Controller con) {
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
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("");
		Image imgWinningLines = new ImageIcon(this.getClass().getResource("/SlotIcons/start.png")).getImage();
		lblNewLabel.setIcon(new ImageIcon(imgWinningLines));
		lblNewLabel.setBounds(122, 32, 353, 242);
		contentPane.add(lblNewLabel);

		JLabel lblSelectLinesYou = new JLabel("Select lines you want to play on");
		lblSelectLinesYou.setForeground(new Color(75, 0, 130));
		lblSelectLinesYou.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblSelectLinesYou.setBounds(524, 32, 317, 31);
		contentPane.add(lblSelectLinesYou);

		JLabel testLabel = new JLabel("");
		testLabel.setBounds(122, 498, 409, 31);
		contentPane.add(testLabel);

		JCheckBox chckbxNewCheckBox_0 = new JCheckBox("Line 1", true);
		chckbxNewCheckBox_0.setForeground(new Color(153, 50, 204));
		chckbxNewCheckBox_0.setBounds(524, 85, 97, 23);
		contentPane.add(chckbxNewCheckBox_0);

		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("Line 2");
		chckbxNewCheckBox_1.setForeground(new Color(153, 50, 204));
		chckbxNewCheckBox_1.setBounds(524, 119, 97, 23);
		contentPane.add(chckbxNewCheckBox_1);

		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("Line 3");
		chckbxNewCheckBox_2.setForeground(new Color(153, 50, 204));
		chckbxNewCheckBox_2.setBounds(524, 155, 97, 23);
		contentPane.add(chckbxNewCheckBox_2);

		JCheckBox chckbxNewCheckBox_3 = new JCheckBox("Line 4");
		chckbxNewCheckBox_3.setForeground(new Color(153, 50, 204));
		chckbxNewCheckBox_3.setBounds(524, 191, 97, 23);
		contentPane.add(chckbxNewCheckBox_3);

		JCheckBox chckbxNewCheckBox_4 = new JCheckBox("Line 5");
		chckbxNewCheckBox_4.setForeground(new Color(153, 50, 204));
		chckbxNewCheckBox_4.setBounds(524, 229, 97, 23);
		contentPane.add(chckbxNewCheckBox_4);

		chckbxNewCheckBox_0.setMnemonic(KeyEvent.VK_A);
		chckbxNewCheckBox_1.setMnemonic(KeyEvent.VK_B);
		chckbxNewCheckBox_2.setMnemonic(KeyEvent.VK_C);
		chckbxNewCheckBox_3.setMnemonic(KeyEvent.VK_D);
		chckbxNewCheckBox_4.setMnemonic(KeyEvent.VK_E);	 

		if (chckbxNewCheckBox_0.isSelected())
			linesSelected[0] = 1;
		
		chckbxNewCheckBox_0.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {         
				/*testLabel.setText("Line 1 " 
						+ (e.getStateChange()==1?"selected":"unchecked"));*/
				if (chckbxNewCheckBox_0.isSelected())
					linesSelected[0] = 1;
			}        
		});

		chckbxNewCheckBox_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				/*testLabel.setText("Line 2 " 
						+ (e.getStateChange()==1?"selected":"unchecked")); */
				if (chckbxNewCheckBox_1.isSelected())
				linesSelected[1] = 1;
			}           
		});

		chckbxNewCheckBox_2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
			/*	testLabel.setText("Line 3 " 
						+ (e.getStateChange()==1?"selected":"unchecked"));*/
				if (chckbxNewCheckBox_2.isSelected())
				linesSelected[2] = 1;
			}           
		});

		chckbxNewCheckBox_3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				/*testLabel.setText("Line 4 " 
						+ (e.getStateChange()==1?"selected":"unchecked"));*/
				if (chckbxNewCheckBox_3.isSelected())
				linesSelected[3] = 1;
			}           
		});

		chckbxNewCheckBox_4.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {             
				/*testLabel.setText("Line 5 " 
						+ (e.getStateChange()==1?"selected":"unchecked"));*/
				if (chckbxNewCheckBox_4.isSelected())
				linesSelected[4] = 1;
			}           
		});


		JLabel lblSelectYourBet = new JLabel("Select your seed amount");
		lblSelectYourBet.setForeground(new Color(75, 0, 130));
		lblSelectYourBet.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblSelectYourBet.setBounds(122, 336, 317, 31);
		contentPane.add(lblSelectYourBet);

		JLabel lblMoreNumberOf = new JLabel("More number of lines increase your chances of winning");
		lblMoreNumberOf.setForeground(new Color(255, 0, 0));
		lblMoreNumberOf.setBounds(524, 64, 335, 14);
		contentPane.add(lblMoreNumberOf);

		JLabel lblYoutBetAmount = new JLabel("Yout bet amount will be number of lines times seed your enter");
		lblYoutBetAmount.setForeground(Color.RED);
		lblYoutBetAmount.setBounds(122, 368, 353, 14);
		contentPane.add(lblYoutBetAmount);

		JLabel lblSeed = new JLabel("Seed: ");
		lblSeed.setForeground(new Color(153, 50, 204));
		lblSeed.setBounds(122, 397, 46, 14);
		contentPane.add(lblSeed);

		textField = new JTextField();
		textField.setText("5");
		textField.setBounds(191, 393, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act) {
				seedAmount = Integer.valueOf(textField.getText());
				dispose();
				controller.ButtonPressAction(controller.SLOT_MACHINE_STARTGAME_BUTTON);
			}
		});
		btnNewButton.setToolTipText("Start Game");
		Image imgStartButton = new ImageIcon(this.getClass().getResource("/SlotIcons/starticon.png")).getImage();
		btnNewButton.setIcon(new ImageIcon(imgStartButton));
		btnNewButton.setBounds(686, 440, 72, 72);
		contentPane.add(btnNewButton);

	}
}
