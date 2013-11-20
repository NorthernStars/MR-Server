package mrserver.tempgui.options;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class Graphics extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
    private JPanel mPanelFiller = new JPanel();
	private JPanel mConnetedGraphicsPanel;

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
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(10, 25, 200, 20);
		add(textField);
		
		JLabel lblServerport = new JLabel("ServerPort");
		lblServerport.setBounds(220, 11, 86, 14);
		add(lblServerport);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(220, 25, 86, 20);
		add(textField_1);
		
		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				addConnectedGraphicPanel( new ConnectedGraphicPanel() );
				
			}
		});
		btnOpen.setBounds(317, 11, 91, 34);
		add(btnOpen);
		
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
		
        mPanelFiller.setMinimumSize( new Dimension(0,0) );
        mPanelFiller.setPreferredSize( new Dimension(0,0) );

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
}
