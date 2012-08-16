/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.ofbiz.entity;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.Map;

import javolution.util.FastMap;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Read entities from a CSV file
 * @author Liang Zhang
 */
public class CsvEntityFileReader implements EntityFileReader {
	
	private Document doc;
	private Element root;

	@Override
	public String getEntitiesInXml(File file) throws Exception {
		InputStream is = new FileInputStream(file);
		return this.getEntitiesInXml(is);
	}
	
	@Override
	public String getEntitiesInXml(ByteBuffer byteBuffer) throws Exception {
	    byte[] inputByteArray = byteBuffer.array();
	    InputStream is = new ByteArrayInputStream(inputByteArray);
		return this.getEntitiesInXml(is);
	}
	
	@Override
	public String getEntitiesInXml(InputStream is) throws Exception {
		CSVReader reader = new CSVReader(new InputStreamReader(is));
		String entityName = this.readEntityName(reader);
		
		Map<Integer, String> attrNameMap = this.readAttributeNames(reader);
		
		this.doc = DocumentFactory.getInstance().createDocument("UTF-8");
		this.root = this.doc.addElement("entity-engine-xml");
		
	    String [] nextLine;
	    String key;
	    String value;
	    while ((nextLine = reader.readNext()) != null) {
	    	int len = nextLine.length;
	    	Element entity = this.root.addElement(entityName);
	        for(int i = 0; i < len; i ++) {
				key = attrNameMap.get(i);
				value = nextLine[i];
				entity.addAttribute(key, value);
	        }
	    }
	           
		return doc.asXML();
	}
	
	private String readEntityName(CSVReader reader) throws Exception {
		String [] line = reader.readNext();
		if(line == null || line.length ==0) {
			throw new Exception("The entity name in the first line cannot be null");
		}
		return line[0];
	}
	
	private Map<Integer, String> readAttributeNames(CSVReader reader) throws Exception {
		Map<Integer, String> map = FastMap.<Integer, String>newInstance();
		String [] line = reader.readNext();
		if(line == null || line.length ==0) {
			throw new Exception("The entity name in the first line cannot be null");
		}
		int len = line.length;
        for(int i = 0; i < len; i ++) {
        	map.put(i, line[i]);
        }
		return map;
	}

}
