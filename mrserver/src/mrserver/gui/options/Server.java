package mrserver.gui.options;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import mrserver.core.Core;

@SuppressWarnings("serial")
public class Server extends JPanel {
	
	private JTextField mServerName;

	/**
	 * Create the panel.
	 */
	public Server() {
		setLayout(null);
		
		JLabel lblServername = new JLabel("Servername");
		lblServername.setBounds(10, 11, 145, 14);
		add(lblServername);
		
		mServerName = new JTextField();
		mServerName.setBounds(10, 25, 286, 20);
		add(mServerName);
		mServerName.setColumns(10);
		mServerName.setText( Core.getInstance().getServerConfig().getServerName() );
		
		JButton btnNewButton = new JButton("Save settings to file");
		btnNewButton.setBounds(10, 56, 286, 23);
		add(btnNewButton);
		
		reload();

	}
	
	void save(){
		
		Core.getInstance().getServerConfig().setServerName( mServerName.getText() );
		
	}
	
	void reload(){
		
		mServerName.setText( Core.getInstance().getServerConfig().getServerName() );
		
	}

}
