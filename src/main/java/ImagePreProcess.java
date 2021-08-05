import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;

public class ImagePreProcess {
    final File dir = new File("D:\\draft");
    final String[] EXTENSIONS = new String[]{
            "jpg", "png", "jpeg", "png.png"
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
        if (dir.isDirectory()) { // make sure it's a directory
            for (final File f : dir.listFiles(IMAGE_FILTER)) {
                BufferedImage image = ImageIO.read(f);
                BufferedImage resized = resize(image, 256, 256);
                File output = new File( f + ".png");
                ImageIO.write(resized, "png", output);
            }
        }
    }
    private BufferedImage resize (BufferedImage img,int height, int width){
        Image images = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(images, 0, 0, null);
        g2d.dispose();
        return resized;
    }
}
