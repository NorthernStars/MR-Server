package mrscenariofootball.core.data.worlddata.server;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "pointtype")
@XmlEnum
public enum ReferencePointName {
	
	@XmlEnumValue("nofixedname") NoFixedName( -1, -1, -1 ),
    @XmlEnumValue("player") Player( -2, -1, -1),
    @XmlEnumValue("ball") Ball( -3, -1, -1 ),
	
    @XmlEnumValue("bottom_center") CenterLineBottom( 0, 0.5, 0.08 ),
    @XmlEnumValue("bottom_left_corner") YellowFieldCornerBottom( 1, 0.04, 0.08 ),
    @XmlEnumValue("bottom_left_goal") YellowPenaltyAreaFrontBottom( 2, 0.1918, 0.269 ),
    @XmlEnumValue("bottom_left_pole") YellowGoalCornerBottom( 3, 0.04, 0.4118 ),
    @XmlEnumValue("bottom_left_small_area") YellowGoalAreaFrontBottom( 4, 0.0906, 0.4118 ),
    @XmlEnumValue("bottom_right_corner") BlueFieldCornerBottom( 5, 0.96, 0.08 ),
    @XmlEnumValue("bottom_right_goal") BluePenaltyAreaFrontBottom( 6, 0.8082, 0.269 ),
    @XmlEnumValue("bottom_right_pole") BlueGoalCornerBottom( 7, 0.96, 0.4118 ),
    @XmlEnumValue("bottom_right_small_area") BlueGoalAreaFrontBottom( 8, 0.9094, 0.4118 ),
    @XmlEnumValue("middle_center") FieldCenter( 9, 0.5, 0.5 ),
    @XmlEnumValue("top_center") CenterLineTop( 10, 0.5, 0.92 ),
    @XmlEnumValue("top_left_corner") YellowFieldCornerTop( 11, 0.04, 0.92 ),
    @XmlEnumValue("top_left_goal") YellowPenaltyAreaFrontTop( 12, 0.1918, 0.731 ),
    @XmlEnumValue("top_left_pole") YellowGoalCornerTop( 13, 0.04, 0.5882 ),
    @XmlEnumValue("top_left_small_area") YellowGoalAreaFrontTop( 14, 0.0906, 0.5882 ),
    @XmlEnumValue("top_right_corner") BlueFieldCornerTop( 15, 0.96, 0.92 ),
    @XmlEnumValue("top_right_goal") BluePenaltyAreaFrontTop( 16, 0.8082, 0.731 ),
    @XmlEnumValue("top_right_pole") BlueGoalCornerTop( 17, 0.96, 0.5882 ),
    @XmlEnumValue("top_right_small_area") BlueGoalAreaFrontTop( 18, 0.9094, 0.5882 );
    
    private final int mId;
	private ServerPoint mRelativePosition;
	private String mShortName = "";
    
    private ReferencePointName( int aId , double aRelativeX, double aRelativeY) {

        mId = aId;
        mRelativePosition = new ServerPoint( aRelativeX, aRelativeY );
        for( int vCharCounter = 0; vCharCounter < name().length(); vCharCounter++){
           if( Character.isUpperCase( name().charAt(vCharCounter) )){
        	   mShortName += name().charAt(vCharCounter);
           }
        }
        
    }

    public int getId(){
        
        return mId;
        
    }

	public ServerPoint getRelativePosition() {

		return mRelativePosition;
		
	}
	
	public String getShortName(){
		
		return mShortName;
		
		
	}
    
}