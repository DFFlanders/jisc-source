package com.intrallect.osidimpl.repository.intralibrary;

public class Repository
implements org.osid.repository.Repository
{
    private java.util.Vector _assetVector = new java.util.Vector();
	private Configuration _configuration = Configuration.getInstance();
	
	public String getDisplayName()
		throws org.osid.repository.RepositoryException
    {
        return _configuration.getRepositoryDisplayName();
    }

    public void updateDisplayName(String displayName)
		throws org.osid.repository.RepositoryException
    {
        if (displayName == null) {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public String getDescription()
		throws org.osid.repository.RepositoryException
    {
        return _configuration.getRepositoryDescription();
    }

    public void updateDescription(String description)
    throws org.osid.repository.RepositoryException
    {
        if (description == null) {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public org.osid.shared.Id getId()
		throws org.osid.repository.RepositoryException
    {
        return _configuration.getRepositoryId();
    }

    public org.osid.shared.Type getType()
		throws org.osid.repository.RepositoryException
    {
        return _configuration.getRepositoryType();
    }

    public org.osid.repository.Asset createAsset(String displayName,
                                                 String description,
                                                 org.osid.shared.Type assetType)
		throws org.osid.repository.RepositoryException
    {
		return new Asset(_configuration.upload(displayName));
    }

    public void deleteAsset(org.osid.shared.Id assetId)
		throws org.osid.repository.RepositoryException
    {
        if (assetId == null) {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public org.osid.repository.AssetIterator getAssets()
		throws org.osid.repository.RepositoryException
    {
		/* Modify this if browsing is possible.  One straightforward approach is to have a container asset type.  Assets
		   of this type exist soley to hold other assets.  Use the Asset.getAssets() method to expose successive layers
		   of containment.
		*/
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public org.osid.repository.AssetIterator getAssetsByType(org.osid.shared.Type assetType)
		throws org.osid.repository.RepositoryException
    {
        if (assetType == null) {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }
		// modify this if there is more than one asset type
		return getAssets();
    }

    public org.osid.shared.TypeIterator getAssetTypes()
		throws org.osid.repository.RepositoryException
    {
		return _configuration.getAssetTypes();
    }

    public org.osid.repository.RecordStructureIterator getRecordStructures()
		throws org.osid.repository.RepositoryException
    {
		// Modify this if needed.  Multiple asset types might warrant more record structures.  
		// Another case is different metadata groupings.
        java.util.Vector results = new java.util.Vector();
        results.addElement(new RecordStructure());
        return new RecordStructureIterator(results);
    }

    public org.osid.repository.RecordStructureIterator getRecordStructuresByType(org.osid.shared.Type recordStructureType)
		throws org.osid.repository.RepositoryException
    {
        if (recordStructureType == null) {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }

		java.util.Vector results = new java.util.Vector();
		org.osid.repository.RecordStructureIterator recordStructureIterator = getRecordStructures();
		while (recordStructureIterator.hasNextRecordStructure()) {
			org.osid.repository.RecordStructure recordStructure = recordStructureIterator.nextRecordStructure();
			if (recordStructure.getType().isEqual(recordStructureType)) {
				results.addElement(recordStructure);
			}
		}
		return new RecordStructureIterator(results);
    }
	
    public org.osid.repository.RecordStructureIterator getMandatoryRecordStructures(org.osid.shared.Type assetType)
		throws org.osid.repository.RepositoryException
    {
        if (assetType == null) {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }
		// modify this if record strucutres vary with asset type 
		return getRecordStructures();
    }

    public org.osid.shared.TypeIterator getSearchTypes()
		throws org.osid.repository.RepositoryException
    {
		return _configuration.getSearchTypes();
    }

    public org.osid.shared.TypeIterator getStatusTypes()
		throws org.osid.repository.RepositoryException
    {
		// modify this if assets have status
        java.util.Vector results = new java.util.Vector();
        try {
            results.addElement(new Type("mit.edu","asset","valid"));
            return new TypeIterator(results);
        } catch (Throwable t) {
            Utilities.log(t.getMessage());
            throw new org.osid.repository.RepositoryException(org.osid.repository.RepositoryException.OPERATION_FAILED);
        }
    }

    public org.osid.shared.Type getStatus(org.osid.shared.Id assetId)
		throws org.osid.repository.RepositoryException
    {
		// modify this if assets have status
        return new Type("mit.edu","asset","valid");
    }

    public boolean validateAsset(org.osid.shared.Id assetId)
		throws org.osid.repository.RepositoryException
    {
        return true;
    }

    public void invalidateAsset(org.osid.shared.Id assetId)
		throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public org.osid.repository.Asset getAsset(org.osid.shared.Id assetId)
		throws org.osid.repository.RepositoryException
    {
        if (assetId == null) {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }
		try {
			org.osid.repository.Asset asset = _configuration.singleAssetSearch(assetId);
			if (asset != null) return asset;
		} catch (Throwable t) {
			Utilities.log(t);
		}
		throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.UNKNOWN_ID);		
    }

    public org.osid.repository.Asset getAssetByDate(org.osid.shared.Id assetId,
                                                   long date)
		throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public org.osid.shared.LongValueIterator getAssetDates(org.osid.shared.Id assetId)
		throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

    public org.osid.repository.AssetIterator getAssetsBySearch(java.io.Serializable searchCriteria,
                                                               org.osid.shared.Type searchType,
                                                               org.osid.shared.Properties searchProperties)
		throws org.osid.repository.RepositoryException
    {
		if ( (searchCriteria == null) || (searchType == null) ) {
			throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
		}
		return _configuration.assetsSearch(searchCriteria, searchType);
    }

    public org.osid.shared.Id copyAsset(org.osid.repository.Asset asset)
		throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }

	// use these properties-related methods if you want to offer some metadata
    public org.osid.shared.PropertiesIterator getProperties()
		throws org.osid.repository.RepositoryException
    {
        try {
            return new PropertiesIterator(new java.util.Vector());
        } catch (Throwable t) {
            Utilities.log(t.getMessage());
            throw new org.osid.repository.RepositoryException(org.osid.repository.RepositoryException.OPERATION_FAILED);
        }        
    }

	// use these properties-related methods if you want to offer some metadata
    public org.osid.shared.Properties getPropertiesByType(org.osid.shared.Type propertiesType)
		throws org.osid.repository.RepositoryException
    {
        if (propertiesType == null) {
            throw new org.osid.repository.RepositoryException(org.osid.shared.SharedException.NULL_ARGUMENT);
        }
		try {
			return new SharedProperties();
		} catch (Throwable t) {
		}
		return null;
    }

	// use these properties-related methods if you want to offer some metadata
    public org.osid.shared.TypeIterator getPropertyTypes()
		throws org.osid.repository.RepositoryException
    {
        try {
            return new TypeIterator(new java.util.Vector());
        } catch (Throwable t) {
            Utilities.log(t.getMessage());
            throw new org.osid.repository.RepositoryException(org.osid.repository.RepositoryException.OPERATION_FAILED);
        }        
    }

    protected void addAsset(org.osid.repository.Asset asset)
		throws org.osid.repository.RepositoryException
    {
        _assetVector.addElement(asset);
    }

    public boolean supportsUpdate()
		throws org.osid.repository.RepositoryException
    {
        return true;
    }

    public boolean supportsVersioning()
		throws org.osid.repository.RepositoryException
    {
		// modify this if updating is supported
        return false;
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
