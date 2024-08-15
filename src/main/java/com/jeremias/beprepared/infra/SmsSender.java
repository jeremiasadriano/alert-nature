package com.jeremias.beprepared.infra;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SmsSender {
    @Value("${app.services.sms.account-sid}")
    private String accountSid;
    @Value("${app.services.sms.auth-token}")
    private String authToken;
    @Value("${app.services.sms.number-from}")
    private String numberFrom;

    @Async
    public void send(String numberTo, String message) {
        Twilio.init(accountSid, authToken);
        Message.creator(
                new com.twilio.type.PhoneNumber(numberTo),
                new com.twilio.type.PhoneNumber(numberFrom),
                message).create();
    }
}
