package pl.jach.gdanskinternshipdynatrace.exception;

public class NumberOutOfRangeException extends RuntimeException{
    public NumberOutOfRangeException() {
        super("The number should be in range between 1 and 255");
    }
}
