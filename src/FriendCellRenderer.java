import Person.Person;

import javax.swing.*;
import java.awt.*;

/**
 * @author Silhouette76
 */
public class FriendCellRenderer extends DefaultListCellRenderer {


    @Override
    public Component getListCellRendererComponent(JList<? extends Object> list , Object value, int index, boolean isSelected, boolean cellHasFocus){
        setText(value.toString());
        ImageIcon icon = new ImageIcon("img/milk.png");
        Image img = icon.getImage();
        img = img.getScaledInstance(25,25,Image.SCALE_DEFAULT);
        icon.setImage(img);
        setIcon(icon);
        if(isSelected){
            setForeground(Color.WHITE);
            setBackground(Color.YELLOW);
        }
        else{
            setForeground(Color.BLACK);
            setBackground(Color.WHITE);
        }
        return this;
    }

}
