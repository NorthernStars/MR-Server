package mrscenariofootball.core.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;

import mrscenariofootball.core.data.ScenarioInformation;
import mrscenariofootball.core.data.worlddata.server.Player;
import mrscenariofootball.core.data.worlddata.server.Team;
import mrscenariofootball.core.data.worlddata.server.WorldData;

public class PlayFieldForeground extends JPanel{
	private BufferedImage mBallImage, mBlueBotImage, mYellowBotImage, mNoneBotImage;
    private JPopupMenu mPlayfieldPopupMenu;

	/**
	 * Create the panel.
	 */
	public PlayFieldForeground() {
		setBackground(new Color(34, 139, 34, 0));
		
		setOpaque(false);
		
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
			mBallImage = ImageIO.read( PlayField.class.getResource( "/mrscenariofootball/core/gui/ball.png" ));
			mBlueBotImage = ImageIO.read( PlayField.class.getResource( "/mrscenariofootball/core/gui/playerblue.gif" ));
			mYellowBotImage = ImageIO.read( PlayField.class.getResource( "/mrscenariofootball/core/gui/playeryellow.gif" ));
			mNoneBotImage = ImageIO.read( PlayField.class.getResource( "/mrscenariofootball/core/gui/playernone.gif" ));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
    public void paintComponent( Graphics g ) {
    	try{
	    	super.paintComponent(g);       

    		System.out.println("front");
    		
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
				
			// draw player
			AffineTransform mTransformation;
			for( Player vPlayer : vWorld.getListOfPlayers() ){
			
				mTransformation = new AffineTransform();
	            mTransformation.translate( 	width * vPlayer.getPosition().getX() - ( height / 40.0 ) / mBlueBotImage.getWidth() / 2,
	            		 					-(height * vPlayer.getPosition().getY() - ( height / 40.0 ) / mBlueBotImage.getHeight() / 2 ));
	            mTransformation.rotate( Math.toRadians( -vPlayer.getOrientationAngle() ) );
	            mTransformation.scale( ( height / 40.0 ) / mBlueBotImage.getWidth(), ( height / 40.0 ) / mBlueBotImage.getHeight() );
	            mTransformation.translate( -mBlueBotImage.getWidth() / 2, -mBlueBotImage.getHeight() / 2);
				g2d.drawImage( vPlayer.getTeam() == Team.Yellow ? mYellowBotImage : vPlayer.getTeam() == Team.Blue ? mBlueBotImage : mNoneBotImage, mTransformation, this );
				
				lblNewLabel = new JLabel( vPlayer.getNickname() + " (" + vPlayer.getId() + ")" );
				lblNewLabel.setForeground( Color.WHITE );
				lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
				lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
				lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				lblNewLabel.setBounds( 	(int)( width * vPlayer.getPosition().getX() - 100 ),
										(int)( -height * vPlayer.getPosition().getY() + 10 ), 200, 14);
				add(lblNewLabel);
				
			}
			
			g2d.drawImage( 	mBallImage, 
							(int)(width*vWorld.getBallPosition().getPosition().getX() - height/160),
							(int)(-height*vWorld.getBallPosition().getPosition().getY() - height/160), 
							(int)(height/80), (int)(height/80), this);
			
	    } catch (Exception e) {
			e.printStackTrace();
		}		
    }  
	
}
