
import com.toedter.calendar.JDateChooser;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Suyeta
 */
public class RescheduleFrame extends javax.swing.JFrame {
int empID;
GregorianCalendar apptDate;
Time time;
String user;
String pass;
CAMISQLDao camiDAO;
DateTimeFormatter df = DateTimeFormatter.ofPattern("MMM dd, yyyy");
char colIndex = 'B';
String colIndexS = "B";
DefaultTableModel tblModel;
public static boolean closed = false;
    /**
     * Creates new form RescheduleFrame
     */
    public RescheduleFrame() {
        this.user = CAMIFrame.username;
        this.pass = CAMIFrame.password;
    try {
        camiDAO = new CAMISQLDao(user, pass);
    } catch (SQLException ex) {
        Logger.getLogger(RescheduleFrame.class.getName()).log(Level.SEVERE, null, ex);
    }
        initComponents();
        setUpTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        tblReschedule = new JTable(){
            public boolean isCellEditable(int rowIndex, int vColIndex) {
                return false;
            }};
            jdcReschAppt = new com.toedter.calendar.JDateChooser();
            lblSuccessFail = new javax.swing.JLabel();

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowActivated(java.awt.event.WindowEvent evt) {
                    formWindowActivated(evt);
                }
            });

            tblReschedule.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                    {"8:00 am", null, null},
                    {"8:30 am", null, null},
                    {"9:00 am", null, null},
                    {"9:30 am", null, null},
                    {"10:00am", null, null},
                    {"10:30am", null, null},
                    {"11:00am", null, null},
                    {"1:00pm", null, null},
                    {"1:30pm", null, null},
                    {"2:00pm", null, null}
                },
                new String [] {
                    "Times", "B", "C"
                }
            ));
            tblReschedule.setCellSelectionEnabled(true);
            tblReschedule.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            tblReschedule.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblRescheduleMouseClicked(evt);
                }
            });
            jScrollPane2.setViewportView(tblReschedule);

            jdcReschAppt.setDateFormatString("MMM dd, yyyy");
            jdcReschAppt.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
                public void propertyChange(java.beans.PropertyChangeEvent evt) {
                    jdcReschApptPropertyChange(evt);
                }
            });

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jdcReschAppt, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(31, 31, 31))
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 726, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(172, 172, 172)
                            .addComponent(lblSuccessFail, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jdcReschAppt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(26, 26, 26)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(lblSuccessFail, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addContainerGap())
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents
public void setUpTable()
    {
        
        ArrayList<Employee> emps = (ArrayList<Employee>)camiDAO.findAllEmployeeType("Groomer");
           emps.stream().forEach((Employee specificItem) -> {
            TableColumn col = tblReschedule.getColumn(colIndexS);
            col.setHeaderValue(specificItem.getEmpID() + ", " + specificItem.getFirstName());
            col.setIdentifier(specificItem.getEmpID());
            colIndex++;
            colIndexS = String.valueOf(colIndex);
            });
    }

    private void tblRescheduleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRescheduleMouseClicked

        int column = tblReschedule.getSelectedColumn();
        if(column == 0)
        {
            lblSuccessFail.setText("<html>That is an invalid appointment slot</html>");
        }
        else
        {
            TableColumn selectedColumn = tblReschedule.getColumnModel().getColumn(column);
            int row = tblReschedule.getSelectedRow();
            Object value = tblReschedule.getModel().getValueAt(row, column);
            if(value == null)
            {
                int apptID = Integer.parseInt(CAMIFrame.lblApptID.getText());
                int dogID = Integer.parseInt(CAMIFrame.cmbDogID.getSelectedItem().toString());
                empID = Integer.parseInt(selectedColumn.getIdentifier().toString());
                String appDate = ((JTextField)jdcReschAppt.getDateEditor().getUiComponent()).getText();
                LocalDate date = LocalDate.parse(appDate, df);
                apptDate = new GregorianCalendar(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
                String t;
                switch (row)
                {
                    case 0: t = "08:00:00";
                        break;
                    case 1: t = "08:30:00";
                        break;
                    case 2: t = "09:00:00";
                        break;
                    case 3: t = "09:30:00";
                        break;
                    case 4: t = "10:00:00";
                        break;
                    case 5: t = "10:30:00";
                        break;
                    case 6: t = "11:00:00";
                        break;
                    case 7: t = "13:00:00";
                        break;
                    case 8: t = "13:30:00";
                        break;
                    case 9: t = "14:00:00";
                        break;
                    default: t = "00:00:00";
                        break;
                    }
                time = Time.valueOf(t);
                 Appointment appt = new Appointment(apptID, empID, dogID, apptDate, time);
                 camiDAO.updateAppointment(appt);
                FillTableWithAppts(apptDate);
            }
            else
            {
              lblSuccessFail.setText("That time slot is already filled");

            }
            this.dispose();
            closed = true;
            CAMIFrame.clearApptFields();
        
        }
    }//GEN-LAST:event_tblRescheduleMouseClicked

    private void jdcReschApptPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jdcReschApptPropertyChange
        // TODO add your handling code here:
        updateTable();

    }//GEN-LAST:event_jdcReschApptPropertyChange

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
      LocalDate now = LocalDate.now();
           GregorianCalendar today = new GregorianCalendar(now.getMonthValue(), now.getDayOfMonth(), now.getYear());
           jdcReschAppt.setCalendar(today);
           java.util.Date rightnow = new java.util.Date();
           jdcReschAppt.setDate(rightnow);
 
            FillTableWithAppts(jdcReschAppt.getCalendar());
    }//GEN-LAST:event_formWindowActivated
  public static void clearTable(JTable table) {
   for (int i = 0; i < table.getRowCount(); i++)
      for(int j = 1; j < table.getColumnCount(); j++) {
          table.setValueAt(null, i, j);}
  }
  public void updateTable()
    {
        String appDate = ((JTextField)jdcReschAppt.getDateEditor().getUiComponent()).getText();
        if(!appDate.isEmpty())
        {
        clearTable(tblReschedule);
        LocalDate date = LocalDate.parse(appDate, df);
        GregorianCalendar apptDate = new GregorianCalendar(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        FillTableWithAppts(apptDate);
        }
    }
  public void FillTableWithAppts(Calendar c)
    {   //method to fill the appointments table with appointmens for the day selected.
      
        Date date = new Date(c.getTimeInMillis());
        ArrayList<Appointment> appts = (ArrayList<Appointment>) camiDAO.findAppointments(date);
        appts.stream().forEach((specificItem)-> {
            int apptID = specificItem.getApptID();
            int dogID = specificItem.getDogID();
            Dog dog = camiDAO.displayDog(dogID);
            int dogB = dog.getDogBreed();
            String dogBreed = camiDAO.getDogBreedString(dogB);
            String dogName = dog.getDogName();
            String appointment = apptID + ", " + dogName + "/" + dogBreed;
            Time time = specificItem.getApptTime();
            int empID = specificItem.getEmpID();
            Employee emp = camiDAO.findEmployee(empID);
            String t = time.toString();
            int row;
            TableColumn col = tblReschedule.getColumn(empID);
            int column = col.getModelIndex();
            switch(t)
            {
                case "08:00:00": row = 0;
                break;
                case "08:30:00": row = 1;
                break;
                case "09:00:00": row = 2;
                break;
                case "09:30:00": row = 3;
                break;
                case "10:00:00": row = 4;
                break;
                case "10:30:00": row = 5;
                break;
                case "11:00:00": row = 6;
                break;
                case "13:00:00": row = 7;
                break;
                case "13:30:00": row = 8;
                break;
                case "14:00:00": row = 9;
                break;
                default: row = 0;
                break;
            }
            TableModel model = tblReschedule.getModel();
            model.setValueAt(appointment, row, column);
            
        });
    }
  
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private com.toedter.calendar.JDateChooser jdcReschAppt;
    private javax.swing.JLabel lblSuccessFail;
    private javax.swing.JTable tblReschedule;
    // End of variables declaration//GEN-END:variables
}
