//Author: Tanya Huisman
//Date: 10/6/2016
//Purpose: Holds all the methods with SQL Strings to pass to the Database.


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;

public class CAMISQLDao implements CAMIDAO
{
    private String user;    //variable to hold user entered username
    private String pass;    //variable to hold user entered password
    
    public CAMISQLDao(String un, String pw)throws SQLException
    {
        this.user = un;
        this.pass = pw;
        
        Connection connection = ConnectionFactory.getConnection(user, pass);
        DbUtil.close(connection);
    }
    //SQL for Appointments added 10/18/16
    @Override
    public boolean insertAppointment(Appointment appt)
    {   //method overrid for adding a new appointment
         boolean result = false;
         GregorianCalendar apptDate;
	Connection connection = null;
	String sqlStatement = new String("INSERT INTO appointments VALUES (Null, ?, ?, ?, ?)"); 
	PreparedStatement prepSqlStatement = null;
                
        try {
		connection = ConnectionFactory.getConnection(user, pass);
		prepSqlStatement = connection.prepareStatement(sqlStatement);
		prepSqlStatement.setInt(1, appt.getEmpID());
                prepSqlStatement.setInt(2, appt.getDogID());
                apptDate = appt.getApptDate();
                java.sql.Date appDate = new java.sql.Date(apptDate.getTimeInMillis());
                prepSqlStatement.setDate(3, appDate);
                Time appTime = appt.getApptTime();
                prepSqlStatement.setTime(4, appTime);
                
                
		
                       
		int rowCount = prepSqlStatement.executeUpdate();
		result = (rowCount == 1) ? true : false; 
		} catch (SQLException ex) {
			ex.printStackTrace();
			result =  false;
		}	
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		return result; 
        
       
    }
    @Override
    public Appointment findAppointment(int ID)
    {//Method ovverride for finding an appointment
        
         GregorianCalendar apptDate;
	Connection connection = null;
	String sqlStatement = new String("SELECT * FROM appointments WHERE ApptID = ?"); 
	PreparedStatement prepSqlStatement = null;
        ResultSet rs;
        Appointment appointment = null;
                
        try {
		connection = ConnectionFactory.getConnection(user, pass);
		prepSqlStatement = connection.prepareStatement(sqlStatement);
		prepSqlStatement.setInt(1, ID);
                rs = prepSqlStatement.executeQuery();
			while (rs.next()){
                            java.sql.Date d = rs.getDate("ApptDate");
                            LocalDate date = d.toLocalDate();
                            apptDate = new GregorianCalendar(date.getMonthValue(), date.getDayOfMonth(), date.getYear());
				appointment = new Appointment(rs.getInt("ApptID"), rs.getInt("EmpID"),
                                        rs.getInt("DogID"), apptDate, rs.getTime("ApptTime"));
			}
                        if(rs.wasNull())
                        {
                            appointment = null;
                        }
		} catch (SQLException e) {
				e.printStackTrace();
		}
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		return appointment;
               
                
		
                       
		
        
    }
    @Override
    public Collection<Appointment> findAppointments(Date date)
    { //Method override for finding all appointments on a specific day
        Connection connection = null;
		String sqlStatement = new String("SELECT * FROM appointments WHERE ApptDate = ?"); 
		PreparedStatement prepSqlStatement = null;
		ArrayList<Appointment> apptList = new ArrayList();
		ResultSet rs;
                GregorianCalendar apptDate = null;
                Time apptTime;
		Appointment appointment;
		
		try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
                        prepSqlStatement.setDate(1, date);
			rs = prepSqlStatement.executeQuery();
                        if(rs == null)
                        {
                            apptList = null;
                        }
                        else{
			while (rs.next())
                        {
                                java.sql.Date appDate = rs.getDate("ApptDate");
                                LocalDate aDate = appDate.toLocalDate();
				apptDate = new GregorianCalendar(aDate.getYear(), aDate.getMonthValue() - 1, aDate.getDayOfMonth());
				appointment = new Appointment(rs.getInt("ApptID"), rs.getInt("EmpID"), rs.getInt("DogID"), apptDate, rs.getTime("ApptTime"));
				apptList.add(appointment);
			}
                        }
		} catch (SQLException e) {
				e.printStackTrace();
		}
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}		
		return apptList;
    }
    @Override
    public boolean updateAppointment(Appointment appointment)
    {//Method override for updating an appointment
        boolean result = false;	
		Connection connection = null;
		GregorianCalendar apptDate;
		String sqlStatement = new String("UPDATE appointments SET EmpID = ?, DogID = ?, ApptDate = ?, ApptTime = ? WHERE ApptID = ?"); 
		PreparedStatement prepSqlStatement = null;
		try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setInt(1, appointment.getEmpID());
			prepSqlStatement.setInt(2, appointment.getDogID());
                        apptDate = appointment.getApptDate();
                        java.sql.Date aDate = new java.sql.Date(apptDate.getTimeInMillis());
			prepSqlStatement.setDate(3, aDate);
                        prepSqlStatement.setTime(4, appointment.getApptTime());
                        prepSqlStatement.setInt(5, appointment.getApptID());
			
			int rowCount = prepSqlStatement.executeUpdate();
			result = (rowCount == 1) ? true : false; //should throw custom exception but keeping it simple
		} catch (SQLException ex) {
			ex.printStackTrace();
			result =  false;
		}	
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		return result; 
    }
    @Override
    public boolean deleteAppointment(int apptID)
    {//Method override for deleting an appointment
        Connection connection = null;
		String sqlStatement = new String("DELETE FROM appointments WHERE ApptID = ?"); 
		PreparedStatement prepSqlStatement = null;
		boolean result = false;
		
		try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setInt(1, apptID);
			int rowCount = prepSqlStatement.executeUpdate();
			result = (rowCount == 1) ? true : false;
		} catch (SQLException e) {
				e.printStackTrace();
		}
                finally {
                DbUtil.close(prepSqlStatement);
		DbUtil.close(connection);}
		return result;
    }
    @Override
    public Collection<Appointment> findAllAppointments()
    {//Method override for finding all appointments
        
       Connection connection = null;
		String sqlStatement = new String("SELECT * FROM appointments"); 
		PreparedStatement prepSqlStatement = null;
		ArrayList<Appointment> apptList = new ArrayList();
		ResultSet rs;
                GregorianCalendar aDate = null;
		Appointment appointment;
		
		try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			rs = prepSqlStatement.executeQuery();
			while (rs.next())
                        {
                                java.sql.Date apptDate = rs.getDate("ApptDate");
                                LocalDate appDate = apptDate.toLocalDate();
				aDate = new GregorianCalendar(appDate.getYear(), appDate.getMonthValue() - 1, appDate.getDayOfMonth());
				appointment = new Appointment(rs.getInt("ApptID"), rs.getInt("EmpID"), rs.getInt("DogID"), aDate, rs.getTime("ApptTime"));
				apptList.add(appointment);
			}
		} catch (SQLException e) {
				e.printStackTrace();
		}
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}		
		return apptList;
    }
    
    //SQL for Customers
     @Override
    public boolean insertCustomer(Customer customer)
    {
        //method override for adding a new customer
        //return true if successful, false if not
        boolean result = false;	
	Connection connection = null;
	String sqlStatement = new String("INSERT INTO customers VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"); 
	PreparedStatement prepSqlStatement = null;
                
        try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setInt(1, customer.getCustID());
			prepSqlStatement.setString(2, customer.getLastName());
                        prepSqlStatement.setString(3, customer.getFirstName());
                        prepSqlStatement.setString(4, customer.getAddress());
                        prepSqlStatement.setString(5, customer.getApartment());
                        prepSqlStatement.setString(6, customer.getCity());
                        prepSqlStatement.setString(7, customer.getState());
                        prepSqlStatement.setInt(8, customer.getZip());
                        prepSqlStatement.setString(9, customer.getPhone());
                        prepSqlStatement.setBoolean(10, false);
                       
			int rowCount = prepSqlStatement.executeUpdate();
			result = (rowCount == 1) ? true : false; 
		} catch (SQLException ex) {
			ex.printStackTrace();
			result =  false;
		}	
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		return result; 
    }
        public Customer findCustomerByID(int ID)
        {//method for finding a customer by ID
            Connection connection = null;
		String sqlStatement = new String("SELECT * FROM customers WHERE CustID = ?"); 
		PreparedStatement prepSqlStatement = null;
		ResultSet rs = null;
		Customer customer = null;
                try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setInt(1, ID);
			rs = prepSqlStatement.executeQuery();
			while (rs.next()){
				
				customer = new Customer(rs.getInt("CustID"), rs.getString("LastName"), 
                                        rs.getString("FirstName"), rs.getString("Address"), rs.getString("Apartment"), 
                                        rs.getString("City"), rs.getString("State"), rs.getInt("Zip"), 
                                        rs.getString("Phone"));
			}
                        if(rs.wasNull())
                        {
                            customer = null;
                        }
		} catch (SQLException e) {
				e.printStackTrace();
		}
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		return customer;
                
        }
    @Override
	public Customer findCustomer(String ph) 
        {
		 //method override for finding a customer by phone number
                 //return null customer if failed
		Connection connection = null;
		String sqlStatement = new String("SELECT * FROM customers WHERE Phone = ?"); 
		PreparedStatement prepSqlStatement = null;
		ResultSet rs = null;
		Customer customer = null;
		
		
		try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setString(1, ph);
			rs = prepSqlStatement.executeQuery();
			while (rs.next()){
				
				customer = new Customer(rs.getInt("CustID"), rs.getString("LastName"), 
                                        rs.getString("FirstName"), rs.getString("Address"), rs.getString("Apartment"), 
                                        rs.getString("City"), rs.getString("State"), rs.getInt("Zip"), 
                                        rs.getString("Phone"));
			}
                        if(rs.wasNull())
                        {
                            customer = null;
                        }
		} catch (SQLException e) {
				e.printStackTrace();
		}
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		return customer;
	}
        @Override
        public boolean updateCustomer(Customer customer)
        {
            //method override for updating a customer
            //return true if successful, false if not
            Connection connection = null;
            String sqlStatement = ("UPDATE customers SET LastName = ?, FirstName = ?, Address = ?,"
                    + "Apartment = ?, City = ?, State = ?, Zip = ?, Phone = ? WHERE CustID = ?");
            boolean result = false;
            PreparedStatement prepSqlStatement = null;
            try{
                connection = ConnectionFactory.getConnection(user, pass);
                prepSqlStatement = connection.prepareStatement(sqlStatement);
                prepSqlStatement.setString(1, customer.getLastName());
                prepSqlStatement.setString(2, customer.getFirstName());
                prepSqlStatement.setString(3, customer.getAddress());
                prepSqlStatement.setString(4, customer.getApartment());
                prepSqlStatement.setString(5, customer.getCity());
                prepSqlStatement.setString(6, customer.getState());
                prepSqlStatement.setInt(7, customer.getZip());
                prepSqlStatement.setString(8, customer.getPhone());
                prepSqlStatement.setInt(9, customer.getCustID());
                
                int dbResult = prepSqlStatement.executeUpdate();
			result = (dbResult == 1) ? true : false;
		} 
            catch (SQLException e) {
			e.printStackTrace();}
            finally {
                DbUtil.close(prepSqlStatement);
		DbUtil.close(connection);
            }
            return result;
        }
        @Override
        public boolean deleteCustomer(int id)
        {
            //method override for deleting a customer
            //return true if successful, false if not
            Connection connection = null;
            String sqlStatement = new String("DELETE FROM customers WHERE CustID = ?"); 
		PreparedStatement prepSqlStatement = null;
		boolean result = false;
		
		try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setInt(1, id);
			int rowCount = prepSqlStatement.executeUpdate();
			result = (rowCount == 1) ? true : false;
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
                finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		return result;
        }
        @Override
        public Collection<Customer> selectAllCustomers()
        {
            //method override for finding all customers
            //return an arraylist of all customers
            Connection connection = null;
            String sqlStatement = new String("SELECT * FROM customers"); 
		PreparedStatement prepSqlStatement = null;
		ArrayList<Customer> custList = new ArrayList();
		ResultSet rs;
		Customer customer;
                try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			rs = prepSqlStatement.executeQuery();
			while (rs.next()){
				customer = new Customer(rs.getInt("CustID"), rs.getString("LastName"), rs.getString("FirstName"),
                                        rs.getString("Address"), rs.getString("Apartment"), rs.getString("City"), rs.getString("State"),
                                        rs.getInt("Zip"),rs.getString("Phone"));
				custList.add(customer);
			}
		} catch (SQLException e) {
				e.printStackTrace();
		}
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}		
		return custList;
        }
        public boolean custIsBanned(int id)
        {//method to check if a customer is banned. Added 11/16/16
            //return true if a customer is banned, false if not
            Connection connection = null;
            String sqlStatement = new String("SELECT banned FROM customers WHERE CustID = ?"); 
            PreparedStatement prepSqlStatement = null;
            ResultSet rs = null;
            boolean result = false;
		
		
		try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setInt(1, id);
			rs = prepSqlStatement.executeQuery();
			while (rs.next()){
				result = rs.getBoolean("Banned");
			}
                       
		} catch (SQLException e) {
				e.printStackTrace();
		}
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		return result;
        }
        public boolean banCustomer(int id)
        {//method to ban a customer. Added 11/16/16
            //return true if successful, false if not
             Connection connection = null;
            String sqlStatement = new String("UPDATE customers SET banned = true WHERE CustID = ?"); 
		PreparedStatement prepSqlStatement = null;
		boolean result = false;
		
		try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setInt(1, id);
			int rowCount = prepSqlStatement.executeUpdate();
			result = (rowCount == 1) ? true : false;
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
                finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		return result;
        }
        public boolean unbanCustomer(int id)
        {//method to unban a customer. Added 11/16/16
            //return true if successful, false if not
             Connection connection = null;
            String sqlStatement = new String("UPDATE customers SET banned = false WHERE CustID = ?"); 
		PreparedStatement prepSqlStatement = null;
		boolean result = false;
		
		try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setInt(1, id);
			int rowCount = prepSqlStatement.executeUpdate();
			result = (rowCount == 1) ? true : false;
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
                finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		return result;
        }
        
    
    //SQL for Dogs
    @Override
	public boolean insertDog(Dog dog) 
        {
		//method override for adding a new dog
                //return true if successful, false if not
		boolean result = false;	
		Connection connection = null;
		GregorianCalendar bDate;
                GregorianCalendar rDate;
		String sqlStatement = new String("INSERT INTO dogs VALUES (?, ?, ?, ?,?, ?, ?, ?)"); 
		PreparedStatement prepSqlStatement = null;
		try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
                        prepSqlStatement.setInt(1, dog.getDogID());
			prepSqlStatement.setString(2, dog.getDogName());
			prepSqlStatement.setInt(3, dog.getDogBreed());
                        rDate = dog.getRabExp();
                        java.sql.Date rabDate = new java.sql.Date(rDate.getTimeInMillis());
                        prepSqlStatement.setDate(4, rabDate);
                        bDate = dog.getBDay();
                        //10/12/16 fixed code that was forcing the birthday and rab date to use the current date
                        java.sql.Date birthDate = new java.sql.Date(bDate.getTimeInMillis());
			prepSqlStatement.setDate(5, birthDate);
                       
                        
                        prepSqlStatement.setString(6, dog.getInstructions());
			prepSqlStatement.setInt(7, dog.getCustID());
                        prepSqlStatement.setBoolean(8, false);
			
			int rowCount = prepSqlStatement.executeUpdate();
			result = (rowCount == 1) ? true : false; //should throw custom exception but keeping it simple
		} catch (SQLException ex) {
			ex.printStackTrace();
			result =  false;
		}	
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		return result; 
	}
        @Override
	public Collection<Dog> findDog(int id) 
        {
            //method override for finding all dogs that belong to a certain customer
            //return an arraylist of all dogs belonging to the owner
		 Connection connection = null;
		String sqlStatement = new String("SELECT * FROM dogs WHERE CustID = ?"); 
		PreparedStatement prepSqlStatement = null;
		ArrayList<Dog> dogList = new ArrayList();
		ResultSet rs;
                GregorianCalendar bDate = null;
                GregorianCalendar rDate = null;
		Dog dog;
		
		try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
                        prepSqlStatement.setInt(1, id);
			rs = prepSqlStatement.executeQuery();
                        if(rs == null)
                        {
                            dogList = null;
                        }
                        else{
			while (rs.next())
                        {
                                java.sql.Date BirthDate = rs.getDate("Birthday");
                                LocalDate birDate = BirthDate.toLocalDate();
				bDate = new GregorianCalendar(birDate.getYear(), birDate.getMonthValue() - 1, birDate.getDayOfMonth());
                                java.sql.Date RabDate = rs.getDate("RabExp");
                                LocalDate rabDate = RabDate.toLocalDate();
                                rDate = new GregorianCalendar(rabDate.getYear(), rabDate.getMonthValue() -1, rabDate.getDayOfMonth());
				dog = new Dog(rs.getInt("DogID"), rs.getString("DogName"), rs.getInt("BreedID"), bDate, rDate, rs.getString("Instructions"), rs.getInt("CustID"));
				dogList.add(dog);
			}
                        }
		} catch (SQLException e) {
				e.printStackTrace();
		}
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}		
		return dogList;
	}
        public Dog findDogFromList(int cID, String dn)
        {
            //method to display the correct dog when selected from the list
            //return the correct dog
            Connection connection = null;
            String sqlStatement = new String("SELECT * FROM dogs WHERE CustID = ? AND DogName = ?");
            PreparedStatement prepSqlStatement = null;
            ResultSet rs = null;
            Dog dog = null;
            try{
                connection = ConnectionFactory.getConnection(user, pass);
                prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setInt(1, cID);
                        prepSqlStatement.setString(2, dn);
			rs = prepSqlStatement.executeQuery();
			while (rs.next()){
				
				java.sql.Date BirthDate = rs.getDate("Birthday");
                                LocalDate birDate = BirthDate.toLocalDate();
				GregorianCalendar bDate = new GregorianCalendar(birDate.getYear(), birDate.getMonthValue() - 1, birDate.getDayOfMonth());
                                java.sql.Date RabDate = rs.getDate("RabExp");
                                LocalDate rabDate = RabDate.toLocalDate();
                                GregorianCalendar rDate = new GregorianCalendar(rabDate.getYear(), rabDate.getMonthValue() -1, rabDate.getDayOfMonth());
				dog = new Dog(rs.getInt("DogID"), rs.getString("DogName"), rs.getInt("BreedID"), bDate, rDate, rs.getString("Instructions"), rs.getInt("CustID"));
			}
                        if(rs.wasNull())
                        {
                            dog = null;
                        }
		} catch (SQLException e) {
				e.printStackTrace();
		}
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		return dog;
                
           
        }
        @Override
        public Dog displayDog(int id)
        {
            //method override for finding one specific dog by dogID
            //return the dog selected by the dog's ID, or a null dog object if the owner does not have any
            Connection connection = null;
		String sqlStatement = new String("SELECT * FROM dogs WHERE DogID = ?"); 
		PreparedStatement prepSqlStatement = null;
		ResultSet rs = null;
		Dog dog = null;
		
		
		try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setInt(1, id);
			rs = prepSqlStatement.executeQuery();
			while (rs.next()){
				
				java.sql.Date BirthDate = rs.getDate("Birthday");
                                LocalDate birDate = BirthDate.toLocalDate();
				GregorianCalendar bDate = new GregorianCalendar(birDate.getYear(), birDate.getMonthValue() - 1, birDate.getDayOfMonth());
                                java.sql.Date RabDate = rs.getDate("RabExp");
                                LocalDate rabDate = RabDate.toLocalDate();
                                GregorianCalendar rDate = new GregorianCalendar(rabDate.getYear(), rabDate.getMonthValue() -1, rabDate.getDayOfMonth());
				dog = new Dog(rs.getInt("DogID"), rs.getString("DogName"), rs.getInt("BreedID"), bDate, rDate, rs.getString("Instructions"), rs.getInt("CustID"));
			}
                        if(rs.wasNull())
                        {
                            dog = null;
                        }
		} catch (SQLException e) {
				e.printStackTrace();
		}
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		return dog;
        }
        @Override
        public String getDogBreedString(int id)
        {
            //method override to get the Breed(String) of a certain dog
            //return the string version of the dog breed
            String breed = null; 
            Connection connection = null;
		String sqlStatement = new String("SELECT DogBreed FROM pricing WHERE BreedID = ?"); 
		PreparedStatement prepSqlStatement = null;
		ResultSet rs = null;
		
		
		
		try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setInt(1, id);
			rs = prepSqlStatement.executeQuery();
			while (rs.next()){
				
				breed = rs.getString("DogBreed");
			}
                        if(rs.wasNull())
                        {
                            breed = null;
                        }
		} catch (SQLException e) {
				e.printStackTrace();
		}
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
            
            return breed;
        }
        @Override
        public int getDogBreedID(String db)
        {
            //method override to get the BreedID of the selected breed(String)
            //return the breed ID of the string version of the dog breed
            int breedID = 0;
            Connection connection = null;
		String sqlStatement = new String("SELECT BreedID FROM pricing WHERE DogBreed = ?"); 
		PreparedStatement prepSqlStatement = null;
		ResultSet rs = null;
		
		
		
		try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setString(1, db);
			rs = prepSqlStatement.executeQuery();
			while (rs.next()){
				
				breedID = rs.getInt("BreedID");
			}
                        if(rs.wasNull())
                        {
                            breedID = 0;
                        }
		} catch (SQLException e) {
				e.printStackTrace();
		}
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
            
            return breedID;
        }
         @Override
	public boolean updateDog(Dog dog) 
        {
		//method override to update a dog
                //return true if successful, false if not
		boolean result = false;	
		Connection connection = null;
		GregorianCalendar bDate;
                GregorianCalendar rDate;
		String sqlStatement = new String("UPDATE dogs SET DogName = ?, BreedID = ?, Birthday = ?, RabExp = ?, Instructions = ?, CustID = ? WHERE DogID = ?"); 
		PreparedStatement prepSqlStatement = null;
		try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setString(1, dog.getDogName());
			prepSqlStatement.setInt(2, dog.getDogBreed());
                        bDate = dog.getBDay();
                        java.sql.Date birthDate = new java.sql.Date(bDate.getTimeInMillis());
			prepSqlStatement.setDate(3, birthDate);
                        rDate = dog.getRabExp();
                        java.sql.Date rabDate = new java.sql.Date(rDate.getTimeInMillis());
                        prepSqlStatement.setDate(4, rabDate);
			prepSqlStatement.setString(5, dog.getInstructions());
                        prepSqlStatement.setInt(6, dog.getCustID());
                        prepSqlStatement.setInt(7, dog.getDogID());
			
			int rowCount = prepSqlStatement.executeUpdate();
			result = (rowCount == 1) ? true : false; //should throw custom exception but keeping it simple
		} catch (SQLException ex) {
			ex.printStackTrace();
			result =  false;
		}	
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		return result; 
	}
        @Override
	public boolean deleteDog(int id) 
        {
		//method override to delete a dog
                //return true if successful, false if not
		Connection connection = null;
		String sqlStatement = new String("DELETE FROM dogs WHERE DogID = ?"); 
		PreparedStatement prepSqlStatement = null;
		boolean result = false;
		
		try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setInt(1, id);
			int rowCount = prepSqlStatement.executeUpdate();
			result = (rowCount == 1) ? true : false;
		} catch (SQLException e) {
				e.printStackTrace();
		}
                finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		return result;
	}
        @Override
	public Collection<Dog> selectAllDogs() {
		//method override to find all dogs
                //return an arraylist of all dogs in the database
		Connection connection = null;
		String sqlStatement = new String("SELECT * FROM dogs"); 
		PreparedStatement prepSqlStatement = null;
		ArrayList<Dog> dogList = new ArrayList();
		ResultSet rs;
                GregorianCalendar bDate = null;
                GregorianCalendar rDate = null;
		Dog dog;
		
		try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			rs = prepSqlStatement.executeQuery();
			while (rs.next())
                        {
                                java.sql.Date BirthDate = rs.getDate("Birthday");
                                LocalDate birDate = BirthDate.toLocalDate();
				bDate = new GregorianCalendar(birDate.getYear(), birDate.getMonthValue() - 1, birDate.getDayOfMonth());
                                java.sql.Date RabDate = rs.getDate("RabExp");
                                LocalDate rabDate = RabDate.toLocalDate();
                                rDate = new GregorianCalendar(rabDate.getYear(), rabDate.getMonthValue() -1, rabDate.getDayOfMonth());
				dog = new Dog(rs.getInt("DogID"), rs.getString("DogName"), rs.getInt("BreedID"), bDate, rDate, rs.getString("Instructions"), rs.getInt("CustID"));
				dogList.add(dog);
			}
		} catch (SQLException e) {
				e.printStackTrace();
		}
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}		
		return dogList;
	}
        public boolean dogIsBanned(int id)
        {//method to check if a dog is banned. Added 11/16/16
            //return true if dog is banned, false if not
            Connection connection = null;
            String sqlStatement = new String("SELECT banned FROM dogs WHERE DogID = ?"); 
            PreparedStatement prepSqlStatement = null;
            ResultSet rs = null;
            boolean result = false;
		
		
		try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setInt(1, id);
			rs = prepSqlStatement.executeQuery();
			while (rs.next()){
				result = rs.getBoolean("Banned");
			}
                       
		} catch (SQLException e) {
				e.printStackTrace();
		}
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		return result;
        }
        public boolean banDog(int id)
        {//method to ban a dog. Added 11/16/16
            //return true if successful, false if not
             Connection connection = null;
            String sqlStatement = new String("UPDATE dogs SET banned = true WHERE DogID = ?"); 
		PreparedStatement prepSqlStatement = null;
		boolean result = false;
		
		try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setInt(1, id);
			int rowCount = prepSqlStatement.executeUpdate();
			result = (rowCount == 1) ? true : false;
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
                finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		return result;
        }
         public boolean unbanDog(int id)
        {//method to unban a dog. Added 11/16/16
            //return true if successful, false if not
             Connection connection = null;
            String sqlStatement = new String("UPDATE dogs SET banned = false WHERE DogID = ?"); 
		PreparedStatement prepSqlStatement = null;
		boolean result = false;
		
		try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setInt(1, id);
			int rowCount = prepSqlStatement.executeUpdate();
			result = (rowCount == 1) ? true : false;
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
                finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		return result;
        }
    //SQL for Employees
   
            
    
    @Override
    public boolean insertEmployee(Employee emp)
    {
        //method override for adding a new employee
        //return true if successful, false if not
        //altered 11/16/16 to include new information fields
        boolean result = false;	
	Connection connection = null;
	String sqlStatement = new String("INSERT INTO employees VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"); 
	PreparedStatement prepSqlStatement = null;
                
        try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setInt(1, emp.getEmpID());
			prepSqlStatement.setString(2, emp.getLastName());
                        prepSqlStatement.setString(3, emp.getFirstName());
                        prepSqlStatement.setString(4, emp.getAddress());
                        prepSqlStatement.setString(5, emp.getApartment());
                        prepSqlStatement.setString(6, emp.getCity());
                        prepSqlStatement.setString(7, emp.getState());
                        prepSqlStatement.setInt(8, emp.getZip());
                        prepSqlStatement.setString(9, emp.getPhone());
                        prepSqlStatement.setString(10, emp.getPayType());
                        prepSqlStatement.setDouble(11, emp.getHrlyPay());
                        prepSqlStatement.setDouble(12, emp.getCommPercent());
                        prepSqlStatement.setDouble(13, emp.getSalary());
                        prepSqlStatement.setDouble(14, emp.getSales());
                        prepSqlStatement.setDouble(15, emp.getTotalSales());
                        prepSqlStatement.setDouble(16, emp.getCommission());
                        prepSqlStatement.setString(17, emp.getEmpType());
                        GregorianCalendar hDate = emp.getHireDate();
                        java.sql.Date hireDate = new java.sql.Date(hDate.getTimeInMillis());
                        prepSqlStatement.setDate(18, hireDate);
                       
			int rowCount = prepSqlStatement.executeUpdate();
			result = (rowCount == 1) ? true : false; 
		} catch (SQLException ex) {
			ex.printStackTrace();
			result =  false;
		}	
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		return result; 
    }
    public int getLastEmp()
    {
        int id = 0;
        Connection connection = null;
	String sqlStatement = new String("SELECT EmpID FROM employees ORDER BY EmpID DESC LIMIT 1"); 
	PreparedStatement prepSqlStatement = null;
        ResultSet rs = null;
                
       try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			rs = prepSqlStatement.executeQuery();
			while (rs.next()){
				id = rs.getInt("EmpID");
			}
                       
		} catch (SQLException e) {
				e.printStackTrace();
		}
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		return id; 
    }
    @Override
    public Employee findEmployee(int empID)
    {
        //method override for finding an employee
        //return null employee if failed
        //Altered 11/16/16 to include new information fields
        GregorianCalendar hireDate;
	Connection connection = null;
	String sqlStatement = new String("SELECT * FROM employees WHERE EmpID = ?"); 
	PreparedStatement prepSqlStatement = null;
	ResultSet rs = null;
	Employee emp = null;
		
		
	try {
		connection = ConnectionFactory.getConnection(user, pass);
		prepSqlStatement = connection.prepareStatement(sqlStatement);
		prepSqlStatement.setInt(1, empID);
		rs = prepSqlStatement.executeQuery();
		while (rs.next())
                {
			java.sql.Date date = rs.getDate("EmpHireDate");
                        LocalDate hDate = date.toLocalDate();
                        hireDate = new GregorianCalendar(hDate.getYear(), hDate.getMonthValue() -1, hDate.getDayOfMonth());	
                    emp = new Employee(rs.getInt("EmpID"), rs.getString("EmpLastName"), 
                                        rs.getString("EmpFirstName"), rs.getString("EmpAddress"), rs.getString("EmpApartment"), 
                                        rs.getString("EmpCity"), rs.getString("EmpState"), rs.getInt("EmpZip"), 
                                        rs.getString("EmPhone"), rs.getString("EmpPayType"), rs.getDouble("EmpHrly"), rs.getDouble("EmpCommPercent"), rs.getDouble("EmpSalary"),rs.getDouble("EmpSales"), rs.getDouble("EmpTotalSales"),
                                        rs.getDouble("EmpComm"), rs.getString("EmpType"), hireDate);
                        
		}
                    if(rs.wasNull())
                     {
                            emp = null;
                     }
                    
		} catch (SQLException e) {
				e.printStackTrace();
		}
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		return emp;
    }
    @Override
    public boolean updateEmployee(Employee emp)
    {
        //method override for updating an employee
        //return true if successful, false if not
        //altered 11/16/16 to include new information fields
         Connection connection = null;
         String sqlStatement = ("UPDATE employees SET EmpLastName = ?, EmpFirstName = ?, EmpAddress = ?,"
                    + "EmpApartment = ?, EmpCity = ?, EmpState = ?, EmpZip = ?, EmPhone = ?, EmpPayType = ?,"
                 + "EmpHrly = ?, EmpCommPercent = ?, EmpSalary = ?, EmpSales = ?, EmpTotalSales = ?, EmpComm = ?, "
                 + "EmpType = ?, EmpHireDate = ? WHERE EmpID = ?");
          boolean result = false;
         PreparedStatement prepSqlStatement = null;
         try{
                connection = ConnectionFactory.getConnection(user, pass);
                prepSqlStatement = connection.prepareStatement(sqlStatement);
                prepSqlStatement.setString(1, emp.getLastName());
                prepSqlStatement.setString(2, emp.getFirstName());
                prepSqlStatement.setString(3, emp.getAddress());
                prepSqlStatement.setString(4, emp.getApartment());
                prepSqlStatement.setString(5, emp.getCity());
                prepSqlStatement.setString(6, emp.getState());
                prepSqlStatement.setInt(7, emp.getZip());
                prepSqlStatement.setString(8, emp.getPhone());
                prepSqlStatement.setString(9, emp.getPayType());
                prepSqlStatement.setDouble(10, emp.getHrlyPay());
                prepSqlStatement.setDouble(11, emp.getCommPercent());
                prepSqlStatement.setDouble(12, emp.getSalary());
                prepSqlStatement.setDouble(13, emp.getSales());
                prepSqlStatement.setDouble(14, emp.getTotalSales());
                prepSqlStatement.setDouble(15, emp.getCommission());
                prepSqlStatement.setString(16, emp.getEmpType());
                GregorianCalendar hDate = emp.getHireDate();
                java.sql.Date hireDate = new java.sql.Date(hDate.getTimeInMillis());
                prepSqlStatement.setDate(17, hireDate);
                prepSqlStatement.setInt(18, emp.getEmpID());
                
                int dbResult = prepSqlStatement.executeUpdate();
			result = (dbResult == 1) ? true : false;
		} 
            catch (SQLException e) {
			e.printStackTrace();}
            finally {
                DbUtil.close(prepSqlStatement);
		DbUtil.close(connection);
            }
            return result;
    }
    @Override
    public boolean deleteEmployee(int empID)
    {
        //method override for deleting an employee
        //return true if successful, false if not
        Connection connection = null;
        String sqlStatement = new String("DELETE FROM employees WHERE EmpID = ?"); 
	PreparedStatement prepSqlStatement = null;
	boolean result = false;
		
	try {
		connection = ConnectionFactory.getConnection(user, pass);
		prepSqlStatement = connection.prepareStatement(sqlStatement);
		prepSqlStatement.setInt(1, empID);
		int rowCount = prepSqlStatement.executeUpdate();
		result = (rowCount == 1) ? true : false;
	} catch (SQLException e) {
			e.printStackTrace();
		}
        finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		return result;
    }
    @Override
    public Collection<Employee> selectAllEmployees()
    {
        //method override for finding all employees
        //return an arraylist of all employees
        //Altered 11/16/16 to include new information fields
        Connection connection = null;
        String sqlStatement = new String("SELECT * FROM employees"); 
	PreparedStatement prepSqlStatement = null;
	ArrayList<Employee> empList = new ArrayList();
	ResultSet rs;
	Employee emp;
         try {
		connection = ConnectionFactory.getConnection(user, pass);
		prepSqlStatement = connection.prepareStatement(sqlStatement);
		rs = prepSqlStatement.executeQuery();
		while (rs.next()){
                    java.sql.Date date = rs.getDate("EmpHireDate");
                                LocalDate hDate = date.toLocalDate();
                                GregorianCalendar hireDate = new GregorianCalendar(hDate.getYear(), hDate.getMonthValue() -1, hDate.getDayOfMonth());	
                    
			emp = new Employee(rs.getInt("EmpID"), rs.getString("EmpLastName"), rs.getString("EmpFirstName"),
                                        rs.getString("EmpAddress"), rs.getString("EmpApartment"), rs.getString("EmpCity"), rs.getString("EmpState"),
                                        rs.getInt("EmpZip"),rs.getString("EmPhone"), rs.getString("EmpPayType"),rs.getDouble("EmpHrly"), 
                                        rs.getDouble("EmpCommPercent"), rs.getDouble("EmpSalary"), rs.getDouble("EmpSales"), rs.getDouble("EmpTotalSales"),
                                        rs.getDouble("EmpComm"), rs.getString("EmpType"), hireDate);
			empList.add(emp);
			}
		} catch (SQLException e) {
				e.printStackTrace();
		}
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}		
		return empList;
    }
    public Collection<Employee> findAllEmployeeType(String s)
    {
         //method override for finding all employees of a certain type
        //return an arraylist of all employees of type matching the argument passed
        GregorianCalendar hireDate;
        Connection connection = null;
        String sqlStatement = new String("SELECT * FROM employees WHERE EmpType = ?"); 
	PreparedStatement prepSqlStatement = null;
	ArrayList<Employee> empList = new ArrayList();
	ResultSet rs;
	Employee emp;
         try {
		connection = ConnectionFactory.getConnection(user, pass);
		prepSqlStatement = connection.prepareStatement(sqlStatement);
                prepSqlStatement.setString(1, s);
		rs = prepSqlStatement.executeQuery();
		while (rs.next()){
                    
                    java.sql.Date date = rs.getDate("EmpHireDate");
                                LocalDate hDate = date.toLocalDate();
                                hireDate = new GregorianCalendar(hDate.getYear(), hDate.getMonthValue() -1, hDate.getDayOfMonth());	
			emp = new Employee(rs.getInt("EmpID"), rs.getString("EmpLastName"), rs.getString("EmpFirstName"),
                                        rs.getString("EmpAddress"), rs.getString("EmpApartment"), rs.getString("EmpCity"), rs.getString("EmpState"),
                                        rs.getInt("EmpZip"),rs.getString("EmPhone"),rs.getString("EmpPayType"), rs.getDouble("EmpHrly"), 
                                        rs.getDouble("EmpCommPercent"), rs.getDouble("EmpSalary"),rs.getDouble("EmpSales"), rs.getDouble("EmpTotalSales"),
                                        rs.getDouble("EmpComm"), rs.getString("EmpType"), hireDate);
			empList.add(emp);
			
                }
		} catch (SQLException e) {
				e.printStackTrace();
		}
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}		
		return empList;
    }
    public Collection<Employee> findAllEmployeePayType(String s)
    {
        //method override for finding all employees of a certain type
        //return an arraylist of all employees of type matching the argument passed
        GregorianCalendar hireDate;
        Connection connection = null;
        String sqlStatement = new String("SELECT * FROM employees WHERE EmpPayType = ?"); 
	PreparedStatement prepSqlStatement = null;
	ArrayList<Employee> empList = new ArrayList();
	ResultSet rs;
	Employee emp;
         try {
		connection = ConnectionFactory.getConnection(user, pass);
		prepSqlStatement = connection.prepareStatement(sqlStatement);
                prepSqlStatement.setString(1, s);
		rs = prepSqlStatement.executeQuery();
		while (rs.next()){
                    
                    java.sql.Date date = rs.getDate("EmpHireDate");
                                LocalDate hDate = date.toLocalDate();
                                hireDate = new GregorianCalendar(hDate.getYear(), hDate.getMonthValue() -1, hDate.getDayOfMonth());	
			emp = new Employee(rs.getInt("EmpID"), rs.getString("EmpLastName"), rs.getString("EmpFirstName"),
                                        rs.getString("EmpAddress"), rs.getString("EmpApartment"), rs.getString("EmpCity"), rs.getString("EmpState"),
                                        rs.getInt("EmpZip"),rs.getString("EmPhone"),rs.getString("EmpPayType"), rs.getDouble("EmpHrly"), 
                                        rs.getDouble("EmpCommPercent"), rs.getDouble("EmpSalary"),rs.getDouble("EmpSales"), rs.getDouble("EmpTotalSales"),
                                        rs.getDouble("EmpComm"), rs.getString("EmpType"), hireDate);
			empList.add(emp);
			
                }
		} catch (SQLException e) {
				e.printStackTrace();
		}
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}		
		return empList;
    }
    //SQL for Pricing
     @Override
    public boolean insertBreed(Pricing pricing)
    {
        //method override to add a new breed to the price list
        ////return true if successful, false if not
        boolean result = false;	
		Connection connection = null;
		String sqlStatement = new String("INSERT INTO pricing VALUES (?, ?, ?)"); 
		PreparedStatement prepSqlStatement = null;
                
        try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
                        prepSqlStatement.setInt(1, pricing.getBreedID());
			prepSqlStatement.setString(2, pricing.getBreed());
                        prepSqlStatement.setDouble(3, pricing.getPrice());
                       
                       
			int rowCount = prepSqlStatement.executeUpdate();
			result = (rowCount == 1) ? true : false; //should throw custom exception but keeping it simple
		} catch (SQLException ex) {
			ex.printStackTrace();
			result =  false;
		}	
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		return result; 
    }
    @Override
    public Pricing findPrice(int i)
    {
        //method override to find one breed
        //return the pricing object of the breed ID or a null pricing object if it fails
        Connection connection = null;
		String sqlStatement = new String("SELECT * FROM pricing WHERE BreedID = ?"); 
		PreparedStatement prepSqlStatement = null;
		ResultSet rs = null;
		Pricing pricing = null;
		
		
		try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setInt(1, i);
			rs = prepSqlStatement.executeQuery();
			while (rs.next()){
				
				pricing = new Pricing(rs.getInt("BreedID"), rs.getString("DogBreed"), 
                                        rs.getDouble("Price"));
			}
                        if(rs.wasNull())
                        {
                            pricing = null;
                        }
		} catch (SQLException e) {
				e.printStackTrace();
		}
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		return pricing;
    }
    @Override
    public boolean updateBreed(Pricing pricing)
    {
        //method override to update a breed
        //return true if successful, false if not
          Connection connection = null;
            String sqlStatement = ("UPDATE pricing SET Breed = ?, Price = ? WHERE BreedID = ?");
            boolean result = false;
            PreparedStatement prepSqlStatement = null;
            try{
                connection = ConnectionFactory.getConnection(user, pass);
                prepSqlStatement = connection.prepareStatement(sqlStatement);
                prepSqlStatement.setString(1, pricing.getBreed());
                prepSqlStatement.setDouble(2, pricing.getPrice());
                prepSqlStatement.setInt(3, pricing.getBreedID());
            
                
                int dbResult = prepSqlStatement.executeUpdate();
			result = (dbResult == 1) ? true : false;
		} 
            catch (SQLException e) {
			e.printStackTrace();}
            finally {
                DbUtil.close(prepSqlStatement);
		DbUtil.close(connection);
            }
       return result; 
    }
    public boolean deleteBreed(int id)
    {
        //method override to delete a breed from the price list
        //return true if successful, false if not
        Connection connection = null;
            String sqlStatement = new String("DELETE FROM pricing WHERE BreedID = ?"); 
		PreparedStatement prepSqlStatement = null;
		boolean result = false;
		
		try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setInt(1, id);
			int rowCount = prepSqlStatement.executeUpdate();
			result = (rowCount == 1) ? true : false;
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
                finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
        return result;
    }
    @Override
    public Collection<Pricing> selectAllBreeds()
    {
        //method override to find all breeds on the price list
        //return an arraylist of all breeds on the price list
         Connection connection = null;
            String sqlStatement = new String("SELECT * FROM pricing"); 
		PreparedStatement prepSqlStatement = null;
		ArrayList<Pricing> breedList = new ArrayList();
		ResultSet rs;
		Pricing pricing;
                try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			rs = prepSqlStatement.executeQuery();
			while (rs.next()){
				pricing = new Pricing(rs.getInt("BreedID"), rs.getString("DogBreed"), rs.getDouble("Price"));
				breedList.add(pricing);
			}
		} catch (SQLException e) {
				e.printStackTrace();
		}
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
                return breedList;
		}		
    }
    //SQL for Transactions
    @Override
    public boolean insertTrans(Transaction trans)
    {
        //method override to add a new breed to the price list
        ////return true if successful, false if not
        boolean result = false;	
		Connection connection = null;
		String sqlStatement = new String("INSERT INTO transactions VALUES (?, ?, ?, ?, ?, null, null)"); 
		PreparedStatement prepSqlStatement = null;
                
        try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
                        prepSqlStatement.setInt(1, trans.getTranID());
                        prepSqlStatement.setInt(2, trans.getEmpID());
			prepSqlStatement.setInt(3, trans.getDogID());
                        prepSqlStatement.setInt(4, trans.getCustID());
                        prepSqlStatement.setDouble(5, trans.getPrice());
                       
                       
			int rowCount = prepSqlStatement.executeUpdate();
			result = (rowCount == 1) ? true : false; //should throw custom exception but keeping it simple
		} catch (SQLException ex) {
			ex.printStackTrace();
			result =  false;
		}	
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
		return result; 
    }
    //method to get the TransID of the last transaction recorded
    public int getTransID()
    {
        int id = 0;
        Connection connection = null;
	String sqlStatement = new String("SELECT TransID FROM transactions ORDER BY TransID DESC LIMIT 1;"); 
	PreparedStatement prepSqlStatement = null;
        ResultSet rs;
        try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			rs = prepSqlStatement.executeQuery();
                        while(rs.next())
                        {
                            id = rs.getInt("TransID");
                        }
			
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
        finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
        return id;
    }
    @Override
    public Collection<Transaction> findTrans(int CustID)
    {
         //method override to find one transaction
        //return the transaction object of the transaction ID or a null transaction object if it fails
        Connection connection = null;
            String sqlStatement = new String("SELECT * FROM transactions WHERE CustID = ?"); 
		PreparedStatement prepSqlStatement = null;
		ArrayList<Transaction> transList = new ArrayList();
		ResultSet rs;
		Transaction trans;
                try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
                        prepSqlStatement.setInt(1, CustID);
			rs = prepSqlStatement.executeQuery();
			while (rs.next()){
				trans = new Transaction(rs.getInt("TransID"), rs.getInt("EmpID"), rs.getInt("DogID"), rs.getInt("CustID"), rs.getDouble("SalePrice"));
				transList.add(trans);
			}
		} catch (SQLException e) {
				e.printStackTrace();
		}
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
                return transList;
                }
    }
    @Override
    public boolean updateTrans(Transaction trans)
    {
       //method override to update a transaction
        //return true if successful, false if not
          Connection connection = null;
            String sqlStatement = ("UPDATE transactions SET EmpID = ?, DogID = ?, CustID = ?, SalePrice = ? WHERE TransID = ?");
            boolean result = false;
            PreparedStatement prepSqlStatement = null;
            try{
                connection = ConnectionFactory.getConnection(user, pass);
                prepSqlStatement = connection.prepareStatement(sqlStatement);
                prepSqlStatement.setInt(1, trans.getEmpID());
                prepSqlStatement.setInt(2, trans.getDogID());
                prepSqlStatement.setInt(3, trans.getCustID());
                prepSqlStatement.setDouble(4, trans.getPrice());
                prepSqlStatement.setInt(5, trans.getTranID());
            
                
                int dbResult = prepSqlStatement.executeUpdate();
			result = (dbResult == 1) ? true : false;
		} 
            catch (SQLException e) {
			e.printStackTrace();}
            finally {
                DbUtil.close(prepSqlStatement);
		DbUtil.close(connection);
            }
       return result; 
    }
    @Override
    public boolean deleteTrans(int transID)
    {
       //method override to delete a transaction 
        //return true if successful, false if not
        Connection connection = null;
            String sqlStatement = new String("DELETE FROM transactions WHERE TransID = ?"); 
		PreparedStatement prepSqlStatement = null;
		boolean result = false;
		
		try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setInt(1, transID);
			int rowCount = prepSqlStatement.executeUpdate();
			result = (rowCount == 1) ? true : false;
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
                finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
		}
        return result;
    }
    @Override
    public Collection<Transaction> selectAllTrans()
    {
        //method override to find all transactions
        //return an arraylist of all transactions
         Connection connection = null;
            String sqlStatement = new String("SELECT * FROM transactions"); 
		PreparedStatement prepSqlStatement = null;
		ArrayList<Transaction> transList = new ArrayList();
		ResultSet rs;
		Transaction trans;
                try {
			connection = ConnectionFactory.getConnection(user, pass);
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			rs = prepSqlStatement.executeQuery();
			while (rs.next()){
				trans = new Transaction(rs.getInt("TransID"), rs.getInt("EmpID"), rs.getInt("DogID"), rs.getInt("CustID"), rs.getDouble("SalePrice"));
				transList.add(trans);
			}
		} catch (SQLException e) {
				e.printStackTrace();
		}
		finally {
			DbUtil.close(prepSqlStatement);
			DbUtil.close(connection);
                return transList;
		}		
    }
}
