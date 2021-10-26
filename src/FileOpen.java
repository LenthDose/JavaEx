import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Silhouette76
 */
public class FileOpen extends JFrame {
    public String fileName;

     public String open() throws IOException {
         JFileChooser jf = new JFileChooser();
         jf.showOpenDialog(this);
         File f = jf.getSelectedFile();
         String s = f.getAbsolutePath();
         return s;
     }
}
