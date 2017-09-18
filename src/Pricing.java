//Author: Tanya Huisman
//Date: 10/6/2016
//Purpose: creates a pricing object from the information in the pricing table
import java.io.Serializable;

public class Pricing implements Serializable{
     private static final long serialVersionUID = 1L;
     
     private int breedID;
     private String breed;
     private double price;
     
     public Pricing()
     {
         
     }
     public Pricing(String b, double p)
     {
         this.breed = b;
         this.price = p;
     }
     public Pricing(int id, String b, double p)
     {
         this.breedID = id;
         this.breed = b;
         this.price = p;
     }
     public int getBreedID()
     {
         return breedID;
     }
     public void setBreedID(int i)
     {
         this.breedID = i;
     }
     public String getBreed()
     {
         return breed;
     }
     public void setBreed(String b)
     {
         this.breed = b;
     }
     public double getPrice()
     {
         return price;
     }
     public void setPrice(double p)
     {
         this.price = p;
     }
    
}
