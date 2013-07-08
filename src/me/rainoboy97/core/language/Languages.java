package me.rainoboy97.core.language;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import me.rainoboy97.core.Core;
import me.rainoboy97.core.utils.CoreLog;

public class Languages {

	public List<Language> loadedLangs = new ArrayList<Language>();
	
	private File path = new File(Core.lang_path);
	
	public boolean createDir() {
		try {
			path.createNewFile();
			return true;
		} catch (Exception e) {
			CoreLog.log(Level.WARNING, "Could not create the language folder");
			e.printStackTrace();
			return false;
		}
	}
	
	public int loadLangs() {
		int langsLoaded = 0;
		File path = new File(Core.lang_path);
		for(File file : path.listFiles()) {
			String name = file.getName();
			name = name.substring(0, 4);
			loadedLangs.add(new Language(name));
			langsLoaded++;
		}
		return langsLoaded;
	}
	
}
