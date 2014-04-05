package mrscenariofootball.core.gui.menus;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import mrscenariofootball.core.data.ScenarioInformation;
import mrscenariofootball.core.data.worlddata.server.PlayMode;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SetPlayMode extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JComboBox<PlayMode> comboBox;

	public SetPlayMode() {
		setBounds(100, 100, 331, 131);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		comboBox = new JComboBox<PlayMode>();
		comboBox.setModel(new DefaultComboBoxModel<PlayMode>(PlayMode.values()));
		comboBox.setBounds(10, 11, 300, 22);
		contentPanel.add(comboBox);
		
		JLabel lblTimeInPlaymode = new JLabel("Time in PlayMode in s");
		lblTimeInPlaymode.setBounds(10, 44, 200, 14);
		contentPanel.add(lblTimeInPlaymode);
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setBounds(220, 41, 90, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ScenarioInformation.getInstance().getWorldData().setPlayMode( (PlayMode) comboBox.getSelectedItem() );
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
