/*
	Present a form with the list of repositories that can be searched.
	Provide a keyword edit control.
	Provide a search button.
	Provide a results table.
	Allow selection of one or more rows.
	Provide a list of targets.
	Fill in column with comment on migration.
	Go button
	Results dialog
*/
package edu.birkbeck.tools;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.util.*;
import org.osid.repository.*;

public class MigrationTool extends JFrame implements ActionListener, ListSelectionListener
{
	private static final String APPLICATION_TITLE = "Migration Tool";
	private static final String SEARCH_ACTION = "Search";
	private static final String MIGRATE_ACTION = "Migrate";
	private static final String SOURCES_PROMPT = "Source(s): ";
	private static final String CRITERIA_PROMPT = "Keyword(s): ";
	private static final String RESULTS_PROMPT = "Result(s): ";
	private static final String NOT_AVAILABLE = "Not Available";
	private static final String ASSET_ID_COLUMN_HEAD = "Id";
	private static final String REPOISITORY_COLUMN_HEAD = "Source";
	private static final String ASSET_NAME_COLUMN_HEAD = "Asset";
	private static final String COPYRIGHT = "Copyright (c) 2007, Birkbeck College Library.";
	private static final int SPLASH_SLEEP = 2000;
	private static final String ERROR_TITLE = "Error";
	private static final String ERROR_NO_CRITERIA = "You must specify search criteria";
	private static final String ERROR_SEARCHING = "There was a problem performing the search or fetching the results";	
	private static final String ERROR_MIGRATING = "There was a problem performing the migration";	
	private edu.calstate.osidutil.SearchUtilities _searchUtilities = null;
	private JTextField _criteriaTextField = null;
	
	private String _columnHeads[] = new String[3];
	private DefaultTableModel _tableModel = null;
    private JTable _table = null;
	private JScrollPane _tableJSP = null;
	private ListSelectionModel _resultsListSelectionModel = null;
	private Map _assetMap = new HashMap();
	private JButton _migrateButton = new JButton(MIGRATE_ACTION);
	
	public MigrationTool()
	{
		super(APPLICATION_TITLE);
		try {

			//JSplitPane splitPane = new JSplitPane();
			splash();
			
			_columnHeads[0] = ASSET_ID_COLUMN_HEAD;        
			_columnHeads[1] = REPOISITORY_COLUMN_HEAD;
			_columnHeads[2] = ASSET_NAME_COLUMN_HEAD;
			_tableModel = new DefaultTableModel(_columnHeads,0);
            _table = new JTable(_tableModel);
            _table.setGridColor(Color.black);
            _table.setIntercellSpacing(new Dimension(10,1));
            _table.setPreferredScrollableViewportSize(new Dimension(550,200));
			_tableJSP = new JScrollPane(_table);
			_resultsListSelectionModel = _table.getSelectionModel();
			_resultsListSelectionModel.addListSelectionListener(this);
			_migrateButton.addActionListener(this);

			// initialize OSID provider and search support
			edu.calstate.osidutil.ProviderUtilities providerUtilities = new edu.calstate.osidutil.ProviderUtilities("providerConfig.xml",true);
			_searchUtilities = new edu.calstate.osidutil.SearchUtilities(providerUtilities);
			
			// list of repositories to be queried during search
			DefaultListModel searchRepositoryListModel = new DefaultListModel();
			JList searchRepositoryList = new JList(searchRepositoryListModel);
			searchRepositoryList.setSelectionMode(DefaultListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			//searchRepositoryList.addSelectionListener(this);
			JScrollPane searchRepositoryListJSP = new JScrollPane(searchRepositoryList);
			
			// initialize other controls
			_criteriaTextField = new JTextField(20);
			JButton searchButton = new JButton(SEARCH_ACTION);
			searchButton.addActionListener(this);
			
			// populate search repositories list
			Vector searchRepositoryVector = providerUtilities.getRepositories();
/*			Vector searchRepositoryVector = new Vector();
			searchRepositoryVector.addElement("foo1");
			searchRepositoryVector.addElement("foo2");
			searchRepositoryVector.addElement("foo3");
			searchRepositoryVector.addElement("foo4");
			searchRepositoryVector.addElement("foo5");
			searchRepositoryVector.addElement("foo6");
			searchRepositoryVector.addElement("foo7");
			searchRepositoryVector.addElement("foo8");
			searchRepositoryVector.addElement("foo9");
			searchRepositoryVector.addElement("foo10");
*/			for (int i=0, size=searchRepositoryVector.size(); i < size; i++) {
				org.osid.repository.Repository r = (org.osid.repository.Repository)searchRepositoryVector.elementAt(i);
				searchRepositoryListModel.addElement(r.getDisplayName());
			}
			
			// layout controls
			GridBagLayout gbLayout = new GridBagLayout();
			setLayout(gbLayout);
            GridBagConstraints gbConstraints = new GridBagConstraints();
            gbConstraints.insets = new java.awt.Insets(2,2,2,2);			
			//gbConstraints.gridwidth = 2;
            //gbConstraints.fill=GridBagConstraints.BOTH;
            
			gbConstraints.gridx=0;
			gbConstraints.gridy=0;
			gbConstraints.anchor=GridBagConstraints.WEST;
			add(new JLabel(SOURCES_PROMPT),gbConstraints);
			
			gbConstraints.gridx=1;
			gbConstraints.gridy=0;
			gbConstraints.weightx=1;
            gbConstraints.weighty=0;
			add(searchRepositoryListJSP,gbConstraints);
			
			gbConstraints.gridx=0;
			gbConstraints.gridy=1;
			add(new JLabel(CRITERIA_PROMPT),gbConstraints);

			gbConstraints.gridx=1;
			gbConstraints.gridy=1;
			add(_criteriaTextField,gbConstraints);
			
			gbConstraints.gridx=0;
			gbConstraints.gridy=2;
			add(searchButton,gbConstraints);
			
			gbConstraints.gridx=0;
			gbConstraints.gridy=3;
			add(new JLabel(RESULTS_PROMPT),gbConstraints);
			
			gbConstraints.gridx=1;
			gbConstraints.gridy=3;
			adjustColumnWidths();
			add(_tableJSP,gbConstraints);

			gbConstraints.gridx=0;
			gbConstraints.gridy=4;
			add(_migrateButton,gbConstraints);
			_migrateButton.setEnabled(false);
			
			setSize(650,500);
			setVisible(true);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	private void splash()
	{
		JWindow splash = new javax.swing.JWindow();
		JPanel content = (JPanel)splash.getContentPane();
		Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - 300) / 2;
		int y = (screen. height - 50) / 2;
		splash.setBounds(x, y, 300, 50);
		JLabel titleLabel = new JLabel(APPLICATION_TITLE,JLabel.CENTER);		
		titleLabel.setFont(new Font("serif",
									Font.BOLD, 
									14));
		
		JLabel copyrightLabel = new JLabel(COPYRIGHT,JLabel.CENTER);
		copyrightLabel.setFont(new java.awt.Font("sansserif",
												 Font.PLAIN, 
												 12));
		content.add(titleLabel,java.awt.BorderLayout.NORTH);
		content.add(copyrightLabel,java.awt.BorderLayout.SOUTH);
		splash.setVisible(true);
		try { 
			Thread.sleep(SPLASH_SLEEP); 
		} 
		catch (Exception ex) {}
		splash.setVisible(false);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if (ae.getActionCommand().equals(SEARCH_ACTION)) {
			String criteria = _criteriaTextField.getText();
			if ((criteria == null) || (criteria.length() == 0)) {
				errorAlert(ERROR_NO_CRITERIA);
				return;
			}
			System.out.println("criteria: " + criteria);
			try {
				clearResults();
				Vector resultVector = new Vector();
				AssetIterator assetIterator = _searchUtilities.keywordSearch(criteria);
				while (assetIterator.hasNextAsset()) {
					Asset nextAsset = assetIterator.nextAsset();
					
					String assetIdString = NOT_AVAILABLE;
					String displayName = NOT_AVAILABLE;
                    String repositoryName = NOT_AVAILABLE;
					org.osid.shared.Id assetId = null;
					org.osid.shared.Id repositoryId = null;
					
					try {
						assetId = nextAsset.getId();
						assetIdString = assetId.getIdString();
					} catch (Throwable t) {
					}
					try {
						repositoryName = _searchUtilities.getRepositoryNameForAsset(nextAsset.getRepository());
					} catch (Throwable t) {
					}
					try {
						displayName = nextAsset.getDisplayName();
					} catch (Throwable t) {
					}
					try {
						repositoryId = nextAsset.getRepository();
					} catch (Throwable t) {
					}
					
					Vector resultRow = new Vector();
                    resultRow.addElement(assetIdString);
                    resultRow.addElement(repositoryName);
                    resultRow.addElement(displayName);
					_assetMap.put(assetIdString,nextAsset);
					resultVector.addElement(resultRow);
					
					System.out.println("Next result " + nextAsset.getDisplayName());
					_tableModel.addRow(resultRow);
				}
			} catch (Throwable t) {
				t.printStackTrace();
				errorAlert(ERROR_SEARCHING);
			}
		} else if (ae.getActionCommand().equals(MIGRATE_ACTION)) {
			try {
				int firstRow = _table.getSelectionModel().getMinSelectionIndex();
				int lastRow = _table.getSelectionModel().getMaxSelectionIndex();
				for (int i=firstRow; i <= lastRow; i++) {
					System.out.println("Selected  1" + _tableModel.getValueAt(i,0));
					System.out.println("Selected 2 " + _tableModel.getValueAt(i,1));
					try {
						org.osid.repository.Asset a = (org.osid.repository.Asset)_assetMap.get(_tableModel.getValueAt(i,0));
						System.out.println(a.getDescription());
						migrateToHive(a.getDisplayName(),a.getDescription());
					} catch (Throwable t) {
						t.printStackTrace();
						errorAlert(ERROR_MIGRATING);
					}
				}
			} catch (Throwable t) {
				t.printStackTrace();
				errorAlert(ERROR_MIGRATING);
			}
		}
	}

	public void valueChanged(ListSelectionEvent lse)
	{
		if (!lse.getValueIsAdjusting()) {
			_migrateButton.setEnabled(true);
		}
	}
	
	private void errorAlert(String message)
	{
		JOptionPane.showMessageDialog(null,
									  message,
									  ERROR_TITLE,
									  JOptionPane.ERROR_MESSAGE);
	}
	
    private void adjustColumnWidths()
    {
		int col0Width = 250;
		int col1Width = 150;
		int col2Width = 250;
		
		TableColumnModel tableColumnModel = _table.getColumnModel();
		TableColumn tableColumn = tableColumnModel.getColumn(0);
		tableColumn.setWidth(col0Width);
		tableColumn.setMaxWidth(col0Width);
		tableColumn.setMinWidth(col0Width);
		
		tableColumn = tableColumnModel.getColumn(1);
		tableColumn.setWidth(col1Width);
		tableColumn.setMaxWidth(col1Width);
		tableColumn.setMinWidth(col1Width);
		
		tableColumn = tableColumnModel.getColumn(2);
		tableColumn.setWidth(col2Width);
		tableColumn.setMaxWidth(col2Width);
		tableColumn.setMinWidth(col2Width);
	}

	private void clearResults()
	{
		int numRows = _tableModel.getRowCount()-1;
		for (int i=numRows; i >= 0; i--) _tableModel.removeRow(i);
		_migrateButton.setEnabled(false);
		_assetMap.clear();
	}
		
	private void migrateToHive(String name, String description)
	{
		org.osid.repository.RepositoryManager repositoryManager = null;
		try {
			org.osid.OsidContext context = new org.osid.OsidContext();
			java.util.Properties properties = new java.util.Properties();
			properties.setProperty("hiveDisplayName","HarvestRoad Hive");
			properties.setProperty("hiveUsername","jkahn");
			properties.setProperty("hivePassword","jkverbena");
			properties.setProperty("hiveHost","bazzim.mit.edu");
			properties.setProperty("hivePort","80");
			
			repositoryManager = (org.osid.repository.RepositoryManager)org.osid.OsidLoader.getManager("org.osid.repository.RepositoryManager",
																									  "com.harvestroad.osidimpl.repository.hive.publish",
																									  context,
																									  properties);
			boolean published = false;
			org.osid.repository.RepositoryIterator repositoryIterator = repositoryManager.getRepositories();
			while (repositoryIterator.hasNextRepository() && !published) {
				org.osid.repository.Repository repository = repositoryIterator.nextRepository();
				org.osid.repository.AssetIterator assetIterator = repository.getAssetsByType(new Type("com.harvestroad","asset","category"));
				while (assetIterator.hasNextAsset() && !published) {
					org.osid.repository.Asset asset = assetIterator.nextAsset();
					System.out.println("Asset: " + asset.getDisplayName());
					if (asset.getDisplayName().equals("IntraLibrary")) {
						System.out.println("Found the right place to add an asset");
						org.osid.repository.Asset newAsset = repository.createAsset(name,description,new Type("com.harvestroad","asset","upload"));
						org.osid.repository.Record record = newAsset.createRecord(com.harvestroad.osidimpl.repository.hive.publish.DublinCoreRecordStructure.getInstance().getId());
						record.createPart(com.harvestroad.osidimpl.repository.hive.publish.FormatPartStructure.getInstance().getId(),"zip");
						asset.addAsset(newAsset.getId());
						published = true;
					}
				}
			} 		
		} catch (Throwable t) {
			System.out.println(t.getMessage());
			t.printStackTrace();
		} finally {
			if (repositoryManager != null) {
				((com.harvestroad.osidimpl.repository.hive.publish.RepositoryManager)repositoryManager).quit();
			}
		}
	}

	public static void main(String args[])
	{
		MigrationTool mt = new MigrationTool();
	}
}