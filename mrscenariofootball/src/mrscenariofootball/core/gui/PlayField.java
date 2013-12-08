package mrscenariofootball.core.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import mrscenariofootball.core.ScenarioCore;
import mrscenariofootball.core.data.ScenarioInformation;
import mrscenariofootball.core.data.worlddata.server.Player;
import mrscenariofootball.core.data.worlddata.server.ReferencePoint;
import mrscenariofootball.core.data.worlddata.server.Team;
import mrscenariofootball.core.data.worlddata.server.WorldData;

import javax.swing.JLabel;

import java.awt.Component;

import javax.swing.SwingConstants;

public class PlayField extends JPanel {

	private BufferedImage mBackgroundImage, mBallImage, mBlueBotImage, mYellowBotImage, mNoneBotImage;
    private JPopupMenu mPlayfieldPopupMenu;

	/**
	 * Create the panel.
	 */
	public PlayField() {
		setBackground(new Color(34, 139, 34));
		setLayout(null);
		
		mPlayfieldPopupMenu = new PlayfieldPopup();
		
		addMouseListener(new MouseAdapter() {

			@Override
            public void mouseClicked( MouseEvent e ) {
                
                if( e.getButton() == MouseEvent.BUTTON3){
                    
                    mPlayfieldPopupMenu.show( e.getComponent(), e.getX(), e.getY() );
                    
                }
                
            }
        });
		
		
		try {
			mBackgroundImage = ImageIO.read( PlayField.class.getResource( "/mrscenariofootball/core/gui/grass.jpg" ));
			mBallImage = ImageIO.read( PlayField.class.getResource( "/mrscenariofootball/core/gui/ball.png" ));
			mBlueBotImage = ImageIO.read( PlayField.class.getResource( "/mrscenariofootball/core/gui/playerblue.gif" ));
			mYellowBotImage = ImageIO.read( PlayField.class.getResource( "/mrscenariofootball/core/gui/playeryellow.gif" ));
			mNoneBotImage = ImageIO.read( PlayField.class.getResource( "/mrscenariofootball/core/gui/playernone.gif" ));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


    public void paintComponent( Graphics g ) {
    	try{
	    	super.paintComponent(g);       

			Graphics2D g2d = (Graphics2D) g;
			
			g2d.translate( 0, this.getHeight() );
			g2d.scale( 1.0 / ScenarioInformation.getInstance().getXFactor(),
					   1.0 / ScenarioInformation.getInstance().getYFactor());
			
	    	removeAll();
	    	
	        WorldData vWorld = ScenarioInformation.getInstance().getWorldData().copy();
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
			
			AffineTransform mTransformation;
			for( Player vPlayer : vWorld.getListOfPlayers() ){
			
				mTransformation = new AffineTransform();
	            mTransformation.translate( 	width * vPlayer.getPosition().getX() - ( height / 25.0 ) / mBlueBotImage.getWidth() / 2,
	            		 					-(height * vPlayer.getPosition().getY() - ( height / 25.0 ) / mBlueBotImage.getHeight() / 2 ));
	            mTransformation.rotate( Math.toRadians( -vPlayer.getOrientationAngle() ) );
	            mTransformation.scale( ( height / 25.0 ) / mBlueBotImage.getWidth(), ( height / 25.0 ) / mBlueBotImage.getHeight() );
	            mTransformation.translate( -mBlueBotImage.getWidth() / 2, -mBlueBotImage.getHeight() / 2);
				g2d.drawImage( vPlayer.getTeam() == Team.Yellow ? mYellowBotImage : vPlayer.getTeam() == Team.Blue ? mBlueBotImage : mNoneBotImage, mTransformation, this );
				
				lblNewLabel = new JLabel( vPlayer.getNickname() + " (" + vPlayer.getId() + ")" + " (" + (int) vPlayer.getOrientationAngle() + "°)"  );
				lblNewLabel.setForeground( Color.WHITE );
				lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
				lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
				lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				lblNewLabel.setBounds( 	(int)( width * vPlayer.getPosition().getX() - 100 ),
										(int)( -height * vPlayer.getPosition().getY() + 10 ), 200, 14);
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
    
    public void update(){
    	
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				repaint();
				validate();
				
			}
		});
    	
    }
}
