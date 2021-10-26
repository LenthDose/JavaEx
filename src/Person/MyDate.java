package Person;

public class MyDate {
    private int year,month,day;
    private static int thisYear=2018;
    static
    {
        thisYear=2018;


    }
    public MyDate()
    {
        this(1970,1,1);
    }


    public MyDate(int year, int month, int day)
    {
        this.set(year, month, day);
    }

    public MyDate(MyDate date)
    {
        this.set(date);
    }

    public void set(MyDate date)
    {
        this.set(date.year, date.month, date.day);
    }

//    public void set(int year, int month, int day) {
//        this.year = year;
//        this.month = (month >= 1 && month <= 12) ? month :1;
//        this.day = (day >= 1 && day <= 31) ? day : 1;
//    }

    public int getYear()
    {
        return this.year;
    }
    public int getMonth()
    {
        return this.month;
    }
    public int getDay()
    {
        return this.day;
    }

    public String toString()
    {
        return this.year+"年"+String.format("%02d", this.month)+"月"+
                String.format("%02d", this.day)+"日";
    }

    public static int getThisYear()
    {
        return thisYear;
    }

    public static boolean isLeapYear(int year)
    {
        return year%400==0 || year%100!=0 && year%4==0;
    }
    public boolean isLeapYear()
    {
        return isLeapYear(this.year);
    }


    public boolean equals(MyDate date)
    {
        return this==date ||
                date!=null && this.year==date.year && this.month==date.month && this.day==date.day;

    }

    public static int daysOfMonth(int year, int month)
    {
        switch(month)
        {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:  return 31;
            case 4: case 6: case 9: case 11:  return 30;
            case 2:  return MyDate.isLeapYear(year) ? 29 : 28;
            default: return 0;
        }
    }
    public int daysOfMonth()
    {
        return daysOfMonth(this.year, this.month);
    }


    public void tomorrow()
    {
        this.day = this.day%this.daysOfMonth()+1;
        if(this.day==1)
        {
            this.month = this.month%12+1;
            if(this.month==1)
                this.year++;
        }
    }

    public MyDate yesterday()
    {
        MyDate date = new MyDate(this);
        date.day--;
        if(date.day==0)
        {
            date.month = (date.month-2+12)%12+1;
            if(date.month==12)
                date.year--;
            date.day = daysOfMonth(date.year, date.month);
        }
        return date;
    }


    public void set(int year, int month, int day) throws DateFormatException
    {
        String str = year+"年"+month+"月"+day+"日";
        if(year<=-2000 || year>2500)
            throw new DateFormatException(str+"年份不合适，有效年份为2000-2500");
        if(month<1 || month>12)
            throw new DateFormatException(str+"月，月份错误");
        if(day<1 || day>MyDate.daysOfMonth(year, month))
            throw new DateFormatException(str+"日，日期错误");
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public MyDate(String datestr) throws NumberFormatException, DateFormatException
    {
        if(datestr.isEmpty())
            throw new DateFormatException("空串，日期错误");
        int i=datestr.indexOf('年'), j=datestr.indexOf('月',i), k=datestr.indexOf('日',j);
        int year = Integer.parseInt(datestr.substring(0,i));
        int month= Integer.parseInt(datestr.substring(i+1,j));
        int day  = Integer.parseInt(datestr.substring(j+1,k));
        this.set(year, month, day);
    }

    public int compareTo(MyDate date)
    {
        if(this==date || date!=null && this.year==date.year && this.month==date.month && this.day==date.day)
            return 0;
        return (this.year>date.year || this.year==date.year && this.month>date.month
                || this.year==date.year && this.month==date.month && this.day>date.day) ? 1 : -1;
    }
}



