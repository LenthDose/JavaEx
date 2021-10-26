package Person;

public class Person {
    public String name;
    public MyDate birthdate;
    public String gender, province, city;
    private static int count=0;

    public Person(String name, MyDate birthdate, String gender, String province, String city)
    {
        super();
        this.set(name, birthdate, gender, province, city);
        count++;
    }
    public Person(String name, MyDate birthdate)
    {
        this(name, birthdate, "", "", "");
    }
    public Person()
    {
        this("", new MyDate());

    }
    public Person(Person per)
    {

        this(per.name, new MyDate(per.birthdate), per.gender, per.province, per.city);
    }


    public static void howMany()
    {
        System.out.print(Person.count+"个Person对象");
    }


    public void set(String name, MyDate birthdate, String gender, String province, String city)
    {
        this.name = name==null?"":name;
        this.birthdate = birthdate;
        this.gender = gender==null?"":gender;
        this.province = province==null?"":province;
        this.city = city==null?"":city;
    }
    public void set(String name, MyDate birthdate)
    {
        this.set(name, birthdate, "", "", "");
    }

    @Override
    public String toString()
    {
        return this.name+","+(this.birthdate==null?"":birthdate.toString())+","+
                this.gender+","+this.province+","+this.city;

    }

    public int getAge(int year) {
        return year - this.birthdate.getYear();
    }

    public int getAge()
    {
        return getAge(MyDate.getThisYear());
    }

}
