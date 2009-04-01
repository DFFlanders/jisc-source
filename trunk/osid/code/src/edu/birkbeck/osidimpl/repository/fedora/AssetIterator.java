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
											   SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
											   HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE 
 USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package edu.birkbeck.osidimpl.repository.fedora;

/*
 How do we get a single result by id
 How do separate identifier from URL
 Is there a downloada URL vs a preview URL
 Are ids GUIDs
 */

public class AssetIterator
implements org.osid.repository.AssetIterator
{
    private java.util.Iterator iterator = null;
	private Configuration configuration = Configuration.getInstance();
	
    public AssetIterator(java.util.Vector vector)
		throws org.osid.repository.RepositoryException
    {
        this.iterator = vector.iterator();
    }
	
    public AssetIterator(String query)
		throws org.osid.repository.RepositoryException
    {
		java.util.Vector results = new java.util.Vector();
		try 
		{
			java.net.URL url = new java.net.URL(query);
			java.net.URLConnection connection = url.openConnection();
			java.net.HttpURLConnection http = (java.net.HttpURLConnection)connection;
			java.io.InputStreamReader in = new java.io.InputStreamReader(http.getInputStream());
			StringBuffer xml = new StringBuffer();
			try
			{
				int i = 0;
				while ( (i = in.read()) != -1 )
				{
					xml.append(Character.toString((char)i));
				}
			}
			catch (Throwable t) {}
			//System.out.println("xml " + xml);

			javax.xml.parsers.DocumentBuilderFactory dbf = null;
			javax.xml.parsers.DocumentBuilder db = null;
			org.w3c.dom.Document document = null;
			
			dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			document = db.parse(new java.io.ByteArrayInputStream(xml.toString().getBytes()));
			org.osid.shared.Id recordStructureId = RecordStructure.getInstance().getId();
			
			// for each DOC (maps 1-to-1 with Asset)
			org.w3c.dom.NodeList docs = document.getElementsByTagName("SRW:record");
			int numDocs = docs.getLength();
			for (int i=0; i < numDocs; i++)
			{
				org.w3c.dom.Element doc = (org.w3c.dom.Element)docs.item(i);
				
				String assetTitle = null;
				String assetDescription = "";
				String assetId = null;
				org.osid.repository.Asset asset = null;
				org.osid.repository.Record record = null;
				
				org.w3c.dom.NodeList dcs = doc.getElementsByTagName("dc:title");
				int numDCs = dcs.getLength();
				for (int k=0; k < numDCs; k++)
				{
					org.w3c.dom.Element dc = (org.w3c.dom.Element)dcs.item(k);
					if (dc.hasChildNodes()) 
					{
						assetTitle = dc.getFirstChild().getNodeValue();
					}
				}
				dcs = doc.getElementsByTagName("dc:description");
				numDCs = dcs.getLength();
				for (int k=0; k < numDCs; k++)
				{
					org.w3c.dom.Element dc = (org.w3c.dom.Element)dcs.item(k);
					if (dc.hasChildNodes()) 
					{
						assetDescription = dc.getFirstChild().getNodeValue();
					}
				}
				String previewURL = null;
				dcs = doc.getElementsByTagName("dc:identifier");
				numDCs = dcs.getLength();
				for (int k=0; k < numDCs; k++)
				{
					org.w3c.dom.Element dc = (org.w3c.dom.Element)dcs.item(k);
					if (dc.hasChildNodes()) 
					{
						previewURL = dc.getFirstChild().getNodeValue();
						String guid = previewURL; // preview only?
						int index = guid.indexOf("learning_object_key=");
						if (index != -1) {
							guid = guid.substring(index + 20); // chars in key arg
						}
						assetId = configuration.getRepositoryIdString() + "|" + guid;
					}
				}
				
				if ((assetTitle != null) && (assetId != null))
				{
					asset = new Asset(assetTitle,assetDescription,assetId);
					record = asset.createRecord(recordStructureId);
					results.addElement(asset);
					//record.createPart(TitlePartStructure.getInstance().getId(),assetTitle);
					if (assetDescription != null) //record.createPart(DescriptionPartStructure.getInstance().getId(),assetDescription);
					//record.createPart(URLPartStructure.getInstance().getId(),previewURL);

					dcs = doc.getElementsByTagName("dc:language");
					numDCs = dcs.getLength();
					for (int k=0; k < numDCs; k++)
					{
						org.w3c.dom.Element dc = (org.w3c.dom.Element)dcs.item(k);
						if (dc.hasChildNodes()) 
						{
							String language = dc.getFirstChild().getNodeValue();
							//System.out.println("language " + language);
							
							record = asset.createRecord(recordStructureId);
							//record.createPart(LanguagePartStructure.getInstance().getId(),language);
						}
					}
					dcs = doc.getElementsByTagName("dc:creator");
					numDCs = dcs.getLength();
					for (int k=0; k < numDCs; k++)
					{
						org.w3c.dom.Element dc = (org.w3c.dom.Element)dcs.item(k);
						if (dc.hasChildNodes()) 
						{
							String creator = dc.getFirstChild().getNodeValue();
							//System.out.println("creator " + creator);
							
							record = asset.createRecord(recordStructureId);
							//record.createPart(CreatorPartStructure.getInstance().getId(),creator);
							//record.createPart(LastEditorPartStructure.getInstance().getId(),creator);
						}
					}
					dcs = doc.getElementsByTagName("dc:format");
					numDCs = dcs.getLength();
					for (int k=0; k < numDCs; k++)
					{
						org.w3c.dom.Element dc = (org.w3c.dom.Element)dcs.item(k);
						if (dc.hasChildNodes()) 
						{
							String format = dc.getFirstChild().getNodeValue();
							//System.out.println("format " + format);
							
							record = asset.createRecord(recordStructureId);
							//record.createPart(FormatPartStructure.getInstance().getId(),format);
							//record.createPart(FormatMIMETypePartStructure.getInstance().getId(),format);
						}
					}
					dcs = doc.getElementsByTagName("dc:subject");
					numDCs = dcs.getLength();
					for (int k=0; k < numDCs; k++)
					{
						org.w3c.dom.Element dc = (org.w3c.dom.Element)dcs.item(k);
						if (dc.hasChildNodes()) 
						{
							String subject = dc.getFirstChild().getNodeValue();
							//System.out.println("subject " + subject);
							
							record = asset.createRecord(recordStructureId);
							//record.createPart(SubjectPartStructure.getInstance().getId(),subject);
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
        this.iterator = results.iterator();
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
