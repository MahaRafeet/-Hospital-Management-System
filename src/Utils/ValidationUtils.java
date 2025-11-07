package Utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

public class ValidationUtils {

    /* ===========================
       Null Check Methods
    ============================ */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNull(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    public static boolean isNotNull(String str) {
        return str != null && !str.trim().isEmpty();
    }

    /* ===========================
       String Validation Methods
    ============================ */
    public static boolean isValidString(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public static boolean isValidString(String str, int minLength) {
        return isValidString(str) && str.length() >= minLength;
    }

    public static boolean isValidString(String str, int minLength, int maxLength) {
        return isValidString(str) && str.length() >= minLength && str.length() <= maxLength;
    }

    public static boolean isValidString(String str, String regex) {
        return isValidString(str) && Pattern.matches(regex, str);
    }

    /* ===========================
       ID Generation Methods
    ============================ */
    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    public static String generateId(String prefix) {
        return prefix + "-" + (int) (Math.random() * 100000);
    }

    public static String generateId(String prefix, int length) {
        StringBuilder sb = new StringBuilder(prefix + "-");
        for (int i = 0; i < length; i++) {
            sb.append((int) (Math.random() * 10));
        }
        return sb.toString();
    }

    public static String generateId(String prefix, String suffix) {
        return prefix + "-" + (int) (Math.random() * 100000) + "-" + suffix;
    }

    /* ===========================
       Date Validation Methods
    ============================ */
    public static boolean isValidDate(Date date) {
        return date != null;
    }

    public static boolean isValidDate(String dateStr) {
        if (isNull(dateStr)) return false;
        try {
            LocalDate.parse(dateStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidDate(Date date, Date minDate, Date maxDate) {
        if (date == null || minDate == null || maxDate == null) return false;
        return !date.before(minDate) && !date.after(maxDate);
    }

    public static boolean isFutureDate(Date date) {
        return date != null && date.after(new Date());
    }

    public static boolean isPastDate(Date date) {
        return date != null && date.before(new Date());
    }

    public static boolean isToday(Date date) {
        if (date == null) return false;
        LocalDate given = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return given.equals(LocalDate.now());
    }

    public static boolean isPastDate(LocalDate date) {
        return date != null && date.isBefore(LocalDate.now());
    }

    public static boolean isFutureDate(LocalDate date) {
        return date != null && date.isAfter(LocalDate.now());
    }

    public static boolean isToday(LocalDate date) {
        return date != null && date.equals(LocalDate.now());
    }

    /* ===========================
       Numeric Validation Methods
    ============================ */
    public static boolean isValidNumber(int num, int min, int max) {
        return num >= min && num <= max;
    }

    public static boolean isValidNumber(double num, double min, double max) {
        return num >= min && num <= max;
    }

    public static boolean isPositive(int num) {
        return num > 0;
    }

    public static boolean isPositive(double num) {
        return num > 0;
    }

    public static boolean isNegative(int num) {
        return num < 0;
    }

    public static boolean isNegative(double num) {
        return num < 0;
    }

    /* ===========================
       Age Validation
    ============================ */
    public static boolean isValidAge(int age) {
        return age >= 0 && age <= 120;
    }

    public static boolean isValidAge(LocalDate dateOfBirth) {
        if (dateOfBirth == null) return false;
        int age = LocalDate.now().getYear() - dateOfBirth.getYear();
        return isValidAge(age);
    }
    // email validation
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        // Basic email regex: something@something.something
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

}
