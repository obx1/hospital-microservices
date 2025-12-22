package ma.you.hospital.appointments.services;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) { super(message); }
}
