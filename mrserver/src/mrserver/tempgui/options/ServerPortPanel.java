package mrserver.tempgui.options;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JLabel;

import mrserver.core.botai.BotAIManagement;
import mrserver.core.botai.data.BotAI;
import mrserver.core.botai.network.receive.Receiver;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
				
						BotAIManagement.getInstance().removeReceiver( mReceiver );
						
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								
								Container vParent = getParent();
								vParent.remove( ((JButton) e.getSource()).getParent() );
								vParent.getParent().validate();
								vParent.repaint();
								
							}
						});
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
