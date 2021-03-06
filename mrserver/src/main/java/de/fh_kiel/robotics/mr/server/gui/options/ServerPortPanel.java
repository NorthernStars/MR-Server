package de.fh_kiel.robotics.mr.server.gui.options;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JLabel;

import de.fh_kiel.robotics.mr.server.core.botai.BotAIManagement;
import de.fh_kiel.robotics.mr.server.core.botai.network.receive.Receiver;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ServerPortPanel extends JPanel {
	
	private Receiver mReceiver;

	public ServerPortPanel( Receiver aReceiver ) {
		
		mReceiver = aReceiver;
		
		setLayout(new BorderLayout(0, 0));
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed( final ActionEvent e ) {

				new Thread( new Runnable() {

					@Override
					public void run() {
				
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								
								Container vParent = getParent();
								vParent.remove( ((JButton) e.getSource()).getParent() );
								vParent.getParent().validate();
								vParent.repaint();
								
							}
						});
						
						BotAIManagement.getInstance().removeReceiver( mReceiver );
					}
				} ).start();
				
			}
		});
		add(btnClose, BorderLayout.EAST);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setText( "Team" + mReceiver.getBotAIConnect().getTeam() + "(" + mReceiver.getBotAIConnect().getPort() + ")" );
		add(lblPort, BorderLayout.CENTER);
	}

}
