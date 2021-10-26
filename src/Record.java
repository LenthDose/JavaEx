import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Silhouette76
 */
public class Record extends JFrame implements ActionListener {
    private JList list;
    private DefaultListModel<String> listModel;
    private JTextField textDate;

    /**
     * 聊天记录界面
     */
    public Record() throws IOException {
        super("聊天记录");
        this.setBounds(520,340,800,240);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //聊天记录显示文本区
        this.listModel = new DefaultListModel<>();
        this.list = new JList(this.listModel);
        JScrollPane scrollPane = new JScrollPane(list);
        this.getContentPane().add(scrollPane);

        //底部操作区
        JToolBar toolBar = new JToolBar();
        this.getContentPane().add(toolBar,"South");
        toolBar.add(this.textDate = new JTextField(20));
        JButton button = new JButton("查找");
        toolBar.add(button);
        button.addActionListener(this);
        this.readRecord();
        this.setVisible(true);
    }

    /**
     * 查找聊天记录
     */
    public void find() throws IOException {
        String s = this.textDate.getText();
        BufferedReader bf = new BufferedReader(new FileReader("C:/Users/Silhouette76/Desktop/ChatRecord.txt"));
        String line;
        String[] str = new String[512];
        int i =0;
        this.listModel.removeAllElements();
        while((line=bf.readLine())!=null){
            if(line.contains(s)) {
                str[i] = line;
                listModel.addElement(str[i]);
                i++;
            }
        }
        bf.close();

    }

    /**
     * 将聊天记录从文件写入列表框
     * @throws IOException
     */
    public void readRecord() throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader("C:/Users/Silhouette76/Desktop/ChatRecord.txt"));
        String line;
        String[] str = new String[512];
        int i =0;
        while((line=bf.readLine())!=null){
            str[i] = line;
            listModel.addElement(str[i]);
            i++;
        }
        bf.close();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            find();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}
