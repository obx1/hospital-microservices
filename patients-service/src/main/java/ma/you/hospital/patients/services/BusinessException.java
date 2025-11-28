package ma.you.hospital.patients.services;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) { super(message); }
}
