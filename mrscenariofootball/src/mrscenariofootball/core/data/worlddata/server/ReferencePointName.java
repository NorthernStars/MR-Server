package mrscenariofootball.core.data.worlddata.server;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "id")
@XmlEnum
public enum ReferencePointName {
    
    @XmlEnumValue("nofixedname") NoFixedName( -1 ),
    @XmlEnumValue("bottom_center") CenterLineBottom( 0 ),
    @XmlEnumValue("bottom_left_corner") YellowFieldCornerBottom( 1 ),
    @XmlEnumValue("bottom_left_goal") YellowPenaltyAreaFrontBottom( 2 ),
    @XmlEnumValue("bottom_left_pole") YellowGoalCornerBottom( 3 ),
    @XmlEnumValue("bottom_left_small_area") YellowGoalAreaFrontBottom( 4 ),
    @XmlEnumValue("bottom_right_corner") BlueFieldCornerBottom( 5 ),
    @XmlEnumValue("bottom_right_goal") BluePenaltyAreaFrontBottom( 6 ),
    @XmlEnumValue("bottom_right_pole") BlueGoalCornerBottom( 7 ),
    @XmlEnumValue("bottom_right_small_area") BlueGoalAreaFrontBottom( 8 ),
    @XmlEnumValue("middle_center") FieldCenter( 9 ),
    @XmlEnumValue("top_center") CenterLineTop( 10 ),
    @XmlEnumValue("top_left_corner") YellowFieldCornerTop( 11 ),
    @XmlEnumValue("top_left_goal") YellowPenaltyAreaFrontTop( 12 ),
    @XmlEnumValue("top_left_pole") YellowGoalCornerTop( 13 ),
    @XmlEnumValue("top_left_small_area") YellowGoalAreaFrontTop( 14),
    @XmlEnumValue("top_right_corner") BlueFieldCornerTop( 15 ),
    @XmlEnumValue("top_right_goal") BluePenaltyAreaFrontTop( 16 ),
    @XmlEnumValue("top_right_pole") BlueGoalCornerTop( 17 ),
    @XmlEnumValue("top_right_small_area") BlueGoalAreaFrontTop( 18 ),
    @XmlEnumValue("player") Player( 19 ),
    @XmlEnumValue("ball") Ball( 20 );
    
    private final int mPosition;
    
    private ReferencePointName( int aPosition ) {

        mPosition = aPosition;
        
    }

    public int getPosition(){
        
        return mPosition;
        
    }
    
}