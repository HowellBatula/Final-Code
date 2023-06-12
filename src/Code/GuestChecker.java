package Code;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;

import Code.ResidentChecker.ButtonMouseAdapter;

import java.awt.Color;
import java.awt.Font;
import java.io.FileWriter;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class GuestChecker extends JFrame  {
    private static final long serialVersionUID = 1L;
    private List<String> reservations;
    private Date selectedDate;
    private String selectedStartTime;
    private String selectedEndTime;
    private JTextArea textArea;
	protected Object frame;
	

    public GuestChecker() {
        methods m = new methods();
    	initialize();
        reservations = m.loadReservations();
    }
    
    private void initialize () {
        setTitle("Calendar");
        setBounds(100, 100, 1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);
        methods m = new methods();


        // Create and configure components
        JDateChooser daychecker = new JDateChooser();
        daychecker.setBounds(186, 150, 243, 20);
        daychecker.setForeground(new Color(0, 0, 0));
        daychecker.setDateFormatString("MMMM d, y");

        List<String> startOptions = m.generateTimeOptionsStart();
        DefaultComboBoxModel<String> comboBoxModelStart = new DefaultComboBoxModel<>(startOptions.toArray(new String[0]));

        List<String> endOptions = m.generateTimeOptionsEnd();
        DefaultComboBoxModel<String> comboBoxModelEnd = new DefaultComboBoxModel<>(endOptions.toArray(new String[0]));

        JComboBox<String> startBox = new JComboBox<>(comboBoxModelStart);
        startBox.setBounds(186, 305, 195, 22);

        JComboBox<String> endBox = new JComboBox<>(comboBoxModelEnd);
        endBox.setBounds(186, 355, 195, 22);

        // Add components to the content pane
        getContentPane().add(daychecker);
        getContentPane().add(startBox);
        getContentPane().add(endBox);
        
        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(500, 150, 410, 250);
        getContentPane().add(scrollPane_1);
        
        
        textArea = new JTextArea();
        scrollPane_1.setViewportView(textArea);
        textArea.setEditable(false);

        // Register event listeners
        daychecker.getCalendarButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedDate = daychecker.getDate();
                String selectedDateString = String.format("Selected Date: %tF", selectedDate);
                System.out.println(selectedDateString);         
            }
        });

        endBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedEndTime = (String) endBox.getSelectedItem();
                System.out.println("Selected end time: " + selectedEndTime);
            }
        });

        startBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedStartTime = (String) startBox.getSelectedItem();
                System.out.println("Selected start time: " + selectedStartTime);
            }
        });
                
        JPanel checkPanel = new JPanel();
		checkPanel.setOpaque(false);
		checkPanel.setBounds(157, 414, 140, 40);
		getContentPane().add(checkPanel);
		checkPanel.setLayout(null);
		
		JLabel checkBtn = new JLabel("");
		checkBtn.setBounds(5, 5, 125, 30);
		checkPanel.add(checkBtn);
		checkBtn.setIcon(new ImageIcon(GuestChecker.class.getResource("/IMAGES/availbtn.png")));
		
		JLabel checkBig = new JLabel("");
		checkPanel.addMouseListener(new ButtonMouseAdapter(checkBtn, checkBig) {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		checkBig.setIcon(new ImageIcon(GuestChecker.class.getResource("/IMAGES/availBig.png")));
		checkBig.setBounds(0, 0, 140, 40);
		checkBig.setVisible(false);
		checkPanel.add(checkBig);
	
		JPanel seePanel = new JPanel();
		seePanel.setOpaque(false);
		seePanel.setBounds(500, 100, 140, 45);
		getContentPane().add(seePanel);
		seePanel.setLayout(null);
		
		JLabel seeBtn = new JLabel("");
		seeBtn.setBounds(5, 5, 128, 36);
		seePanel.add(seeBtn);
		seeBtn.setIcon(new ImageIcon(GuestChecker.class.getResource("/IMAGES/reservationsbtn.png")));
		
		JLabel seeBig = new JLabel("");
		seePanel.addMouseListener(new ButtonMouseAdapter(seeBtn, seeBig) {
			@Override
			public void mouseClicked(MouseEvent e) {
				reservations = m.loadReservations();
		        StringBuilder sb = new StringBuilder();
		        for (String reservation : reservations) {
		            sb.append(reservation).append("\n");
		        }
		        textArea.setText(sb.toString());
			}
		});
		seeBig.setIcon(new ImageIcon(GuestChecker.class.getResource("/IMAGES/reservationsBig.png")));
		seeBig.setHorizontalAlignment(SwingConstants.CENTER);
		seeBig.setBounds(0, 0, 140, 45);
		seeBig.setVisible(false);
		seePanel.add(seeBig);
		
		JPanel reservePanel = new JPanel();
		reservePanel.setOpaque(false);
		reservePanel.setBounds(460, 480, 100, 57);
		getContentPane().add(reservePanel);
		reservePanel.setLayout(null);
		reservePanel.setVisible(false);
		
		JLabel reserveBtn = new JLabel("");
		reserveBtn.setHorizontalAlignment(SwingConstants.CENTER);
		reserveBtn.setBounds(0, 11, 100, 36);
		reservePanel.add(reserveBtn);
		reserveBtn.setIcon(new ImageIcon(GuestChecker.class.getResource("/IMAGES/reservebtn.png")));
		
		JLabel reserveBig = new JLabel("");
		
		reserveBig.setIcon(new ImageIcon(GuestChecker.class.getResource("/IMAGES/reserveBig.png")));
		reserveBig.setBounds(0, 11, 100, 35);
		reserveBig.setVisible(false);
		reservePanel.add(reserveBig);
		
		
		reservePanel.addMouseListener(new ButtonMouseAdapter(reserveBtn, reserveBig) {
			@Override
			public void mouseClicked(MouseEvent e) {
				GuestReservation r = new GuestReservation(selectedDate, selectedStartTime, selectedEndTime);
				r.frmGuestReservationManagement.setVisible(true);
				dispose();
				
			}
		});
		
		checkPanel.addMouseListener(new ButtonMouseAdapter(checkBtn, checkBig) {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        // Check if the selected date is null
		    	// Check availability logic here
		    	 if (selectedDate == null) {
			            JOptionPane.showMessageDialog(null, "Please select a date.");
			            return; // Exit the method early
			        }
		    	 if (selectedStartTime.equals(selectedEndTime)) {
			            JOptionPane.showMessageDialog(null, "Cannot make reservation. Start time and end time are the same.");
		    	 } else if  (m.isStartTimeGreaterThanEndTime(selectedStartTime, selectedEndTime)) {
		             JOptionPane.showMessageDialog(null, "Cannot make a reservation. Start time is greater than end time.");
		    	 } else {
		    	 if (selectedDate != null && selectedStartTime != null && selectedEndTime != null) {
		            String selectedStartDateTime = m.getFormattedDateTime(selectedDate, selectedStartTime);
		            String selectedEndDateTime = m.getFormattedDateTime(selectedDate, selectedEndTime);
		            
		            if (m.isTimeRangeAvailable(selectedStartDateTime, selectedEndDateTime)) {
		                JOptionPane.showMessageDialog(null, "Date and time are available.", "Availability", JOptionPane.INFORMATION_MESSAGE);
		                reservePanel.setVisible(true);
		            } else {
		                JOptionPane.showMessageDialog(null, "Date and time are not available.", "Availability", JOptionPane.ERROR_MESSAGE);
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Please select a date and time range.", "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    	 }
		    }
		});
		
		JLabel backBtn = new JLabel("");
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				StartingPage.main(null);
				dispose();
			}
		});
		backBtn.setIcon(new ImageIcon(GuestChecker.class.getResource("/IMAGES/backButton.png")));
		backBtn.setBounds(922, 89, 32, 39);
		getContentPane().add(backBtn);
		
		JLabel availImg = new JLabel("");
		availImg.setIcon(new ImageIcon(GuestChecker.class.getResource("/IMAGES/AVAILABILITYCHECKING.png")));
		availImg.setBounds(0, 0, 984, 561);
		getContentPane().add(availImg);

    }
   


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	GuestChecker window = new GuestChecker();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    private class ButtonMouseAdapter extends MouseAdapter{
		JLabel label;
		JLabel bigLabel;
		public ButtonMouseAdapter(JLabel label, JLabel bigLabel) {
			this.label = label;
			this.bigLabel = bigLabel;
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			label.setVisible(false);
			bigLabel.setVisible(true);
		}
		public void mouseExited(MouseEvent e) {
			label.setVisible(true);
			bigLabel.setVisible(false);
		}
		public void mousePressed(MouseEvent e) {
			label.setVisible(true);
			bigLabel.setVisible(false);
		}
		public void mouseReleased(MouseEvent e) {
			label.setVisible(false);
			bigLabel.setVisible(true);
		}	
    }
}