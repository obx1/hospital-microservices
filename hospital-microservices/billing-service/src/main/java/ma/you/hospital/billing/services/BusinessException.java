package ma.you.hospital.billing.services;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) { super(message); }
}
