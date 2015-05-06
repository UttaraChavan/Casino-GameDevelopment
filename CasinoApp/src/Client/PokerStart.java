package Client;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import ControllerPck.Controller;
import ControllerPck.Sqlite;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class PokerStart extends JFrame {

	public Controller controller;
	private JPanel contentPane;
	public JLabel labelOtherPlayer;
	public JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PokerStart frame = new PokerStart();
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
	public PokerStart(Controller con) {
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

		JLabel lblPokerTexas = new JLabel("Poker - Texas Hold'em");
		lblPokerTexas.setHorizontalAlignment(SwingConstants.CENTER);
		lblPokerTexas.setBounds(334, 11, 200, 50);
		contentPane.add(lblPokerTexas);

		btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act) {
				dispose();
				controller.ButtonPressAction(controller.POKER_STARTGAME_BUTTON);
			}
		});
		btnNewButton.setToolTipText("Start Game");
		Image imgStartButton = new ImageIcon(this.getClass().getResource("/SlotIcons/starticon.png")).getImage();
		btnNewButton.setIcon(new ImageIcon(imgStartButton));
		btnNewButton.setBounds(686, 440, 72, 72);
		contentPane.add(btnNewButton);
		
		labelOtherPlayer = new JLabel("");
		labelOtherPlayer.setBounds(183, 192, 483, 93);
		contentPane.add(labelOtherPlayer);
		
		
	}
}
