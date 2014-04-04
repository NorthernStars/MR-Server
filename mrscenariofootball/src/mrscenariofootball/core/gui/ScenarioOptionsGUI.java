package mrscenariofootball.core.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import mrscenariofootball.core.data.ScenarioInformation;
import mrscenariofootball.core.data.ScenarioSettings;
import mrscenariofootball.core.data.worlddata.server.Team;
import mrscenariofootball.game.Core;

import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ScenarioOptionsGUI extends JFrame {
	public JCheckBox chckbxShowBackgroundImage;

	/**
	 * Create the frame.
	 */
	public ScenarioOptionsGUI() {
		ScenarioInformation info = ScenarioInformation.getInstance();
		
		setTitle( info.getmScenarioName() + " Options" );
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 112);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, "2, 2, 3, 1, fill, fill");
		
		JButton button = new JButton("KickOff");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Core core = Core.getInstance();
				core.setKickoff(Team.None);
			}
		});
		panel.add(button, "1, 1, fill, default");
		
		JButton btnKickoffYellow = new JButton("KickOff Yellow Team");
		btnKickoffYellow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Core core = Core.getInstance();
				core.setKickoff(Team.Yellow);
			}
		});
		panel.add(btnKickoffYellow, "3, 1, fill, fill");
		
		JButton btnKickoffBlue = new JButton("KickOff Blue Team");
		btnKickoffBlue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Core core = Core.getInstance();
				core.setKickoff(Team.Blue);
			}
		});
		panel.add(btnKickoffBlue, "5, 1, fill, fill");
		
		chckbxShowBackgroundImage = new JCheckBox("Show background image");
		chckbxShowBackgroundImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ScenarioSettings settings = ScenarioSettings.getInstance();
				settings.setShowFieldBackground(chckbxShowBackgroundImage.isSelected());
			}
		});
		contentPane.add(chckbxShowBackgroundImage, "2, 4, 3, 1");
	}

}
