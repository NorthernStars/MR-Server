package mrserver.tempgui.options;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ServerPortPanel extends JPanel {
	public ServerPortPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Container vParent = getParent();
				vParent.remove( ((JButton) e.getSource()).getParent() );
				vParent.getParent().validate();
				vParent.repaint();
			
			}
		});
		add(btnClose, BorderLayout.EAST);
		
		JLabel lblPort = new JLabel("Port");
		add(lblPort, BorderLayout.CENTER);
	}

}
