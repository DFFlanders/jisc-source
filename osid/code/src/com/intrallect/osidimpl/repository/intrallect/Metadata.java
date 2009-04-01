/*
 Copyright (c) 2007, Birkbeck College Library
 All rights reserved.
 
 Redistribution and use in source and binary forms, with or without modification, are permitted provided 
 that the following conditions are met:
 
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the 
 following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and 
 the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name of the University of London, Birkbeck College nor the names of its contributors may 
 be used to endorse or promote products derived from this software without specific prior written permission.
 
 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, 
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
 SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR 
											   SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE 
 USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.intrallect.osidimpl.repository.intralibrary;

public class Metadata
{
	java.util.Vector _assetVector = new java.util.Vector();
		
	public java.util.Vector getAssets()
	{
		return _assetVector;
	}

	public Metadata(String xml)
	{
		try {
			_assetVector.removeAllElements();
			javax.xml.parsers.DocumentBuilderFactory dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
			javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();
			org.w3c.dom.Document document = db.parse(new java.io.ByteArrayInputStream(xml.getBytes()));		
			
			// extract DC content
			org.w3c.dom.NodeList recordsNodeList = document.getElementsByTagName("SRW:records");
			if (recordsNodeList.getLength() > 0) {
				org.w3c.dom.Element recordsElement = (org.w3c.dom.Element)recordsNodeList.item(0);
				
				org.w3c.dom.NodeList recordNodeList = document.getElementsByTagName("SRW:record");
				int numRecords = recordNodeList.getLength();
				for (int i=0; i < numRecords; i++) {
					org.w3c.dom.Element recordElement = (org.w3c.dom.Element)recordNodeList.item(i);
					
					String contributor = null;
					String coverage = null;
					String creator = null;
					String date = null;
					String description = null;
					String format = null;
					String identifier = null;
					String language = null;
					String publisher = null;
					String relation = null;
					String rights = null;
					String source = null;
					String subject = null;
					String title = null;
					String type = null;
					String url = null;
					String idString = null;
					
					org.w3c.dom.NodeList recordDataNodeList = document.getElementsByTagName("SRW:recordData");
					if (recordDataNodeList.getLength() > 0) {
						org.w3c.dom.Element recordDataElement = (org.w3c.dom.Element)recordDataNodeList.item(0);
						contributor = getElementValue(recordDataElement,"dc:contributor");
						coverage = getElementValue(recordDataElement,"dc:coverage");
						creator = getElementValue(recordDataElement,"dc:creator");
						date = getElementValue(recordDataElement,"dc:date");
						description = getElementValue(recordDataElement,"dc:description");
						format = getElementValue(recordDataElement,"dc:format");
						identifier = getElementValue(recordDataElement,"dc:identifier");
						language = getElementValue(recordDataElement,"dc:language");
						publisher = getElementValue(recordDataElement,"dc:publisher");
						relation = getElementValue(recordDataElement,"dc:relation");
						rights = getElementValue(recordDataElement,"dc:rights");
						source = getElementValue(recordDataElement,"dc:source");
						subject = getElementValue(recordDataElement,"dc:subject");
						title = getElementValue(recordDataElement,"dc:title");
						type = getElementValue(recordDataElement,"dc:type");
					}
				
					idString = identifier;
					if (idString == null) {
						idString = "UnknownIdentifier";
					}
					org.w3c.dom.NodeList extraRecordDataNodeList = document.getElementsByTagName("SRW:extraRecordData");
					if (extraRecordDataNodeList.getLength() > 0) {
						org.w3c.dom.Element extraRecordDataElement = (org.w3c.dom.Element)extraRecordDataNodeList.item(0);
						url = getElementValue(extraRecordDataElement,"package:packageDownloadLocator");
					}

					org.osid.repository.Asset asset = new Asset(idString,
																contributor,
																coverage,
																creator,
																date,
																description,
																format,
																identifier,
																language,
																publisher,
																relation,
																rights,
																source,
																subject,
																title,
																type,
																url);
					_assetVector.addElement(asset);
				}
			}
		} catch (Throwable t) {
			Utilities.log(t);
		}
	}
		
	private String getElementValue(org.w3c.dom.Element element, String tag)
	{
		try {
			org.w3c.dom.NodeList nodeList = element.getElementsByTagName(tag);
			if (nodeList.getLength() > 0) {
				org.w3c.dom.Element e = (org.w3c.dom.Element)nodeList.item(0);
				if (e.hasChildNodes()) {
					String value = e.getFirstChild().getNodeValue();
					return value;
				}
			}
		} catch (Throwable t) {
		}
		return null;
	}
}