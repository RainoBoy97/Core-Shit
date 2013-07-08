package me.rainoboy97.core.language;

import java.io.File;

import me.rainoboy97.core.Core;

public class Language {

	private File lang_File;
	private String name;

	public Language(String name) {
		this.name = name;
		this.lang_File = new File(Core.lang_path + name + ".lang");
	}

}
