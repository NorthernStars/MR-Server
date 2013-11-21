package mrscenariofootball.core.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JButton;

import mrscenariofootball.core.ScenarioCore;
import mrscenariofootball.core.data.worlddata.server.Player;
import mrscenariofootball.core.data.worlddata.server.ReferencePoint;
import mrscenariofootball.core.data.worlddata.server.WorldData;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import java.awt.Component;

import javax.swing.SwingConstants;

public class ScenarioGUI extends JPanel {

	private BufferedImage mBackgroundImage, mBallImage, mBlueBotImage, mYellowBotImage;


	/**
	 * Create the panel.
	 */
	public ScenarioGUI() {
		setBackground(new Color(34, 139, 34));
		
		setLayout(null);
		
		try {
			mBackgroundImage = ImageIO.read( ScenarioGUI.class.getResource( "/mrscenariofootball/core/gui/grass.jpg" ));
			mBallImage = ImageIO.read( ScenarioGUI.class.getResource( "/mrscenariofootball/core/gui/ball.png" ));
			mBlueBotImage = ImageIO.read( ScenarioGUI.class.getResource( "/mrscenariofootball/core/gui/playerblue.gif" ));
			mYellowBotImage = ImageIO.read( ScenarioGUI.class.getResource( "/mrscenariofootball/core/gui/playeryellow.gif" ));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


    public void paintComponent( Graphics g ) {
    	try{
	    	super.paintComponent(g);       

			Graphics2D g2d = (Graphics2D) g;
			
			g2d.translate( 0, this.getHeight() );
			
	    	removeAll();
	    	
	        WorldData vWorld = ScenarioCore.getInstance().getScenarioInformation().getWorldData().copy();
			int width, height;
			JLabel lblNewLabel;
			
			width = this.getWidth();
			height = this.getHeight();
			
			//g2d.drawImage( mBackgroundImage, 0, 0, width, -height, this);
			
			g2d.setColor( Color.WHITE );
			for( ReferencePoint vPoint : vWorld.getReferencePoints() ){
				
				g2d.fillOval( (int)( width * vPoint.getPosition().getX() - height / 100), 
							(int)( -height * vPoint.getPosition().getY() - height / 100 ), 
							(int)( height / 50 ), 
							(int)( height / 50 ) );
				
				lblNewLabel = new JLabel( vPoint.getPointName().getShortName() );
				lblNewLabel.setForeground( Color.WHITE );
				lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
				lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
				lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				lblNewLabel.setBounds( 	(int)( width * vPoint.getPosition().getX() - 100 ),
										(int)( -height * vPoint.getPosition().getY() + height / 50 ), 200, 14);
				add(lblNewLabel);
				
			}
			
			// create the transform, note that the transformations happen
            // in reversed order (so check them backwards)
            AffineTransform at = new AffineTransform();

            // 4. translate it to the center of the component
            at.translate( getWidth() / 2, -getHeight() / 2 );

            // 3. do the actual rotation
            at.rotate( Math.toRadians( -45 ) );

            // 2. just a scale because this image is big
            at.scale( ( height / 25.0 ) / mBlueBotImage.getWidth(), ( height / 25.0 ) / mBlueBotImage.getHeight() );

            // 1. translate the object so that you rotate it around the 
            //    center (easier :))
            at.translate( mBlueBotImage.getWidth() / 2, mBlueBotImage.getHeight() / 2);
			g2d.drawImage( mBlueBotImage, at, this );
			
			for( Player vPlayer : vWorld.getListOfPlayers() ){
				
				g2d.fillOval( (int)( width * vPlayer.getPosition().getX() - height / 100), 
							(int)( -height * vPlayer.getPosition().getY() - height / 100 ), 
							(int)( height / 50 ), 
							(int)( height / 50 ) );
				
				lblNewLabel = new JLabel( vPlayer.getPointName().getShortName() );
				lblNewLabel.setForeground( Color.WHITE );
				lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
				lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
				lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				lblNewLabel.setBounds( 	(int)( width * vPlayer.getPosition().getX() - 100 ),
										(int)( -height * vPlayer.getPosition().getY() + height / 50 ), 200, 14);
				add(lblNewLabel);
				
			}
			
			g2d.drawImage( 	mBallImage, 
							(int)(width*vWorld.getBallPosition().getPosition().getX() - height/60),
							(int)(-height*vWorld.getBallPosition().getPosition().getY() - height/60), 
							(int)(height/30), (int)(height/30), this);
			
			
	    } catch (Exception e) {
			e.printStackTrace();
		}		
    }  
}
