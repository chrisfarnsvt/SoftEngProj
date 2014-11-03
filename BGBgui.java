import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTree;
import javax.swing.JTextPane;


public class BGBgui {

	private JFrame frmBabyGotBackup;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BGBgui window = new BGBgui();
					window.frmBabyGotBackup.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BGBgui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBabyGotBackup = new JFrame();
		frmBabyGotBackup.setTitle("Baby Got Backup");
		frmBabyGotBackup.setBounds(100, 100, 450, 300);
		frmBabyGotBackup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBabyGotBackup.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		frmBabyGotBackup.getContentPane().add(splitPane, BorderLayout.WEST);
		
		JTree tree = new JTree();
		splitPane.setRightComponent(tree);
		
		JTree tree_1 = new JTree();
		splitPane.setLeftComponent(tree_1);
		
		JPanel panel = new JPanel();
		frmBabyGotBackup.getContentPane().add(panel, BorderLayout.NORTH);
		
		JButton btnNewButton_3 = new JButton("New button");
		panel.add(btnNewButton_3);
		
		JButton btnNewButton_2 = new JButton("New button");
		panel.add(btnNewButton_2);
		
		JButton btnNewButton_1 = new JButton("New button");
		panel.add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("New button");
		panel.add(btnNewButton);
		
		JPanel panel_1 = new JPanel();
		frmBabyGotBackup.getContentPane().add(panel_1, BorderLayout.CENTER);
		
		JTextPane txtpnBackupInfo = new JTextPane();
		txtpnBackupInfo.setText("Backup Info");
		panel_1.add(txtpnBackupInfo);
	}

}
