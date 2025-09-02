package de.fh_kiel.robotics.mr.server.misc.network.data.visionmode;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

import de.fh_kiel.robotics.mr.server.misc.network.data.position.VisionMode;
import de.fh_kiel.robotics.mr.server.misc.network.xml.Helpers;

@XmlRootElement(name="changevisionmode")
public class ChangeVisionMode {
	
	@XmlElement(name="visionmode")
	VisionMode mVisionMode;
	
	public ChangeVisionMode(){}

	public ChangeVisionMode( VisionMode aVisionMode ){
		
		mVisionMode = aVisionMode;
		
	}
	
	public String toXMLString(){
		
		return Helpers.marshallXMLString( this, ChangeVisionMode.class );
		
	}
	
	@Override
	public String toString() {
		return "VisionDataPackage [mVisionMode=" + mVisionMode + "]";
	}
	
	
	public static ChangeVisionMode unmarshallXMLChangeVisionModeString( String aXMLChangeVisionMode ){
			
		return Helpers.unmarshallXMLString( aXMLChangeVisionMode, ChangeVisionMode.class );
		
	}

	@XmlTransient
	public VisionMode getVisionMode() {
		
		return mVisionMode;
		
	}

	public void setVisionMode( VisionMode aVisionMode ) {
		
		mVisionMode = aVisionMode;
		
	}
}
