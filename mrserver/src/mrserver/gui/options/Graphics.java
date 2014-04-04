package mrserver.gui.options;

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
import mrserver.core.graphics.GraphicsManagement;
import mrserver.core.scenario.ScenarioManagement;
import mrserver.gui.options.interfaces.GraphicsManagementListener;
import java.awt.BorderLayout;

@SuppressWarnings("serial")
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
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, BorderLayout.CENTER);
		
		mConnetedGraphicsPanel = new JPanel();
		scrollPane.setViewportView(mConnetedGraphicsPanel);
		mConnetedGraphicsPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0};
		gbl_panel.rowHeights = new int[]{0};
		gbl_panel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{Double.MIN_VALUE};
		mConnetedGraphicsPanel.setLayout(gbl_panel);
		
        mPanelFiller.setMinimumSize( new Dimension(0,0) );
        mPanelFiller.setPreferredSize( new Dimension(0,0) );
        
        GraphicsManagement.getInstance().registerListener(this);
        
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(10, 60));
        panel.setMinimumSize(new Dimension(10, 50));
        add(panel, BorderLayout.NORTH);
        panel.setLayout(null);
        
        JLabel lblServeripaddress = new JLabel("ServerIPAddress");
        lblServeripaddress.setBounds(10, 9, 89, 14);
        panel.add(lblServeripaddress);
        
        mOwnIP = new JTextField();
        mOwnIP.setBounds(10, 24, 200, 20);
        panel.add(mOwnIP);
        mOwnIP.setEditable(false);
        mOwnIP.setColumns(10);
        
        JLabel lblServerport = new JLabel("ServerPort");
        lblServerport.setBounds(220, 9, 86, 14);
        panel.add(lblServerport);
        
        mOwnPortForGraphics = new JTextField();
        mOwnPortForGraphics.setBounds(220, 24, 86, 20);
        panel.add(mOwnPortForGraphics);
        mOwnPortForGraphics.setColumns(10);
        
        mBtnOpen = new JButton("Open");
        mBtnOpen.setBounds(319, 18, 91, 34);
        panel.add(mBtnOpen);
        
        mBtnClose = new JButton("Close");
        mBtnClose.setBounds(319, 18, 91, 33);
        panel.add(mBtnClose);
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

		Core.getInstance().getServerConfig().setGraphicsPort( Integer.parseInt( mOwnPortForGraphics.getText() ) );
				
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
