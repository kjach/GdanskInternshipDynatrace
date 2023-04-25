package pl.jach.gdanskinternshipdynatrace.exception;

public class BadDateFormatException extends RuntimeException{
    public BadDateFormatException() {
        super("Date format should be YYYY-MM-DD");
    }
}
