package com.alfresco.support.alfjmxdump;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JmxDumpHandler {
	
	private String option;
	private ArrayList<String> fileLines;
	
	public JmxDumpHandler(String option, ArrayList<String> fileLines) {
		this.option = option;
		this.fileLines = fileLines;
	}
	
	public HashMap<String,String> getTemplate() {
		HashMap<String,String> template = new HashMap<String,String>();
		
		if (option.equals("metadata")) {
			
			template.put("Alfresco Version", 			"module.version");
			template.put("JVM Version", 				"java.runtime.version  ");
			template.put("OS Name", 					"os.name  ");
			template.put("OS Version", 					"os.version  ");
			template.put("Database Vendor", 			"DatabaseProductName");
			template.put("Database Version",  			"DatabaseProductVersion");
			template.put("Physical Memory", 			"TotalPhysicalMemorySize");
			template.put("JVM Max Heap",				"Xmx");
			template.put("JVM Initial Heap", 			"Xms");
			template.put("Alfresco Home", 				"-Dalfresco.home=");
			template.put("Tomcat Home",					"-Dcatalina.home=");
			template.put("JVM DisableExplicitGC", 		"-XX:+DisableExplicitGC");
			template.put("JVM UseConcMarkSweepGC",   	"-XX:+UseConcMarkSweepGC");
			template.put("JVM UseParNewGC", 			"-XX:+UseParNewGC");
		
		}
		
		else if (option.equals("sync") || (option.equals("auth"))) {
			
			for (String line : fileLines) {
				if (
						line.startsWith( "ldap." ) 	|| 
						line.startsWith( "synchronization." ) 		|| 
						line.startsWith( "authentication." )			||
						line.startsWith( "alfresco.authentication" )  ||
						line.startsWith( "ntlm.authentication" )      ||
						line.startsWith( "kerberos." )
					) 
				{
					template.put(line.split(" ")[0], line.split(" ")[0]);
				}				
			}
		}
		
		else if (option.equals("search") || (option.equals("index"))) {
			
			for (String line: fileLines) {
				if (
						line.startsWith("index.") ||
						line.startsWith("solr.") ||
						line.startsWith("lucene.") ||
						line.startsWith("fts.") ||
						line.startsWith("tracker.")
				   )
				{
					template.put(line.split(" ")[0], line.split(" ")[0]);
				}
			}
		}
		
		else if (option.equals("cache")) {
			
			for (String line : fileLines) {
				if (
						line.startsWith("cache.")
				   )
				{
					template.put(line.split(" ")[0],  line.split(" ")[0]);
				}
			}
		}
		
		else {
			AlfJmxDump.showUsage();
		}
		
		return template;
	}
	
	public HashMap<String,String> getSettings() {
		
		HashMap<String,String> settingsList = new HashMap<String,String>();
		HashMap<String,String> template = getTemplate();
		
		for (Map.Entry<String, String> entry : template.entrySet()) {
			
			for (String line : fileLines) {
				if (line.contains(entry.getValue())) {
					try {
						settingsList.put(entry.getKey(), line.split(entry.getValue())[1].trim());
					} catch (ArrayIndexOutOfBoundsException e) {
						settingsList.put(entry.getKey(), "true");
					}
				}
			}
			
		}
		
		return settingsList;
	}

}
