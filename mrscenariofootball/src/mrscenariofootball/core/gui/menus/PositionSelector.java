package mrscenariofootball.core.gui.menus;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;

import mrscenariofootball.core.ScenarioCore;
import mrscenariofootball.core.data.ScenarioInformation;
import mrscenariofootball.core.data.worlddata.server.BallPosition;
import mrscenariofootball.core.data.worlddata.server.ReferencePoint;
import mrscenariofootball.core.data.worlddata.server.ServerPoint;

import javax.swing.DefaultComboBoxModel;

import mrscenariofootball.core.data.worlddata.server.ReferencePointName;
import mrscenariofootball.core.gui.PlayField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PositionSelector extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JComboBox<ReferencePointName> comboBox;
	
	private ReferencePointName mSelectedPoint;
	private Component mInvoker;

	public PositionSelector( Component aInvoker ) {
		mInvoker = aInvoker;
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 321, 96);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			comboBox = new JComboBox<ReferencePointName>();
			DefaultComboBoxModel<ReferencePointName> vModel = new DefaultComboBoxModel<ReferencePointName>( ReferencePointName.values() );
			vModel.removeElement( ReferencePointName.Ball );
			vModel.removeElement( ReferencePointName.NoFixedName );
			vModel.removeElement( ReferencePointName.Player );
			comboBox.setModel( vModel );
			comboBox.setBounds(10, 10, 300, 22);
			contentPanel.add(comboBox);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						
						mSelectedPoint = (ReferencePointName) comboBox.getSelectedItem();

						BallPosition vNewPosition = new BallPosition( ReferencePointName.Ball,
		    					new ServerPoint( mSelectedPoint.getRelativePosition().getX() * ScenarioInformation.getInstance().getXFactor(),
		    								     mSelectedPoint.getRelativePosition().getY() * ScenarioInformation.getInstance().getYFactor()));
		            	
		            	ScenarioInformation.getInstance().setBall( vNewPosition );
		            	ScenarioCore.getLogger().info( "Ball set to position: {}", vNewPosition );
		            	((PlayField) mInvoker).update();
		            	
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

	public ReferencePointName getSelectedPoint() {
		return mSelectedPoint;
	}

}
