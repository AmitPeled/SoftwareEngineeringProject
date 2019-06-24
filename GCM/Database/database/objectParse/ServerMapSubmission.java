package database.objectParse;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import approvalReports.ActionTaken;
import approvalReports.mapApprovalReports.MapSubmission;
import maps.Map;

public class ServerMapSubmission implements Serializable {
	 private static final long serialVersionUID = 1L;
	 private Map               map;
	 private byte[]            rawMapFileBytes;
	 private ActionTaken       actionTaken;
	 /**
	  * the id of the city contained in. if ADD/DELETE, field contains the id of the
	  * city added to or deleted from
	  */
	 private int               containingCityID;

	 public ServerMapSubmission(int containingCityID, Map map, byte[] mapFile, ActionTaken actionTaken) {
		  this.containingCityID = containingCityID;
		  this.map = map;
		  this.rawMapFileBytes = mapFile;
		  this.actionTaken = actionTaken;
	 }

	 public Map getMap() {
		  return map;
	 }

	 public String getActionTaken() {
		  return actionTaken.toString();
	 }

	 public int getContainingCityID() {
		  return containingCityID;
	 }

	 public byte[] getMapFileBytes() {
		  return rawMapFileBytes;
	 }

	 public ActionTaken getAction() {
		  return actionTaken;
	 }

	 public MapSubmission getMapSubmission(String desiredFilePath) throws IOException {
		  File mapFile = ImageDownloader.downloadImage(rawMapFileBytes, desiredFilePath);
		  return new MapSubmission(containingCityID, map, mapFile, actionTaken);
	 }
}
