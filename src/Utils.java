
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.stream.Stream;

public class Utils {
	public static void main(String[] args) {
		try (Stream<Path> paths = Files
				.walk(Paths.get("/Users/tharitpongsaneh/Desktop/apache-tomcat-9.0.13-src/java/org/apache"))) {
			paths.forEach(file -> {
				FileRead read = new FileRead();
				File f = new File(file.toString());
				if (f.isDirectory()) {
					File folder = new File(f.getPath());
					String[] files = folder.list();
					for (String myFile : files) {
						if (myFile.contains(".java")) {
							read.readInFile(folder.toString() + "/" + myFile);
						}
					}
					PackageInfo pack = new PackageInfo(read.getNa(), read.getNc(), read.getCa(), read.getCe());
					DecimalFormat df = new DecimalFormat("#.######");
					// System.out.println(folder.toString());
					try {
						Double instability = Double.parseDouble(df.format(pack.getInstability()));
						Double abstractness = Double.parseDouble(df.format(pack.getAbstractness()));
						if (!instability.isNaN() && !abstractness.isNaN())
							if (instability <= 1 && abstractness <= 1)
								System.out.println(instability + ", " + abstractness);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}