package dataAccess.generalManager;

import java.util.List;

import maps.Map;

public interface GeneralManagerDAO {
	List<Map> getPriceEdits();

	void discardMapPriceEdit(int mapId);

	void approveMapPriceEdit(int mapId);

	void setMapPrice(int mapId, double mapPrice);
}
