package mrserver.tempgui.options;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JLabel;

import mrserver.core.graphics.GraphicsManagement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class ConnectedGraphicPanel extends JPanel {
	
	InetSocketAddress mGraphic;
	
	public ConnectedGraphicPanel( SocketAddress aGraphic ) {
		
		mGraphic = (InetSocketAddress) aGraphic;
		
		setLayout(new BorderLayout(0, 0));
		
		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed( final ActionEvent e ) {

				new Thread( new Runnable() {

					@Override
					public void run() {
		
						GraphicsManagement.getInstance().getMapOfConnections().remove( (SocketAddress) mGraphic );
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
		add(btnDisconnect, BorderLayout.EAST);
		
		JLabel lblGraphics = new JLabel("Graphics");
		lblGraphics.setText( GraphicsManagement.getInstance().getMapOfConnections().get( aGraphic ).getName() + " (" + 
							 mGraphic.getHostName() + ":" + mGraphic.getPort() + ")" );
		add(lblGraphics, BorderLayout.CENTER);
	}

}
