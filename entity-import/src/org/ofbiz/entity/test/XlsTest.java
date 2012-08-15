/**
 * 
 */
package org.ofbiz.entity.test;

import java.io.File;
import java.io.IOException;

import jxl.read.biff.BiffException;

import org.ofbiz.entity.XlsEntityFileReader;

/**
 * @author sbrzhang
 *
 */
public class XlsTest {

	public static void main(String argv []) throws BiffException, IOException {
		XlsEntityFileReader r = new XlsEntityFileReader();
		String str = r.getEntitiesInXml(new File("c:/Book1.xls"));
		System.out.println(str);
	}
	
}
