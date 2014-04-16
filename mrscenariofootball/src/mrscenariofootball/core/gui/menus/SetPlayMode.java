package mrscenariofootball.core.gui.menus;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import mrscenariofootball.core.ScenarioCore;
import mrscenariofootball.core.data.ScenarioInformation;
import mrscenariofootball.core.data.worlddata.server.PlayMode;
import mrscenariofootball.game.Core;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class SetPlayMode extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JComboBox<PlayMode> comboBox;

	public SetPlayMode() {
		setTitle("Select PlayMode");
		setResizable(false);
		setBounds(100, 100, 331, 131);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		comboBox = new JComboBox<PlayMode>();
		comboBox.setPreferredSize(new Dimension(300, 22));
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textField.setText( Double.toString( ScenarioInformation.getInstance().getPlayModeTimeToRun( (PlayMode) comboBox.getSelectedItem() ) ) );
				
			}
		});
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		comboBox.setModel(new DefaultComboBoxModel<PlayMode>(PlayMode.values()));
		contentPanel.add(comboBox);
		
		JPanel panel = new JPanel();
		contentPanel.add(panel);
		
		JLabel lblTimeInPlaymode = new JLabel("Time in PlayMode in s");
		lblTimeInPlaymode.setPreferredSize(new Dimension(205, 14));
		panel.add(lblTimeInPlaymode);
		
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(30, 20));
		panel.add(textField);
		textField.setColumns(10);
		textField.setText( Double.toString( ScenarioInformation.getInstance().getPlayModeTimeToRun( (PlayMode) comboBox.getSelectedItem() ) ) );
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						try{
							
							ScenarioInformation.getInstance().setPlayModeTimeToRun( (PlayMode) comboBox.getSelectedItem(), Double.parseDouble( textField.getText() ) );
							
						} catch ( NullPointerException | NumberFormatException vException ){
							
							ScenarioCore.getLogger().error( "Could not parse playmodetime {} ", vException );
							ScenarioCore.getLogger().catching(vException);
							
						}
						
						ScenarioInformation.getInstance().getWorldData().setPlayMode( (PlayMode) comboBox.getSelectedItem() );
						Core.getInstance().setPlayMode();
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
