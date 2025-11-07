package Services;

import java.time.LocalDate;
import java.util.Scanner;

import Entities.Appointment;
import Interfaces.Appointable;
import Interfaces.Manageable;
import Interfaces.Searchable;
import Utils.ValidationUtils;

import java.util.ArrayList;
import java.util.List;

public class AppointmentService implements Manageable, Searchable, Appointable {

    public static Scanner scanner = new Scanner(System.in);
    public static List<Appointment> appointmentList = new ArrayList<>();

    public static Appointment addAppointment() {
        Appointment appointment = new Appointment();
        System.out.println("\n===== Add New Appointment =====");
        appointment.setPatientId();
        appointment.setDoctorId();
        appointment.setAppointmentDate();
        System.out.print("Enter Appointment Time (HH:mm): ");
        appointment.setAppointmentTime(scanner.nextLine());
        System.out.print("Enter Reason for Visit: ");
        appointment.setReason(scanner.nextLine());
        System.out.print("Enter Status (Scheduled, Completed, Canceled): ");
        appointment.setStatus(scanner.nextLine());
        System.out.println("Enter any Notes: ");
        appointment.setNotes(scanner.nextLine());
        System.out.println("Appointment added successfully with ID: " + appointment.getAppointmentId());
        return appointment;

    }

    public static Appointment editAppointment(Appointment appointment) {
        System.out.println("Enter the appointment ID to edit:");
        String appointmentIdToEdit = scanner.nextLine();
        if (!checkIfIdExist(appointmentIdToEdit)) {
            System.out.println("The appointment id is not exist ");
        } else {
            System.out.println("Editing Appointment ID : " + appointment.getAppointmentId());
            System.out.println("Enter what do you want to update? ");
            System.out.println("1-Appointment Date");
            System.out.println("2-Appointment Time");
            System.out.println("3-Reason for Visit");
            System.out.println("4-Status");
            System.out.println("5-Notes");
            System.out.println("6-Exit");
            System.out.print("Enter your choice: ");
            Integer choice = scanner.nextInt();
            if (choice == 1) {
                System.out.println("The old appointment date is : " + appointment.getAppointmentDate());
                System.out.print("Enter new appointment date (yyyy-mm-dd): ");
                scanner.nextLine(); // Consume newline
                appointment.setAppointmentDate();
                System.out.println("The appointment date updated successfully");
            } else if (choice == 2) {
                System.out.println("The old appointment time was : " + appointment.getAppointmentTime());
                System.out.print("Enter new appointment time (HH:mm): ");
                appointment.setAppointmentTime(scanner.nextLine());
                System.out.println("The appointment time updated successfully");
            } else if (choice == 3) {
                System.out.println("The old Reason of this appointment was : " + appointment.getReason());
                System.out.print("Enter the new reason ");
                appointment.setReason(scanner.nextLine());
            } else if (choice == 4) {
                System.out.println("The old status of this appointment was : " + appointment.getStatus());
                System.out.print("Enter the new status :");
                appointment.setStatus(scanner.nextLine());
                System.out.println("The status updated Successfully");
            } else if (choice == 5) {
                System.out.println("The old notes was " + appointment.getNotes());
                System.out.println("Enter the new notes : ");
                appointment.setNotes(scanner.nextLine());
                System.out.println("The Notes are updated Successfully");
            } else if (choice == 6) {
                System.out.println("Exiting edit menu.");
                return appointment;
            } else {
                System.out.println("Invalid choice. Please try again.");

            }
        }
        return appointment;
    }

    // ✅ Version 1: used when you want to ask the user for the ID (menu use)
    public static List<Appointment> getAppointmentsByPatientIdInteractive() {
        System.out.print("Please enter Patient ID to view appointments: ");
        String patientId = scanner.nextLine().trim();
        return getAppointmentsByPatientId(patientId);
    }

    // ✅ Version 2: used internally (like inside Patient.displayInfo)
    public static List<Appointment> getAppointmentsByPatientId(String patientId) {
        List<Appointment> appointments = new ArrayList<>();

        for (Appointment app : appointmentList) {
            if (app.getPatientId().equalsIgnoreCase(patientId)) {
                appointments.add(app);
            }
        }

        return appointments;
    }

    public static List<Appointment> getAppointmentsByDoctor() {
        List<Appointment> doctorAppointments = new ArrayList<>();
        System.out.print("Enter Doctor ID: ");
        String doctorId = scanner.nextLine();
        boolean isFound = false;

        System.out.println("===== Appointments for Doctor ID: " + doctorId + " =====");
        for (Appointment appointment : appointmentList) {
            if (appointment.getDoctorId().equalsIgnoreCase(doctorId)) {
                appointment.displayInfo();
                System.out.println("-------------------");
                doctorAppointments.add(appointment);
                isFound = true;
            }
        }

        if (!isFound) {
            System.out.println("⚠️ There are no appointments for this doctor.");
        }

        return doctorAppointments;
    }

    public static List<Appointment> getAppointmentsByDate() {
        System.out.print("Enter Appointment Date (yyyy-mm-dd): ");
        LocalDate appointmentDate = LocalDate.parse(scanner.nextLine());
        List<Appointment> appointmentsOnDate = new ArrayList<>();
        boolean isFound = false;

        System.out.println("===== Appointments on: " + appointmentDate + " =====");
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentDate().equals(appointmentDate)) {
                appointment.displayInfo();
                System.out.println("-------------------");
                appointmentsOnDate.add(appointment);
                isFound = true;
            }
        }

        if (!isFound) {
            System.out.println("⚠️ There are no appointments on this date.");
        }

        return appointmentsOnDate;
    }

    public static Appointment rescheduleAppointment() {
        System.out.print("Enter appointment ID to reschedule: ");
        String appointmentId = scanner.nextLine().trim();

        Appointment foundAppointment = null;

        // 🔍 Search for the appointment
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentId().equalsIgnoreCase(appointmentId)) {
                foundAppointment = appointment;
                break;
            }
        }

        // If not found
        if (foundAppointment == null) {
            System.out.println("Appointment ID not found in the system.");
            return null;
        }

        // ✅ If found — reschedule
        System.out.println("\n--- Rescheduling Appointment ID: " + foundAppointment.getAppointmentId() + " ---");

        System.out.print("Enter the new Date (yyyy-MM-dd): ");
        foundAppointment.setAppointmentDate(); // this uses your Appointment’s own setter (with validation & retry loop)

        System.out.print("Enter the new time (HH:mm): ");
        foundAppointment.setAppointmentTime(scanner.nextLine());

        System.out.println("✅ Appointment rescheduled successfully!");
        return foundAppointment;
    }

    public static void viewUpcomingAppointments() {
        LocalDate today = LocalDate.now();
        boolean isFound = false;

        System.out.println("===== Upcoming Appointments =====");
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentDate().isAfter(today) || appointment.getAppointmentDate().isEqual(today)) {
                appointment.displayInfo();
                System.out.println("-------------------");
                isFound = true;
            }
        }

        if (!isFound) {
            System.out.println("⚠️ There are no upcoming appointments.");
        }
    }


    public void createAppointment(String patientId, String doctorId, LocalDate date) {
        Appointment appointment = new Appointment();
        appointment.setPatientId();
        appointment.setDoctorId();
        appointment.setAppointmentDate();
        appointment.setStatus("Scheduled");
        appointmentList.add(appointment);
        System.out.println("Appointment created successfully with ID: " + appointment.getAppointmentId());
    }

    public void createAppointment(String patientId, String doctorId, LocalDate date, String time) {
        Appointment appointment = new Appointment();
        appointment.setPatientId();
        appointment.setDoctorId();
        appointment.setAppointmentDate();
        appointment.setAppointmentTime(time);
        appointment.setStatus("Scheduled");
        appointmentList.add(appointment);
        System.out.println("Appointment created successfully with ID: " + appointment.getAppointmentId());
    }

    public void createAppointment(Appointment appointment) {
        appointment.setStatus("Scheduled");
        appointmentList.add(appointment);
        System.out.println("Appointment created successfully with ID: " + appointment.getAppointmentId());
    }

    @Override
    public void scheduleAppointment() {
        Appointment newAppointment = addAppointment();
        // Now add it to the list
        appointmentList.add(newAppointment);

        // Confirm scheduling
        System.out.println("Appointment scheduled successfully with ID: " + newAppointment.getAppointmentId());

    }


    @Override
    public void cancelAppointment() {
        System.out.print("Enter appointment ID to cancel: ");
        String appointmentId = scanner.nextLine();
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentId().equals(appointmentId)) {
                appointment.setStatus("Canceled");
                System.out.println("Appointment with ID " + appointmentId + " has been canceled.");
                return;
            }
        }
        System.out.println("Appointment ID not found.");

    }

    public void rescheduleAppointment(String appointmentId, LocalDate newDate) {
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentId().equals(appointmentId)) {
                appointment.setAppointmentDate();
                System.out.println("Appointment rescheduled successfully to " + newDate);
                return;
            }
        }
        System.out.println("Appointment ID not found.");
    }

    public void rescheduleAppointment(String appointmentId, LocalDate newDate, String newTime) {
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentId().equals(appointmentId)) {
                appointment.setAppointmentDate();
                appointment.setAppointmentTime(newTime);
                System.out.println("Appointment rescheduled successfully to " + newDate + " at " + newTime);
                return;
            }
        }
        System.out.println("Appointment ID not found.");
    }

    public static void completeAppointment() {
        System.out.print("Enter Appointment ID to mark as completed: ");
        String appointmentId = scanner.nextLine().trim();

        // Check for null or empty input
        if (!ValidationUtils.isValidString(appointmentId)) {
            System.out.println("Appointment ID cannot be null or empty.");
            return;
        }

        boolean found = false;

        // Search for appointment
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentId().equalsIgnoreCase(appointmentId)) {
                //  Update status
                appointment.setStatus("Completed");
                System.out.println("Appointment " + appointmentId + " marked as Completed successfully!");
                found = true;
                break;
            }
        }

        //  Not found case
        if (!found) {
            System.out.println("Appointment ID not found in the system.");
        }
    }

    public void rescheduleAppointment(Appointment appointment, LocalDate newDate, String newTime, String reason) {
        appointment.setAppointmentDate();
        appointment.setAppointmentTime(newTime);
        appointment.setReason(reason);
        System.out.println("Appointment rescheduled successfully to " + newDate + " at " + newTime);
    }

    public void displayAppointments(LocalDate date) {
        System.out.println("Appointments on " + date + ":");
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentDate().equals(date)) {
                appointment.displayInfo();
            }
        }
    }

    public void displayAppointments(String doctorId, LocalDate startDate, LocalDate endDate) {
        System.out.println("Appointments for Doctor ID " + doctorId + " from " + startDate + " to " + endDate + ":");
        for (Appointment appointment : appointmentList) {
            if (appointment.getDoctorId().equals(doctorId) &&
                    (appointment.getAppointmentDate().isEqual(startDate) || appointment.getAppointmentDate().isAfter(startDate)) &&
                    (appointment.getAppointmentDate().isEqual(endDate) || appointment.getAppointmentDate().isBefore(endDate))) {
                appointment.displayInfo();
            }
        }

    }

    public static boolean checkIfIdExist(String appointmentId) {
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentId().equals(appointmentId)) {
                return true;
            }
        }
        return false;
    }

    //Interface method implementations
    @Override
    public void add(Object entity) {
        if (entity instanceof Appointment) {
            appointmentList.add((Appointment) entity);
            System.out.println("Appointment added successfully.");
        } else {
            System.out.println("Invalid entity type. Expected an Appointment.");
        }
    }

    @Override
    public void remove() {

        System.out.print("Enter appointment ID to cancel: ");
        String appointmentId = scanner.nextLine();

        Appointment toRemove = null;

        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentId().equals(appointmentId)) {
                toRemove = appointment; // mark for removal
                break;
            }
        }

        if (toRemove != null) {
            appointmentList.remove(toRemove);
            System.out.println(" Appointment with ID " + appointmentId + " has been cancelled.");
        } else {
            System.out.println("Appointment ID not found.");
        }


    }

    @Override
    public void getAll() {
        System.out.println("\n===== All Appointments =====");

        if (appointmentList.isEmpty()) {
            System.out.println("There are no appointments in the system.");
        } else {
            for (Appointment appointment : appointmentList) {
                System.out.println("------------------");
                appointment.displayInfo();
            }
            System.out.println("------------------");
        }


    }

    @Override
    public void search(String keyword) {
        System.out.println("\n===== Search Appointments =====");
        boolean found = false;
        for (Appointment appointment : appointmentList) {
            if (appointment.getPatientId().equalsIgnoreCase(keyword) ||
                    appointment.getDoctorId().equalsIgnoreCase(keyword) ||
                    appointment.getStatus().equalsIgnoreCase(keyword)) {
                appointment.displayInfo();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No appointments found matching the keyword.");
        }


    }

    @Override
    public void searchById(String id) {
        System.out.println("\n===== Search Appointment by ID =====");
        boolean found = false;
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentId().equalsIgnoreCase(id)) {
                appointment.displayInfo();
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Appointment ID not found.");
        }

    }
}
