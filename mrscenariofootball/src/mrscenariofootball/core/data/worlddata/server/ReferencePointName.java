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
	
    @XmlEnumValue("bottom_center") CenterLineBottom( 0, 0.5, 0 ),
    @XmlEnumValue("bottom_left_corner") YellowFieldCornerBottom( 1, 0, 0 ),
    @XmlEnumValue("bottom_left_goal") YellowPenaltyAreaFrontBottom( 2, 0.165, 0.225 ),
    @XmlEnumValue("bottom_left_pole") YellowGoalCornerBottom( 3, 0, 0.5 ),
    @XmlEnumValue("bottom_left_small_area") YellowGoalAreaFrontBottom( 4, 0.055, 0.395 ),
    @XmlEnumValue("bottom_right_corner") BlueFieldCornerBottom( 5, 1, 1 ),
    @XmlEnumValue("bottom_right_goal") BluePenaltyAreaFrontBottom( 6, 0.835, 0.225 ),
    @XmlEnumValue("bottom_right_pole") BlueGoalCornerBottom( 7, 1, 0.55 ),
    @XmlEnumValue("bottom_right_small_area") BlueGoalAreaFrontBottom( 8, 0.945, 0.395 ),
    @XmlEnumValue("middle_center") FieldCenter( 9, 0.5, 0.5 ),
    @XmlEnumValue("top_center") CenterLineTop( 10, 0.5, 1 ),
    @XmlEnumValue("top_left_corner") YellowFieldCornerTop( 11, 0, 1 ),
    @XmlEnumValue("top_left_goal") YellowPenaltyAreaFrontTop( 12, 0.165, 0.775 ),
    @XmlEnumValue("top_left_pole") YellowGoalCornerTop( 13, 0, 0.45 ),
    @XmlEnumValue("top_left_small_area") YellowGoalAreaFrontTop( 14, 0.055, 0.605),
    @XmlEnumValue("top_right_corner") BlueFieldCornerTop( 15, 1, 1 ),
    @XmlEnumValue("top_right_goal") BluePenaltyAreaFrontTop( 16, 0.835, 0.775 ),
    @XmlEnumValue("top_right_pole") BlueGoalCornerTop( 17, 1, 0.55 ),
    @XmlEnumValue("top_right_small_area") BlueGoalAreaFrontTop( 18, 0.945, 0.605 );
    
    private final int mId;
	private ServerPoint mRelativePosition;
    
    private ReferencePointName( int aId , double aRelativeX, double aRelativeY) {

        mId = aId;
        mRelativePosition = new ServerPoint( aRelativeX, aRelativeY );
        
    }

    public int getId(){
        
        return mId;
        
    }

	public ServerPoint getRelativePosition() {

		return mRelativePosition;
		
	}
    
}