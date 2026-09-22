package paybandhu.notification;

import org.springframework.stereotype.Service;

@Service
public class DummySmsService implements SmsService{

    @Override
    public void send(String mobileNumber, String message) {

        System.out.println(
                "===================================="
        );

        System.out.println("SMS TO: " + mobileNumber);
        System.out.println("MESSAGE: " + message);

        System.out.println(
                "===================================="
        );
    }
}
