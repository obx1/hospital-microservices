package ma.you.hospital.patients.service;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) { super(message); }
}
