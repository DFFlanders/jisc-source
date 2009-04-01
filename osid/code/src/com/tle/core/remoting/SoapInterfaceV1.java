package com.tle.core.remoting;

import com.dytech.edge.common.valuebean.ItemKey;

/**
 * Copyright of The Learning Edge International.
 * Not for redistribution.
 */
public interface SoapInterfaceV1
{
	String login(String username, String password);

	String loginWithToken(String token);

	void logout(String ssid);

	/**
	 * Returns xml that can be used as a template for creating a new item using
	 * "stopEdit" or "saveEdit" <br>
	 * It will be initialised with a new UUID and a new staging UUID, which can
	 * be used to upload attachments with
	 *
	 * @param ss_uuid The soap session id
	 * @return The xml for the item
	 * @throws RuntimeApplicationException
	 */
	String newItem(String ss_uuid, String itemdefid) throws Exception;

	/**
	 * Edit an already existing item. The item is locked for editing, and
	 * attachments are copied to a staging folder if requested by bModifyAttach
	 *
	 * @param ss_uuid The soap session id
	 * @param uuid The uuid of the item
	 * @param version The version of the item
	 * @param itemdefid The item definition the item belongs to
	 * @param bModifyAttach Whether or not you want to edit the attached files
	 *            for this item
	 * @return The xml for the item, which you can edit
	 * @throws RuntimeApplicationException
	 */
	String startEdit(String ss_uuid, String uuid, int version, String itemdefid,
		boolean bModifyAttach) throws Exception;

	/**
	 * Stop editing an item and save changes made to an item also unlocks the
	 * item <br>
	 * Before calling this, you should either use "startEdit" or "newItem"
	 *
	 * @param ss_uuid The soap session id
	 * @param itemXML The item's xml
	 * @param bSubmit Whether or not to submit the item for moderation
	 * @throws RuntimeApplicationException
	 * @dytech.jira see Jira System Change Request (SCR) TLE-683 : http://apps.dytech.com.au/jira/browse/TLE-683
	 */
	String stopEdit(String ss_uuid, String itemXML, boolean bSubmit) throws Exception;

	/**
	 * Cancel the editing of a previous "startEdit" call Unlocks the item
	 *
	 * @param ss_uuid The soap session id
	 * @param uuid The uuid of the item
	 * @param version The version of the item
	 * @param itemdefid The item definition the item belongs to
	 * @throws RuntimeApplicationException
	 */
	void cancelEdit(String ss_uuid, String uuid, int version, String itemdefid) throws Exception;

	/**
	 * Force the unlocking of an item (must be the owner of the lock, unless
	 * you're an admin)
	 *
	 * @param ss_uuid The soap session id
	 * @param uuid The uuid of the item
	 * @param version The version of the item
	 * @param itemdefid The item definition the item belongs to
	 * @throws RuntimeApplicationException
	 */
	void forceUnlock(String ss_uuid, String uuid, int version, String itemdefid) throws Exception;

	/**
	 * Provides a soap access method for removal of items from repository
	 *
	 * @param ss_uuid the uuid of the user's soap session
	 * @throws RuntimeApplicationException
	 */
	void deleteItem(String ss_uuid, String uuid, int version, String itemdef_uuid) throws Exception;

	/**
	 * Upload a file into the staging area of the repository
	 *
	 * @param ss_uuid soap session uuid
	 * @param stagingid uuid of the item this is an attachment for
	 * @param filename
	 * @param data file bytes as a base64 encoded String
	 * @return file bytes as a base64 encoded String
	 * @throws RuntimeApplicationException
	 * @see com.dytech.devlib.Base64 To Do: Apply security check on soap
	 *      session
	 */
	void uploadAttachment(String ss_uuid, String stagingid, String filename, String data,
		boolean overwrite) throws Exception;

	/**
	 *
	 * @param soapsession_id
	 * @param stagingid
	 * @param file_name
	 * @throws RuntimeApplicationException
	 */
	void deleteAttachment(String soapsession_id, String stagingid, String file_name)
		throws Exception;

	void unzipFile(String soapsession_id, String uuid, String zipfile, String outpath)
		throws Exception;

	/**
	 * Search items using an xml description of the Search Request parameters.
	 *
	 * <code><pre>
	 * &lt;com.dytech.edge.common.valuebean.SearchRequest>
	 *  &lt;query>search text&lt;/query>
	 *  &lt;select>*&lt;/select>
	 *  &lt;where>where /xml/item/name like '%hello%'&lt;/where>
	 *  &lt;orderby>ORDER BY /xml/item/sortorder DESC&lt;/orderby>
	 *  &lt;onlyLive>false&lt;/onlyLive>
	 *  &lt;orderType>2&lt;/orderType>
	 *  &lt;sortReverse>false&lt;/sortReverse>
	 *  &lt;itemdefs class="list">
	 *   &lt;string>uuid1&lt;/string>
	 *   &lt;string>uuid2&lt;/string>
	 *   &lt;string>uuid3&lt;/string>
	 *  &lt;/itemdefs>
	 * &lt;/com.dytech.edge.common.valuebean.SearchRequest>
	 * </code></pre>
	 * If you leave a tag out, it will be assumed to be the default (usually null, 0 or false)
	 * orderType can be 0 - ranking, 1 - Date Modified, 2 - Name<br>
	 * orderType is not used if you specify an XOQL "orderby"<br>
	 * to not use a freetext query, (e.g. return all items that the where matches)
	 * just leave out the <code>&lt;query></code> tag
	 * @param soap_uuid The session UUID
	 * @param request A string representing the xml for the SearchRequest
	 * @param offset The offset into the resultset you want to get results for.
	 * @param limit The max number of results returned with this query.
	 * @param prepare Whether or not to prepare all the item's xml for the assembler (reduce manifests, setup thumbnail url etc)
	 * @return xml search results in the form of <br>
	 *         &lt;results> <br>
	 *         &lt;result> <br>..<br>
	 *         &lt;/result> <br>
	 *         &lt;result> <br>
	 *         ... <br>
	 *         &lt;/result> <br>
	 *         &lt;/results> <br>
	 * @throws RuntimeApplicationException
	 */
	String searchItems(String soap_uuid, String searchReqStr, int offset, int limit)
		throws Exception;

	/**
	 * Counts the number of items for the given itemdefs and where clause.
	 * @param sessid a logged in session.
	 * @param itemdefs an array of item definition UUIDs.
	 * @param where a where clause.  Can be blank.
	 * @return the number of items that were counted.
	 * @throws RuntimeApplicationException
	 */
	int queryCount(String sessid, String[] itemdefs, String where) throws Exception;

	/**
	 * Retrieves the XML for an item.
	 * @param soapId The soap session identifier.
	 * @param item_uuid UUID of the item to retrieve.
	 * @param itemdef UUID of the item definition for the item.
	 * @param version The version of the item to retrieve.  A version of '0' will retrieve the latest version.
	 * @param select An XOQL select clause for choosing which parts of the item to retrieve.  <code>null</code> or an empty string is equivalent to &quot;*&quot;
	 * @return A string representation of the item XML.
	 * @throws RuntimeApplicationException
	 */
	String getItem(String soapId, String item_uuid, int version, String itemdef, String select)
		throws Exception;

	/**
	 * Return an xml enumeration of all writable itemdef's of the form:
	 * &lt;xml> &lt;itemdef> &lt;uuid/> - the itemdef's uuid &lt;name/> - the
	 * itemdef name &lt;type/> - the itemdef type (e.g. generic) &lt;contuuid/> -
	 * the container uuid &lt;table/> - the itemdef's table &lt;writable/> -
	 * true/false &lt;searchable/> - true/false whether the itemdef is
	 * searchable &lt;expert/> true/false whether or not the itemdef has an
	 * expert search &lt;identifier/> the itemdef identifer (e.g basres)
	 * &lt;/itemdef> ... &lt;/xml>
	 *
	 * @param ss_uuid
	 * @return The xml for the enumeration
	 * @throws RuntimeApplicationException
	 */
	String enumerateWritableItemDefs(String ss_uuid) throws Exception;

	/**
	 * Enumerates the dependencies of items.
	 *
	 * @param items An array of ItemKey's to calculate dependencies for
	 * @param recurse Recursively find dependencies for the items
	 * @return An array of ItemKey dependencies for each item specified in the input array
	 */
	ItemKey[][] enumerateItemDependencies(String session, ItemKey[] items, boolean recurse);

	/**
	 * Activates a list of attachments for a specific Item and Course.
	 * 
	 * @param session
	 * @param uuid
	 * @param version
	 * @param courseCode
	 * @throws Exception 
	 */
	void activateItemAttachments(String session, String uuid, int version, String courseCode,
		String[] attachments) throws Exception;
}