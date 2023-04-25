package pl.jach.gdanskinternshipdynatrace.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException() {
        super("Could not find");
    }
}
