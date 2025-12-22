package ma.you.hospital.doctors.services;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
