//Author: Tanya Huisman
//Date: 10/18/2016
//Purpose: creates Employee objects from the information in the employee table
import java.io.Serializable;
import java.util.GregorianCalendar;

public class Employee implements Serializable
{//altered 11/16/16 to include new fields for the employee table in the database
    private static final long serialVersionUID = 1L;
    
    private int empID;
    private String lastname;
    private String firstname;
    private String address;
    private String apartment;
    private String city;
    private String state;
    private int zip;
    private String phone;
    private String payType;
    private double hrlyPay;
    private double commPercent;
    private double salPay;
    private double sales;
    private double totalSales;
    private double commission;
    private String empType;
    private GregorianCalendar hireDate;
    //Default Constructor
    public Employee()
    {
        
    }
    //Constructor with 17 items for inserting a new employee
    public Employee(String lname, String fname, String add,String apt, String c, 
            String s, int z, String ph, String pt, double hr, double cp, double sal, 
            double salesAmnt, double tSales, double comm, String type, 
            GregorianCalendar hd)
    {
        this.lastname = lname;
        this.firstname = fname;
        this.address = add;
        this.apartment = apt;
        this.city = c;
        this.state = s;
        this.zip = z;
        this.phone = ph;
        this.payType = pt;
        this.hrlyPay = hr;
        this.commPercent = cp;
        this.salPay = sal;
        this.sales = salesAmnt;
        this.totalSales = tSales;
        this.commission = comm;
        this.empType = type;
        this.hireDate = hd;
    }
    //Constructor with all fields
    public Employee(int eID, String lname, String fname, String add, String apt, 
                    String c, String s, int z, String ph, String pt, double hr, double cp, double sal, double salesAmnt, 
                    double tSales, double comm, String type, GregorianCalendar hd)
    {
        this.empID = eID;
        this.lastname = lname;
        this.firstname = fname;
        this.address = add;
        this.apartment = apt;
        this.city = c;
        this.state = s;
        this.zip = z;
        this.phone = ph;
        this.payType = pt;
        this.hrlyPay = hr;
        this.commPercent = cp;
        this.salPay = sal;
        this.sales = salesAmnt;
        this.totalSales = tSales;
        this.commission = comm;
        this.empType = type;
        this.hireDate = hd;
    }
    //getters and setters for each field
    public int getEmpID()
    {
        return empID;
    }
    public void setEmpID(int id)
    {
        this.empID = id;
    }
  
    public String getLastName()
    {
        return lastname;
    }
    public void setLastName(String ln)
    {
        this.lastname = ln;
    }
      public String getFirstName()
    {
        return firstname;
    }
    public void setFirstName(String n)
    {
        this.firstname = n;
    }
    public String getAddress()
    {
        return address;
    }
    public void setAddress(String add)
    {
        this.address = add;
    }
    public String getApartment()
    {
        return apartment;
    }
    public void setApartment(String a)
    {
        this.apartment = a;
    }
    public String getCity()
    {
        return city;
    }
    public void setCity(String c)
    {
        this.city = c;
    }
    public String getState()
    {
        return state;
    }
    public void setState(String s)
    {
        this.state = s;
    }
    public int getZip()
    {
        return zip;
    }
    public void setZip(int z)
    {
        this.zip = z;
    }
    public String getPhone()
    {
        return phone;
    }
    public void setPhone(String ph)
    {
        this.phone = ph;
    }
    public String getPayType()
    {//added 11/16/16
        return payType;
    }
    public void setPayRate(String pt)
    {//added 11/16/16
        this.payType = pt;
    }
    public double getHrlyPay()
    {//added 11/16/16
        return hrlyPay;
    }
    public void setHrlyPay(double hr)
    {//added 11/16/16
        this.hrlyPay = hr;
    }
    public double getCommPercent()
    {//added 11/16/16
        return commPercent;
    }
    public void setCommPercent(double cp)
    {//added 11/16/16
        this.commPercent = cp;
    }
    public double getSalary()
    {//added 11/16/16
        return salPay;
    }
    public void setSalary(double sal)
    {//added 11/16/16
        this.salPay = sal;
    }
    public double getSales()
    {
        return sales;
    }
    public void setSales(double s)
    {
        this.sales = s;
    }
    public double getTotalSales()
    {
        return totalSales;
    }
    public void setTotalSales(double ts)
    {
        this.totalSales = ts;
    }
    public double getCommission()
    {
        return commission;
    }  
    public void setCommission(double c)
    {
        this.commission = c;
    }
    public String getEmpType()
    {
        return empType;
    }
    public void setEmpType(String t)
    {
        this.empType = t;
    }
    public GregorianCalendar getHireDate()
    {//added 11/16/16
        return hireDate;
    }
    public void setHireDate(GregorianCalendar hd)
    {//added 11/16/16
        this.hireDate = hd;
    }
}
