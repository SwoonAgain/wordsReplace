package com.vico.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.DosFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FileList {
	
	private static final List<File> FILE_LIST = new ArrayList<File>();
	
	public static List<File> getFiles(String path, String extension){
		if (path != null) {
			return getFiles(new File(path),extension);
		}
		return FILE_LIST;
	}
	
	public static List<File> getFiles(File path, String extension){
		if (path.isDirectory()&&path.canRead()) {
			File[] list = path.listFiles(new FileFilter() {
				 @Override
			        public boolean accept(File file) {
			            Path path = Paths.get(file.getAbsolutePath());
			            DosFileAttributes dfa;
			            try {
			                dfa = Files.readAttributes(path, DosFileAttributes.class);
			            } catch (IOException e) {
			                // bad practice
			                return false;
			            }
			            return (!dfa.isHidden() && !dfa.isSystem());
			        }
			});
			if (list != null) {
				for (File file : list) {
					if (file.isFile()&&file.getName().endsWith(extension)) {
						System.out.println(file);
						FILE_LIST.add(file);
					}
					else getFiles(file,extension);
				}
			}
		}
		return FILE_LIST;
	}
}
