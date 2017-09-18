//Author: Tanya Huisman
//Date: 10/6/2016
//Purpose: Handles the login dialog form for users to login to the system
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.prefs.Preferences;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginDialog {
	private String userName;
	private char[] password;
	
	public LoginDialog(){
		 JPanel loginPanel = new JPanel();
		 loginPanel.setLayout(new GridLayout(2,2));  //Labels for the textfield components  
		 JLabel lblUserName = new JLabel("Username:"); 
		 JLabel lblPassword = new JLabel("Password:");  
		 JTextField txtUserName = new JTextField(); 
		 JPasswordField txtPassword = new JPasswordField();  //Add the components to the JPanel  
		 loginPanel.add(lblUserName); 
		 loginPanel.add(txtUserName); 
		 loginPanel.add(lblPassword); 
		 loginPanel.add(txtPassword);  
		 
		 int input = JOptionPane.showConfirmDialog(null, loginPanel, 
				 "Login Required" ,JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		 if(input == JOptionPane.CANCEL_OPTION)
                 {
                     System.exit(0);
                 }
		 setUserName(txtUserName.getText());
		 setPassword(txtPassword.getPassword());	
	}
	public void setCredentials(String username, char[] password) {
		setUserName( username );
		setPassword( password );
	  }
	public void setUserName(String newUser){
		
		this.userName = newUser;
	}
	public void setPassword (char[] newPassword){
		
		password = Arrays.copyOf(newPassword, newPassword.length);
	}
	public void setPassword(String newPassword){
		password = newPassword.toCharArray();
	}
	public String getUserName() {
	    return this.userName;
	}

	public String getPassword() {
	    return new String(this.password);
	}
}
