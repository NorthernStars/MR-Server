package mrserver.tempgui.options;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.BoxLayout;

import java.awt.Component;

import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;

import mrserver.core.Core;
import mrserver.core.vision.VisionManagement;
import mrserver.tempgui.Main;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.plaf.basic.BasicScrollPaneUI.VSBChangeListener;

import mrservermisc.network.data.position.VisionMode;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Vision extends JPanel {
	private JTextField mVisionIPAddress;
	private JTextField mVisionPort;
	private JTextField mOwnIP;
	private JTextField mOwnToVisionPort;
	private JComboBox<VisionMode> mVisionModes;
	private JButton mBtnSetMode;
	private JButton mBtnConnect;
	private JButton mBtnDisconnect;
	private JButton mBtnReconnect;

	/**
	 * Create the panel.
	 */
	public Vision() {
		setLayout(null);
		
		JLabel lblVisionipaddress = new JLabel("VisionIPAddress");
		lblVisionipaddress.setBounds(10, 11, 200, 14);
		add(lblVisionipaddress);
		
		mVisionIPAddress = new JTextField();
		mVisionIPAddress.setBounds(10, 25, 200, 20);
		add(mVisionIPAddress);
		mVisionIPAddress.setColumns(10);
		
		mVisionPort = new JTextField();
		mVisionPort.setBounds(220, 25, 86, 20);
		add(mVisionPort);
		mVisionPort.setColumns(10);
		
		JLabel lblVisionport = new JLabel("VisionPort");
		lblVisionport.setBounds(220, 11, 86, 14);
		add(lblVisionport);
		
		mBtnConnect = new JButton("Connect");
		mBtnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				save();
				new Thread( new Runnable() {

					@Override
					public void run() {

						VisionManagement.getInstance().connectToVision( Integer.parseInt( mOwnToVisionPort.getText() ) );
						VisionManagement.getInstance().startRecievingPackets();
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								reload();
							}
						});
		
					}
				} ).start();
				
			}
		});
		mBtnConnect.setBounds(10, 56, 91, 23);
		add(mBtnConnect);
		
		mBtnDisconnect = new JButton("Disconnect");
		mBtnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				new Thread( new Runnable() {

					@Override
					public void run() {

						VisionManagement.getInstance().disconnectVision();
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								reload();
							}
						});
					}
				} ).start();
				
			}
		});
		mBtnDisconnect.setBounds(215, 56, 91, 23);
		add(mBtnDisconnect);
		
		mBtnReconnect = new JButton("Reconnect");
		mBtnReconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				save();	
				new Thread( new Runnable() {

					@Override
					public void run() {

						VisionManagement.getInstance().reconnectToVision( Integer.parseInt( mOwnToVisionPort.getText() ) );
						VisionManagement.getInstance().startRecievingPackets();
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								reload();
							}
						});
					}
				} ).start();
				
			}
		});
		mBtnReconnect.setBounds(111, 56, 94, 23);
		add(mBtnReconnect);
		
		mOwnIP = new JTextField();
		mOwnIP.setEditable(false);
		mOwnIP.setColumns(10);
		mOwnIP.setBounds(10, 104, 200, 20);
		add(mOwnIP);
		
		JLabel lblServeripaddress = new JLabel("ServerIPAddress");
		lblServeripaddress.setBounds(10, 90, 200, 14);
		add(lblServeripaddress);
		
		JLabel lblServerport = new JLabel("ServerPort");
		lblServerport.setBounds(220, 90, 86, 14);
		add(lblServerport);
		
		mOwnToVisionPort = new JTextField();
		mOwnToVisionPort.setText("-1");
		mOwnToVisionPort.setColumns(10);
		mOwnToVisionPort.setBounds(220, 104, 86, 20);
		add(mOwnToVisionPort);
		
		mBtnSetMode = new JButton("Set Mode");
		mBtnSetMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new Thread( new Runnable() {

					@Override
					public void run() {

						VisionManagement.getInstance().changeVisionMode( (VisionMode) mVisionModes.getSelectedItem()  );
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								reload();
							}
						});
					}
				} ).start();
				
			}
		});
		mBtnSetMode.setBounds(215, 135, 91, 23);
		add(mBtnSetMode);
		
		mVisionModes = new JComboBox<VisionMode>();
		mVisionModes.setModel(new DefaultComboBoxModel<VisionMode>(VisionMode.values()));
		mVisionModes.setBounds(10, 135, 200, 22);
		add(mVisionModes);

	}
	
	void save(){

		Core.getInstance().getServerConfig().setVisionIPAdress( mVisionIPAddress.getText() );
		Core.getInstance().getServerConfig().setVisionPort( Integer.parseInt( mVisionPort.getText() ) );
				
	}
	
	void reload(){
		
		try { mOwnIP.setText( InetAddress.getLocalHost().getHostAddress() ); } catch ( UnknownHostException vIgnoreingExceptionOYeah) { }
		mOwnToVisionPort.setText( mOwnToVisionPort.getText().equals("-1") ? Integer.toString( VisionManagement.getInstance().getPortToVision() ) : mOwnToVisionPort.getText() );
		mVisionIPAddress.setText( Core.getInstance().getServerConfig().getVisionIPAdress().getHostAddress() );
		mVisionPort.setText( Integer.toString( Core.getInstance().getServerConfig().getVisionPort() ) );

		mBtnConnect.setEnabled( !VisionManagement.getInstance().isConnected() );
		mBtnReconnect.setEnabled( !VisionManagement.getInstance().isConnected() );
		mBtnDisconnect.setEnabled( VisionManagement.getInstance().isConnected() );
		mBtnSetMode.setEnabled( VisionManagement.getInstance().isConnected() );
		
	}
}
