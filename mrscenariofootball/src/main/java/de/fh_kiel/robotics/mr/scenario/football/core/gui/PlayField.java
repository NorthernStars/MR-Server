package de.fh_kiel.robotics.mr.scenario.football.core.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JLabel;

import java.awt.Component;

import javax.swing.SwingConstants;

import de.fh_kiel.robotics.mr.scenario.football.core.data.ScenarioInformation;
import de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server.Player;
import de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server.ReferencePoint;
import de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server.ReferencePointName;
import de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server.ServerPoint;
import de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server.Team;
import de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server.WorldData;

@SuppressWarnings("serial")
public class PlayField extends JPanel implements ComponentListener{

	private BufferedImage mBallImage, mBackGround;
    private JPopupMenu mPlayfieldPopupMenu;
	private List<ReferencePointName> mLinesToDraw;
	private double mGoalWidth, mCenterCircleSize;
	private float mPointSize, mHalfPointSize, mLineWidth;
	private ReferencePoint mRefPoint1, mRefPoint2;
	private ServerPoint mTop, mBottom, mCenter;
	private Graphics2D g2d;
	private WorldData mWorld;
	private int mWidth,  mHeight;
	private Font mFont;
	private Stroke mDashedStroke, mNormalStroke ;

	/**
	 * Create the panel.
	 */
	public PlayField() {
		setBackground(new Color(34, 139, 34));
		setLayout(null);
		
		addComponentListener(this);
		
		createLinesToDraw();
		
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
			} catch (IOException e) {
				e.printStackTrace();
			}

		float dash1[] = {10.0f};
	    mDashedStroke = new BasicStroke(1.0f,
		                BasicStroke.CAP_BUTT,
		                BasicStroke.JOIN_MITER,
		                10.0f, dash1, 0.0f);
		
	}

	@Override
    public void paintComponent( Graphics g ) {
    	try{
    		
    		g2d = (Graphics2D) g;
    		
    		if( mNormalStroke == null){
    			mNormalStroke = g2d.getStroke();
    		}
    		
    		if( mBackGround == null){
    			createBackground();
    		}
    		g2d.drawImage( 	mBackGround, 0, 0, this);
    		
    		g2d.translate( 0, mHeight );
    		g2d.scale( 1.0 / ScenarioInformation.getInstance().getXFactor(),
    				   1.0 / ScenarioInformation.getInstance().getYFactor());
    		
    		mWorld = ScenarioInformation.getInstance().getWorldData();

			for( Player vPlayer : mWorld.getListOfPlayers() ){
			
				switch( vPlayer.getTeam() ){
				
					case Blue: 
						g2d.setColor(Color.BLUE);
						break;
					case Yellow: 
						g2d.setColor(Color.YELLOW);
						break;
					default: 
						g2d.setColor(Color.GRAY);
					
				}
				
				g2d.setStroke(mNormalStroke);
				g2d.drawLine( (int)( mWidth * vPlayer.getPosition().getX() ), 
						(int)( -mHeight * vPlayer.getPosition().getY() ),
						(int)( mWidth * vPlayer.getPosition().getX() + 0.010 * mWidth * Math.cos( Math.toRadians( -vPlayer.getOrientationAngle() ) ) ),
						(int)( -mHeight * vPlayer.getPosition().getY() + 0.010 * mWidth * Math.sin( Math.toRadians( -vPlayer.getOrientationAngle() ) ) ) );
				
	            g2d.fillOval( (int)( mWidth * vPlayer.getPosition().getX() - mHalfPointSize ), 
						(int)( -mHeight * vPlayer.getPosition().getY() - mHalfPointSize ), 
						(int)( mPointSize ), 
						(int)( mPointSize ) );
	            g2d.drawOval( (int)( mWidth * vPlayer.getPosition().getX() - 0.010 * mWidth ), 
								(int)( -mHeight * vPlayer.getPosition().getY() - 0.010 * mWidth ), 
								(int)( 0.020 * mWidth ), 
								(int)( 0.020 * mWidth ) );
				
				String vPlayerString = vPlayer.getNickname() + " (" + vPlayer.getId() + ")";
				g2d.setFont(mFont);
				g2d.drawString( vPlayerString, 
						(int)( mWidth * vPlayer.getPosition().getX() - g2d.getFontMetrics(mFont).stringWidth( vPlayerString ) / 2 ), 
						(int)( -mHeight * vPlayer.getPosition().getY()  + 0.014 * mWidth ) + g2d.getFontMetrics(mFont).getHeight() );
				
				g2d.setStroke(mDashedStroke);
	            g2d.drawOval( (int)( mWidth * vPlayer.getPosition().getX() - 0.013 * mWidth ), 
								(int)( -mHeight * vPlayer.getPosition().getY() - 0.013 * mWidth ), 
								(int)( 0.026 * mWidth ), 
								(int)( 0.026 * mWidth ) );
				
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
		
		mWidth = this.getWidth();
		mHeight = this.getHeight();
		
		int vScale = mHeight/40;
				
		mBackGround = new BufferedImage( mWidth, mHeight, BufferedImage.TYPE_INT_RGB );
		Graphics2D g = (Graphics2D) mBackGround.getGraphics();
		if( g == null ){
			mBackGround= null;
			return;
		}

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    
		super.paintComponent(g); 
		
		g.translate( 0, mHeight );
		g.scale( 1.0 / ScenarioInformation.getInstance().getXFactor(),
				   1.0 / ScenarioInformation.getInstance().getYFactor());
		
		mWorld = ScenarioInformation.getInstance().getWorldData().copy();
		
		// draw blue goal
		if( mWorld.getMapOfReferencePoints() .containsKey(ReferencePointName.BlueGoalCornerTop)
				&& mWorld.getMapOfReferencePoints().containsKey(ReferencePointName.BlueGoalCornerBottom)){
			mTop = mWorld.getMapOfReferencePoints().get(ReferencePointName.BlueGoalCornerTop).getPosition();
			mBottom = mWorld.getMapOfReferencePoints().get(ReferencePointName.BlueGoalCornerBottom).getPosition();
			mGoalWidth = (1.0-mTop.getX()) * 0.75;
			
			g.setColor( Color.BLUE );
			g.fillRect( 	(int)(mWidth * mTop.getX()),
							(int)(-mHeight * mTop.getY()),
							(int)(mWidth * mGoalWidth),
							(int)(mHeight * (mTop.getY() - mBottom.getY())));				
		}
		
		// draw yellow goal
		if( mWorld.getMapOfReferencePoints().containsKey(ReferencePointName.YellowGoalCornerTop)
				&& mWorld.getMapOfReferencePoints().containsKey(ReferencePointName.YellowGoalCornerBottom)){
			mTop = mWorld.getMapOfReferencePoints().get(ReferencePointName.YellowGoalCornerTop).getPosition();
			mBottom = mWorld.getMapOfReferencePoints().get(ReferencePointName.YellowGoalCornerBottom).getPosition();
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
			
			mRefPoint1 = mWorld.getMapOfReferencePoints().get(mLinesToDraw.get(i++));
			mRefPoint2 = mWorld.getMapOfReferencePoints().get(mLinesToDraw.get(i++));
			g.drawLine( (int)( mWidth * mRefPoint1.getPosition().getX()),
					(int)( -mHeight * mRefPoint1.getPosition().getY()),
					(int)( mWidth * mRefPoint2.getPosition().getX()),
					(int)( -mHeight * mRefPoint2.getPosition().getY()) );
			
		}
		
		// draw center circle
		if( mWorld.getMapOfReferencePoints().containsKey(ReferencePointName.BlueGoalCornerTop)
				&& mWorld.getMapOfReferencePoints().containsKey(ReferencePointName.BlueGoalCornerBottom)
				&& mWorld.getMapOfReferencePoints().containsKey(ReferencePointName.FieldCenter)){
			
			mTop = mWorld.getMapOfReferencePoints().get(ReferencePointName.BlueGoalCornerTop).getPosition();
			mBottom = mWorld.getMapOfReferencePoints().get(ReferencePointName.BlueGoalCornerBottom).getPosition();
			mCenter = mWorld.getMapOfReferencePoints().get(ReferencePointName.FieldCenter).getPosition();
			mCenterCircleSize = (mTop.getY() - mBottom.getY()) * 0.75;
			
			g.setColor( Color.WHITE );
			g.setStroke( new BasicStroke(mLineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL) );
			g.drawArc( (int)(mWidth * (mCenter.getX() - mCenterCircleSize * 0.5)),
						 (int)(-mHeight * mCenter.getY() - mHeight * mCenterCircleSize * 0.5),
						 (int)(mWidth * mCenterCircleSize),
						 (int)(mHeight * mCenterCircleSize),
						 0, 360);
			
		}
		DecimalFormat df = new DecimalFormat("#.###");
		g.drawString( df.format( (double)mHeight/(double)mWidth ), 0, 0 );
		
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
