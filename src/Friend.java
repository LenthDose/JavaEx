import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Silhouette76
 */
public class Friend extends JFrame implements ActionListener, MouseListener {
    private JList list;
    private DefaultListModel listModel;

    public Friend() {
        this.setBounds(500,500,800,300);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        listModel = new DefaultListModel();
        listModel.addElement("a");
        listModel.addElement("b");
        list = new JList(listModel);
        list.setCellRenderer(new FriendCellRenderer());
        JScrollPane scrollPane = new JScrollPane(list);
        this.getContentPane().add(scrollPane);
        list.addMouseListener(this);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2){
            new ChangeFriendMessage();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
