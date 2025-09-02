package de.fh_kiel.robotics.mr.scenario.football.core.data.worlddata.server;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "team")
@XmlEnum
public enum Team {

	@XmlEnumValue("yellow") Yellow,
	@XmlEnumValue("blue") Blue,
	@XmlEnumValue("none") None;
	
}
