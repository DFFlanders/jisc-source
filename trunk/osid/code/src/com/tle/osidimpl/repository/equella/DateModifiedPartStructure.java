package com.tle.osidimpl.repository.equella;

public class DateModifiedPartStructure
implements org.osid.repository.PartStructure
{
	private Configuration configuration = Configuration.getInstance();
    private String displayName = "Modified Date";
    private String description = "Date of Last Modification";
    private String idString = "LastModifiedDateStringPartStructureId";
    private org.osid.shared.Id id = null;
    private org.osid.shared.Type type = new Type("mit.edu","partStructure","lastModifiedDateString","Modified Date");
	private static DateModifiedPartStructure dateModifiedPartStructure = new DateModifiedPartStructure();
	
	public static DateModifiedPartStructure getInstance()
	{
		return dateModifiedPartStructure;
	}
	
    protected DateModifiedPartStructure()
    {
        try
	{
		this.id = this.configuration.getIdManager().getId(this.idString);
	}
        catch (Throwable t)
	{
            Utilities.log(t);
	}
    }
	
    public String getDisplayName()
		throws org.osid.repository.RepositoryException
    {
        return this.displayName;
    }
	
    public String getDescription()
		throws org.osid.repository.RepositoryException
    {
        return this.description;
    }
	
    public org.osid.shared.Id getId()
		throws org.osid.repository.RepositoryException
    {
        return this.id;
    }
	
    public org.osid.shared.Type getType()
		throws org.osid.repository.RepositoryException
    {
        return this.type;
    }
	
    public org.osid.repository.PartStructureIterator getPartStructures()
		throws org.osid.repository.RepositoryException
    {
        return new PartStructureIterator(new java.util.Vector());
    }
	
    public org.osid.repository.RecordStructure getRecordStructure()
		throws org.osid.repository.RepositoryException
    {
        return new RecordStructure();
    }
	
    public boolean isMandatory()
		throws org.osid.repository.RepositoryException
    {
        return false;
    }
	
    public boolean isPopulatedByRepository()
		throws org.osid.repository.RepositoryException
    {
        return false;
    }
	
    public boolean isRepeatable()
		throws org.osid.repository.RepositoryException
    {
        return false;
    }
	
    public boolean validatePart(org.osid.repository.Part part)
		throws org.osid.repository.RepositoryException
    {
        return true;
    }
	
    public void updateDisplayName(String displayName)
		throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }
}
