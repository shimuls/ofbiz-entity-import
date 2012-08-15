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
package org.ofbiz.entity.service;

import java.nio.ByteBuffer;
import java.util.Map;

import javolution.util.FastMap;

import org.ofbiz.entity.EntityFileReader;
import org.ofbiz.entity.EntityFileReaderFactory;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.ServiceUtil;

public class EntityImportServices {

	public static Map<String, Object> entityImport(DispatchContext dctx, Map<String, ? extends Object> context) {
		GenericValue userLogin = (GenericValue) context.get("userLogin");
		ByteBuffer byteBuffer = (ByteBuffer) context.get("uploadedFile");
		String fileName = (String) context.get("_uploadedFile_fileName");
		String type = fileName.substring(fileName.lastIndexOf(".") + 1);
		EntityFileReader reader = EntityFileReaderFactory.getInstance().getReader(type);
		
		if(reader == null) {
			return ServiceUtil.returnError("No matched reader found for file [" + fileName + "]");
		}
		
		String xml = null;
		try {
			xml = reader.getEntitiesInXml(byteBuffer);
		} catch (Exception e) {
			e.printStackTrace();
			return ServiceUtil.returnError(e.getMessage());
		}
		
		Map<String, Object> params = FastMap.<String, Object>newInstance();
		params.put("xmltext", xml);
		params.put("userLogin", userLogin);
		Map<String, Object> result = null;
		
		try {
			result = dctx.getDispatcher().runSync("parseEntityXmlFile", params);
		} catch (GenericServiceException e) {
			e.printStackTrace();
			return ServiceUtil.returnError(e.getMessage());
		}
		
		return result;
	}
	
}
