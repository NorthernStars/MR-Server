package mrservermisc.network.data.worlddata;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "team")
@XmlEnum
public enum Team {

	@XmlEnumValue("yellow") Yellow,
	@XmlEnumValue("blue") Blue;
	
}
