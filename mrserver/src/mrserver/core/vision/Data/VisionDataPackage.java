package mrserver.core.vision.Data;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import mrserver.core.vision.VisionMode;

@XmlRootElement(name="VisionDataPackage")
public class VisionDataPackage {
	
	@XmlElement(name="visionmode")
	public VisionMode mVisionMode;
	@XmlElement(name="visionobjects")
	public List<VisionObject> mListOfVisionObjects;
	
	public static VisionDataPackage unmarshallXMLVisionDataPackageString( String aXMLVisionDataPackage ){
		
		StringReader vXMLDataStream = new StringReader( aXMLVisionDataPackage );
		JAXBContext jc;
		try {
			jc = JAXBContext.newInstance( mrserver.core.vision.Data.VisionDataPackage.class, 
					mrserver.core.vision.Data.VisionObject.class,
					mrserver.core.vision.Data.VisionObjectBot.class, 
					mrserver.core.vision.Data.VisionObjectRectangle.class );

			Unmarshaller u = jc.createUnmarshaller();
			return (VisionDataPackage) u.unmarshal( vXMLDataStream );
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public String toXMLString(){
	    
		StringWriter vXMLDataStream = new StringWriter();		
		JAXBContext jc;
		try {
			jc = JAXBContext.newInstance( mrserver.core.vision.Data.VisionDataPackage.class, 
					mrserver.core.vision.Data.VisionObject.class,
					mrserver.core.vision.Data.VisionObjectBot.class, 
					mrserver.core.vision.Data.VisionObjectRectangle.class );
		
	        Marshaller m = jc.createMarshaller();
	        m.marshal( this, vXMLDataStream );
	        
		} catch ( JAXBException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String vXMLDataString = vXMLDataStream.toString();
		
		return vXMLDataString;
		
	}
	
	@Override
	public String toString() {
		return "VisionDataPackage [mVisionMode=" + mVisionMode
				+ ", mListOfVisionObjects=" + mListOfVisionObjects + "]";
	}
	
}

/*

	        SchemaOutputResolver sor = new MySchemaOutputResolver();
	        jc.generateSchema(sor);
	        
	        
	public class MySchemaOutputResolver extends SchemaOutputResolver {

	    public Result createOutput(String namespaceURI, String suggestedFileName) throws IOException {
	        File file = new File(suggestedFileName);
	        StreamResult result = new StreamResult(file);
	        result.setSystemId(file.toURI().toURL().toString());
	        return result;
	    }

	}

 
*/