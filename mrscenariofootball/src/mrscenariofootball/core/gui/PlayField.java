package mrscenariofootball.core.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import mrscenariofootball.core.data.ScenarioInformation;
import mrscenariofootball.core.data.worlddata.server.Player;
import mrscenariofootball.core.data.worlddata.server.ReferencePoint;
import mrscenariofootball.core.data.worlddata.server.ReferencePointName;
import mrscenariofootball.core.data.worlddata.server.ServerPoint;
import mrscenariofootball.core.data.worlddata.server.Team;
import mrscenariofootball.core.data.worlddata.server.WorldData;

import javax.swing.JLabel;

import java.awt.Component;

import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import javax.swing.JLayeredPane;

@SuppressWarnings("serial")
public class PlayField extends JPanel implements ComponentListener {
	private JPanel panel_1;
	private JPanel panel;

	/**
	 * Create the panel.
	 */
	public PlayField() {
		setLayout(new BorderLayout(0, 0));
		
		JLayeredPane layeredPane = new JLayeredPane();
		add(layeredPane, BorderLayout.CENTER);
		layeredPane.setLayout(null);
		
		panel = new PlayFieldBackground();
		panel.setBounds(0, 0, getWidth(), getHeight());
		layeredPane.setLayer(panel, 0);
		layeredPane.add(panel);
		panel.setLayout(null);
		
		panel_1 = new PlayFieldForeground();
		panel_1.setBounds(0, 0, getWidth(), getHeight());
		layeredPane.setLayer(panel_1, 1);
		layeredPane.add(panel_1);
		panel_1.setLayout(null);
		

		
	}

    /**
     * Updates graphic
     */
    public void update(){
    	
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				panel_1.repaint();
				panel_1.validate();
				
			}
		});
    	
    }

	@Override
	public void componentResized(ComponentEvent e) {
		panel.setBounds(0, 0, getWidth(), getHeight());
		panel_1.setBounds(0, 0, getWidth(), getHeight());
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
    
}
