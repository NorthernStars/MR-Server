package mrscenariofootball.core.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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

@SuppressWarnings("serial")
public class PlayField extends JPanel implements ComponentListener{

	private BufferedImage mBallImage, mBlueBotImage, mYellowBotImage, mNoneBotImage, mBackGround;
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
	private Font mFont;
	private double mWidthHolder;

	/**
	 * Create the panel.
	 */
	public PlayField() {
		setBackground(new Color(34, 139, 34));
		setLayout(null);
		
		addComponentListener(this);
		
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
    		
    		g2d = (Graphics2D) g;

    		mWidth = this.getWidth();
    		mHeight = this.getHeight();
    		
    		if( mBackGround == null){
    			createBackground();
    		}
    		g2d.drawImage( 	mBackGround, 0, 0, this);
    		
    		g2d.translate( 0, mHeight );
    		g2d.scale( 1.0 / ScenarioInformation.getInstance().getXFactor(),
    				   1.0 / ScenarioInformation.getInstance().getYFactor());
    		
    		mWorld = ScenarioInformation.getInstance().getWorldData();

			System.out.println(mHeight + " " + mHeightHolder + " " + mBlueBotImage.getHeight());
			for( Player vPlayer : mWorld.getListOfPlayers() ){
			
				mTransformation = new AffineTransform();
	            mTransformation.translate( 	mWidth * vPlayer.getPosition().getX() - mWidthHolder * mBlueBotImage.getWidth() / 2,
	            		 					-mHeight * vPlayer.getPosition().getY() - mHeightHolder * mBlueBotImage.getHeight() / 2 );
	            mTransformation.rotate( Math.toRadians( -vPlayer.getOrientationAngle() ), mBlueBotImage.getWidth() / 2, mBlueBotImage.getHeight() / 2 );
	            mTransformation.scale( mWidthHolder, mHeightHolder );
				g2d.drawImage( vPlayer.getTeam() == Team.Yellow ? mYellowBotImage : vPlayer.getTeam() == Team.Blue ? mBlueBotImage : mNoneBotImage, mTransformation, this );

				g.fillOval( (int)( mWidth * vPlayer.getPosition().getX() - mHalfPointSize ), 
						(int)( -mHeight * vPlayer.getPosition().getY() - mHalfPointSize ), 
						(int)( mPointSize ), 
						(int)( mPointSize ) );
				g.drawOval( (int)( mWidth * vPlayer.getPosition().getX() - 0.013 * mWidth ), 
						(int)( -mHeight * vPlayer.getPosition().getY() - 0.013 * mHeight), 
						(int)( 0.026 * mWidth ), 
						(int)( 0.026 * mHeight ) );
				
				//g.drawString( vPlayer.getNickname() + " (" + vPlayer.getId() + ")", (int)( mWidth * vPlayer.getPosition().getX() ), (int)( -mHeight * vPlayer.getPosition().getY() ) );
				
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

	private void createBackground() {
		
		if( getWidth() == 0 || getHeight() == 0){
			mBackGround = null;
			return;
		}
		
		mBackGround = new BufferedImage( getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB );
		Graphics2D g = (Graphics2D) mBackGround.getGraphics();
		if( g == null ){
			mBackGround= null;
			return;
		}

		mWidthHolder = (double) mHeight / ( mBlueBotImage.getWidth() * 40.0 );
		mHeightHolder = (double) mHeight / ( mBlueBotImage.getHeight() * 40.0 );
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    
		super.paintComponent(g); 
		
		g.translate( 0, mHeight );
		g.scale( 1.0 / ScenarioInformation.getInstance().getXFactor(),
				   1.0 / ScenarioInformation.getInstance().getYFactor());
		
		mWorld = ScenarioInformation.getInstance().getWorldData();
		// draw blue goal
		if( mRefPointMap.containsKey(ReferencePointName.BlueGoalCornerTop)
				&& mRefPointMap.containsKey(ReferencePointName.BlueGoalCornerBottom)){
			mTop = mRefPointMap.get(ReferencePointName.BlueGoalCornerTop).getPosition();
			mBottom = mRefPointMap.get(ReferencePointName.BlueGoalCornerBottom).getPosition();
			mGoalWidth = (1.0-mTop.getX()) * 0.75;
			
			g.setColor( Color.BLUE );
			g.fillRect( 	(int)(mWidth * mTop.getX()),
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
			
			g.setColor( Color.YELLOW );
			g.fillRect( 	(int)(mWidth * (mTop.getX() - mGoalWidth)),
							(int)(-mHeight * mTop.getY()),
							(int)(mWidth * mGoalWidth),
							(int)(mHeight * (mTop.getY() - mBottom.getY())));				
		}
		
		// draw reference points
		g.setColor( Color.WHITE );
		mPointSize = mHeight / 100;
		mHalfPointSize = mPointSize * 0.5f;

	    mFont = new Font(Font.MONOSPACED, Font.PLAIN, (int)(mPointSize * 1.5));
	    g.setFont(mFont);
	    
		for( ReferencePoint vPoint : mWorld.getReferencePoints() ){
			
			g.fillOval( (int)( mWidth * vPoint.getPosition().getX() - mHalfPointSize ), 
						(int)( -mHeight * vPoint.getPosition().getY() - mHalfPointSize ), 
						(int)( mPointSize ), 
						(int)( mPointSize ) );
			
		    g.drawString(vPoint.getPointName().getShortName(), (int)( mWidth * vPoint.getPosition().getX() + mHalfPointSize ), (int)( -mHeight * vPoint.getPosition().getY() + 1.5 * mPointSize ) );
						
		}
		
		mLineWidth = mPointSize * 0.25f;
		g.setColor( Color.WHITE );
		g.setStroke( new BasicStroke(mLineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL) );
		int i = 0;
		while( i < mLinesToDraw.size()-1 ){
			
			mRefPoint1 = mRefPointMap.get(mLinesToDraw.get(i++));
			mRefPoint2 = mRefPointMap.get(mLinesToDraw.get(i++));
			g.drawLine( (int)( mWidth * mRefPoint1.getPosition().getX()),
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
			
			g.setColor( Color.WHITE );
			g.setStroke( new BasicStroke(mLineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL) );
			g.drawArc( (int)(mWidth * (mCenter.getX() - mCenterCircleSize * 0.5)),
						 (int)(-mHeight * mCenter.getY() - mHeight * mCenterCircleSize * 0.5),
						 (int)(mWidth * mCenterCircleSize),
						 (int)(mHeight * mCenterCircleSize),
						 0, 360);
			
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

	@Override
	public void componentResized(ComponentEvent e) {
		
		createBackground();
		
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
