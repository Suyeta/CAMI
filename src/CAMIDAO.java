//Author: Tanya Huisman
//Date: 10/6/2016
//Purpose: to act as a Database Access Object Interface
import java.sql.Date;
import java.util.Collection;

public interface CAMIDAO 
{
    //interface for Appointments added 10/18/16
    public boolean insertAppointment(Appointment appt);//add appointment
    public Appointment findAppointment(int dogID);//find appointment by dog ID
    public Collection<Appointment> findAppointments(Date date);//find all appointments on a certain date
    public boolean updateAppointment(Appointment appt);//update appointment
    public boolean deleteAppointment(int ID);//delete appointment
    public Collection<Appointment> findAllAppointments();//find all appointments
    
    //interface for Customers
    public boolean insertCustomer(Customer customer);//add customer
    public Customer findCustomer(String ph);	     //search customer
    public boolean updateCustomer(Customer customer);//update customer
    public boolean deleteCustomer(int id);	     //delete customer	
    public Collection<Customer> selectAllCustomers();//find all customers
    
    //interface for Dogs
    public boolean insertDog(Dog dog);		//add dog
    public Collection<Dog> findDog(int id);	//find all dogs who belong to the custID passed
    public Dog displayDog(int id);              //find all information about one dog to display in frame
    public int getDogBreedID(String s);         //method to get the breedID from the pricing table of the selected breed(string)
    public String getDogBreedString(int id);    //method to get the string version of the dog breed from the pricing table for display purposes
    public boolean updateDog(Dog dog);		//update dog
    public boolean deleteDog(int id);		//delete dog
    public Collection<Dog> selectAllDogs();     //find all dogs
    
    //interface for Employee
    public boolean insertEmployee(Employee emp);
    public Employee findEmployee(int empID);
    public boolean updateEmployee(Employee emp);
    public boolean deleteEmployee(int empID);
    public Collection<Employee> selectAllEmployees();
    
    //interface for Pricing
    public boolean insertBreed(Pricing pricing); //add a new breed to the price list
    public Pricing findPrice(int breedID);	//find the current price of the specific breed
    public boolean updateBreed(Pricing pricing);//update the information on a specific breed
    public boolean deleteBreed(int id);		//delete a breed from the price list
    public Collection<Pricing> selectAllBreeds(); //find all breeds
    
    //interface for Transactions
    public boolean insertTrans(Transaction trans);
    public Collection<Transaction> findTrans(int CustID);
    public boolean updateTrans(Transaction trans);
    public boolean deleteTrans(int transID);
    public Collection<Transaction> selectAllTrans();
}
