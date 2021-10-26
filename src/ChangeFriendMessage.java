import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeFriendMessage extends JFrame implements ActionListener {
    private JTextField Name, Phone, Sex, UserName, ID, Email;
    private JPasswordField Password, Password2;

    public ChangeFriendMessage() {
        this.setSize(350, 350);
        this.setTitle("好友信息");
        this.setLocationRelativeTo(getOwner());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.getContentPane().setLayout(new GridLayout(9, 2));
        this.getContentPane().add(new JLabel("姓名"));
        UserName = new JTextField();
        this.getContentPane().add(UserName);
        this.getContentPane().add(new JLabel("生日"));
        Name = new JTextField();
        this.getContentPane().add(Name);
        this.getContentPane().add(new JLabel("性别"));
        Sex = new JTextField();
        this.getContentPane().add(Sex);
        this.getContentPane().add(new JLabel("省份"));
        ID = new JTextField();
        this.getContentPane().add(ID);
        this.getContentPane().add(new JLabel("城市"));
        Email = new JTextField();
        this.getContentPane().add(Email);
        this.getContentPane().add(new JLabel("联系方式"));
        Phone = new JTextField();
        this.getContentPane().add(Phone);
        JButton confirm = new JButton("确认");
        this.getContentPane().add(confirm);
        confirm.addActionListener(this);
        JButton cancel = new JButton("取消");
        cancel.addActionListener(this);
        this.getContentPane().add(cancel);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
