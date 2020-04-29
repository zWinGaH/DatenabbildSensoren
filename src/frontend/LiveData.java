/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import backend.resource;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    
import org.eclipse.californium.core.CoapClient;

/**
 *
 * @author tobia
 */
public class LiveData extends javax.swing.JFrame {

    /**
     * Creates new form LiveData*/
    
    public static Boolean thisIsVisible = false;
    public static resource[] activeArray = null;
    private int oldseconds;
    
    public LiveData() {
        initComponents();
        this.setLocation(300, 200);
        table.setTableHeader(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Live");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFocusable(false);
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        table.setBackground(new java.awt.Color(240, 240, 240));
        table.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        table.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Adress", "Datetime", "Result", "Notifications"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table.setEnabled(false);
        table.setFocusable(false);
        table.setGridColor(new java.awt.Color(255, 255, 255));
        table.setRowSelectionAllowed(false);
        table.setSelectionBackground(new java.awt.Color(153, 255, 255));
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(false);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setResizable(false);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(2).setResizable(false);
            table.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 904, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        thisIsVisible = false;
        MainPageInterface.StopButton.doClick();
    }//GEN-LAST:event_formWindowClosing

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
    }//GEN-LAST:event_formWindowOpened
     
    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        try {
            //System.out.println("window opend");
            activeArray = LoginInterface.db.getActiveArray();
        } catch (SQLException ex) {
            Logger.getLogger(LiveData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        thisIsVisible = true;
        Thread t = new Thread(new Runnable(){
            public void run(){
                while (thisIsVisible){
                    //System.out.println("running");
                    startQueryFrequency();
                }
            }
        });
        t.start();
    }//GEN-LAST:event_formComponentShown

    public void addRow(String adress, String datetime, String value, String warning){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{adress, datetime, value, warning});
        model.removeRow(1);
    }
    
    public void startQueryFrequency(){
        Calendar calendar = Calendar.getInstance();
        int seconds = calendar.get(Calendar.SECOND);
        if (oldseconds != seconds){
            oldseconds = seconds;
            for (resource resource1 : activeArray){
                if(seconds%resource1.queryFrquzy == 0){
                    //lets process the Coap-request and get the result
                    String result = null;
                    String warning = " ";
                    if(resource1.protocol.equals(("coap"))){
                        result = coapRequest(resource1);
                    }
                    //send Email
                    else if (resource1.protocol.equals(("raspberry"))){
                        //"sensorValue" ist in diesem Fall nur eine beliebige zufällige Nummer um Testungen
                        //durchzuführen und einen wirklichen Sensor-wert zu simulieren! Im Endsystem wird hier 
                        //natürlich der wirkliche Wert eines Sensors verwendet! weiters wird das "protocol" 
                        //nicht "raspberry" heißen. Auch dies ist nur für Testzwecke!
                        int sensorValue = (int )(Math. random() * 35 + 1);
                        result = Integer.toString(sensorValue);
                        //System.out.println(sumrequest + print);
                        
                        //Send email if too high
                        if(sensorValue > resource1.upperLimit) {
                            warning = "Warning! Mail sent!";
                            sendEmail(Integer.toString(sensorValue), resource1, "high");
                        }
                        //Send email if too low
                        if(sensorValue < resource1.lowerLimit) {
                            warning = "Warning! Mail sent!";
                            sendEmail(Integer.toString(sensorValue), resource1, "low");
                        }
                    }else{
                        result = "invalid adress";
                    }
                    String datetime = DateTimeFormatter.ofPattern("dd/MM HH:mm").format(LocalDateTime.now());
                    
                    //put together what we wanna seeee
                    String sumrequest = resource1.protocol + resource1.adress + resource1.attribute;
                    addRow(sumrequest, datetime, result, warning);
                    
                    //insert data (new thread needed because of timeproblems)
                    saveData(resource1.id, result, datetime);
                }
            }
        }
    }
    
    public String coapRequest(resource resource){
        String request = resource.protocol + resource.adress + resource.attribute;
        CoapClient client = new CoapClient(request);
        try{
            return(client.get().getResponseText());
        } catch(Exception ex){
            return("invalid request or no web-connection");
        }
    }
    
    public void saveData(int id, String result, String datetime){
        Thread t2 = new Thread(new Runnable(){
            public void run(){
                try {
                    LoginInterface.db.insertData(id, result, datetime);
                } catch (SQLException ex) {
                    Logger.getLogger(LiveData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t2.start();
    }
    
    public void sendEmail(String result, resource resource1, String highlow){
        Thread t = new Thread(new Runnable(){
            public void run(){
                //Send Email
                backend.email email1 = new backend.email(   
                    "smtp.gmail.com",
                    "coapWarning@gmail.com",
                    "mVi1969gg",
                    "coapWarning@gmail.com",
                    " " + LoginInterface.useremail + " ",
                    "",
                    "Warning from " + resource1.protocol,
                    "Warning from \"" + resource1.protocol + resource1.adress + resource1.attribute + "\":"
                            + "\n Returned: " + result + " which exceeded a given limit."
                        
                    );
                backend.email.send(email1);
                
                SwingUtilities.invokeLater(new Runnable()
                {
                    public void run()
                    {
                        //TODO:
                        //RessourcenInterface.doneLabel.setText("");
                    }
                });
            }
        });
        t.start();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LiveData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LiveData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LiveData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LiveData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LiveData().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}