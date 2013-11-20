package mrscenariofootball.core.gui;

import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JButton;

public class ScenarioGUI extends JPanel {

	/**
	 * Create the panel.
	 */
	public ScenarioGUI() {
		
		JButton btnNewButton = new JButton("New button");
		add(btnNewButton);

	}


    public void paintComponent(Graphics g) {
        super.paintComponent(g);       

        // Draw Text
        g.drawString("This is my custom Panel!",10,20);
        
    }  
	
}
