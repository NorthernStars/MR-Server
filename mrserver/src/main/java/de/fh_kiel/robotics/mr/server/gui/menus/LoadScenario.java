package de.fh_kiel.robotics.mr.server.gui.menus;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JTextField;

import de.fh_kiel.robotics.mr.server.core.Core;
import de.fh_kiel.robotics.mr.server.core.scenario.ScenarioManagement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

@SuppressWarnings("serial")
public class LoadScenario extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;

	public LoadScenario( final JFrame aParent ) {
		super( aParent, true );
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Load Scenario");
		setResizable(false);
		setBounds(100, 100, 555, 151);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblScenarioArchive = new JLabel("Scenario Archive");
		lblScenarioArchive.setBounds(10, 11, 275, 14);
		contentPanel.add(lblScenarioArchive);
		
		textField = new JTextField();
		textField.setBounds(10, 25, 400, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
		textField.setText( Core.getInstance().getServerConfig().getScenarioLibrary() );
		
		JButton btnNewButton = new JButton("Select Archive");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser chooser = new JFileChooser();
			    chooser.setCurrentDirectory( new File( System.getProperty( "user.dir" ) ));
			    chooser.setSelectedFile( new File( textField.getText() ) );
			    FileNameExtensionFilter filter = new FileNameExtensionFilter( "Scenario JARs", "jar");
			    chooser.setFileFilter( filter );
			    if( chooser.showOpenDialog( contentPanel ) == JFileChooser.APPROVE_OPTION ) {
			    	
			    	textField.setText( chooser.getSelectedFile().toString() );
			    	
			    }
				
			}
		});
		btnNewButton.setBounds(414, 24, 125, 23);
		contentPanel.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Core Class ( implements Scenario Interface )");
		lblNewLabel.setBounds(10, 56, 275, 14);
		contentPanel.add(lblNewLabel);
		
		textField_1 = new JTextField();
		textField_1.setBounds(10, 70, 400, 20);
		contentPanel.add(textField_1);
		textField_1.setColumns(10);
		textField_1.setText( Core.getInstance().getServerConfig().getScenarioClass() );
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						Core.getInstance().getServerConfig().setScenarioLibrary( textField.getText() );
						Core.getInstance().getServerConfig().setScenarioClass( textField_1.getText() );
				    	
				    	new Thread( new Runnable() {

							@Override
							public void run() {
				
								ScenarioManagement.getInstance().loadScenario();

							}
						} ).start();
						
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
