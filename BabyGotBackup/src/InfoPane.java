import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;


public class InfoPane {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InfoPane window = new InfoPane();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InfoPane() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTextPane bUpText = new JTextPane();
		bUpText.setText("Backup File(s) Affected");
		bUpText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		frame.getContentPane().add(bUpText, BorderLayout.WEST);
		
		JTextPane versionText = new JTextPane();
		versionText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		versionText.setText("Versions\n________\n\nv1\nv2\nv3");
		frame.getContentPane().add(versionText);
		
		JPanel resPanel = new JPanel();
		frame.getContentPane().add(resPanel, BorderLayout.EAST);
		resPanel.setLayout(new BoxLayout(resPanel, BoxLayout.Y_AXIS));
		
		JTextPane resText = new JTextPane();
		resText.setText("Results Go Here");
		resPanel.add(resText);
		
		JButton confirmButton = new JButton("Confirm");
		resPanel.add(confirmButton);
		
	}
}
