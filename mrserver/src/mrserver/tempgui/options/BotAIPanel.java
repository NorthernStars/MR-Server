package mrserver.tempgui.options;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BotAIPanel extends JPanel {
	public BotAIPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Container vParent = getParent();
				vParent.remove( ((JButton) e.getSource()).getParent() );
				vParent.getParent().validate();
				vParent.repaint();
			
			}
		});
		add(btnDisconnect, BorderLayout.EAST);
		
		JLabel lblBot = new JLabel("Bot");
		add(lblBot, BorderLayout.CENTER);
	}

}
