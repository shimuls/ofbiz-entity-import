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

import org.ofbiz.base.util.UtilValidate;

/**
 * The factory which produce instances of EntityFileReader
 * @author Liang Zhang
 */
public class EntityFileReaderFactory {
	
	private static final EntityFileReaderFactory INSTANCE = new EntityFileReaderFactory();
	
	private EntityFileReaderFactory() {};
	
	private XlsEntityFileReader xlsReader = new XlsEntityFileReader();
	
	private XmlEntityFileReader xmlReader = new XmlEntityFileReader();
	
	private static final String TYPE_XLS = "XLS";
	private static final String TYPE_XML = "XML";
	
	public static EntityFileReaderFactory getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Get reader by type
	 * @param type - supported type XML/XLS
	 * @return reader
	 */
	public EntityFileReader getReader(String type) {
		if(UtilValidate.isEmpty(type)) {
			return null;
		}
		
		String typeUppered = type.toUpperCase();
		
		if(TYPE_XLS.equals(typeUppered)) {
			return this.xlsReader;
		}
		
		if(TYPE_XML.equals(typeUppered)) {
			return this.xmlReader;
		}
		
		return null;
	}

}
