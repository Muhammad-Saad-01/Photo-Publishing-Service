package com.yeshtery.pps.service;

public interface EmailSenderService {
    void send(String name, String toEmail, String token);

    String buildConfirmationEmail(String name, String link);
}
