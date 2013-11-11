package mrservermisc.network.data.visionmode;

import java.beans.Transient;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import mrservermisc.network.data.position.VisionMode;
import mrservermisc.network.xml.Helpers;

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

	@Transient
	public VisionMode getVisionMode() {
		
		return mVisionMode;
		
	}

	public void setVisionMode(VisionMode aVisionMode) {
		
		mVisionMode = aVisionMode;
		
	}
}
