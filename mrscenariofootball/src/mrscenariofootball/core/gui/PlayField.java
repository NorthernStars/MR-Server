package mrscenariofootball.core.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

@SuppressWarnings("serial")
public class PlayField extends JPanel {

	private BufferedImage mBallImage, mBlueBotImage, mYellowBotImage, mNoneBotImage;
    private JPopupMenu mPlayfieldPopupMenu;
	private List<ReferencePointName> mLinesToDraw;
	private Map<ReferencePointName , ReferencePoint> mRefPointMap;
	private double mHeightHolder, mGoalWidth, mCenterCircleSize;
	private float mPointSize, mHalfPointSize, mLineWidth;
	private ReferencePoint mRefPoint1, mRefPoint2;
	private ServerPoint mTop, mBottom, mCenter;
	private AffineTransform mTransformation;
	private Graphics2D g2d;
	private WorldData mWorld;
	private int mWidth,  mHeight, mFontSize;
	private JLabel mMultiUseLabel;

	/**
	 * Create the panel.
	 */
	public PlayField() {
		setBackground(new Color(34, 139, 34));
		setLayout(null);
		
		createLinesToDraw();
		
		mPlayfieldPopupMenu = new PlayfieldPopup();

		mRefPointMap = new HashMap<ReferencePointName, ReferencePoint>();
		for( ReferencePoint vPoint : ScenarioInformation.getInstance().getWorldData().getReferencePoints() ){
			mRefPointMap.put(vPoint.getPointName(), vPoint);
		}
		
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
	    	removeAll();
	    	
			g2d = (Graphics2D) g;

			mWidth = this.getWidth();
			mHeight = this.getHeight();
			
			g2d.translate( 0, mHeight );
			g2d.scale( 1.0 / ScenarioInformation.getInstance().getXFactor(),
					   1.0 / ScenarioInformation.getInstance().getYFactor());
			
	        mWorld = ScenarioInformation.getInstance().getWorldData();
			// draw blue goal
			if( mRefPointMap.containsKey(ReferencePointName.BlueGoalCornerTop)
					&& mRefPointMap.containsKey(ReferencePointName.BlueGoalCornerBottom)){
				mTop = mRefPointMap.get(ReferencePointName.BlueGoalCornerTop).getPosition();
				mBottom = mRefPointMap.get(ReferencePointName.BlueGoalCornerBottom).getPosition();
				mGoalWidth = (1.0-mTop.getX()) * 0.75;
				
				g2d.setColor( Color.BLUE );
				g2d.fillRect( 	(int)(mWidth * mTop.getX()),
								(int)(-mHeight * mTop.getY()),
								(int)(mWidth * mGoalWidth),
								(int)(mHeight * (mTop.getY() - mBottom.getY())));				
			}
			
			// draw yellow goal
			if( mRefPointMap.containsKey(ReferencePointName.YellowGoalCornerTop)
					&& mRefPointMap.containsKey(ReferencePointName.YellowGoalCornerBottom)){
				mTop = mRefPointMap.get(ReferencePointName.YellowGoalCornerTop).getPosition();
				mBottom = mRefPointMap.get(ReferencePointName.YellowGoalCornerBottom).getPosition();
				mGoalWidth = mTop.getX() * 0.75;
				
				g2d.setColor( Color.YELLOW );
				g2d.fillRect( 	(int)(mWidth * (mTop.getX() - mGoalWidth)),
								(int)(-mHeight * mTop.getY()),
								(int)(mWidth * mGoalWidth),
								(int)(mHeight * (mTop.getY() - mBottom.getY())));				
			}
			
			// draw reference points
			g2d.setColor( Color.WHITE );
			mPointSize = mHeight / 100;
			mHalfPointSize = mPointSize * 0.5f;
			mFontSize = (int)(mPointSize);
			for( ReferencePoint vPoint : mWorld.getReferencePoints() ){
				
				g2d.fillOval( (int)( mWidth * vPoint.getPosition().getX() - mHalfPointSize ), 
							(int)( -mHeight * vPoint.getPosition().getY() - mHalfPointSize ), 
							(int)( mPointSize ), 
							(int)( mPointSize ) );
				
				mMultiUseLabel = new JLabel( vPoint.getPointName().getShortName() );
				mMultiUseLabel.setForeground( Color.WHITE );
				mMultiUseLabel.setFont( new Font(Font.SANS_SERIF, Font.PLAIN, mFontSize) );
				mMultiUseLabel.setHorizontalAlignment(SwingConstants.LEFT);
				mMultiUseLabel.setHorizontalTextPosition(SwingConstants.LEFT);
				mMultiUseLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
				mMultiUseLabel.setBounds( 	(int)( mWidth * vPoint.getPosition().getX() + mHalfPointSize ),
										(int)( -mHeight * vPoint.getPosition().getY() + mHalfPointSize ),
										200, mFontSize);
				add(mMultiUseLabel);
				
			}
			
			mLineWidth = mPointSize * 0.25f;
			g2d.setColor( Color.WHITE );
			g2d.setStroke( new BasicStroke(mLineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL) );
			int i = 0;
			while( i < mLinesToDraw.size()-1 ){
				
				mRefPoint1 = mRefPointMap.get(mLinesToDraw.get(i++));
				mRefPoint2 = mRefPointMap.get(mLinesToDraw.get(i++));
				g2d.drawLine( (int)( mWidth * mRefPoint1.getPosition().getX()),
						(int)( -mHeight * mRefPoint1.getPosition().getY()),
						(int)( mWidth * mRefPoint2.getPosition().getX()),
						(int)( -mHeight * mRefPoint2.getPosition().getY()) );
				
			}
			
			// draw center circle
			if( mRefPointMap.containsKey(ReferencePointName.BlueGoalCornerTop)
					&& mRefPointMap.containsKey(ReferencePointName.BlueGoalCornerBottom)
					&& mRefPointMap.containsKey(ReferencePointName.FieldCenter)){
				
				mTop = mRefPointMap.get(ReferencePointName.BlueGoalCornerTop).getPosition();
				mBottom = mRefPointMap.get(ReferencePointName.BlueGoalCornerBottom).getPosition();
				mCenter = mRefPointMap.get(ReferencePointName.FieldCenter).getPosition();
				mCenterCircleSize = (mTop.getY() - mBottom.getY()) * 0.75;
				
				g2d.setColor( Color.WHITE );
				g2d.setStroke( new BasicStroke(mLineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL) );
				g2d.drawArc( (int)(mWidth * (mCenter.getX() - mCenterCircleSize * 0.5)),
							 (int)(-mHeight * mCenter.getY() - mHeight * mCenterCircleSize * 0.5),
							 (int)(mWidth * mCenterCircleSize),
							 (int)(mHeight * mCenterCircleSize),
							 0, 360);
				
			}
			
			mHeightHolder = mHeight / 40.0;
			for( Player vPlayer : mWorld.getListOfPlayers() ){
			
				mTransformation = new AffineTransform();
	            mTransformation.translate( 	mWidth * vPlayer.getPosition().getX() - ( mHeightHolder ) / mBlueBotImage.getWidth() / 2,
	            		 					-(mHeight * vPlayer.getPosition().getY() - ( mHeightHolder ) / mBlueBotImage.getHeight() / 2 ));
	            mTransformation.rotate( Math.toRadians( -vPlayer.getOrientationAngle() ) );
	            mTransformation.scale( ( mHeightHolder ) / mBlueBotImage.getWidth(), ( mHeightHolder ) / mBlueBotImage.getHeight() );
				g2d.drawImage( vPlayer.getTeam() == Team.Yellow ? mYellowBotImage : vPlayer.getTeam() == Team.Blue ? mBlueBotImage : mNoneBotImage, mTransformation, this );
				
				mMultiUseLabel = new JLabel( vPlayer.getNickname() + " (" + vPlayer.getId() + ")" );
				mMultiUseLabel.setForeground( Color.WHITE );
				mMultiUseLabel.setHorizontalAlignment(SwingConstants.CENTER);
				mMultiUseLabel.setHorizontalTextPosition(SwingConstants.CENTER);
				mMultiUseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
				mMultiUseLabel.setBounds( 	(int)( mWidth * vPlayer.getPosition().getX() - 100 ),
										(int)( -mHeight * vPlayer.getPosition().getY() + 10 ), 200, 14);
				add(mMultiUseLabel);
				
			}
			
			g2d.drawImage( 	mBallImage, 
							(int)(mWidth*mWorld.getBallPosition().getPosition().getX() - mHeight/160),
							(int)(-mHeight*mWorld.getBallPosition().getPosition().getY() - mHeight/160), 
							(int)(mHeight/80), (int)(mHeight/80), this);
			
			g2d.dispose();
			
	    } catch (Exception e) {
			e.printStackTrace();
		}		
    }  
    
    /**
     * Sets {@link List} of lines to draw
     * @return {@link List} of {@link ReferencePointName}.
     * Every pair of two points are start and end point of line.
     */
    private void createLinesToDraw(){
    	mLinesToDraw = new ArrayList<ReferencePointName>();
    	
    	mLinesToDraw.add( ReferencePointName.BlueFieldCornerBottom );
    	mLinesToDraw.add( ReferencePointName.BlueFieldCornerTop );
    	
    	mLinesToDraw.add( ReferencePointName.BlueFieldCornerTop );
    	mLinesToDraw.add( ReferencePointName.YellowFieldCornerTop );
    	
    	mLinesToDraw.add( ReferencePointName.YellowFieldCornerTop );
    	mLinesToDraw.add( ReferencePointName.YellowFieldCornerBottom );
    	
    	mLinesToDraw.add( ReferencePointName.YellowFieldCornerBottom );
    	mLinesToDraw.add( ReferencePointName.BlueFieldCornerBottom ); 
    	
    	mLinesToDraw.add( ReferencePointName.CenterLineTop );
    	mLinesToDraw.add( ReferencePointName.CenterLineBottom );
    	
    	mLinesToDraw.add( ReferencePointName.YellowGoalCornerTop );
    	mLinesToDraw.add( ReferencePointName.YellowGoalAreaFrontTop ); 
    	
    	mLinesToDraw.add( ReferencePointName.YellowGoalAreaFrontTop );
    	mLinesToDraw.add( ReferencePointName.YellowGoalAreaFrontBottom ); 
    	
    	mLinesToDraw.add( ReferencePointName.YellowGoalAreaFrontBottom );
    	mLinesToDraw.add( ReferencePointName.YellowGoalCornerBottom ); 
    	
    	mLinesToDraw.add( ReferencePointName.BlueGoalCornerTop );
    	mLinesToDraw.add( ReferencePointName.BlueGoalAreaFrontTop ); 
    	
    	mLinesToDraw.add( ReferencePointName.BlueGoalAreaFrontTop );
    	mLinesToDraw.add( ReferencePointName.BlueGoalAreaFrontBottom ); 
    	
    	mLinesToDraw.add( ReferencePointName.BlueGoalAreaFrontBottom );
    	mLinesToDraw.add( ReferencePointName.BlueGoalCornerBottom );
    	
    	mLinesToDraw.add( ReferencePointName.BluePenaltyAreaBackTop );
    	mLinesToDraw.add( ReferencePointName.BluePenaltyAreaFrontTop );
    	
    	mLinesToDraw.add( ReferencePointName.BluePenaltyAreaFrontTop );
    	mLinesToDraw.add( ReferencePointName.BluePenaltyAreaFrontBottom ); 
    	
    	mLinesToDraw.add( ReferencePointName.BluePenaltyAreaFrontBottom );
    	mLinesToDraw.add( ReferencePointName.BluePenaltyAreaBackBottom );
    	
    	mLinesToDraw.add( ReferencePointName.YellowGoalAreaFrontBottom );
    	mLinesToDraw.add( ReferencePointName.YellowGoalCornerBottom );
    	
    	mLinesToDraw.add( ReferencePointName.YellowPenaltyAreaBackTop );
    	mLinesToDraw.add( ReferencePointName.YellowPenaltyAreaFrontTop );
    	
    	mLinesToDraw.add( ReferencePointName.YellowPenaltyAreaFrontTop );
    	mLinesToDraw.add( ReferencePointName.YellowPenaltyAreaFrontBottom ); 
    	
    	mLinesToDraw.add( ReferencePointName.YellowPenaltyAreaFrontBottom );
    	mLinesToDraw.add( ReferencePointName.YellowPenaltyAreaBackBottom ); 
    	
//    	vLinesToDraw.add( ReferencePointName. );
//    	vLinesToDraw.add( ReferencePointName. );  dinge
    	
    }
    
    /**
     * Updates graphic
     */
    public void update(){
    	
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				repaint();
				validate();
				
			}
		});
    	
    }
}
