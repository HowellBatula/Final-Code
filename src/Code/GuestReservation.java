package Code;

import java.awt.EventQueue;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;

public class GuestReservation extends JFrame {
    JFrame frmGuestReservationManagement;
    private String userName;
    private String contactNumber;
    private Date date;
    private List<String> reservations;
    private String startTime;
    private String endTime;
    private JTextField purpose;
    private JTextField name;
    private JTextField number;
	protected Object frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {

            }
        });
    }

    /**
     * Create the application.
     */
    public GuestReservation(Date selectedDate, String startTime,String endTime) {
        methods m = new methods();

        this.date = selectedDate;
        this.startTime = startTime;
        this.endTime = endTime;
        reservations = m.loadReservations();


        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmGuestReservationManagement = new JFrame();
        frmGuestReservationManagement.setTitle("Guest Reservation Management");
        frmGuestReservationManagement.setIconImage(Toolkit.getDefaultToolkit().getImage(GuestReservation.class.getResource("/Code/IMAGES/LASTHOME.png")));
        frmGuestReservationManagement.setResizable(false);
        frmGuestReservationManagement.setBounds(100, 100, 1000, 600);
        frmGuestReservationManagement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmGuestReservationManagement.setLocationRelativeTo(null);
        frmGuestReservationManagement.getContentPane().setLayout(null);
        methods m = new methods();


        JLabel reserveBtn = new JLabel("");

        reserveBtn.setBounds(463, 491, 80, 36);
        reserveBtn.setIcon(new ImageIcon(GuestReservation.class.getResource("/IMAGES/reservebtn.png")));
        frmGuestReservationManagement.getContentPane().add(reserveBtn);

        JLabel startTimeShow = new JLabel(startTime);
        startTimeShow.setHorizontalAlignment(SwingConstants.CENTER);
        startTimeShow.setFont(new Font("Bahnschrift", Font.PLAIN, 14));
        startTimeShow.setBounds(142, 317, 221, 26);
        frmGuestReservationManagement.getContentPane().add(startTimeShow);

        JLabel endTimeShow = new JLabel(endTime);
        endTimeShow.setHorizontalAlignment(SwingConstants.CENTER);
        endTimeShow.setFont(new Font("Bahnschrift", Font.PLAIN, 14));
        endTimeShow.setBounds(638, 322, 221, 26);
        frmGuestReservationManagement.getContentPane().add(endTimeShow);

        JLabel dateShow = new JLabel();
        dateShow.setHorizontalAlignment(SwingConstants.CENTER);
        dateShow.setFont(new Font("Bahnschrift", Font.PLAIN, 14));
        dateShow.setBounds(389, 279, 221, 26);
        frmGuestReservationManagement.getContentPane().add(dateShow);

        // Convert the selectedDate to a string representation
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(date);
        dateShow.setText(dateString);

        purpose = new JTextField();
        purpose.setBounds(426, 362, 156, 36);
        frmGuestReservationManagement.getContentPane().add(purpose);
        purpose.setColumns(10);
        
        name = new JTextField();
        name.setColumns(10);
        name.setBounds(426, 125, 156, 36);
        frmGuestReservationManagement.getContentPane().add(name);
        
        number = new JTextField();
        number.setColumns(10);
        number.setBounds(426, 190, 156, 36);
        frmGuestReservationManagement.getContentPane().add(number);
                
                JLabel backBtn = new JLabel("");
                backBtn.addMouseListener(new MouseAdapter() {
                	@Override
                	public void mouseClicked(MouseEvent e) {
                		GuestChecker r = new GuestChecker();
        				r.setVisible(true);
                	}
                });
                backBtn.setIcon(new ImageIcon(GuestReservation.class.getResource("/IMAGES/backButton.png")));
                backBtn.setBounds(28, 102, 32, 36);
                frmGuestReservationManagement.getContentPane().add(backBtn);
                
                        JLabel managementImg = new JLabel("");
                        managementImg.setBounds(0, 0, 984, 561);
                        managementImg.setIcon(new ImageIcon(GuestReservation.class.getResource("/IMAGES/RESERVATIONMANAGEMENT.png")));
                        frmGuestReservationManagement.getContentPane().add(managementImg);

        //Event Listeners
        // Inside the initialize() method
        reserveBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Create the receipt message
            	String enteredName = name.getText();
                String enteredNumber = number.getText();
                String enteredPurpose = purpose.getText();

                StringBuilder receiptBuilder = new StringBuilder();
                receiptBuilder.append("Reservation Details:\n");
                receiptBuilder.append("Name: ").append(enteredName).append("\n");
                receiptBuilder.append("Contact Number: ").append(enteredNumber).append("\n");
                receiptBuilder.append("Date: ").append(dateString).append("\n");
                receiptBuilder.append("Start Time: ").append(startTime).append("\n");
                receiptBuilder.append("End Time: ").append(endTime).append("\n");
                receiptBuilder.append("Purpose: ").append(enteredPurpose).append("\n");
                receiptBuilder.append("THIS WILL SERVE AS YOUR RECEIPT SCREENSHOT THIS!").append("\n");

                // Show the receipt message in a JOptionPane
                JOptionPane.showMessageDialog(frmGuestReservationManagement, receiptBuilder.toString(), "Reservation Receipt",
                        JOptionPane.INFORMATION_MESSAGE);

            	m.reserve(date,startTime,endTime);
            }
        });
    }
    
}
