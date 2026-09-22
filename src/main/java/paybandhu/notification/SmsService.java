package paybandhu.notification;

public interface SmsService {

    void send(String mobileNumber, String message);
}
