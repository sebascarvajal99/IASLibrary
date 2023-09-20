package ias.com.co.IASLibrary.domain.exceptions;

public class BookValidationException extends RuntimeException  {
    public BookValidationException(String message) {
        super(message);
    }
}
