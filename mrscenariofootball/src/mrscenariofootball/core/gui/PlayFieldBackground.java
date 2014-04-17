package mrscenariofootball.core.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import mrscenariofootball.core.data.ScenarioInformation;
import mrscenariofootball.core.data.worlddata.server.ReferencePoint;
import mrscenariofootball.core.data.worlddata.server.ReferencePointName;
import mrscenariofootball.core.data.worlddata.server.ServerPoint;
import mrscenariofootball.core.data.worlddata.server.WorldData;

public class PlayFieldBackground extends JPanel implements ComponentListener {

	private Map<ReferencePointName , ReferencePoint> mRefPointMap;
	private boolean mPainted = false;
	
	public PlayFieldBackground() {
		setBackground(new Color(34, 139, 34));
		addComponentListener( this );
		
		mRefPointMap = new HashMap<ReferencePointName, ReferencePoint>();
		for( ReferencePoint vPoint : ScenarioInformation.getInstance().getWorldData().copy().getReferencePoints() ){
			mRefPointMap.put(vPoint.getPointName(), vPoint);
		}
		
	}
	
	@Override
    public void paintComponent( Graphics g ) {
    	try{
    		
    		System.out.println("back");
    		
	    	if(mPainted){       

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
				
				// draw background image
				// g2d.drawImage( mBackgroundImage, 0, 0, width, -height, this);
					
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
									(int)(height * (vTop.getY() - vBottom.getY())));				
				}
				
	            g2d.fillRect( (int)100, (int)100, (int)100,(int)100 );
				
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
				
				mPainted = true;
				
	    	}
				
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
    	List<ReferencePointName> vLinesToDraw = new ArrayList<ReferencePointName>();
    	
    	vLinesToDraw.add( ReferencePointName.BlueFieldCornerBottom );
    	vLinesToDraw.add( ReferencePointName.BlueFieldCornerTop );
    	
    	vLinesToDraw.add( ReferencePointName.BlueFieldCornerTop );
    	vLinesToDraw.add( ReferencePointName.YellowFieldCornerTop );
    	
    	vLinesToDraw.add( ReferencePointName.YellowFieldCornerTop );
    	vLinesToDraw.add( ReferencePointName.YellowFieldCornerBottom );
    	
    	vLinesToDraw.add( ReferencePointName.YellowFieldCornerBottom );
    	vLinesToDraw.add( ReferencePointName.BlueFieldCornerBottom ); 
    	
    	vLinesToDraw.add( ReferencePointName.CenterLineTop );
    	vLinesToDraw.add( ReferencePointName.CenterLineBottom );
    	
    	vLinesToDraw.add( ReferencePointName.YellowGoalCornerTop );
    	vLinesToDraw.add( ReferencePointName.YellowGoalAreaFrontTop ); 
    	
    	vLinesToDraw.add( ReferencePointName.YellowGoalAreaFrontTop );
    	vLinesToDraw.add( ReferencePointName.YellowGoalAreaFrontBottom ); 
    	
    	vLinesToDraw.add( ReferencePointName.YellowGoalAreaFrontBottom );
    	vLinesToDraw.add( ReferencePointName.YellowGoalCornerBottom ); 
    	
    	vLinesToDraw.add( ReferencePointName.BlueGoalCornerTop );
    	vLinesToDraw.add( ReferencePointName.BlueGoalAreaFrontTop ); 
    	
    	vLinesToDraw.add( ReferencePointName.BlueGoalAreaFrontTop );
    	vLinesToDraw.add( ReferencePointName.BlueGoalAreaFrontBottom ); 
    	
    	vLinesToDraw.add( ReferencePointName.BlueGoalAreaFrontBottom );
    	vLinesToDraw.add( ReferencePointName.BlueGoalCornerBottom );
    	
    	vLinesToDraw.add( ReferencePointName.BluePenaltyAreaBackTop );
    	vLinesToDraw.add( ReferencePointName.BluePenaltyAreaFrontTop );
    	
    	vLinesToDraw.add( ReferencePointName.BluePenaltyAreaFrontTop );
    	vLinesToDraw.add( ReferencePointName.BluePenaltyAreaFrontBottom ); 
    	
    	vLinesToDraw.add( ReferencePointName.BluePenaltyAreaFrontBottom );
    	vLinesToDraw.add( ReferencePointName.BluePenaltyAreaBackBottom );
    	
    	vLinesToDraw.add( ReferencePointName.YellowGoalAreaFrontBottom );
    	vLinesToDraw.add( ReferencePointName.YellowGoalCornerBottom );
    	
    	vLinesToDraw.add( ReferencePointName.YellowPenaltyAreaBackTop );
    	vLinesToDraw.add( ReferencePointName.YellowPenaltyAreaFrontTop );
    	
    	vLinesToDraw.add( ReferencePointName.YellowPenaltyAreaFrontTop );
    	vLinesToDraw.add( ReferencePointName.YellowPenaltyAreaFrontBottom ); 
    	
    	vLinesToDraw.add( ReferencePointName.YellowPenaltyAreaFrontBottom );
    	vLinesToDraw.add( ReferencePointName.YellowPenaltyAreaBackBottom ); 
    	
//    	vLinesToDraw.add( ReferencePointName. );
//    	vLinesToDraw.add( ReferencePointName. );    	
    	
    	return vLinesToDraw;
    }
    
	@Override
	public void componentResized(ComponentEvent e) {
		
		mPainted = false;
		
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
