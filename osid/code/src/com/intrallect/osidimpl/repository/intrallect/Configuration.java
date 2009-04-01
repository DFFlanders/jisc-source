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
package com.intrallect.osidimpl.repository.intralibrary;

public class Configuration
{
	private static Configuration _configuration = new Configuration();
	
	// ID Manager
	private org.osid.id.IdManager _idManager = null;
	private static final String ID_IMPLEMENTATION = "comet.osidimpl.id.no_persist";

	// Used in loading managers
	private org.osid.OsidContext _osidContext = new org.osid.OsidContext();
	private java.util.Properties _properties = new java.util.Properties();
	private org.osid.repository.RepositoryManager _repositoryManager = null;	
	private String _IntraLibraryHost = null;
	private String _IntraLibraryPort = null;
	private String _IntraLibrarySearchService = null;
	private String _IntraLibraryDepositService = null;
	private String _IntraLibraryDepositServiceUsername = null;
	private String _IntraLibraryDepositServicePassword = null;
	private String _IntraLibraryDepositServiceDestination = null;

	// Logging Manager
	private static final String LOG_FILENAME = "IntraLibrary";
	private static final String LOGGING_IMPLEMENTATION = "comet.osidimpl.logging.plain";
	private static final String LOGGING_TYPE_AUTHORITY = "mit.edu";
	private static final String LOGGING_TYPE_DOMAIN = "logging";
	private static final String LOGGING_TYPE_FORMAT = "plain";
	private static final String LOGGING_TYPE_PRIORITY = "info";
	
	// Assuming 1 Repository
	private static final String REPOSITORY_ID_STRING = "B6D75797-A731-4811-B170-C65F277812D4-2595-000008D77B737D4C";
	private org.osid.shared.Id _repositoryId = null;
	private static final String _repositoryDisplayName = "IntraLibrary Repository";
	private static final String _repositoryDescription = "IntraLibrary Repository Description";
	private java.util.Vector _repositoryVector = null;
		
	// Types for 1 Repository
	private static final org.osid.shared.Type _intralibraryRepositoryType = new Type("intrallect.com","repository","intralibrary");	
	private static final org.osid.shared.Type _intralibraryAssetType = new Type("intrallect.com","asset","intralibrary");
	private static final org.osid.shared.Type _keywordSearchType = new Type("mit.edu","search","keyword");
	private java.util.Vector _repositoryTypeVector = null;
	private java.util.Vector _assetTypeVector = null;
	private java.util.Vector _searchTypeVector = null;

	private boolean _debug = false;
	
	// implementation specific
	private static String _operation = "?operation=searchRetrieve&version=1.1&query=CRITERIA&maximumRecords=100&version=1.1&recordSchema=dc";
	private static String _keywordSearchURL = null;
	
	protected static final int CONTRIBUTOR_INDEX = 0;
	protected static final int COVERAGE_INDEX = 1;
	protected static final int CREATOR_INDEX = 2;
	protected static final int DATE_INDEX = 3;
	protected static final int DESCRIPTION_INDEX = 4;
	protected static final int FORMAT_INDEX = 5;
	protected static final int IDENTIFIER_INDEX = 6;
	protected static final int LANGUAGE_INDEX = 7;
	protected static final int PUBLISHER_INDEX = 8;
	protected static final int RELATION_INDEX = 9;
	protected static final int RIGHTS_INDEX = 10;
	protected static final int SOURCE_INDEX = 11;
	protected static final int SUBJECT_INDEX = 12;
	protected static final int TITLE_INDEX = 13;
	protected static final int TYPE_INDEX = 14;
	protected static final int URL_INDEX = 19;
	
	protected static final String[] PartStructureDisplayNames = {
		"Contributor",
		"Coverage",
		"Creator",
		"Date",
		"Description",
		"Format",
		"Identifier",
		"Language",
		"Publisher",
		"Relation",
		"Rights",
		"Source",
		"Subject",
		"Title",
		"Type",
		"URL"
	};
	
	protected static final String[] PartStructureDescriptions = {
		"An entity responsible for making contributions to the resource.",
		"The spatial or temporal topic of the resource, the spatial applicability of the resource, or the jurisdiction under which the resource is relevant.",
		"An entity primarily responsible for making the resource.",
		"A point or period of time associated with an event in the lifecycle of the resource.",
		"An account of the resource.",
		"The file format, physical medium, or dimensions of the resource.",
		"An unambiguous reference to the resource within a given context.",
		"A language of the resource.",
		"An entity responsible for making the resource available.",
		"A related resource.",
		"Information about rights held in and over the resource.",
		"The resource from which the described resource is derived.",
		"The topic of the resource.",
		"A name given to the resource.",
		"The nature or genre of the resource.",
		"URL"
	};

	protected static final String[] PartStructureIDs = {
		"Contributor.PartStructure.ID",
		"Coverage.PartStructure.ID",
		"Creator.PartStructure.ID",
		"Date.PartStructure.ID",
		"Description.PartStructure.ID",
		"Format.PartStructure.ID",
		"Identifier.PartStructure.ID",
		"Language.PartStructure.ID",
		"Publisher.PartStructure.ID",
		"Relation.PartStructure.ID",
		"Rights.PartStructure.ID",
		"Source.PartStructure.ID",
		"Subject.PartStructure.ID",
		"Title.PartStructure.ID",
		"Type.PartStructure.ID",
		"URL.PartStructure.ID"
	};
	
	protected static final String[] PartDisplayNames = {
		"Contributor",
		"Coverage",
		"Creator",
		"Date",
		"Description",
		"Format",
		"Identifier",
		"Language",
		"Publisher",
		"Relation",
		"Rights",
		"Source",
		"Subject",
		"Title",
		"Type",
		"URL"
	};
	
	protected static final String[] PartIDs = {
		"Contributor.Part.ID",
		"Coverage.Part.ID",
		"Creator.Part.ID",
		"Date.Part.ID",
		"Description.Part.ID",
		"Format.Part.ID",
		"Identifier.Part.ID",
		"Language.Part.ID",
		"Publisher.Part.ID",
		"Relation.Part.ID",
		"Rights.Part.ID",
		"Source.Part.ID",
		"Subject.Part.ID",
		"Title.Part.ID",
		"Type.Part.ID",
		"URL.Part.ID"
	};
	
	protected static final String[] PartStructureTypes = {
		"partStructure/contributor@mit.edu",
		"partStructure/coverage@mit.edu",
		"partStructure/creator@mit.edu",
		"partStructure/date@mit.edu",
		"partStructure/description@mit.edu",
		"partStructure/format@mit.edu",
		"partStructure/identifier@mit.edu",
		"partStructure/language@mit.edu",
		"partStructure/publisher@mit.edu",
		"partStructure/relation@mit.edu",
		"partStructure/rights@mit.edu",
		"partStructure/source@mit.edu",
		"partStructure/subject@mit.edu",
		"partStructure/title@mit.edu",
		"partStructure/type@mit.edu",
		"partStructure/URL@mit.edu",
	};
	
	protected static final String URL_PREFIX = "http://www.college.edu";
	
	protected static final String RECORD_ID = "intralibrary.record.id";
	protected static final String RECORD_STRUCTURE_DISPLAY_NAME = "IntraLibrary Record Structure";
	protected static final String RECORD_STRUCTURE_DESCRIPTION = "IntraLibrary Record Structure";
	protected static final String RECORD_STRUCTURE_ID = "intralibrary.recordstructure.id";
	protected static final String RECORD_STRUCTURE_FORMAT = "String";
	protected static final String RECORD_STRUCTURE_SCHEMA = "DC";
	protected static final org.osid.shared.Type RECORD_STRUCTURE_TYPE = new Type("intrallect.com","recordStructure","intralibrary");
	
	protected static Configuration getInstance()
	{
		return _configuration;
	}
	
	// Setup ID Manager and Logging Manager
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
			Utilities.log(t);
		}
	}

	// Some attributes of a single Repository
	public org.osid.shared.Id getRepositoryId()
	{
		return _repositoryId;
	}
	
	public String getRepositoryDisplayName()
	{
		return _repositoryDisplayName;
	}
	
	public String getRepositoryDescription()
	{
		return _repositoryDescription;
	}
	
	public org.osid.shared.Type getRepositoryType()
	{
		return _intralibraryRepositoryType;
	}
	
	public org.osid.shared.TypeIterator getRepositoryTypes()
		throws org.osid.repository.RepositoryException
	{
		try {
			if (_repositoryTypeVector == null) {
				_repositoryTypeVector = new java.util.Vector();
				_repositoryTypeVector.addElement(_intralibraryRepositoryType);
			}
			return new TypeIterator(_repositoryTypeVector);
		} catch (Throwable t) {
			throw new org.osid.repository.RepositoryException(org.osid.OsidException.OPERATION_FAILED);
		}
	}
	
	public org.osid.shared.TypeIterator getAssetTypes()
		throws org.osid.repository.RepositoryException
	{
		try {
			if (_assetTypeVector == null) {
				_assetTypeVector = new java.util.Vector();
				_assetTypeVector.addElement(_intralibraryAssetType);
			}
			return new TypeIterator(_assetTypeVector);
		} catch (Throwable t) {
			throw new org.osid.repository.RepositoryException(org.osid.OsidException.OPERATION_FAILED);
		}
	}
	
	public org.osid.shared.TypeIterator getSearchTypes()
		throws org.osid.repository.RepositoryException
    {
		try {
			if (_searchTypeVector == null) {
				_searchTypeVector = new java.util.Vector();
				_searchTypeVector.addElement(_keywordSearchType);
			}
			return new TypeIterator(_searchTypeVector);
		} catch (Throwable t) {
			throw new org.osid.repository.RepositoryException(org.osid.OsidException.OPERATION_FAILED);
		}
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
	
	public boolean debug()
	{
		return _debug;
	}
	
	public void setConfiguration(java.util.Properties properties)
	{
		Object o = properties.getProperty("IntraLibraryHost");
		if (o != null) {
			_IntraLibraryHost = (String)o;
			if (!(_IntraLibraryHost.startsWith("http"))) {
				_IntraLibraryHost = "http://" + _IntraLibraryHost;
			}
		}
		o = properties.getProperty("IntraLibraryPort");
		if (o != null) {
			_IntraLibraryPort = (String)o;
		}
		o = properties.getProperty("IntraLibrarySearchService");
		if (o != null) {
			_IntraLibrarySearchService = (String)o;
		}
		o = properties.getProperty("IntraLibraryDepositService");
		if (o != null) {
			_IntraLibraryDepositService = (String)o;
		}
		o = properties.getProperty("IntraLibraryDepositServiceUsername");
		if (o != null) {
			_IntraLibraryDepositServiceUsername = (String)o;
		}
		o = properties.getProperty("IntraLibraryDepositServicePassword");
		if (o != null) {
			_IntraLibraryDepositServicePassword = (String)o;
		}
		o = properties.getProperty("IntraLibraryDepositServiceDestination");
		if (o != null) {
			_IntraLibraryDepositServiceDestination = (String)o;
		}
		o = properties.getProperty("IntraLibraryDebug");
		if (o != null) {
			String s = (String)o;
			_debug = (s.trim().toLowerCase().equals("true"));
		}
		
		// keyword URL
		StringBuffer sb = new StringBuffer();
		sb.append(_IntraLibraryHost);
		sb.append(":");
		sb.append(_IntraLibraryPort);
		sb.append("/");
		sb.append(_IntraLibrarySearchService);
		sb.append(_operation);
		_keywordSearchURL = sb.toString();
	}	
	
	/**
		upload content to IntraLibrary
	 
		Exceptions:	
	 
		shared.SharedException.UNKNOWN_TYPE			content is not a string (filepath)
		org.osid.OsidException.OPERATION_FAILED		filepath not found (refer to log)
		org.osid.OsidException.OPERATION_FAILED		deposit failed (refer to log)
		org.osid.OsidException.CONFIGURATION_ERROR	no username or no password 
	 
		*/
	public String upload(java.io.Serializable content)
		throws org.osid.repository.RepositoryException
	{
		String assetIdString = null;
		if (!(content instanceof String)) {
			throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.UNKNOWN_TYPE);
		}
		
		String filepath = (String)content;
		java.io.File file = new java.io.File(filepath);
		if (!file.exists()) {
			Utilities.log("Cannot locate file: " + filepath);
			throw new org.osid.repository.RepositoryException(org.osid.OsidException.OPERATION_FAILED);
		}
		
		if ( (_IntraLibraryHost == null) 
			 || ( _IntraLibraryPort == null)
			 || ( _IntraLibraryDepositService == null)
			 || ( _IntraLibraryDepositServiceUsername == null)
			 || ( _IntraLibraryDepositServicePassword == null)
			 || ( _IntraLibraryDepositServiceDestination == null) ) {
			throw new org.osid.repository.RepositoryException(org.osid.OsidException.CONFIGURATION_ERROR);
		}
		
		try {
			org.purl.sword.client.Client client = new org.purl.sword.client.Client();
			
			StringBuffer location = new StringBuffer();
			location.append(_IntraLibraryHost);
			location.append(":");
			location.append(_IntraLibraryPort);
			location.append("/");
			location.append(_IntraLibraryDepositService);
			
			String server = _IntraLibraryHost.substring(7); // removes http://
			client.setServer(server,(new Integer(_IntraLibraryPort)).intValue());
			client.setCredentials(_IntraLibraryDepositServiceUsername,_IntraLibraryDepositServicePassword);
			
			if (_debug) {
				System.out.println("Location " + location.toString());
				System.out.println("Server " + server);
				System.out.println("Port " + _IntraLibraryPort);
				System.out.println("Username " + _IntraLibraryDepositServiceUsername);
				System.out.println("Password " + _IntraLibraryDepositServicePassword);
			}

			org.purl.sword.base.ServiceDocument serviceDocument = client.getServiceDocument(location.toString());
			if (_debug) {
				System.out.println("Response: " + serviceDocument.toString());
			}
			
			org.purl.sword.client.PostMessage postMessage = new org.purl.sword.client.PostMessage();
			
			location = new StringBuffer();
			location.append(_IntraLibraryHost);
			location.append(":");
			location.append(_IntraLibraryPort);
			location.append("/");
			location.append(_IntraLibraryDepositServiceDestination);
			postMessage.setDestination(location.toString());
			postMessage.setFilepath(filepath);
			String fileType = java.net.URLConnection.guessContentTypeFromName(filepath);
			postMessage.setFiletype(fileType);
			
			if (_debug) {
				System.out.println("Location " + location.toString());
				System.out.println("Content Type " + fileType);
			}
			
			org.purl.sword.base.DepositResponse documentResponse = client.postFile(postMessage);
			
			// use atom:id as asset id
			String assetIdSource = documentResponse.toString();
			int index = assetIdSource.indexOf("atom:id");
			assetIdString = assetIdSource.substring(index + 8);
			index = assetIdString.indexOf("</atom:id");
			assetIdString = assetIdString.substring(0,index);
			
			if (_debug) {
				System.out.println("Response: " + documentResponse.toString());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Utilities.log(ex);
			throw new org.osid.repository.RepositoryException(org.osid.OsidException.OPERATION_FAILED);
		}
		return assetIdString;
	}
	
	public org.osid.repository.Asset singleAssetSearch(org.osid.shared.Id assetId)
		throws org.osid.repository.RepositoryException
	{
		try {
			String idString = assetId.getIdString();
			// make some use of idString
			//return new Asset("some title", "some description", idString);
		} catch (Throwable t) {
			Utilities.log(t);
		}
		return null;
	}
	
	public org.osid.repository.AssetIterator assetsSearch(java.io.Serializable searchCriteria, org.osid.shared.Type searchType)
		throws org.osid.repository.RepositoryException
	{
		if (!searchType.isEqual(_keywordSearchType)) {
			throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.UNKNOWN_TYPE);
		}
		if (!(searchCriteria instanceof String))
		{
			Utilities.log("invalid criteria");
			throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.UNKNOWN_TYPE);
		}
		
		java.util.Vector assetVector = new java.util.Vector();
		try {
			String criteria = (String)searchCriteria;
			criteria = criteria.trim().toLowerCase();
			criteria = java.net.URLEncoder.encode(criteria,"UTF-8");			
			String query = _keywordSearchURL;
			query = query.replaceAll("CRITERIA",criteria);
			if (_debug) System.out.println("query " + query);
			
			java.net.URL url = new java.net.URL(query);
			java.net.URLConnection connection = url.openConnection();
			java.net.HttpURLConnection http = (java.net.HttpURLConnection)connection;
			java.io.InputStreamReader in = new java.io.InputStreamReader(http.getInputStream());
			
			StringBuffer xml = new StringBuffer();
			try	{
				int i = 0;
				while ( (i = in.read()) != -1 ) {
					xml.append(Character.toString((char)i));
				}
			} catch (Throwable t) {}
			in.close();			
			if (_debug) System.out.println("XML: " + xml);
			
			Metadata md = new Metadata(xml.toString());
			assetVector = md.getAssets();
		} catch (Throwable t) {
			Utilities.log(t);
		}
		return new AssetIterator(assetVector);
	}
}