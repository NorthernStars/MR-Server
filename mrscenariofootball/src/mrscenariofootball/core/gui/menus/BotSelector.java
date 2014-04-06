package mrscenariofootball.core.gui.menus;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;

import mrscenariofootball.core.data.BotAI;
import mrscenariofootball.core.data.ScenarioInformation;
import mrscenariofootball.core.data.worlddata.server.Player;
import mrscenariofootball.core.data.worlddata.server.ReferencePointName;
import mrscenariofootball.core.gui.PlayField;
import mrservermisc.bots.interfaces.Bot;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

public class BotSelector extends JDialog {

	private static class FormatedBotAI extends BotAI{
		
		public FormatedBotAI( Bot aRemoteAI ) {
			super( aRemoteAI );
		}
		
		@Override
		public String toString() {
			return getName() + "( " + getRcId() + "/" + getVtId() + ")";
		}
		
	}
	
	private final JPanel contentPanel = new JPanel();
	private JComboBox<FormatedBotAI> comboBoxBots;
	private Component mInvoker;
	private double mX;
	private double mY;

	public BotSelector( Component aInvoker, double aX, double aY ) {
		setResizable(false);
		
		mInvoker = aInvoker;
		mX = aX;
		mY = aY;
		
		setBounds(100, 100, 336, 101);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		{
			comboBoxBots = new JComboBox<FormatedBotAI>();
			comboBoxBots.setPreferredSize(new Dimension(300, 22));
			contentPanel.add(comboBoxBots);
			
			DefaultComboBoxModel<FormatedBotAI> vModel = new DefaultComboBoxModel<FormatedBotAI>();
			
			for( BotAI vBotAi : ScenarioInformation.getInstance().getBotAIs().values() ){
				
				vModel.addElement( new FormatedBotAI( vBotAi.getRemoteAI() ) );
				
			}
			comboBoxBots.setModel( vModel );
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						if( comboBoxBots.getSelectedItem() != null ){
						
							for( Player vPlayer : ScenarioInformation.getInstance().getWorldData().getListOfPlayers() ){
								
								if( vPlayer.getId() == ((FormatedBotAI) comboBoxBots.getSelectedItem()).getVtId() ){
									
									vPlayer.getPosition().setLocation( mX, mY );
									((PlayField) mInvoker).update();
									break;
								}
							
							}
							
						}
						
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
