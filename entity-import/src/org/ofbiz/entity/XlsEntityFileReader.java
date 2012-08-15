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
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Map;

import javolution.util.FastMap;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.ofbiz.base.util.UtilValidate;

/**
 * Read entities from a xls file
 * @author Liang Zhang
 */
public class XlsEntityFileReader implements EntityFileReader {

	private Document doc;
	private Element root;
	
	@Override
	public String getEntitiesInXml(File file) throws BiffException, IOException {
		InputStream is = new FileInputStream(file);
		return this.getEntitiesInXml(is);
	}
	
	@Override
	public String getEntitiesInXml(ByteBuffer byteBuffer) throws BiffException, IOException {
	    byte[] inputByteArray = byteBuffer.array();
	    InputStream is = new ByteArrayInputStream(inputByteArray);
		return this.getEntitiesInXml(is);
	}
	
	@Override
	public String getEntitiesInXml(InputStream is) throws BiffException, IOException {
		this.doc = DocumentFactory.getInstance().createDocument("UTF-8");
		this.root = this.doc.addElement("entity-engine-xml");
		
		Workbook workbook = Workbook.getWorkbook(is); 
		Sheet[] sheets = workbook.getSheets();
		
		for(Sheet sheet : sheets) {
			getEntities(sheet);	
		}
		return doc.asXML();
	}
	
	private void getEntities(Sheet sheet) {
		String entityName = sheet.getName();
		
		int colNum = sheet.getColumns();
		int rowNum = sheet.getRows();
		
		Map<Integer, String> nameMap = FastMap.<Integer, String>newInstance();
		
		for(int i=0; i<colNum; i++) {
			String name = sheet.getCell(i, 0).getContents();
			if(UtilValidate.isNotEmpty(name)) {
				nameMap.put(i, name);
			}
		}
		
		String key;
		String value;
		for (int row = 1; row < rowNum; row ++) {
			Element entity = this.root.addElement(entityName);
			for(int col = 0; col < colNum; col ++) {
				key = nameMap.get(col);
				value = sheet.getCell(col, row).getContents();
				entity.addAttribute(key, value);
			}
		}
	}

}
