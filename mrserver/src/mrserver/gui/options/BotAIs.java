package mrserver.gui.options;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import mrserver.core.botai.BotAIManagement;
import mrserver.core.botai.data.BotAI;
import mrserver.core.botai.network.receive.Receiver;
import mrserver.gui.options.interfaces.AIListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;

@SuppressWarnings("serial")
public class BotAIs extends JPanel implements AIListener {
	private JTextField mOwnIP;
	private JTextField mOwnPortToBots;
	private JPanel mBotAIPanel;
	private JPanel mServerPortPanel;
	private JPanel mPanelFiller = new JPanel();;

	/**
	 * Create the panel.
	 */
	public BotAIs() {
		setMaximumSize(new Dimension(420, 375));
		setMinimumSize(new Dimension(420, 275));
		setPreferredSize(new Dimension(420, 375));
		setSize(new Dimension(420, 375));
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane_1);
		
		mBotAIPanel = new JPanel();
		mBotAIPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		scrollPane_1.setViewportView(mBotAIPanel);
		GridBagLayout gbl_mBotAIPanel = new GridBagLayout();
		gbl_mBotAIPanel.columnWidths = new int[]{0};
		gbl_mBotAIPanel.rowHeights = new int[]{0};
		gbl_mBotAIPanel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_mBotAIPanel.rowWeights = new double[]{Double.MIN_VALUE};
		mBotAIPanel.setLayout(gbl_mBotAIPanel);
		
        mPanelFiller .setMinimumSize( new Dimension(0,0) );
        mPanelFiller.setPreferredSize( new Dimension(0,0) );
        
        BotAIManagement.getInstance().registerBotAIListener( this );
        
        JPanel panel = new JPanel();
        add(panel, BorderLayout.NORTH);
        panel.setLayout(new BorderLayout(0, 0));
        
        JPanel panel_1 = new JPanel();
        panel_1.setPreferredSize(new Dimension(10, 60));
        panel.add(panel_1, BorderLayout.NORTH);
        panel_1.setLayout(null);
        
        JLabel label = new JLabel("ServerIPAddress");
        label.setBounds(17, 9, 81, 14);
        panel_1.add(label);
        
        mOwnIP = new JTextField();
        mOwnIP.setBounds(17, 29, 140, 20);
        panel_1.add(mOwnIP);
        mOwnIP.setEditable(false);
        mOwnIP.setColumns(10);
        
        JLabel label_1 = new JLabel("ServerPort");
        label_1.setBounds(167, 9, 52, 14);
        panel_1.add(label_1);
        
        mOwnPortToBots = new JTextField();
        mOwnPortToBots.setBounds(167, 29, 86, 20);
        panel_1.add(mOwnPortToBots);
        mOwnPortToBots.setText("3311");
        mOwnPortToBots.setColumns(10);
        
        JButton button = new JButton("Open");
        button.setBounds(263, 11, 81, 38);
        panel_1.add(button);
        
        mServerPortPanel = new JPanel();
        panel.add(mServerPortPanel, BorderLayout.CENTER);
        mServerPortPanel.setPreferredSize(new Dimension(10, 50));
        mServerPortPanel.setMinimumSize(new Dimension(10, 50));
        mServerPortPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        mServerPortPanel.setLayout(new BoxLayout(mServerPortPanel, BoxLayout.Y_AXIS));
        button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        						
        		save();
        		new Thread( new Runnable() {

        			@Override
        			public void run() {
        
        				final Receiver vReceiver = BotAIManagement.getInstance().addBotAIPort( Integer.parseInt( mOwnPortToBots.getText() ) );
        				if( vReceiver != null ){
        					
        					EventQueue.invokeLater(new Runnable() {
        						public void run() {
        							
        							addServerPortPanel( new ServerPortPanel( vReceiver ) );
        							reload();
        							
        						}
        					});
        					
        				}
        			}
        		} ).start();
        		
        	}
        });
        
        for( Receiver vReceiver: BotAIManagement.getInstance().getBotAIReceiver() ){
        	
        	addServerPortPanel( new ServerPortPanel( vReceiver ) );
        	
        }

	}

    public void addServerPortPanel( ServerPortPanel aServerPort ) {

        
        mServerPortPanel.add( aServerPort );
        validate();
        
    }

    public void addBotAIPanel( BotAIPanel aBotAIPanel ) {

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.weighty = 0.0;
        
        mBotAIPanel.remove( mPanelFiller );
        mBotAIPanel.add( aBotAIPanel, c );
        
        c.weighty = 1.0;
        mBotAIPanel.add( mPanelFiller, c );
        
        validate();
        
    }	
    
    void save(){
				
	}
    
    @Override
    public void validate() {
    	
    	mServerPortPanel.setPreferredSize( new Dimension( 10, mServerPortPanel.getComponentCount() * 30 ));
    	super.validate();
    	
    };
	
	void reload(){
		
		try { mOwnIP.setText( InetAddress.getLocalHost().getHostAddress() ); } catch ( UnknownHostException vIgnoreingExceptionOYeah) { };
		
	}

	@Override
	public void newAI( final BotAI aAI ) {
					
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				addBotAIPanel( new BotAIPanel( aAI ) );
				reload();
				
			}
		});
		
	}
	
}
