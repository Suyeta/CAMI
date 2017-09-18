//Author: Tanya Huisman
//Date: 10/6/2016
//Purpose: Handles Customer Objects made from the customer table.
import java.io.Serializable;

public class Customer implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private int custID;
    private String lastName;
    private String firstName;
    private String address;
    private String apartment;
    private String city;
    private String state;
    private int zip;
    private String phone;
    
    
    public Customer()
    {
        
    }
    public Customer(int id, String ln, String fn, String add, String apt, String cty, String st, int z, String ph)
    {
        this.custID = id;
        this.lastName = ln;
        this.firstName = fn;
        this.address = add;
        this.apartment = apt;
        this.city = cty;
        this.state = st;
        this.zip = z;
        this.phone = ph;
        
    }
    public Customer(String ln, String fn, String add, String apt, String cty, String st, int z, String ph)
    {
        this.lastName = ln;
        this.firstName = fn;
        this.address = add;
        this.apartment = apt;
        this.city = cty;
        this.state = st;
        this.zip = z;
        this.phone = ph;
        
    }
    public int getCustID ()
    {
        return custID;
    }
    public void setCustID(int cust)
    {
        this.custID = cust;
    }
    public String getLastName()
    {
        return lastName;
    }
    public void setLastName(String ln)
    {
        this.lastName = ln;
    }
    public String getFirstName()
    {
        return firstName;
    }
    public void setFirstName(String fn)
    {
        this.firstName = fn;
    }
    public String getAddress()
    {
        return address;
    }
    public void setAddress(String ad)
    {
        this.address = ad;
    }
    public String getApartment()
    {
        return apartment;
    }
    public void setApartment(String ap)
    {
        this.apartment = ap;
    }
    public String getCity()
    {
        return city;
    }
    public void setCity(String ct)
    {
        this.city = ct;
    }
    public String getState()
    {
        return state;
    }
    public void setState(String st)
    {
        this.state = st;
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
}
