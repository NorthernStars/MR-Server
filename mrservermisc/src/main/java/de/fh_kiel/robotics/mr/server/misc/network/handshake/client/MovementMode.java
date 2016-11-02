package de.fh_kiel.robotics.mr.server.misc.network.handshake.client;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "movement_mode")
@XmlEnum
public enum MovementMode {
	
	@XmlEnumValue("wheel_velocities") WheelVelocities

}
