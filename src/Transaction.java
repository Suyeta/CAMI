//Author: Tanya Huisman
//Date: 10/6/2016
//Purpose: creates a Transaction object from the information in the transaction table
import java.io.Serializable;

public class Transaction implements Serializable{
    private static final long serialVersionUID = 1L;
    private int transID;
    private int empID;
    private int dogID;
    private int custID;
    private double price;
    
    public Transaction()
    {
        
    }
    public Transaction(int tr, int em, int dg, int cu, double pr)
    {
        this.transID = tr;
        this.empID = em;
        this.dogID = dg;
        this.custID = cu;
        this.price = pr;
    }
    public Transaction(int em, int dg, int cu, double pr)
    {
        this.empID = em;
        this.dogID = dg;
        this.custID = cu;
        this.price = pr;
    }
    public int getTranID()
    {
        return transID;
    }
    public void setTranID(int id)
    {
        this.transID = id;
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
    public int getCustID()
    {
        return custID;
    }
    public void setCustID(int id)
    {
        this.custID = id;
    }
    public double getPrice()
    {
        return price;
    }
    public void setPrice(double pr)
    {
        this.price = pr;
    }
}

