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
package edu.birkbeck.osidimpl.repository.fedora;

public class Configuration
{
	private static org.osid.id.IdManager _idManager = null;
	private org.osid.OsidContext _osidContext = new org.osid.OsidContext();
	private java.util.Properties _properties = new java.util.Properties();
    private static java.util.Vector _repositoryVector = null;
	private static org.osid.shared.Id _repositoryId = null;

	private static org.osid.shared.Type _repositoryType = new Type("org.fedora-commons","repository","fedora2.2");
	private static final String REPOSITORY_ID_STRING = "";
	private static final String LOG_FILENAME = "Fedora Repository";
	private static final String ID_IMPLEMENTATION = "comet.osidimpl.id.no_persist";
	private static final String LOGGING_IMPLEMENTATION = "comet.osidimpl.logging.plain";
	private static final String LOGGING_TYPE_AUTHORITY = "mit.edu";
	private static final String LOGGING_TYPE_DOMAIN = "logging";
	private static final String LOGGING_TYPE_FORMAT = "plain";
	private static final String LOGGING_TYPE_PRIORITY = "info";
	private static Configuration _configuration = new Configuration();
	private static String _displayName = "Fedora";
	private static final String _description = "Fedora Repository.";
	private static String _keywordSearch = "";
	public static String _operation = "?operation=searchRetrieve&version=1.1&query=CRITERIA";

	private Configuration()
	{
		try {
			org.osid.logging.LoggingManager loggingManager = (org.osid.logging.LoggingManager)org.osid.OsidLoader.getManager("org.osid.logging.LoggingManager",
																															 LOGGING_IMPLEMENTATION,
																															 _osidContext,
																															 _properties);
			org.osid.logging.WritableLog log = null;
			try {
				log = loggingManager.getLogForWriting(LOG_FILENAME);
			} catch (org.osid.logging.LoggingException lex) {
				log = loggingManager.createLog(LOG_FILENAME);
			}
			log.assignFormatType(new Type(LOGGING_TYPE_AUTHORITY,LOGGING_TYPE_DOMAIN,LOGGING_TYPE_FORMAT));
			log.assignPriorityType(new Type(LOGGING_TYPE_AUTHORITY,LOGGING_TYPE_DOMAIN,LOGGING_TYPE_PRIORITY));
			Utilities.setLog(log);
			
			_idManager = (org.osid.id.IdManager)org.osid.OsidLoader.getManager("org.osid.id.IdManager",
																			   ID_IMPLEMENTATION,
																			   _osidContext,
																			   _properties);
			_repositoryId = _idManager.getId(REPOSITORY_ID_STRING);
		} catch (Throwable t) {
			t.printStackTrace();
			Utilities.log(t);
		}
	}
	
	public void setProperties(java.util.Properties properties)
		throws org.osid.repository.RepositoryException
	{
		String server = properties.getProperty("FedoraAddress");
		String port = properties.getProperty("FedoraPort");
		String serviceInterface = properties.getProperty("FedoraService");
		if ((server != null) && (port != null) && (serviceInterface != null)) {
			StringBuffer sb = new StringBuffer();
			if (!(server.startsWith("http"))) {
				sb.append("http://");
			}
			sb.append(server);
			sb.append(":");
			sb.append(port);
			sb.append("/");
			sb.append(serviceInterface);
			sb.append(_operation);
			_keywordSearch = sb.toString();
		}
		return;
	}

	public String getKeywordSearchURL()
	{
		return _keywordSearch;
	}

	protected static Configuration getInstance()
	{
		return _configuration;
	}
	
	public org.osid.shared.Id getRepositoryId()
	{
		return _repositoryId;
	}
	
	public String getRepositoryIdString()
	{
		return REPOSITORY_ID_STRING;
	}
	
	public org.osid.shared.Type getRepositoryType()
	{
		return _repositoryType;
	}	

	public String getDescription()
	{
		return _description;
	}
	
	public java.util.Vector getRepositories()
	{
		if (_repositoryVector == null) {
			_repositoryVector = new java.util.Vector();
			_repositoryVector.addElement(new Repository());
		}
		return _repositoryVector;
	}
	
	public org.osid.id.IdManager getIdManager()
	{
		return _idManager;
	}
	
	public String getDisplayName()
	{
		return _displayName;
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