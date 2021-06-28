import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import javax.imageio.ImageIO;

public class Histogram {
     final File dir = new File("C:\\Users\\Thong\\Downloads\\ImageIndex\\pokemon");

     final String[] EXTENSIONS = new String[]{
            "jpg", "png", "jpeg"
    };
     final FilenameFilter IMAGE_FILTER = new FilenameFilter() {
        @Override
        public boolean accept(final File dir, final String name) {
            for (final String ext : EXTENSIONS) {
                if (name.endsWith("." + ext)) {
                    return (true);
                }
            }
            return (false);
        }
    };
    public void main() throws Exception {
        FileWriter writer = new FileWriter("MyFile5.csv", Charset.forName("UTF8"));
        if (dir.isDirectory()) { // make sure it's a directory
            for (final File f : dir.listFiles(IMAGE_FILTER)) {
                int[][][] ch = new int[4][4][4];
                BufferedImage img = null;
                try {
                    img = ImageIO.read(f);
                    for (int x = 0; x < img.getWidth(); x++)
                        for (int y = 0; y < img.getHeight(); y++) {
                            int color = img.getRGB(x, y);
                            int red = (color & 0x00ff0000) >> 16;
                            int green = (color & 0x0000ff00) >> 8;
                            int blue = color & 0x000000ff;
                            ch[red/64][green/64][blue/64]++;
                        }
                    for (int i = 0; i < ch.length; i++) {
                        for (int j = 0; j < ch[i].length; j++) {
                            for (int p = 0; p < ch[i][j].length; p++) {
                                System.out.println("t[" + i + "][" + j + "][" + p + "] = " + ch[i][j][p]);
                                writer.write(String.valueOf(ch[i][j][p]));
                                writer.write(",");
                            }
                        }
                    }
                    writer.write("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}