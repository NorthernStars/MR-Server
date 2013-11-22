package mrserver.tempgui.options;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import mrserver.core.Core;
import mrserver.core.botcontrol.BotControlManagement;
import mrserver.core.graphics.GraphicsManagement;
import mrserver.core.scenario.ScenarioManagement;
import mrserver.tempgui.options.interfaces.GraphicsManagementListener;

public class Graphics extends JPanel implements GraphicsManagementListener{
	private JTextField mOwnIP;
	private JTextField mOwnPortForGraphics;
    private JPanel mPanelFiller = new JPanel();
	private JPanel mConnetedGraphicsPanel;
	private JButton mBtnOpen;
	private JButton mBtnClose;

	/**
	 * Create the panel.
	 */
	public Graphics() {
		setMaximumSize(new Dimension(420, 210));
		setMinimumSize(new Dimension(420, 210));
		setSize(new Dimension(420, 210));
		setPreferredSize(new Dimension(420, 210));
		setLayout(null);
		
		JLabel lblServeripaddress = new JLabel("ServerIPAddress");
		lblServeripaddress.setBounds(10, 11, 89, 14);
		add(lblServeripaddress);
		
		mOwnIP = new JTextField();
		mOwnIP.setEditable(false);
		mOwnIP.setColumns(10);
		mOwnIP.setBounds(10, 25, 200, 20);
		add(mOwnIP);
		
		JLabel lblServerport = new JLabel("ServerPort");
		lblServerport.setBounds(220, 11, 86, 14);
		add(lblServerport);
		
		mOwnPortForGraphics = new JTextField();
		mOwnPortForGraphics.setColumns(10);
		mOwnPortForGraphics.setBounds(220, 25, 86, 20);
		add(mOwnPortForGraphics);
		
		mBtnOpen = new JButton("Open");
		mBtnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				save();
				new Thread( new Runnable() {

					@Override
					public void run() {
		
						GraphicsManagement.getInstance().startGraphicsManagement();
			            ScenarioManagement.getInstance().registerGraphics( GraphicsManagement.getInstance() );
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								reload();
							}
						});
					}
				} ).start();

			}
		});
		mBtnOpen.setBounds(317, 11, 91, 34);
		add(mBtnOpen);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 56, 398, 147);
		add(scrollPane);
		
		mConnetedGraphicsPanel = new JPanel();
		scrollPane.setViewportView(mConnetedGraphicsPanel);
		mConnetedGraphicsPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0};
		gbl_panel.rowHeights = new int[]{0};
		gbl_panel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{Double.MIN_VALUE};
		mConnetedGraphicsPanel.setLayout(gbl_panel);
		
		mBtnClose = new JButton("Close");
		mBtnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				new Thread( new Runnable() {

					@Override
					public void run() {
		
						GraphicsManagement.getInstance().close();
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								reload();
							}
						});
					}
				} ).start();

			}
		});
		mBtnClose.setEnabled(false);
		mBtnClose.setBounds(317, 12, 91, 33);
		add(mBtnClose);
		
        mPanelFiller.setMinimumSize( new Dimension(0,0) );
        mPanelFiller.setPreferredSize( new Dimension(0,0) );
        
        GraphicsManagement.getInstance().registerListener(this);

	}
	
    public void addConnectedGraphicPanel( ConnectedGraphicPanel aConnectedGraphic ) {

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.weighty = 0.0;
        
        mConnetedGraphicsPanel.remove( mPanelFiller );
        mConnetedGraphicsPanel.add( aConnectedGraphic, c );
        
        c.weighty = 1.0;
        mConnetedGraphicsPanel.add( mPanelFiller, c );
        
        validate();
        
    }

	void save(){

		Core.getInstance().getServerConfig().setVisionIPAdress( mOwnIP.getText() );
		Core.getInstance().getServerConfig().setVisionPort( Integer.parseInt( mOwnPortForGraphics.getText() ) );
				
	}
	
	void reload(){
		
		try { mOwnIP.setText( InetAddress.getLocalHost().getHostAddress() ); } catch ( UnknownHostException vIgnoreingExceptionOYeah) { }
		mOwnPortForGraphics.setText( Integer.toString( Core.getInstance().getServerConfig().getGraphicsPort() ) );

		mBtnOpen.setEnabled( !GraphicsManagement.getInstance().isStarted() );
		mBtnOpen.setVisible( !GraphicsManagement.getInstance().isStarted() );
		mBtnClose.setEnabled( GraphicsManagement.getInstance().isStarted() );
		mBtnClose.setVisible( GraphicsManagement.getInstance().isStarted() );
		
	}

	@Override
	public void newConnection( final SocketAddress aGraphic ) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				addConnectedGraphicPanel( new ConnectedGraphicPanel( aGraphic ) );
			}
		});
		
	}
    
}
