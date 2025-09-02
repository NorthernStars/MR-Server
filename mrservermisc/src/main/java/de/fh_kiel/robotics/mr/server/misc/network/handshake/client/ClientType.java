package de.fh_kiel.robotics.mr.server.misc.network.handshake.client;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "protocol_version")
@XmlEnum
public enum ClientType {

	@XmlEnumValue("Client") Client
}
