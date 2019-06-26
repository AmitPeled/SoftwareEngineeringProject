package mapFetchment;

import java.io.File;

import javax.swing.JFileChooser;

public class JFrameMapChooser {
	 public static File chooseFromDialog(String initialPath) {
		  final JFileChooser fc = new JFileChooser(initialPath);
		  int returnVal = fc.showOpenDialog(null);
		  if (returnVal == JFileChooser.APPROVE_OPTION) {
			   File mapFile = fc.getSelectedFile();
			   return mapFile;
		  }
		  return null;
	 }
}
