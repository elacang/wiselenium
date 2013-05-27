package org.wiselenium.testng;

import java.io.File;

@SuppressWarnings("javadoc")
public final class FileUtils {
	
	public static String getAbsoluteFilePath(String relativePath) {
		String absolutePath = "file:///" + new File("").getAbsolutePath() + "/src/test/resources/"
			+ relativePath;
		return absolutePath.replaceAll("\\\\", "/");
	}
	
}
