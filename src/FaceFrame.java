import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


/**
 * @author Silhouette76
 */
public class FaceFrame extends JFrame  {

    public FaceFrame(ChatUDPJFrame chat)
    {
        this.setBounds(500,500,800,300);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JLabel[] icon = new JLabel[100];
        GridLayout gridLayout = new GridLayout();
        this.getContentPane().setLayout(gridLayout);
        gridLayout.setColumns(15);
        gridLayout.setHgap(1);
        gridLayout.setRows(6);
        gridLayout.setVgap(1);
        String fileName = " ";


        //添加表情
        for (int i = 1; i < icon.length; i++) {
            if(i<10){
                 fileName = "img/f00"+i+".png";
            }
            else{
                 fileName = "img/f0"+i+".png";
            }
            icon[i] = new JLabel();
            icon[i].setIcon(new ImageIcon(fileName));
            this.getContentPane().add(icon[i]);

            //发送表情的数据包
            byte[] data=(chat.name+"说:"+fileName).getBytes();

            Icon img = icon[i].getIcon();
            icon[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        chat.insert("我说:",chat.getAttributeSet("楷体", 22, Color.red, true));
                        chat.text_receiver.insertIcon(img);
                        chat.insert("          " +chat.Time() + "\n",chat.getAttributeSet("楷体", 22, Color.red, true));
                        DatagramPacket packet=new DatagramPacket(data, data.length,chat.destip, chat.destport);
                        DatagramSocket dataSocket = new DatagramSocket();
                        dataSocket.send(packet);
                    } catch (IOException | BadLocationException badLocationException) {
                        badLocationException.printStackTrace();
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

            });

        }
        this.setVisible(true);
    }
}
