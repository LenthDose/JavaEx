import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;


/**
 * @author Silhouette76
 */
public class ChatUDPJFrame extends JFrame implements ActionListener,MouseListener,WindowListener
{
    public String name;
    public InetAddress destip;
    public int destport;
    public JTextPane text_receiver,text_sender;
    public JTextField text_port;
    private JPopupMenu popupMenu;
    private JComboBox<String> comboBox;

    public ChatUDPJFrame(String name, int receiveport,  String host, int destport) throws Exception
    {
        super(""+name+"  "+InetAddress.getLocalHost().toString()+" : "+receiveport);
        this.setBounds(320,240,800,240);
        this.setLayout(new BorderLayout());
        this.addWindowListener(this);

        //接收文本区
        this.text_receiver = new JTextPane();
        this.getContentPane().add(text_receiver,BorderLayout.CENTER);
        this.text_receiver.setEditable(false);
        this.text_receiver.addMouseListener(this);
        this.getContentPane().add(new JScrollPane(this.text_receiver));

        //底部操作区
        JToolBar toolbar = new JToolBar();
        this.getContentPane().add(toolbar,BorderLayout.SOUTH);
        toolbar.add(this.text_sender= new JTextPane());
        this.text_sender.setFont(new Font("宋体",Font.ITALIC,20));
        this.text_sender.addMouseListener(this);
        JButton button = new JButton("发送");
        toolbar.add(button);
        button.addActionListener(this);
        toolbar.add(new JLabel("端口"));
        toolbar.add(this.text_port=new JTextField());
        toolbar.add(this.comboBox = new JComboBox<>());
        JButton button1 = new JButton("聊天记录");
        toolbar.add(button1);
        button1.addActionListener(this);
        JButton button2 = new JButton("表情");
        toolbar.add(button2);
        button2.addActionListener(this);
        JButton button3 = new JButton("文件");
        toolbar.add(button3);
        button3.addActionListener(this);
        JButton button4 = new JButton("好友");
        toolbar.add(button4);
        button4.addActionListener(this);
        this.comboBox.setEditable(true);
        this.comboBox.addActionListener(this);
        this.text_port.setHorizontalAlignment(JTextField.CENTER);

        this.addMenu();
        this.Buffered();
        this.setVisible(true);

        //获得自己的网名、目标主机的IP地址和接收端口
        this.name = name;
        this.destip=InetAddress.getByName(host);
        this.destport=destport;

        //以下接收数据包，解压缩获得包裹内容，将字节序列转换成字符串显示在文本区中
        byte[] data = new byte[1024];
        DatagramPacket packet=new DatagramPacket(data,data.length);
        DatagramSocket datasocket=new DatagramSocket(receiveport);

        while(datasocket!=null)
        {
            //提示音
            Frame  f = new Frame();
            Toolkit toolkit = f.getToolkit();
            for (int i = 0; i < 3; i++) {
                toolkit.beep();
            }

            datasocket.receive(packet);
            String s = new String(packet.getData(),0,packet.getLength());
            SimpleAttributeSet friendSet = getAttributeSet("黑体", 16, Color.blue, false);

             if(s.contains("img")){
                String[] ss = s.split(":");
                insert(ss[0]+":",friendSet);
                //执行快慢有问题
                this.text_receiver.insertIcon(new ImageIcon(ss[1]));
                insert(Time()+"\r\n",friendSet);
            }

            else if(s.contains("\\")){
                int result = JOptionPane.showConfirmDialog(text_receiver,"确认接收文件吗？",null,JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    //文件名称
                    String[] ss = s.split("\\\\");
                    String fileName = "";
                    for (int i = 0; i < ss.length; i++) {
                        if(ss[i].contains(".")){
                            fileName = ss[i];
                        }
                    }

                    File dest = new File("C:\\Users\\Silhouette76\\Desktop\\test\\"+fileName);
                    FileOutputStream os = new FileOutputStream(dest);
                    int len = 0;
                    while (len == 0){
                        datasocket.receive(packet);
                        len = packet.getLength();
                        if(len > 0){
                            os.write(data,0,len);
                            os.flush();
                            len = 0;
                        }
                    }
                }
            }
            else{
                insert(s+"          "+Time()+"\r\n",friendSet);
             }
        }

    }

    @Override
    public void actionPerformed(ActionEvent event) {

        if (("发送").equals(event.getActionCommand())) {
            byte[] data = (name + "说:" + this.text_sender.getText()).getBytes();
            try {
                DatagramPacket packet = new DatagramPacket(data, data.length, destip, destport);
                DatagramSocket datasocket = new DatagramSocket();
                datasocket.send(packet);
                this.text_port.setText(datasocket.getLocalPort() + "");
                this.insert("我说:" + this.text_sender.getText() + "          " + Time() + "\n", getAttributeSet("楷体", 22, Color.red, true));
                this.text_sender.setText("");
            } catch (IOException | BadLocationException ex) {
                JOptionPane.showMessageDialog(this, "IP地址或端口错误，发送错误");
                System.out.println(ex.getClass().getName());
            }
        }

        else if (event.getSource() instanceof JMenuItem) {
            switch (event.getActionCommand()) {
                case "复制":
                    this.text_receiver.copy();
                    break;
                case "粘贴":
                    this.text_sender.paste();
                    break;
                default:
                    break;
            }
        }

        else if (event.getSource() instanceof JComboBox) {
            Object obj = this.comboBox.getSelectedItem();
            if (obj != null) {
                insert(this.comboBox, (String) obj);
            }
        }

        else if (("聊天记录").equals(event.getActionCommand())) {
            try {
                WriterRecord();
                new Record();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        else if (("表情").equals(event.getActionCommand()))
        {
            new FaceFrame(this);
        }

        else if (("文件").equals(event.getActionCommand()))
        {
            try {
                FileOpen fileOpen = new FileOpen();
                String s = fileOpen.open();
                byte[] data = s.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, destip, destport);
                DatagramSocket datasocket = new DatagramSocket();
                datasocket.send(packet);
                try {
                       UploadFile(s);
                    }
                catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }

            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(("好友").equals(event.getActionCommand())){
            new Friend();
        }
    }

    /**
     * 读文件
     * @throws IOException
     */
    private void Buffered() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("C:/Users/Silhouette76/Desktop/Port.txt"));
        String address ;
        while((address = br.readLine())!=null){
            comboBox.addItem(address);
        }
        br.close();
    }

    /**
     * 写文件(端口）
     * @throws IOException
     */
    private void WriterPort() throws IOException {

        Writer wr = new FileWriter("C:/Users/Silhouette76/Desktop/OutPort.txt",true);
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            wr.write(this.comboBox.getItemAt(i));
            wr.write("\n");
        }
        wr.close();
    }

    /**
     * 写文件（聊天记录）
     * @throws IOException
     */

    public void WriterRecord() throws IOException {
        Writer wr2 = new FileWriter("C:/Users/Silhouette76/Desktop/ChatRecord.txt",true);
        wr2.write(this.text_receiver.getText());
        wr2.write("\n");
        wr2.close();

    }

    /**
     * 快捷菜单
     */
    private void addMenu(){
        this.popupMenu = new JPopupMenu();
        String[] menuitems_cut = {"复制","粘贴"};
        JMenuItem[] popmenuitem = new JMenuItem[menuitems_cut.length];
        for (int i = 0; i < popmenuitem.length; i++) {
            popmenuitem[i] = new JMenuItem(menuitems_cut[i]);
            this.popupMenu.add(popmenuitem[i]);
            popmenuitem[i].addActionListener(this);
        }
        this.text_receiver.add(this.popupMenu);
        this.text_sender.add(this.popupMenu);
    }

    /**
     * 时间格式
     * @return
     */
    public String Time(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd  HH:mm:ss");
        String Time = simpleDateFormat.format(System.currentTimeMillis());
        return Time;
    }

    /**
     * 组合框插入
     * @param comBox
     * @param x
     * @param <T>
     */

    public  <T extends Comparable<? super T>> void insert(JComboBox<T> comBox, T x)
    {
        int begin=0, end=comBox.getItemCount()-1, mid=end;
        while(begin<=end)
        {
            mid = (begin+end)/2;
            if(x.compareTo(comBox.getItemAt(mid))==0) {
                return;
            }
            if(x.compareTo(comBox.getItemAt(mid))<0) {
                end = mid - 1;
            }
            else {
                begin = mid + 1;
            }
        }
        comBox.insertItemAt(x, begin);
    }

    /**
     * 聊天区插入文字
     * @param s
     * @param set
     * @throws BadLocationException
     */
    public void insert(String s,SimpleAttributeSet set) throws BadLocationException {
        StyledDocument doc = text_receiver.getStyledDocument();
        doc.insertString(doc.getLength(),s,set);
    }

    /**
     * 设置显示字体格式
     * @param name
     * @param size
     * @param color
     * @param bold
     * @return
     */
    public SimpleAttributeSet getAttributeSet(String name,int size,Color color,boolean bold){
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setFontFamily(set,name);
        StyleConstants.setForeground(set,color);
        StyleConstants.setFontSize(set,size);
        StyleConstants.setBold(set,bold);
        return set;
    }

    /**
     * 上传文件
     * @param filePath
     * @throws IOException
     * @throws InterruptedException
     */
    public void UploadFile(String filePath) throws IOException, InterruptedException {
        int result = JOptionPane.showConfirmDialog(text_receiver, "确认发送" + filePath + "吗？", "发送文件", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            System.out.println("上传文件");
            File source = new File(filePath);
            InputStream is = new FileInputStream(source);
            byte[] data = new byte[1024];
            while (is.read(data) != -1) {
                DatagramPacket packet = new DatagramPacket(data, data.length, this.destip, this.destport);
                DatagramSocket datagramSocket = new DatagramSocket();
                datagramSocket.send(packet);
                TimeUnit.MICROSECONDS.sleep(1);
            }
        }
    }


    /**
     * 鼠标事件
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == 3){
            this.popupMenu.show(this.text_receiver,e.getX(),e.getY());
            this.popupMenu.show(this.text_sender,e.getX(),e.getY());
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

    /**
     * 窗口事件
     * @param e
     */
    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        try {
            WriterPort();
            WriterRecord();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
