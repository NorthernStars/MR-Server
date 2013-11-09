package mrservermisc.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Loggers {

    private static Logger DATALOGGER = LogManager.getLogger("DATA");
    
    public static Logger getDataLogger(){
        
        return DATALOGGER;
        
    }

    private static Logger NETWORKLOGGER = LogManager.getLogger("NETWORK");
    
    public static Logger getNetworkLogger(){
        
        return NETWORKLOGGER;
        
    }

    private static Logger XMLLOGGER = LogManager.getLogger("XML");
    
    public static Logger getXMLLogger(){
        
        return XMLLOGGER;
        
    }
	
}
