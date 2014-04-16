package mrscenariofootball.core.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import mrscenariofootball.core.data.ScenarioInformation;
import mrscenariofootball.core.data.worlddata.server.ReferencePoint;
import mrscenariofootball.core.data.worlddata.server.ReferencePointName;
import mrscenariofootball.core.data.worlddata.server.ServerPoint;
import mrscenariofootball.core.data.worlddata.server.WorldData;

import javax.swing.JLabel;

import java.awt.Component;

import javax.swing.SwingConstants;
import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class PlayFieldBackground extends JPanel {
	
	private List<ReferencePointName> mLinesToDraw;
	Map<ReferencePointName , ReferencePoint> mRefPointMap;
	
	/**
	 * Create the mPlayFieldOverlay.
	 */
	public PlayFieldBackground() {
		setOpaque(true);
		setBackground(new Color(34, 139, 34));
		setLayout(new BorderLayout(0, 0));
	}
	
	/**
	 * Draws vbackground
	 * @param g	{@link Graphics}
	 */
	private void drawBackground(Graphics g){		
		int width, height;
		JLabel lblNewLabel;
		
		removeAll();
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.translate( 0, this.getHeight() );
		g2d.scale( 1.0 / ScenarioInformation.getInstance().getXFactor(),
				   1.0 / ScenarioInformation.getInstance().getYFactor());		
		
		WorldData vWorld = ScenarioInformation.getInstance().getWorldData().copy();
		width = this.getWidth();
		height = this.getHeight();
		
		// Create map of reference points
		if( mRefPointMap == null ){
			mRefPointMap = new HashMap<ReferencePointName, ReferencePoint>();
			for( ReferencePoint vPoint : vWorld.getReferencePoints() ){
				mRefPointMap.put(vPoint.getPointName(), vPoint);
			}
		}
		
		// draw blue goal
		if( mRefPointMap.containsKey(ReferencePointName.BlueGoalCornerTop)
				&& mRefPointMap.containsKey(ReferencePointName.BlueGoalCornerBottom)){
			ServerPoint vTop = mRefPointMap.get(ReferencePointName.BlueGoalCornerTop).getPosition();
			ServerPoint vBottom = mRefPointMap.get(ReferencePointName.BlueGoalCornerBottom).getPosition();
			
			double goalWidth = (1.0-vTop.getX()) * 0.75;	
			g2d.setColor( Color.BLUE );
			g2d.fillRect( 	(int)(width * vTop.getX()),
							(int)(-height * vTop.getY()),
							(int)(width * goalWidth),
							(int)(height * (vTop.getY() - vBottom.getY())) );				
		}
		
		// draw yellow goal
		if( mRefPointMap.containsKey(ReferencePointName.YellowGoalCornerTop)
				&& mRefPointMap.containsKey(ReferencePointName.YellowGoalCornerBottom)){
			ServerPoint vTop = mRefPointMap.get(ReferencePointName.YellowGoalCornerTop).getPosition();
			ServerPoint vBottom = mRefPointMap.get(ReferencePointName.YellowGoalCornerBottom).getPosition();
			double goalWidth = vTop.getX() * 0.75;
			
			g2d.setColor( Color.YELLOW );
			g2d.fillRect( 	(int)(width * (vTop.getX() - goalWidth)),
							(int)(-height * vTop.getY()),
							(int)(width * goalWidth),
							(int)(height * (vTop.getY() - vBottom.getY())));				
		}
		
		// draw reference points
		g2d.setColor( Color.WHITE );
		float pointSize = height / 100;
		int fontSize = (int)(pointSize);
		for( ReferencePoint vPoint : vWorld.getReferencePoints() ){
			
			g2d.fillOval( (int)( width * vPoint.getPosition().getX() - pointSize * 0.5), 
						(int)( -height * vPoint.getPosition().getY() - pointSize * 0.5 ), 
						(int)( pointSize ), 
						(int)( pointSize ) );
			
			lblNewLabel = new JLabel( vPoint.getPointName().getShortName() );
			lblNewLabel.setForeground( Color.WHITE );
			lblNewLabel.setFont( new Font(Font.SANS_SERIF, Font.PLAIN, fontSize) );
			lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel.setHorizontalTextPosition(SwingConstants.LEFT);
			lblNewLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
			lblNewLabel.setBounds( 	(int)( width * vPoint.getPosition().getX() + pointSize * 0.5 ),
									(int)( -height * vPoint.getPosition().getY() + pointSize * 0.5 ),
									200, fontSize);
			add(lblNewLabel);
			
		}
		
		// set list of lines to draw
		List<ReferencePointName> vLinesToDraw = getLinesToDraw();
		
		// draw lines
		float lineWidth = pointSize * 0.25f;
		g2d.setColor( Color.WHITE );
		g2d.setStroke( new BasicStroke(lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL) );
		int i = 0;
		while( i < vLinesToDraw.size()-1 ){
			
			ReferencePoint vRefPoint1 = mRefPointMap.get(vLinesToDraw.get(i++));
			ReferencePoint vRefPoint2 = mRefPointMap.get(vLinesToDraw.get(i++));
			g2d.drawLine( (int)( width * vRefPoint1.getPosition().getX()),
					(int)( -height * vRefPoint1.getPosition().getY()),
					(int)( width * vRefPoint2.getPosition().getX()),
					(int)( -height * vRefPoint2.getPosition().getY()) );
			
		}
		
		// draw center circle
		if( mRefPointMap.containsKey(ReferencePointName.BlueGoalCornerTop)
				&& mRefPointMap.containsKey(ReferencePointName.BlueGoalCornerBottom)
				&& mRefPointMap.containsKey(ReferencePointName.FieldCenter)){
			
			ServerPoint vTop = mRefPointMap.get(ReferencePointName.BlueGoalCornerTop).getPosition();
			ServerPoint vBottom = mRefPointMap.get(ReferencePointName.BlueGoalCornerBottom).getPosition();
			ServerPoint vCenter = mRefPointMap.get(ReferencePointName.FieldCenter).getPosition();
			double centerCircleSize = (vTop.getY() - vBottom.getY()) * 0.75;
			
			g2d.setColor( Color.WHITE );
			g2d.setStroke( new BasicStroke(lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL) );
			g2d.drawArc( (int)(width * (vCenter.getX() - centerCircleSize * 0.5)),
						 (int)(-height * vCenter.getY() - height * centerCircleSize * 0.5),
						 (int)(width * centerCircleSize),
						 (int)(height * centerCircleSize),
						 0, 360);
			
		}
		
	}

	@Override
    public void paintComponent( Graphics g ) {
    	try{
	    	super.paintComponent(g);
	    	drawBackground(g);
	    } catch (Exception e) {
			e.printStackTrace();
		}		
    }  
    
    /**
     * Sets {@link List} of lines to draw
     * @return {@link List} of {@link ReferencePointName}.
     * Every pair of two points are start and end point of line.
     */
    private List<ReferencePointName> getLinesToDraw(){
    	if( mLinesToDraw == null ){
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
	    	
	//    	mLinesToDraw.add( ReferencePointName. );
	//    	mLinesToDraw.add( ReferencePointName. );
    	}
    	
    	return mLinesToDraw;
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
