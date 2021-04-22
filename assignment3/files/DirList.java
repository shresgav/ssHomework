package assignment3.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 
 * Lists all file paths in directory specified in command line
 * 
 * @author Gavin Shrestha
 *
 */

public class DirList {

	public static void main(String[] args) {
		List<String> files = createFileList(args[0]);
		for (String s : files) {
			System.out.println(s);
		}
	}
	
	public static List<String> createFileList(String s) {
		try(Stream<Path> walk = Files.walk(Paths.get(s))) {
			List<String> result = walk.filter(Files::isRegularFile)
									  .map(x -> x.toString())
									  .collect(Collectors.toList());
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
