package com.alfresco.support.alfjmxdump;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

/*
 * Todo
 */
public class AlfJmxDump {
	
	String[] args;
	
	public AlfJmxDump(String[] args) {
		this.args = args;
	}
	
	protected static void showUsage() {
		System.out.println("Usage: ");
		System.out.println(" java -jar alfjmxdump.jar [jmx dump file] [option]");
		System.out.println("");
		System.out.println("Available Options: ");
		System.out.println(" auth|sync:    Authentication and synchronization settings.");
		System.out.println(" cache:        Cache settings.");
		System.out.println(" metadata:     General Alfresco settings, JVM settings, machine, etc.");
		System.out.println(" search|index: Index settings (i.e. Lucene/Solr).");
		System.exit(0);
		
	}
	
	public void run() throws IOException {
		
		String fileName = "";
		String option = "";
    	File inputFile;
    	BufferedReader reader = null;
    	ArrayList<String> lines = new ArrayList<String>();
    	String line;
    	
    	// Test for file name input by user.
    	try {
    		fileName = args[0];
    		option = args[1];
    		
    	} catch (ArrayIndexOutOfBoundsException e) {
    		showUsage();
    	}
    	
    	// Test that the actual file exists.
    	inputFile = new File(fileName);
    	
    	// Read the file's contents.
    	try {
    		
			reader = new BufferedReader(new FileReader(inputFile));
						
			while ( ( line = reader.readLine() ) != null ) {
				lines.add(line);
			}
			
    	} catch (FileNotFoundException e) {
			
    		System.out.println("File not found: " + inputFile.getPath());
			e.printStackTrace();
			System.exit(1);
		
    	} finally {
    		
    		reader.close();
    		
    	}
    	
    	JmxDumpHandler handler = new JmxDumpHandler(option, lines);
    	HashMap<String,String> settings = handler.getSettings();
    	SortedSet<String> keys = new TreeSet<String>(settings.keySet());
    	
    	for (String key : keys) {
    		System.out.println(key + ":\t" + settings.get(key));
    	}
    	
	}
}
