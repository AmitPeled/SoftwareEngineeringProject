package init;

import javafx.scene.Scene;
import mainApp.SceneNames;

public interface ScenesInitializer {
	Scene getScene(SceneNames name);
}
