package de.fh_kiel.robotics.mr.server.misc.network.handshake.client;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "movement_mode")
@XmlEnum
public enum MovementMode {
	
	@XmlEnumValue("wheel_velocities") WheelVelocities

}
