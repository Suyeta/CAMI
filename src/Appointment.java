//Author: Tanya Huisman
//Date: 10/18/2016
//Purpose: to create and manage Appointment Objects
import java.io.Serializable;
import java.sql.Time;
import java.util.GregorianCalendar;


public class Appointment implements Serializable 
{
    private static final long serialVersionUID = 1L;
    
    private int apptID;
    private int empID;
    private int dogID;
    private GregorianCalendar apptDate;
    private Time apptTime;
    
    
    //Default Constructor
    public Appointment()
    {
        
    }
    //Constructor with 3 items(all items minus the apptID for inserting)
    public Appointment(int eID, int dID, GregorianCalendar d, Time t)
    {
        this.empID = eID;
        this.dogID = dID;
        this.apptDate = d;
        this.apptTime = t;
       
    }
    //Constructor with all 4 items
    public Appointment(int aID, int eID, int dID, GregorianCalendar d, Time t)
    {
        this.apptID = aID;
        this.empID = eID;
        this.dogID = dID;
        this.apptDate = d;
        this.apptTime = t;
    }
    //getters and setters for each variable
    public int getApptID()
    {
        return apptID;
    }
    public void setApptID(int id)
    {
        this.apptID = id;
    }
    public int getEmpID()
    {
        return empID;
    }
    public void setEmpID(int id)
    {
        this.empID = id;
    }
    public int getDogID()
    {
        return dogID;
    }
    public void setDogID(int id)
    {
        this.dogID = id;
    }
    public GregorianCalendar getApptDate()
    {
        return apptDate;
    }
    public void setApptDate(GregorianCalendar date)
    {
        this.apptDate = date;
    }
    public Time getApptTime()
    {
        return apptTime;
    }
    public void setApptTime(Time t)
    {
        this.apptTime = t;
    }
}

