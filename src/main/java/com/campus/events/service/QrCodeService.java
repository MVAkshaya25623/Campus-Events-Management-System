package com.campus.events.service;

import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class QrCodeService {

    public String generateQrCodeUrl(Long registrationId, Long userId, Long eventId) {
        // In a real application, we would use ZXing to generate an image and save it or return Base64.
        // For this assignment, we can generate a URL to an external API (like Google Chart API or QR Server)
        String data = "RegID:" + registrationId + "-User:" + userId + "-Event:" + eventId;
        return "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=" + data;
    }
}
