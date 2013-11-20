package mrserver.tempgui.options;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BotAIs extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
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
		setLayout(null);
		
		JLabel label = new JLabel("ServerIPAddress");
		label.setBounds(10, 11, 89, 14);
		add(label);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(10, 25, 200, 20);
		add(textField);
		
		JLabel label_1 = new JLabel("ServerPort");
		label_1.setBounds(220, 11, 86, 14);
		add(label_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(220, 25, 86, 20);
		add(textField_1);
		
		JButton button = new JButton("Open");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				addServerPortPanel( new ServerPortPanel() );
				
			}
		});
		button.setBounds(317, 11, 91, 34);
		add(button);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 56, 398, 147);
		add(scrollPane);
		
		mServerPortPanel = new JPanel();
		mServerPortPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		scrollPane.setViewportView(mServerPortPanel);
		GridBagLayout gbl_mServerPortPanel = new GridBagLayout();
		gbl_mServerPortPanel.columnWidths = new int[]{0};
		gbl_mServerPortPanel.rowHeights = new int[]{0};
		gbl_mServerPortPanel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_mServerPortPanel.rowWeights = new double[]{Double.MIN_VALUE};
		mServerPortPanel.setLayout(gbl_mServerPortPanel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(10, 214, 398, 147);
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
	
}
