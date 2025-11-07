package Interfaces;

import Entities.Appointment;

import java.time.LocalDate;

public interface Appointable {
    void scheduleAppointment();
    void cancelAppointment();
    void  rescheduleAppointment(String appointmentId, LocalDate newDate);
}
