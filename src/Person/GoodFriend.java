package Person;

public class GoodFriend extends Person{
    private String phoneNum;
    private boolean isFriend;

    public GoodFriend(String name, MyDate birthdate, String gender, String province, String city, String phoneNum, boolean isFriend) {
        super(name, birthdate, gender, province, city);
        this.set(phoneNum,isFriend);
    }

    public void set (String phoneNum, boolean isFriend){
        this.phoneNum = phoneNum == null ? "" : phoneNum;
        this.isFriend = isFriend ;
    }

    @Override
    public String toString() {
        return "GoodFriend{" +
                "phoneNum='" + phoneNum + '\'' +
                ", isFriend=" + isFriend +
                '}';
    }
}
