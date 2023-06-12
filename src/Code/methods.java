package Code;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
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
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JOptionPane;

class methods {
    private List<String> reservations;

    // Checks if the start time is greater than the end time
    boolean isStartTimeGreaterThanEndTime(String startTime, String endTime) {
        DateFormat format = new SimpleDateFormat("HH:mm");
        try {
            Date startDate = format.parse(startTime);
            Date endDate = format.parse(endTime);
            return startDate.after(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Generates a list of time options for the start time (from 7:00 to 22:00)
    List<String> generateTimeOptionsStart() {
        List<String> options = new ArrayList<>();
        for (int hour = 7; hour <= 22; hour++) {
            options.add(String.format("%02d:00", hour));
        }
        return options;
    }

    // Generates a list of time options for the end time (from 7:00 to 22:00)
    List<String> generateTimeOptionsEnd() {
        List<String> options = new ArrayList<>();
        for (int hour = 7; hour <= 22; hour++) {
            options.add(String.format("%02d:00", hour));
        }
        return options;
    }

    // Formats the given date and time into a formatted date-time string
    String getFormattedDateTime(Date date, String time) {
        return String.format("%tF %s", date, time);
    }

    // Checks if the given time range is available for reservation
    boolean isTimeRangeAvailable(String startDateTime, String endDateTime) {
        reservations = loadReservations();
        for (String reservation : reservations) {
            String[] parts = reservation.split(" - ");
            String existingStartDateTime = parts[0];
            String existingEndDateTime = parts[1];
            if (isDateTimeOverlap(startDateTime, endDateTime, existingStartDateTime, existingEndDateTime)) {
                return false; // Reservation time range overlaps with existing reservation
            }
        }
        return true; // Reservation time range is available
    }

    // Checks if two date-time ranges overlap
    boolean isDateTimeOverlap(String startDateTime1, String endDateTime1, String startDateTime2, String endDateTime2) {
        // Convert date-time strings to Date objects for comparison
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date start1 = format.parse(startDateTime1);
            Date end1 = format.parse(endDateTime1);
            Date start2 = format.parse(startDateTime2);
            Date end2 = format.parse(endDateTime2);

            // Check for overlap
            return start1.before(end2) && start2.before(end1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Loads the list of reservations from a file and sorts them by date-time
    List<String> loadReservations() {
        List<String> reservations = new ArrayList<>();
        try {
            Path path = Paths.get("reservation.txt");
            if (Files.exists(path)) {
                reservations = Files.readAllLines(path);
                Collections.sort(reservations, new Comparator<String>() {
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                    @Override
                    public int compare(String reservation1, String reservation2) {
                        try {
                            Date date1 = format.parse(reservation1.split(" - ")[0]);
                            Date date2 = format.parse(reservation2.split(" - ")[0]);
                            return date1.compareTo(date2);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return 0;
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    // Makes a reservation for the given date, start time, and end time
    public void reserve(Date date, String startTime, String endTime) {
        if (date != null && startTime != null && endTime != null) {
            String selectedStartDateTime = getFormattedDateTime(date, startTime);
            String selectedEndDateTime = getFormattedDateTime(date, endTime);
            if (isTimeRangeAvailable(selectedStartDateTime, selectedEndDateTime)) {
                String selectedDateTime = selectedStartDateTime + " - " + selectedEndDateTime;
                reservations.add(selectedDateTime);
                saveReservations(reservations);
                JOptionPane.showMessageDialog(null, "Reservation made.", "Success", JOptionPane.INFORMATION_MESSAGE);
                try {
                    FileWriter writer = new FileWriter("reservation.txt", true);
                    writer.write(selectedDateTime + "\n");
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        }
    }

    // Saves the list of reservations to a file
    void saveReservations(List<String> reservations) {
        try {
            FileWriter writer = new FileWriter("reservations.txt");
            for (String reservation : reservations) {
                writer.write(reservation + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}