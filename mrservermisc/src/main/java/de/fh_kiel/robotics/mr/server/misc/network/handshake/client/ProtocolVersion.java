package de.fh_kiel.robotics.mr.server.misc.network.handshake.client;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "protocol")
@XmlEnum
public enum ProtocolVersion {

	@XmlEnumValue("1.0") OnePointO
	
}
