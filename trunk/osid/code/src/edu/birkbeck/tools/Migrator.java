package edu.birkbeck.tools;

public class Migrator extends javax.swing.JFrame implements java.awt.event.ActionListener, javax.swing.event.ListSelectionListener
{
	public static final String TITLE = "Migration Tool";
	private static final String USERNAME_PROMPT = "Username: ";
	private static final String PASSWORD_PROMPT = "Password: ";
	private static final String PROFILES_PROMPT = "Select Profile To Load: ";
	private static final String SOURCE_PROMPT = "Source";
	private static final String TARGETS_PROMPT = "Tagets";
	private static final String CROSSWALK_PROMPT = "Crosswalk";
	private static final String ASSET_XFORM_PROMPT = "Asset Transform";
	private static final String OBJECT_XFORM_PROMPT = "Object Transform";
	
	private static final String LOGIN = "Log in";
	private static final String LOAD_PROFILE = "Load Profile";
	private static final String MIGRATE = "Migrate";
	private static final String EDIT = "Edit Selections";
	private static final String SAVE_AS = "Save Profile As";

	private static final String LOGIN_URL = "http://localhost:8080/migrator/login.jsp?username=USERNAME&pwd=PASSWORD";
	private static final String GET_PROFILES_LIST_URL = "http://localhost:8080/migrator/getProfilesList.jsp?token=TOKEN";
	private static final String GET_PROFILE_URL = "http://localhost:8080/migrator/getProfile.jsp?token=TOKEN&profile=PROFILE_ID";
	private static final String GET_REPOSITORY_LIST_URL = "http://localhost:8080/migrator/getRepositoryList.jsp?token=TOKEN";
	
	private static final String USERNAME_PARAMETER = "USERNAME: ";
	private static final String PASSWORD_PARAMETER = "PASSWORD: ";
	private static final String AUTHENTICATION_ERROR = "Authentication Error";
	private static final String REPOSITORY_LIST_ERROR = "Error fetching list of Repository names, authentication error, or no repositories found";
	private static final String ZERO_LENGTH_PROFILE_NAME_ERROR = "Pofile name cannot be empty";
	private static final String UNSELECTED_SOURCE_ERROR = "No source selected";
	private static final String UNSELECTED_TARGET_ERROR = "No targets selected";
	private static final String OVERWRITE_PROFILE = "Overwrite Profile?";
	private static final String ERROR_TITLE = "Error";
	private static final String AUDIT_REPORT_TITLE = "Audit Report";

	private String _token = "";
	private javax.swing.JTextField _usernameField = new javax.swing.JTextField(10);
	private javax.swing.JPasswordField _passwordField = new javax.swing.JPasswordField(10);
	private javax.swing.JButton _loginButton = new javax.swing.JButton(LOGIN);
	private javax.swing.JPanel _loginPanel = new javax.swing.JPanel();
	
	private javax.swing.JPanel _profilePanel = new javax.swing.JPanel();
	private javax.swing.DefaultListModel _profileListModel = new javax.swing.DefaultListModel();
    private javax.swing.DefaultListSelectionModel _profileListSelectionModel = new javax.swing.DefaultListSelectionModel();
    private javax.swing.JList _profileList = new javax.swing.JList(_profileListModel);
    private javax.swing.JScrollPane _profileJsp = new javax.swing.JScrollPane(_profileList);
	private javax.swing.JButton _laodProfileButton = new javax.swing.JButton(LOAD_PROFILE);
 	
	private javax.swing.JPanel _migrationPanel = new javax.swing.JPanel();
	private javax.swing.DefaultListModel _sourceListModel = new javax.swing.DefaultListModel();
    private javax.swing.DefaultListSelectionModel _sourceListSelectionModel = new javax.swing.DefaultListSelectionModel();
    private javax.swing.JList _sourceList = new javax.swing.JList(_sourceListModel);
    private javax.swing.JScrollPane _sourceJsp = new javax.swing.JScrollPane(_sourceList);
	private java.util.Vector _repositoryIdVector = new java.util.Vector();
	
	private javax.swing.DefaultListModel _targetsListModel = new javax.swing.DefaultListModel();
    private javax.swing.DefaultListSelectionModel _targetsListSelectionModel = new javax.swing.DefaultListSelectionModel();
    private javax.swing.JList _targetsList = new javax.swing.JList(_targetsListModel);
    private javax.swing.JScrollPane _targetsJsp = new javax.swing.JScrollPane(_targetsList);
	
	private javax.swing.DefaultListModel _crosswalkListModel = new javax.swing.DefaultListModel();
    private javax.swing.DefaultListSelectionModel _crosswalkListSelectionModel = new javax.swing.DefaultListSelectionModel();
    private javax.swing.JList _crosswalkList = new javax.swing.JList(_crosswalkListModel);
    private javax.swing.JScrollPane _crosswalkJsp = new javax.swing.JScrollPane(_crosswalkList);
	
	private javax.swing.DefaultListModel _assetxformListModel = new javax.swing.DefaultListModel();
    private javax.swing.DefaultListSelectionModel _assetxformListSelectionModel = new javax.swing.DefaultListSelectionModel();
    private javax.swing.JList _assetxformList = new javax.swing.JList(_assetxformListModel);
    private javax.swing.JScrollPane _assetxformJsp = new javax.swing.JScrollPane(_assetxformList);
	
	private javax.swing.DefaultListModel _objectxformListModel = new javax.swing.DefaultListModel();
    private javax.swing.DefaultListSelectionModel _objectxformListSelectionModel = new javax.swing.DefaultListSelectionModel();
    private javax.swing.JList _objectxformList = new javax.swing.JList(_objectxformListModel);
    private javax.swing.JScrollPane _objectxformJsp = new javax.swing.JScrollPane(_objectxformList);
	
	private javax.swing.JButton _migrateButton = new javax.swing.JButton(MIGRATE);

	private javax.swing.JTextField _saveAsField = new javax.swing.JTextField(10);
	private javax.swing.JButton _editButton = new javax.swing.JButton(EDIT);
	private javax.swing.JButton _saveAsButton = new javax.swing.JButton(SAVE_AS);
	
	private String _profileSourceRepositoryId = null;
	private java.util.Vector _profileSourceRepositoryIdVector = new java.util.Vector();
	private boolean _isEditing = false;
 	
	public Migrator()
	{
		setTitle(TITLE);
		setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		
		java.awt.GridBagLayout gbLoginLayout = new java.awt.GridBagLayout();
		java.awt.GridBagConstraints gbLoginConstraints = new java.awt.GridBagConstraints();
		gbLoginConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gbLoginConstraints.insets = new java.awt.Insets(2,2,2,2);
		_loginPanel.setLayout(gbLoginLayout);		
		
		gbLoginConstraints.gridx = 0;
		gbLoginConstraints.gridy = 0;
		_loginPanel.add(new javax.swing.JLabel(USERNAME_PROMPT),gbLoginConstraints);

		gbLoginConstraints.gridx = 1;
		gbLoginConstraints.gridy = 0;
		_loginPanel.add(_usernameField,gbLoginConstraints);

		gbLoginConstraints.gridx = 0;
		gbLoginConstraints.gridy = 1;
		_loginPanel.add(new javax.swing.JLabel(PASSWORD_PROMPT),gbLoginConstraints);

		gbLoginConstraints.gridx = 1;
		gbLoginConstraints.gridy = 1;
		_loginPanel.add(_passwordField,gbLoginConstraints);

		gbLoginConstraints.gridx = 0;
		gbLoginConstraints.gridy = 2;
		_loginPanel.add(_loginButton,gbLoginConstraints);

		_loginButton.addActionListener(this);
		add(_loginPanel);
		_loginPanel.setVisible(true);
		
		// create profile list panel for later
		_profilePanel.add(new javax.swing.JLabel(PROFILES_PROMPT));
		_profileList.setFixedCellWidth(150);
		_profileListSelectionModel.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		_profileList.addListSelectionListener(this);
		_profilePanel.add(_profileJsp);
		_laodProfileButton.setEnabled(false);
		_laodProfileButton.addActionListener(this);
		_profilePanel.add(_laodProfileButton);
		
		// create migration list panel for later
		java.awt.GridBagLayout gbMigrateLayout = new java.awt.GridBagLayout();
		java.awt.GridBagConstraints gbMigrateConstraints = new java.awt.GridBagConstraints();
		gbMigrateConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gbMigrateConstraints.insets = new java.awt.Insets(2,2,2,2);
		_migrationPanel.setLayout(gbMigrateLayout);	
		
		gbMigrateConstraints.gridx = 0;
		gbMigrateConstraints.gridy = 0;
		_migrationPanel.add(new javax.swing.JLabel(SOURCE_PROMPT),gbMigrateConstraints);
		_sourceList.setFixedCellWidth(150);		
		_sourceList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

		gbMigrateConstraints.gridx = 0;
		gbMigrateConstraints.gridy = 1;
		_migrationPanel.add(_sourceJsp,gbMigrateConstraints);

		gbMigrateConstraints.gridx = 1;
		gbMigrateConstraints.gridy = 0;
		_migrationPanel.add(new javax.swing.JLabel(TARGETS_PROMPT),gbMigrateConstraints);
		_targetsList.setFixedCellWidth(150);
		_targetsList.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		gbMigrateConstraints.gridx = 1;
		gbMigrateConstraints.gridy = 1;
		_migrationPanel.add(_targetsJsp,gbMigrateConstraints);

		gbMigrateConstraints.gridx = 0;
		gbMigrateConstraints.gridy = 2;
		_migrationPanel.add(new javax.swing.JLabel(CROSSWALK_PROMPT),gbMigrateConstraints);
		_crosswalkList.setFixedCellWidth(150);
		_crosswalkList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		_crosswalkList.setEnabled(false);
		
		gbMigrateConstraints.gridx = 0;
		gbMigrateConstraints.gridy = 3;
		_migrationPanel.add(_crosswalkJsp,gbMigrateConstraints);
		
		gbMigrateConstraints.gridx = 1;
		gbMigrateConstraints.gridy = 2;
		_migrationPanel.add(new javax.swing.JLabel(ASSET_XFORM_PROMPT),gbMigrateConstraints);
		_assetxformList.setFixedCellWidth(150);
		_assetxformList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		_assetxformList.setEnabled(false);
		
		gbMigrateConstraints.gridx = 1;
		gbMigrateConstraints.gridy = 3;
		_migrationPanel.add(_assetxformJsp,gbMigrateConstraints);
		
		gbMigrateConstraints.gridx = 2;
		gbMigrateConstraints.gridy = 2;
		_migrationPanel.add(new javax.swing.JLabel(OBJECT_XFORM_PROMPT),gbMigrateConstraints);
		_objectxformList.setFixedCellWidth(150);
		_objectxformList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		_objectxformList.setEnabled(false);
		
		gbMigrateConstraints.gridx = 2;
		gbMigrateConstraints.gridy = 3;
		_migrationPanel.add(_objectxformJsp,gbMigrateConstraints);
		
		_editButton.addActionListener(this);
		gbMigrateConstraints.gridx = 1;
		gbMigrateConstraints.gridy = 4;
		_migrationPanel.add(_editButton,gbMigrateConstraints);

		_saveAsButton.addActionListener(this);
		gbMigrateConstraints.gridx = 0;
		gbMigrateConstraints.gridy = 4;
		_migrationPanel.add(_saveAsButton,gbMigrateConstraints);

		gbMigrateConstraints.gridx = 0;
		gbMigrateConstraints.gridy = 5;
		_migrationPanel.add(_saveAsField,gbMigrateConstraints);
		
		_migrateButton.addActionListener(this);
		gbMigrateConstraints.gridx = 2;
		gbMigrateConstraints.gridy = 5;
		_migrationPanel.add(_migrateButton,gbMigrateConstraints);
		
		// initially, profile specifics are read-only
		_sourceList.setEnabled(false);
		_targetsList.setEnabled(false);
		_saveAsButton.setEnabled(false);
		_migrateButton.setEnabled(true);
		
		setSize(500,500);
		setVisible(true);
	}
	
	public void actionPerformed(java.awt.event.ActionEvent ae)
	{
		String actionCommand = ae.getActionCommand();
		if (actionCommand.equals(LOGIN)) {
			doLogin();
		} else if (actionCommand.equals(LOAD_PROFILE)) {
			loadProfile();
		} else if (actionCommand.equals(EDIT)) {
			edit();
		} else if (actionCommand.equals(MIGRATE)) {
			migration();
		}
	}
	
	public void valueChanged(javax.swing.event.ListSelectionEvent lse)
	{
		int index = lse.getFirstIndex();
		_laodProfileButton.setEnabled( (index >= 0) );
	}
	
	private void doLogin()
	{
		try {
			String query = LOGIN_URL;
			query = query.replaceAll("USERNAME",_usernameField.getText());
			query = query.replaceAll("PASSWORD",new String(_passwordField.getPassword()));
			System.out.println("query " + query);
			
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
			String x = xml.toString().trim();
			System.out.println("xml " + x);
			
			// Parse XML for Content elements
			javax.xml.parsers.DocumentBuilderFactory dbf = null;
			javax.xml.parsers.DocumentBuilder db = null;
			org.w3c.dom.Document document = null;
			
			dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			document = db.parse(new java.io.ByteArrayInputStream(x.getBytes()));

			org.w3c.dom.NodeList tokenNodeList = document.getElementsByTagName("token");
			int numTokens = tokenNodeList.getLength();
			if (numTokens > 0) {
				org.w3c.dom.Element tokenElement = (org.w3c.dom.Element)tokenNodeList.item(0);
				_token = tokenElement.getAttribute("id");
				if (!(_token.equals("0"))) {
					_loginPanel.setVisible(false);
					doGetProfilesList();
				} else {
					// login failure alert
					javax.swing.JOptionPane.showMessageDialog(null,AUTHENTICATION_ERROR,TITLE,javax.swing.JOptionPane.ERROR_MESSAGE);
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private void doGetProfilesList()
	{
		try {
			String query = GET_PROFILES_LIST_URL;
			query = query.replaceAll("TOKEN",_token);
			System.out.println("query " + query);
			
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
			String x = xml.toString().trim();
			System.out.println("xml " + x);

			// Parse XML for Content elements
			javax.xml.parsers.DocumentBuilderFactory dbf = null;
			javax.xml.parsers.DocumentBuilder db = null;
			org.w3c.dom.Document document = null;
			
			dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			document = db.parse(new java.io.ByteArrayInputStream(x.getBytes()));
			
			org.w3c.dom.NodeList profileNodeList = document.getElementsByTagName("profile");
			int numProfiles = profileNodeList.getLength();
			for (int i=0; i < numProfiles; i++) {
				org.w3c.dom.Element profileElement = (org.w3c.dom.Element)profileNodeList.item(i);
				
				org.w3c.dom.NodeList displayNameNodeList = profileElement.getElementsByTagName("displayName");
				int numDisplayNames = displayNameNodeList.getLength();
				if (numDisplayNames > 0) {
					org.w3c.dom.Element displayNameElement = (org.w3c.dom.Element)displayNameNodeList.item(0);
					if (displayNameElement.hasChildNodes()) {
						String value = displayNameElement.getFirstChild().getNodeValue();
						_profileListModel.addElement(value);
					}
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		add(_profilePanel);
		_profilePanel.setVisible(true);
	}
	
	private void doGetProfile(String profileDisplayName)
	{
		try {
			String query = GET_PROFILE_URL;
			query = query.replaceAll("TOKEN",_token);
			String profileId = java.net.URLEncoder.encode(profileDisplayName,"UTF-8");
			query = query.replaceAll("PROFILE_ID",profileId);
			System.out.println("query " + query);
			
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
			String x = xml.toString().trim();
			System.out.println("xml " + x);
			
			// clear these out
			_sourceListModel.removeAllElements();
			_profileSourceRepositoryId = null;
			_targetsListModel.removeAllElements();
			_profileSourceRepositoryIdVector.removeAllElements();
			_crosswalkListModel.removeAllElements();
			_assetxformListModel.removeAllElements();
			_objectxformListModel.removeAllElements();
			
			// Parse XML for Content elements
			javax.xml.parsers.DocumentBuilderFactory dbf = null;
			javax.xml.parsers.DocumentBuilder db = null;
			org.w3c.dom.Document document = null;
			
			dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			document = db.parse(new java.io.ByteArrayInputStream(x.getBytes()));
			
			org.w3c.dom.NodeList profileNodeList = document.getElementsByTagName("profile");
			int numProfiles = profileNodeList.getLength();
			if (numProfiles > 0) {
				org.w3c.dom.Element profileElement = (org.w3c.dom.Element)profileNodeList.item(0);
				
				org.w3c.dom.NodeList displayNameNodeList = profileElement.getElementsByTagName("displayName");
				int numDisplayNames = displayNameNodeList.getLength();
				if (numDisplayNames > 0) {
					org.w3c.dom.Element displayNameElement = (org.w3c.dom.Element)displayNameNodeList.item(0);
					if (displayNameElement.hasChildNodes()) {
						String value = displayNameElement.getFirstChild().getNodeValue();
						_saveAsField.setText(value);
						_saveAsField.setEditable(false);
					}
				}

				org.w3c.dom.NodeList sourceNodeList = profileElement.getElementsByTagName("source");			
				int numSourceNodes = sourceNodeList.getLength();
				if (numSourceNodes > 0) {
					org.w3c.dom.Element sourceElement = (org.w3c.dom.Element)sourceNodeList.item(0);
					
					org.w3c.dom.NodeList sourceDisplayNameNodeList = sourceElement.getElementsByTagName("displayName");			
					int numSourceDisplayNames = sourceDisplayNameNodeList.getLength();
					if (numSourceDisplayNames > 0) {
						org.w3c.dom.Element sourceDisplayNameElement = (org.w3c.dom.Element)sourceDisplayNameNodeList.item(0);
						String value = sourceDisplayNameElement.getFirstChild().getNodeValue();
						_sourceListModel.addElement(value);
						// select this
						int indices[] = new int[1];
						indices[0] = 0;
						_sourceList.setSelectedIndices(indices);
					}
					org.w3c.dom.NodeList sourceRepositoryIdNodeList = sourceElement.getElementsByTagName("repositoryId");			
					int numSourceRepositoryIds = sourceRepositoryIdNodeList.getLength();
					if (numSourceRepositoryIds > 0) {
						org.w3c.dom.Element sourceRepositoryIdElement = (org.w3c.dom.Element)sourceRepositoryIdNodeList.item(0);
						String value = sourceRepositoryIdElement.getFirstChild().getNodeValue();
						_profileSourceRepositoryId = value;
					}
					
					org.w3c.dom.NodeList targetNodeList = profileElement.getElementsByTagName("target");			
					int numTargetNodes = targetNodeList.getLength();
					for (int k=0; k < numTargetNodes; k++) {
						org.w3c.dom.Element targetElement = (org.w3c.dom.Element)targetNodeList.item(k);
						String value = targetElement.getFirstChild().getNodeValue();
						
						org.w3c.dom.NodeList targetDisplayNameNodeList = targetElement.getElementsByTagName("displayName");			
						int numTargetDisplayNames = targetDisplayNameNodeList.getLength();
						if (numTargetDisplayNames > 0) {
							org.w3c.dom.Element targetDisplayNameElement = (org.w3c.dom.Element)targetDisplayNameNodeList.item(0);
							value = targetDisplayNameElement.getFirstChild().getNodeValue();
							_targetsListModel.addElement(value);
						}
						org.w3c.dom.NodeList targetRepositoryIdNodeList = targetElement.getElementsByTagName("repositoryId");			
						int numTargetRepositoryIds = targetRepositoryIdNodeList.getLength();
						if (numTargetRepositoryIds > 0) {
							org.w3c.dom.Element targetRepositoryIdElement = (org.w3c.dom.Element)targetRepositoryIdNodeList.item(0);
							value = targetRepositoryIdElement.getFirstChild().getNodeValue();
							_profileSourceRepositoryIdVector.addElement(value);
						}
					}
					// select this
					int indices[] = new int[numTargetNodes];
					for (int t=0; t < numTargetNodes; t++) indices[t] = t;
					_targetsList.setSelectedIndices(indices);
					
					org.w3c.dom.NodeList crosswalkNodeList = profileElement.getElementsByTagName("crosswalk");			
					int numCrosswalkNodes = crosswalkNodeList.getLength();
					for (int k=0; k < numCrosswalkNodes; k++) {
						org.w3c.dom.Element crosswalkElement = (org.w3c.dom.Element)crosswalkNodeList.item(k);
						String value = crosswalkElement.getFirstChild().getNodeValue();
						_crosswalkListModel.addElement(value);
					}
					// select this
					indices = new int[numCrosswalkNodes];
					for (int t=0; t < numCrosswalkNodes; t++) indices[t] = t;
					_crosswalkList.setSelectedIndices(indices);
					
					org.w3c.dom.NodeList assetxformNodeList = profileElement.getElementsByTagName("assetxform");			
					int numAssetxformNodes = assetxformNodeList.getLength();
					for (int k=0; k < numAssetxformNodes; k++) {
						org.w3c.dom.Element assetxformElement = (org.w3c.dom.Element)assetxformNodeList.item(k);
						String value = assetxformElement.getFirstChild().getNodeValue();
						_assetxformListModel.addElement(value);
					}
					// select this
					indices = new int[numAssetxformNodes];
					for (int t=0; t < numAssetxformNodes; t++) indices[t] = t;
					_assetxformList.setSelectedIndices(indices);
					
					org.w3c.dom.NodeList objectxformNodeList = profileElement.getElementsByTagName("objectxform");			
					int numObjectxformNodes = objectxformNodeList.getLength();
					for (int k=0; k < numObjectxformNodes; k++) {
						org.w3c.dom.Element objectxformElement = (org.w3c.dom.Element)objectxformNodeList.item(k);
						String value = objectxformElement.getFirstChild().getNodeValue();
						_objectxformListModel.addElement(value);
					}
					// select this
					System.out.println("num ber object xform nodes " + numObjectxformNodes);
					indices = new int[numObjectxformNodes];
					for (int t=0; t < numObjectxformNodes; t++) {
						indices[t] = t;
						System.out.println("next t " + t);
					}
					_objectxformList.setSelectedIndices(indices);
					
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		add(_migrationPanel);
		_migrationPanel.setVisible(true);
	}
	
	private java.util.Vector doGetRepositoryList()
	{
		java.util.Vector resultVector = new java.util.Vector();
		
		try {
			String query = GET_REPOSITORY_LIST_URL;
			query = query.replaceAll("TOKEN",_token);
			System.out.println("query " + query);
			
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
			String x = xml.toString().trim();
			System.out.println("xml " + x);
			
			// Parse XML for Repository elements
			javax.xml.parsers.DocumentBuilderFactory dbf = null;
			javax.xml.parsers.DocumentBuilder db = null;
			org.w3c.dom.Document document = null;
			
			dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			document = db.parse(new java.io.ByteArrayInputStream(x.getBytes()));
			
			org.w3c.dom.NodeList repositoryNodeList = document.getElementsByTagName("repository");
			int numRepositories = repositoryNodeList.getLength();
			for (int i=0; i < numRepositories; i++) {
				org.w3c.dom.Element repositoryElement = (org.w3c.dom.Element)repositoryNodeList.item(i);
				
				// if there is both a display name and an id, add it to the result -- storing the ids away for later
				org.w3c.dom.NodeList nodeList = repositoryElement.getElementsByTagName("displayName");
				int numNodes = nodeList.getLength();
				if (numNodes > 0) {
					org.w3c.dom.Element element = (org.w3c.dom.Element)nodeList.item(0);
					if (element.hasChildNodes()) {
						String displayName = (element.getFirstChild().getNodeValue());

						nodeList = repositoryElement.getElementsByTagName("id");
						numNodes = nodeList.getLength();
						if (numNodes > 0) {
							element = (org.w3c.dom.Element)nodeList.item(0);
							if (element.hasChildNodes()) {
								resultVector.addElement(displayName);
								_repositoryIdVector.addElement(element.getFirstChild().getNodeValue());
							}
						}
					}
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return resultVector;
	}
	
	private void doSave()
	{
		//TODO: add code here
	}
	
	private void doMigration()
	{
		// 1 source must be selected
		// 1 or more target must be selected
		int index = _sourceList.getSelectedIndex();
		if (index == -1) {
			javax.swing.JOptionPane.showMessageDialog(null,UNSELECTED_SOURCE_ERROR,TITLE,javax.swing.JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// look at either source loaded from a profile or selected from a list of all
		String sourceRepositoryId = null;
		if (_isEditing) {
			sourceRepositoryId = (String)_repositoryIdVector.elementAt(index);
		} else {
			sourceRepositoryId = _profileSourceRepositoryId;
		}
		
		int selections[] = _targetsList.getSelectedIndices();
		if (selections.length == 0) {
			javax.swing.JOptionPane.showMessageDialog(null,UNSELECTED_TARGET_ERROR,TITLE,javax.swing.JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		try {
			StringBuffer sb = new StringBuffer("http://localhost:8080/migrator/migrate.jsp?token=");
			sb.append(_token);
			
			sb.append("&source=");
			sb.append(sourceRepositoryId);
			
			for (int t=0; t < selections.length; t++) {
				sb.append("&target");
				sb.append(t);
				sb.append("=");
				if (_isEditing) {
					sb.append((String)_repositoryIdVector.elementAt(selections[t]));
				} else {
					sb.append((String)_profileSourceRepositoryIdVector.elementAt(selections[t]));
				}
			}
			
			selections = _crosswalkList.getSelectedIndices();
			for (int t=0; t < selections.length; t++) {
				sb.append("&crosswalk");
				sb.append(t);
				sb.append("=");
				String s = (String)_crosswalkListModel.elementAt(selections[t]);
				s = java.net.URLEncoder.encode(s,"UTF-8");
				sb.append(s);
			}
			
			selections = _assetxformList.getSelectedIndices();
			for (int t=0; t < selections.length; t++) {
				sb.append("&assetxform");
				sb.append(t);
				sb.append("=");
				String s = (String)_assetxformListModel.elementAt(selections[t]);
				s = java.net.URLEncoder.encode(s,"UTF-8");
				sb.append(s);
			}
			
			selections = _objectxformList.getSelectedIndices();
			for (int t=0; t < selections.length; t++) {
				sb.append("&objectxform");
				sb.append(t);
				sb.append("=");
				String s = (String)_objectxformListModel.elementAt(selections[t]);
				s = java.net.URLEncoder.encode(s,"UTF-8");
				sb.append(s);
			}
			
			String query = sb.toString();
			System.out.println("query " + query);
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
			String x = xml.toString().trim();
			System.out.println("xml " + x);
			
			// Parse XML for Repository elements
			javax.xml.parsers.DocumentBuilderFactory dbf = null;
			javax.xml.parsers.DocumentBuilder db = null;
			org.w3c.dom.Document document = null;
			
			dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			document = db.parse(new java.io.ByteArrayInputStream(x.getBytes()));
			
			org.w3c.dom.NodeList nodeList = document.getElementsByTagName("status");
			int num = nodeList.getLength();
			if (num > 0) {
				org.w3c.dom.Element e = (org.w3c.dom.Element)nodeList.item(0);
				if (e.hasChildNodes()) {
					javax.swing.JOptionPane.showMessageDialog(null,
															  e.getFirstChild().getNodeValue(),
															  AUDIT_REPORT_TITLE,
															  javax.swing.JOptionPane.INFORMATION_MESSAGE);
				}
			}			
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	private void loadProfile()
	{
		System.out.println("selected " + _profileList.getSelectedValue());
		_profilePanel.setVisible(false);
		doGetProfile((String)(_profileList.getSelectedValue()));
	}
	
	private void saveAs()
	{
		// prompt to confirm overwrite
		String name = _saveAsField.getText().trim();
		if (name.length() == 0) {
			javax.swing.JOptionPane.showMessageDialog(null,ZERO_LENGTH_PROFILE_NAME_ERROR,TITLE,javax.swing.JOptionPane.ERROR_MESSAGE);
			return;
		}
		int index = _profileListModel.indexOf(name);
		if (index != -1) {
			if (javax.swing.JOptionPane.showConfirmDialog(null,
														  OVERWRITE_PROFILE,
														  TITLE,
														  javax.swing.JOptionPane.OK_CANCEL_OPTION) == javax.swing.JOptionPane.CANCEL_OPTION);
			return;
		}
		
		// try and save
		doSave();
	}
	
	private void edit()
	{
		java.util.Vector resultVector = doGetRepositoryList();
		if (resultVector.size() == 0) {
			javax.swing.JOptionPane.showMessageDialog(null,REPOSITORY_LIST_ERROR,TITLE,javax.swing.JOptionPane.ERROR_MESSAGE);
		} else {
			_isEditing = true;
			// a request to edit necessitates a save / save as
			_sourceList.setEnabled(true);
			_targetsList.setEnabled(true);
			_crosswalkList.setEnabled(true);
			_assetxformList.setEnabled(true);
			_objectxformList.setEnabled(true);
			_saveAsButton.setEnabled(true);
			_saveAsField.setEditable(true);
			
			_sourceListModel.removeAllElements();
			_targetsListModel.removeAllElements();
			for (int i=0, size = resultVector.size(); i < size; i++) {
				_sourceListModel.addElement(resultVector.elementAt(i));
				_targetsListModel.addElement(resultVector.elementAt(i));
			}
		}
	}
	
	private void migration()
	{
		doMigration();
	}
	
	public static void main(String args[])
	{
		Migrator m = new Migrator();
	}
}