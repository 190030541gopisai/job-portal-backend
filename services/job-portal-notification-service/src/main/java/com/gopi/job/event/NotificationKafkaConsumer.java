package com.gopi.job.event;


import com.gopi.job.services.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationKafkaConsumer {

    private final EmailNotificationService emailService;

    @KafkaListener(
            topics = "application.status.changed",
            groupId = "notification-service",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleStatusChanged(ApplicationStatusChangedEvent event) throws Exception {
        emailService.sendStatusChangedEmail(event);
    }
}
