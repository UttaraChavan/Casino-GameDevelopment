package Client;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import ControllerPck.Controller;
import ControllerPck.Sqlite;

import java.awt.Color;
import java.awt.Font;


@SuppressWarnings("serial")
public class SlotMachine extends JFrame {

	Controller controller;
	private JPanel contentPane;
	private JLabel lableToStart, labelBalance, labelRoundPoints;
	ArrayList<JLabel> listOfLabels = new ArrayList<JLabel>();
	ArrayList<Image> currIcon = new ArrayList<Image>();
	HashMap<Integer, String> HMap = new HashMap<Integer, String>();

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SlotMachine frame = new SlotMachine();
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
	public SlotMachine(Controller con) {
		controller = con;
		InitializeHashMapForImagePaths();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

		lableToStart = new JLabel("To start the play hit green button..");
		lableToStart.setFont(new Font("Tahoma", Font.BOLD, 16));
		lableToStart.setForeground(new Color(153, 50, 204));
		lableToStart.setBounds(288, 114, 446, 41);

		labelBalance = new JLabel("Your available balance:");
		labelBalance.setFont(new Font("Tahoma", Font.BOLD, 17));
		labelBalance.setForeground(Color.RED);
		labelBalance.setText("Your available balance:" + controller.player.getBal());
		labelBalance.setBounds(98, 346, 321, 41);

		labelRoundPoints = new JLabel("Points earned in last round:");
		labelRoundPoints.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelRoundPoints.setForeground(new Color(153, 50, 204));
		labelRoundPoints.setVisible(false);
		labelRoundPoints.setBounds(98, 385, 321, 20);

		JButton buttonRefresh = new JButton("");
		buttonRefresh.setToolTipText("Play Again");
		Image imgRefresh = new ImageIcon(this.getClass().getResource("/SlotIcons/refresh.png")).getImage();
		buttonRefresh.setIcon(new ImageIcon(imgRefresh));
		buttonRefresh.setBounds(754, 456, 72, 72);
		buttonRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent act) {
				controller.ButtonPressAction(controller.SLOT_MACHINE_REFRESHGAME_BUTTON);
			}
		});
		contentPane.add(buttonRefresh);

		JLabel label0 = new JLabel();
		label0.setBounds(254, 114, 48, 48);
		listOfLabels.add(label0);

		JLabel label1 = new JLabel();
		label1.setBounds(312, 114, 48, 48);	
		listOfLabels.add(label1);

		JLabel label2 = new JLabel();
		label2.setBounds(370, 114, 48, 48);
		listOfLabels.add(label2);

		JLabel label3 = new JLabel();
		label3.setBounds(428, 114, 48, 48);
		listOfLabels.add(label3);		

		JLabel label4 = new JLabel();
		label4.setBounds(486, 114, 48, 48);
		listOfLabels.add(label4);



		JLabel label5 = new JLabel();
		label5.setBounds(254, 172, 48, 48);
		listOfLabels.add(label5);		

		JLabel label6 = new JLabel();
		label6.setBounds(312, 172, 48, 48);
		listOfLabels.add(label6);		

		JLabel label7 = new JLabel();
		label7.setBounds(370, 172, 48, 48);
		listOfLabels.add(label7);		

		JLabel label8 = new JLabel();
		label8.setBounds(428, 172, 48, 48);
		listOfLabels.add(label8);		

		JLabel label9 = new JLabel();
		label9.setBounds(486, 172, 48, 48);
		listOfLabels.add(label9);


		JLabel label10 = new JLabel();
		label10.setBounds(254, 230, 48, 48);
		listOfLabels.add(label10);

		JLabel label11 = new JLabel();	
		label11.setBounds(312, 230, 48, 48);
		listOfLabels.add(label11);

		JLabel label12 = new JLabel();
		label12.setBounds(370, 230, 48, 48);
		listOfLabels.add(label12);

		JLabel label13 = new JLabel();
		label13.setBounds(428, 230, 48, 48);
		listOfLabels.add(label13);

		JLabel label14 = new JLabel();
		label14.setBounds(486, 230, 48, 48);
		listOfLabels.add(label14);


		AddToContentPane();
		contentPane.add(label0);
		contentPane.add(label1);
		contentPane.add(label2);
		contentPane.add(label3);
		contentPane.add(label4);
		contentPane.add(label5);
		contentPane.add(label6);
		contentPane.add(label7);
		contentPane.add(label8);
		contentPane.add(label9);
		contentPane.add(label10);
		contentPane.add(label11);
		contentPane.add(label12);
		contentPane.add(label13);
		contentPane.add(label14);

		contentPane.add(labelBalance);
		contentPane.add(labelRoundPoints);
		contentPane.add(lableToStart);
	}

	private void InitializeHashMapForImagePaths() {
		HMap.put(1, "/SlotIcons/apple.png"); HMap.put(2, "/SlotIcons/burger.png");
		HMap.put(3, "/SlotIcons/carrot.png"); HMap.put(4, "/SlotIcons/catering.png");
		HMap.put(5, "/SlotIcons/cupcake.png"); HMap.put(6, "/SlotIcons/drink.png");
		HMap.put(7, "/SlotIcons/icecream.png"); HMap.put(8, "/SlotIcons/pizza.png");
		HMap.put(9, "/SlotIcons/sausage.png"); HMap.put(10, "/SlotIcons/strawberry.png");
	}


	private void AddToContentPane() {


	}

	public void ChangeState(int[][] symArray, int lastSessionPoints) {
		lableToStart.setVisible(false);
		for (int i=0; i<symArray.length; i++){
			for (int j=0; j<symArray[0].length; j++){
				String path = HMap.get(symArray[i][j]);
				Image img = new ImageIcon(this.getClass().getResource(path)).getImage();
				listOfLabels.get(i*5+j).setIcon(new ImageIcon(img));
			}
		}

		labelBalance.setText("Your available balance: " + controller.player.getBal());
		labelRoundPoints.setText("Points earned in last round: " + lastSessionPoints);
		labelRoundPoints.setVisible(true);
	}

}
