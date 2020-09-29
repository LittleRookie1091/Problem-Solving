import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

public class Imaging{
// read a jpeg from a inputFile
public static void main(String[] args) throws IOException {
	Imaging image = new Imaging();
final File folder = new File("~/Desktop/test/new");
ArrayList<String> a = image.listFilesForFolder(folder);
for(int i = 0; i<a.size();i++){
File file = new File(a.get(i));
BufferedImage bufferedImage = ImageIO.read(file);

// write the bufferedImage back to outputFile
ImageIO.write(bufferedImage, "png", file);

// this writes the bufferedImage into a byte array called resultingBytes
ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
ImageIO.write(bufferedImage, "png", byteArrayOut);
byte[] resultingBytes = byteArrayOut.toByteArray();
}

}

public ArrayList<String> listFilesForFolder(final File folder) {
	ArrayList<String> a = new ArrayList<>();
	for (final File fileEntry : folder.listFiles()) {
        if (fileEntry.isDirectory()) {
            listFilesForFolder(fileEntry);
        } else {
            a.add(fileEntry.getName());
        }
    }
    return a;
}

}
