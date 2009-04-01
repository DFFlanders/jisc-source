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
package com.tle.osidimpl.repository.equella;

public class AssetIterator
implements org.osid.repository.AssetIterator
{
    private java.util.Iterator iterator = null;
	private static final String RESULT_TAG = "result";
	private static final String XML_TAG = "xml";
	private static final String ITEM_TAG = "item";
	private static final String NAME_TAG = "name";
	private static final String DESCRIPTION_TAG = "description";
	private static final String NEW_ITEM_TAG = "newitem";
	private static final String OWNER_TAG = "owner";
	private static final String DATE_CREATED_TAG = "datecreated";
	private static final String DATE_MODIFIED_TAG = "datemodified";
	private static final String DATE_FOR_INDEX_TAG = "dateforindex";
	private static final String RATING_TAG = "rating";
	private static final String ATTACHMENTS_TAG = "attachments";
	private static final String ITEM_BODY_TAG = "itembody";
	private static final String PACKAGE_FILE_TAG = "packagefile";
	private static final String BAD_URLS_TAG = "badurls";
	private static final String HISTORY_TAG = "history";
	private static final String EDIT_TAG = "edit";
	private static final String STATE_CHANGE_TAG = "statechange";
	private static final String RESET_WORKFLOW_TAG = "resetworkflow";
	private static final String URL_TAG = "url";
	
	private static final String ITEM_KEY_ATTRIBUTE = "key";
	private static final String ITEM_ID_ATTRIBUTE = "id";
	private static final String ITEM_VERSION_ATTRIBUTE = "version";
	private static final String ITEM_STATUS_ATTRIBUTE = "itemstatus";
	
	private static final String RATING_AVERAGE_ATTRIBUTE = "average";
	
	private static final String PACKAGE_FILE_NAME_ATTRIBUTE = "name";
	private static final String PACKAGE_FILE_SIZE_ATTRIBUTE = "size";
	
    public AssetIterator(java.util.Vector vector)
		throws org.osid.repository.RepositoryException
    {
        this.iterator = vector.iterator();
    }
	
    public AssetIterator(String query)
		throws org.osid.repository.RepositoryException
    {
		java.util.Vector resultVector = new java.util.Vector();
		try 
		{
			String xml = Configuration.getInstance().keywordSearch(query);
			//System.out.println("xml " + xml);

			javax.xml.parsers.DocumentBuilderFactory dbf = null;
			javax.xml.parsers.DocumentBuilder db = null;
			org.w3c.dom.Document document = null;
			
			dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			document = db.parse(new java.io.ByteArrayInputStream(xml.toString().getBytes()));
			org.osid.shared.Id recordStructureId = RecordStructure.getInstance().getId();
			
			org.w3c.dom.NodeList results = document.getElementsByTagName(RESULT_TAG);
			int numResults = results.getLength();
			for (int i=0; i < numResults; i++) {
				org.w3c.dom.Element resultElement = (org.w3c.dom.Element)results.item(i);
				
				String assetTitle = null;
				String assetDescription = "";
				String assetId = null;
				org.osid.repository.Asset asset = null;
				org.osid.repository.Record record = null;				
				
				boolean newItem = false;
				String owner = null;
				String dateCreated = null;
				String dateModified = null;
				String dateForIndex = null;
				String ratingAverage = null;
				String packageFileName = null;
				String packageFileSize = null;
				String sizeInKB = null;
				String packageFile = null;
				String url = null;
				
				org.w3c.dom.NodeList xmls = resultElement.getElementsByTagName(XML_TAG);
				int numXmls = xmls.getLength();
				for (int j=0; j < numXmls; j++) {
					org.w3c.dom.Element xmlElement = (org.w3c.dom.Element)xmls.item(j);
					
					org.w3c.dom.NodeList items = xmlElement.getElementsByTagName(ITEM_TAG);
					int numItems = items.getLength();
					for (int k=0; k < numItems; k++) {
						org.w3c.dom.Element itemElement = (org.w3c.dom.Element)items.item(k);
						
						String itemKey = itemElement.getAttribute(ITEM_KEY_ATTRIBUTE);
						//System.out.println("key " + itemKey);
						
						assetId = itemElement.getAttribute(ITEM_ID_ATTRIBUTE);
						System.out.println("id " + assetId);
						
						String itemVersion = itemElement.getAttribute(ITEM_VERSION_ATTRIBUTE);
						//System.out.println("version " + itemVersion);
						
						String itemStatus = itemElement.getAttribute(ITEM_STATUS_ATTRIBUTE);
						//System.out.println("status " + itemStatus);
						
						org.w3c.dom.NodeList nodeList = xmlElement.getElementsByTagName(NAME_TAG);
						int numNodes = nodeList.getLength();
						for (int x=0; x < numNodes; x++) {
							org.w3c.dom.Element e = (org.w3c.dom.Element)nodeList.item(x);
							assetTitle = e.getFirstChild().getNodeValue();
						}
						
						nodeList = xmlElement.getElementsByTagName(DESCRIPTION_TAG);
						numNodes = nodeList.getLength();
						for (int x=0; x < numNodes; x++) {
							org.w3c.dom.Element e = (org.w3c.dom.Element)nodeList.item(x);
							assetDescription = e.getFirstChild().getNodeValue();
						}
						
						nodeList = xmlElement.getElementsByTagName(NEW_ITEM_TAG);
						numNodes = nodeList.getLength();
						for (int x=0; x < numNodes; x++) {
							org.w3c.dom.Element e = (org.w3c.dom.Element)nodeList.item(x);
							String s = e.getFirstChild().getNodeValue();
							newItem = (new Boolean(s)).booleanValue();
						}
						
						nodeList = xmlElement.getElementsByTagName(OWNER_TAG);
						numNodes = nodeList.getLength();
						for (int x=0; x < numNodes; x++) {
							org.w3c.dom.Element e = (org.w3c.dom.Element)nodeList.item(x);
							owner = e.getFirstChild().getNodeValue();
						}
						
						nodeList = xmlElement.getElementsByTagName(DATE_CREATED_TAG);
						numNodes = nodeList.getLength();
						for (int x=0; x < numNodes; x++) {
							org.w3c.dom.Element e = (org.w3c.dom.Element)nodeList.item(x);
							dateCreated = e.getFirstChild().getNodeValue();
						}
						
						nodeList = xmlElement.getElementsByTagName(DATE_MODIFIED_TAG);
						numNodes = nodeList.getLength();
						for (int x=0; x < numNodes; x++) {
							org.w3c.dom.Element e = (org.w3c.dom.Element)nodeList.item(x);
							dateModified = e.getFirstChild().getNodeValue();
						}
						
						nodeList = xmlElement.getElementsByTagName(DATE_FOR_INDEX_TAG);
						numNodes = nodeList.getLength();
						for (int x=0; x < numNodes; x++) {
							org.w3c.dom.Element e = (org.w3c.dom.Element)nodeList.item(x);
							dateForIndex = e.getFirstChild().getNodeValue();
						}
						
						nodeList = xmlElement.getElementsByTagName(RATING_TAG);
						numNodes = nodeList.getLength();
						for (int x=0; x < numNodes; x++) {
							org.w3c.dom.Element e = (org.w3c.dom.Element)nodeList.item(x);
							ratingAverage = e.getAttribute(RATING_AVERAGE_ATTRIBUTE);
						}
						
						nodeList = xmlElement.getElementsByTagName(ITEM_BODY_TAG);
						numNodes = nodeList.getLength();
						for (int x=0; x < numNodes; x++) {
							org.w3c.dom.Element e = (org.w3c.dom.Element)nodeList.item(x);
							org.w3c.dom.NodeList nl = e.getElementsByTagName(PACKAGE_FILE_TAG);
							int numPackageFileNodes = nl.getLength();
							for (int y=0; y < numPackageFileNodes; y++) {
								org.w3c.dom.Element packageFileElement = (org.w3c.dom.Element)nl.item(y);
								packageFile = packageFileElement.getFirstChild().getNodeValue();
								packageFileName = packageFileElement.getAttribute(PACKAGE_FILE_NAME_ATTRIBUTE);
								packageFileSize = packageFileElement.getAttribute(PACKAGE_FILE_SIZE_ATTRIBUTE);
								if (packageFileSize != null) {
									Long l = (new Long(packageFileSize)).longValue();
									l = l / 1024;
									sizeInKB = l.toString();
								}
							}
						}
						
						nodeList = xmlElement.getElementsByTagName(URL_TAG);
						numNodes = nodeList.getLength();
						for (int x=0; x < numNodes; x++) {
							org.w3c.dom.Element e = (org.w3c.dom.Element)nodeList.item(x);
							url = e.getFirstChild().getNodeValue();
						}
						/*
						System.out.println("assetTitle " + assetTitle);
						System.out.println("assetDescription " + assetDescription);
						System.out.println("assetId " + assetId);
						System.out.println("new item " + newItem);
						System.out.println("owner " + owner);
						System.out.println("date created " + dateCreated);
						System.out.println("date modified " + dateModified);
						System.out.println("date for index " + dateForIndex);
						System.out.println("rating average " + ratingAverage);
						System.out.println("package file name " + packageFileName);
						System.out.println("package file size " + packageFileSize);
						System.out.println("package file " + packageFile);
						System.out.println("url " + url);
						*/
						if ((assetTitle != null) && (assetId != null))
						{
							asset = new Asset(assetTitle,assetDescription,assetId);
							record = asset.createRecord(recordStructureId);
							resultVector.addElement(asset);
							record.createPart(TitlePartStructure.getInstance().getId(),assetTitle);
							if (assetDescription != null) record.createPart(DescriptionPartStructure.getInstance().getId(),assetDescription);
							record.createPart(NewItemPartStructure.getInstance().getId(),newItem);
							if (owner != null) record.createPart(OwnerIdPartStructure.getInstance().getId(),owner);
							if (dateCreated != null) record.createPart(DateCreatedPartStructure.getInstance().getId(),dateCreated);
							if (dateModified != null) record.createPart(DateModifiedPartStructure.getInstance().getId(),dateModified);
							if (dateForIndex != null) {
								record.createPart(DateForIndexPartStructure.getInstance().getId(),dateForIndex);
								record.createPart(DatePartStructure.getInstance().getId(),dateForIndex);
							}
							if (packageFileName != null) record.createPart(RatingAvgPartStructure.getInstance().getId(),ratingAverage);
							if (packageFileName != null) record.createPart(FileDisplayNamePartStructure.getInstance().getId(),packageFileName);
							if (packageFileSize != null) {
								record.createPart(FileSizePartStructure.getInstance().getId(),packageFileSize);
								record.createPart(SizeInKBPartStructure.getInstance().getId(),sizeInKB);
							}
							if (packageFile != null) record.createPart(FileNamePartStructure.getInstance().getId(),packageFile);
							if (url != null) record.createPart(URLPartStructure.getInstance().getId(),url);
						}
					}
				}
			}				
		}
		catch (Throwable t)
		{
			Utilities.log(t);
			throw new org.osid.repository.RepositoryException(org.osid.OsidException.OPERATION_FAILED);
		}		
        this.iterator = resultVector.iterator();
    }
	
    public boolean hasNextAsset()
		throws org.osid.repository.RepositoryException
    {
        return iterator.hasNext();
    }
	
    public org.osid.repository.Asset nextAsset()
		throws org.osid.repository.RepositoryException
    {
        if (iterator.hasNext())
        {
            return (org.osid.repository.Asset)iterator.next();
        }
        else
        {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NO_MORE_ITERATOR_ELEMENTS);
        }
    }
	/**
		<p>MIT O.K.I&#46; SID Implementation License.
	 <p>	<b>Copyright and license statement:</b>
	 </p>  <p>	Copyright &copy; 2003 Massachusetts Institute of
	 Technology &lt;or copyright holder&gt;
	 </p>  <p>	This work is being provided by the copyright holder(s)
	 subject to the terms of the O.K.I&#46; SID Implementation
	 License. By obtaining, using and/or copying this Work,
	 you agree that you have read, understand, and will comply
	 with the O.K.I&#46; SID Implementation License. 
	 </p>  <p>	THE WORK IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
	 KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
	 THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
	 PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
	 MASSACHUSETTS INSTITUTE OF TECHNOLOGY, THE AUTHORS, OR
	 COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
	 OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
	 OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
	 THE WORK OR THE USE OR OTHER DEALINGS IN THE WORK.
	 </p>  <p>	<b>O.K.I&#46; SID Implementation License</b>
	 </p>  <p>	This work (the &ldquo;Work&rdquo;), including software,
	 documents, or other items related to O.K.I&#46; SID
	 implementations, is being provided by the copyright
	 holder(s) subject to the terms of the O.K.I&#46; SID
	 Implementation License. By obtaining, using and/or
	 copying this Work, you agree that you have read,
	 understand, and will comply with the following terms and
	 conditions of the O.K.I&#46; SID Implementation License:
	 </p>  <p>	Permission to use, copy, modify, and distribute this Work
	 and its documentation, with or without modification, for
	 any purpose and without fee or royalty is hereby granted,
	 provided that you include the following on ALL copies of
	 the Work or portions thereof, including modifications or
	 derivatives, that you make:
	 </p>  <ul>	<li>	  The full text of the O.K.I&#46; SID Implementation
	 License in a location viewable to users of the
	 redistributed or derivative work.
	 </li>  </ul>  <ul>	<li>	  Any pre-existing intellectual property disclaimers,
	 notices, or terms and conditions. If none exist, a
	 short notice similar to the following should be used
	 within the body of any redistributed or derivative
	 Work: &ldquo;Copyright &copy; 2003 Massachusetts
	 Institute of Technology. All Rights Reserved.&rdquo;
	 </li>  </ul>  <ul>	<li>	  Notice of any changes or modifications to the
	 O.K.I&#46; Work, including the date the changes were
	 made. Any modified software must be distributed in such
	 as manner as to avoid any confusion with the original
	 O.K.I&#46; Work.
	 </li>  </ul>  <p>	THE WORK IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
	 KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
	 THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
	 PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
	 MASSACHUSETTS INSTITUTE OF TECHNOLOGY, THE AUTHORS, OR
	 COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
	 OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
	 OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
	 THE WORK OR THE USE OR OTHER DEALINGS IN THE WORK.
	 </p>  <p>	The name and trademarks of copyright holder(s) and/or
	 O.K.I&#46; may NOT be used in advertising or publicity
	 pertaining to the Work without specific, written prior
	 permission. Title to copyright in the Work and any
	 associated documentation will at all times remain with
	 the copyright holders.
	 </p>  <p>	The export of software employing encryption technology
	 may require a specific license from the United States
	 Government. It is the responsibility of any person or
	 organization contemplating export to obtain such a
	 license before exporting this Work.
	 </p>*/
}
