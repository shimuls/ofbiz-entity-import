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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import jxl.read.biff.BiffException;

/**
 * The interface reading entities from a file
 * @author Liang Zhang
 */
public interface EntityFileReader {
	
	/**
	 * Read entities in the file 
	 * @param file - data file which contains entities
	 * @return entities
	 * @throws IOException 
	 * @throws BiffException 
	 */
	public String getEntitiesInXml(File file) throws Exception;
	
	/**
	 * Read entities in the file 
	 * @param is - data file input stream which contains entities
	 * @return entities
	 * @throws IOException 
	 * @throws BiffException 
	 */
	public String getEntitiesInXml(InputStream is) throws Exception;
	
	/**
	 * Read entities in the file 
	 * @param byteBuffer - data file byte buffer which contains entities
	 * @return entities
	 * @throws IOException 
	 * @throws BiffException 
	 */
	public String getEntitiesInXml(ByteBuffer byteBuffer) throws Exception;
}
