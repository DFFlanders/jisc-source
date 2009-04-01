package com.tle.osidimpl.repository.equella;

public class Part
implements org.osid.repository.Part
{
    private org.osid.repository.PartStructure partStructure = null;
    private org.osid.shared.Id partStructureId = null;
    private java.io.Serializable value = null;
    private String displayName = null;
    private org.osid.shared.Id id = null;
	
    public String getDisplayName()
		throws org.osid.repository.RepositoryException
    {
        return this.displayName;
    }
	
    public org.osid.shared.Id getId()
		throws org.osid.repository.RepositoryException
    {
        return this.id;
    }
	
    protected Part(org.osid.shared.Id partStructureId
				   , java.io.Serializable value)
		throws org.osid.repository.RepositoryException
    {
        this.partStructureId = partStructureId;
		
        this.value = value;
        try
        {
			if (partStructureId.isEqual(TitlePartStructure.getInstance().getId())) {
				this.partStructure = TitlePartStructure.getInstance();
				this.id = Configuration.getInstance().getIdManager().getId("Title.Part");
			} else if (partStructureId.isEqual(DescriptionPartStructure.getInstance().getId())) {
				this.partStructure = DescriptionPartStructure.getInstance();
				this.id = Configuration.getInstance().getIdManager().getId("Description.Part");
			} else if (partStructureId.isEqual(DatePartStructure.getInstance().getId())) {
				this.partStructure = DatePartStructure.getInstance();
				this.id = Configuration.getInstance().getIdManager().getId("Date.Part");
			} else if (partStructureId.isEqual(DateCreatedPartStructure.getInstance().getId())) {
				this.partStructure = DateCreatedPartStructure.getInstance();
				this.id = Configuration.getInstance().getIdManager().getId("DateCreated.Part");
			} else if (partStructureId.isEqual(DateModifiedPartStructure.getInstance().getId())) {
				this.partStructure = DateModifiedPartStructure.getInstance();
				this.id = Configuration.getInstance().getIdManager().getId("DateModified.Part");
			} else if (partStructureId.isEqual(DateForIndexPartStructure.getInstance().getId())) {
				this.partStructure = DateForIndexPartStructure.getInstance();
				this.id = Configuration.getInstance().getIdManager().getId("DateForIndex.Part");
			} else if (partStructureId.isEqual(FileDisplayNamePartStructure.getInstance().getId())) {
				this.partStructure = FileDisplayNamePartStructure.getInstance();
				this.id = Configuration.getInstance().getIdManager().getId("FileDisplayName.Part");
			} else if (partStructureId.isEqual(FileNamePartStructure.getInstance().getId())) {
				this.partStructure = FileNamePartStructure.getInstance();
				this.id = Configuration.getInstance().getIdManager().getId("FileName.Part");
			} else if (partStructureId.isEqual(FileSizePartStructure.getInstance().getId())) {
				this.partStructure = FileSizePartStructure.getInstance();
				this.id = Configuration.getInstance().getIdManager().getId("FileSize.Part");
			} else if (partStructureId.isEqual(NewItemPartStructure.getInstance().getId())) {
				this.partStructure = NewItemPartStructure.getInstance();
				this.id = Configuration.getInstance().getIdManager().getId("NewItem.Part");
			} else if (partStructureId.isEqual(OwnerIdPartStructure.getInstance().getId())) {
				this.partStructure = OwnerIdPartStructure.getInstance();
				this.id = Configuration.getInstance().getIdManager().getId("OwnerId.Part");
			} else if (partStructureId.isEqual(RatingAvgPartStructure.getInstance().getId())) {
				this.partStructure = RatingAvgPartStructure.getInstance();
				this.id = Configuration.getInstance().getIdManager().getId("RatingAvg.Part");
			} else if (partStructureId.isEqual(SizeInKBPartStructure.getInstance().getId())) {
				this.partStructure = SizeInKBPartStructure.getInstance();
				this.id = Configuration.getInstance().getIdManager().getId("SizeInKB.Part");
			} else if (partStructureId.isEqual(URLPartStructure.getInstance().getId())) {
				this.partStructure = URLPartStructure.getInstance();
				this.id = Configuration.getInstance().getIdManager().getId("URL.Part");
			}
        }
        catch (Throwable t)
        {
            Utilities.log(t.getMessage());
            throw new org.osid.repository.RepositoryException(org.osid.repository.RepositoryException.OPERATION_FAILED);
        }
    }
	
    public org.osid.repository.Part createPart(org.osid.shared.Id partStructureId
											   , java.io.Serializable value)
		throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }
	
    public void deletePart(org.osid.shared.Id partStructureId)
		throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }
	
    public void updateDisplayName(String displayName)
		throws org.osid.repository.RepositoryException
    {
        throw new org.osid.repository.RepositoryException(org.osid.OsidException.UNIMPLEMENTED);
    }
	
    public org.osid.repository.PartIterator getParts()
		throws org.osid.repository.RepositoryException
    {
        return new PartIterator(new java.util.Vector());
    }
	
    public org.osid.repository.PartStructure getPartStructure()
		throws org.osid.repository.RepositoryException
    {
		return this.partStructure;
    }
	
    public java.io.Serializable getValue()
		throws org.osid.repository.RepositoryException
    {
		return this.value;
    }
	
    public void updateValue(java.io.Serializable value)
		throws org.osid.repository.RepositoryException
    {
        this.value = value;
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
