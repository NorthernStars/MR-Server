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
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.GridLayout;

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
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.UNRELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.UNRELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("14px"),
				RowSpec.decode("20px"),
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("147px:grow"),
				FormFactory.UNRELATED_GAP_ROWSPEC,}));
		
		JLabel label = new JLabel("ServerIPAddress");
		add(label, "2, 2, left, top");
		
		mOwnIP = new JTextField();
		mOwnIP.setEditable(false);
		mOwnIP.setColumns(10);
		add(mOwnIP, "2, 3, fill, top");
		
		JLabel label_1 = new JLabel("ServerPort");
		add(label_1, "4, 2, fill, top");
		
		mOwnPortToBots = new JTextField();
		mOwnPortToBots.setText("3311");
		mOwnPortToBots.setColumns(10);
		add(mOwnPortToBots, "4, 3, left, top");
		
		JButton button = new JButton("Open");
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
		add(button, "6, 2, 1, 2, fill, fill");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, "2, 5, 5, 1, fill, fill");
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		mServerPortPanel = new JPanel();
		panel.add(mServerPortPanel);
		mServerPortPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		GridBagLayout gbl_mServerPortPanel = new GridBagLayout();
		gbl_mServerPortPanel.columnWidths = new int[]{0};
		gbl_mServerPortPanel.rowHeights = new int[]{0};
		gbl_mServerPortPanel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_mServerPortPanel.rowWeights = new double[]{Double.MIN_VALUE};
		mServerPortPanel.setLayout(gbl_mServerPortPanel);
		
		mBotAIPanel = new JPanel();
		panel.add(mBotAIPanel);
		mBotAIPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		GridBagLayout gbl_mBotAIPanel = new GridBagLayout();
		gbl_mBotAIPanel.columnWidths = new int[]{0};
		gbl_mBotAIPanel.rowHeights = new int[]{0};
		gbl_mBotAIPanel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_mBotAIPanel.rowWeights = new double[]{Double.MIN_VALUE};
		mBotAIPanel.setLayout(gbl_mBotAIPanel);
		
        mPanelFiller .setMinimumSize( new Dimension(0,0) );
        mPanelFiller.setPreferredSize( new Dimension(0,0) );
        
        BotAIManagement.getInstance().registerBotAIListener( this );

	}

    public void addServerPortPanel( ServerPortPanel aServerPort ) {

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.weighty = 0.0;
        
        mServerPortPanel.remove( mPanelFiller );
        mServerPortPanel.add( aServerPort, c );
        
        c.weighty = 1.0;
        mServerPortPanel.add( mPanelFiller, c );
        
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
