//Author: Tanya Huisman
//Date: 10/6/2016
//Purpose: create dog objects from the information in the dogs table
import java.io.Serializable;
import java.util.GregorianCalendar;

public class Dog implements Serializable
{
     private static final long serialVersionUID = 1L;
     
     private int dogID;
     private String dogName;
     private int dogBreedID;
     private GregorianCalendar bDay;
     private GregorianCalendar rabExp;
     private String instructions;
     private int custID;
     
     public Dog()
     {
         
     }
     public Dog(int id, String dn, int db, GregorianCalendar bd, GregorianCalendar re, String in, int ci)
     {
         this.dogID = id;
         this.dogName = dn;
         this.dogBreedID = db;
         this.bDay = bd;
         this.rabExp = re;
         this.instructions = in;
         this.custID = ci;
     }
     
     public Dog(String dn, int db, GregorianCalendar bd, GregorianCalendar re, String in, int ci)
     {
         this.dogName = dn;
         this.dogBreedID = db;
         this.bDay = bd;
         this.rabExp = re;
         this.instructions = in;
         this.custID = ci;
     }
     
     public int getDogID()
     {
         return dogID;
     }
     public void setDogID(int di)
     {
         this.dogID = di;
     }
     public String getDogName()
     {
         return dogName;
     }
     public void setDogName(String dn)
     {
         this.dogName = dn;
     }
     public int getDogBreed()
     {
         return dogBreedID;
     }
     public void setDogBreed(int db)
     {
         this.dogBreedID = db;
     }
     public GregorianCalendar getBDay()
     {
         return bDay;
     }
     public void setBDay(GregorianCalendar bd)
     {
         this.bDay = bd;
     }
     public GregorianCalendar getRabExp()
     {
         return rabExp;
     }
     public void setRabExp(GregorianCalendar re)
     {
         this.rabExp = re;
     }
     public String getInstructions()
     {
         return instructions;
     }
     public void setInstructions(String in)
     {
         this.instructions = in;
     }
     public int getCustID()
     {
         return custID;
     }
     public void setCustID(int id)
     {
         this.custID = id;
     }
}
