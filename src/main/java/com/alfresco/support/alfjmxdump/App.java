package com.alfresco.support.alfjmxdump;

import java.io.IOException;

/**
 * @author hseritt
 * @version 0.0.1
 * @since 12/28/2016
 * */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	
    	AlfJmxDump app;
    	
    	app = new AlfJmxDump(args);
    	app.run();
    	
    }
}
