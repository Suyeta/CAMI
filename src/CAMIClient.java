//get the program running, main class

public class CAMIClient 
{
    public static void main(String args[]) 
    {    
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            public void run() 
            {
                new CAMIFrame().setVisible(true);
            }
        }
        );
    }
}
