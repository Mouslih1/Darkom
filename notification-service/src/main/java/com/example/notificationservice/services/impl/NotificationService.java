package com.example.notificationservice.services.impl;

import com.example.evenementservice.dto.EvenementProducerDto;
import com.example.notificationservice.client.UserClient;
import com.example.notificationservice.dtos.UserResponse;
import com.example.notificationservice.entities.Notification;
import com.example.notificationservice.entities.User;
import com.example.notificationservice.exception.ValidationException;
import com.example.notificationservice.services.INotificationService;
import com.example.paymentsyndecalservice.dtos.PaymentSyndicatProducerDto;
import com.example.plainte.dto.PlainteProducerDto;
import com.example.travauxservice.dto.TravauxProducerDto;
import com.google.firebase.database.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService implements INotificationService {

    private final UserClient userClient;

    @Override
    public void sendTravauxNotifications(TravauxProducerDto travauxProducerDto)
    {
        List<UserResponse> userDtos = userClient.getUsersByAgenceForNotifie(travauxProducerDto.getAgenceId(), travauxProducerDto.getToken()).getBody();
        User userCreateTravaux = userClient.byUsername(travauxProducerDto.getSenderUsername(), travauxProducerDto.getToken()).getBody();

        assert userDtos != null;
        assert userCreateTravaux != null;

        for (UserResponse user : userDtos)
        {
            if(!user.getUserDto().getUsername().equals(userCreateTravaux.getUsername()))
            {
                saveFirebase(
                        Notification.builder()
                                .agenceId(travauxProducerDto.getAgenceId())
                                .message(travauxProducerDto.getMessage())
                                .receivedId(user.getUserDto().getId())
                                .userCreateNotification(travauxProducerDto.getUserCreateNotification())
                                .relatedId(travauxProducerDto.getRelatedId())
                                .senderUsername(travauxProducerDto.getSenderUsername())
                                .build()
                );
            }
        }
    }

    public void saveFirebase(Notification notification)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference notificationsRef = database.getReference("notifications");


        String key = notificationsRef.push().getKey();

        Map<String, Object> notificationData = new HashMap<>();
        notificationData.put("relatedId", notification.getRelatedId());
        notificationData.put("message", notification.getMessage());
        notificationData.put("userCreateNotification", notification.getUserCreateNotification());
        notificationData.put("receivedId", notification.getReceivedId());
        notificationData.put("senderUsername", notification.getSenderUsername());
        notificationData.put("agenceId", notification.getAgenceId());
        notificationData.put("seen", false);
        notificationData.put("createdAt", Date.from(Instant.now()));

        log.debug("Notification data: {}", notificationData);

        notificationsRef.child(key).setValueAsync(notificationData);
        log.info("Notification sent successfully");
    }

    @Override
    public void sendTravauxNotificationForCreator(TravauxProducerDto travauxProducerDto)
    {
        User user = userClient.byUsername(travauxProducerDto.getSenderUsername(), travauxProducerDto.getToken()).getBody();
        assert user != null;

        saveFirebase(
                Notification.builder()
                        .agenceId(travauxProducerDto.getAgenceId())
                        .message("You are create an travaux")
                        .receivedId(user.getId())
                        .userCreateNotification(travauxProducerDto.getUserCreateNotification())
                        .relatedId(travauxProducerDto.getRelatedId())
                        .senderUsername(travauxProducerDto.getSenderUsername())
                        .build()
        );
    }

    @Override
    public void sendPaymentsSyndicatNotifications(PaymentSyndicatProducerDto paymentSyndicatProducerDto)
    {

        List<UserResponse> userDtos = userClient.getUsersByAgenceForNotifie(paymentSyndicatProducerDto.getAgenceId(), paymentSyndicatProducerDto.getToken()).getBody();
        UserResponse userResponse = userClient.byIdFeign(paymentSyndicatProducerDto.getPayerId(), paymentSyndicatProducerDto.getToken()).getBody();

        assert userDtos != null;
        assert userResponse != null;

        for (UserResponse user : userDtos)
        {
            if(!user.getUserDto().getId().equals(paymentSyndicatProducerDto.getPayerId()))
            {
                saveFirebase(
                        Notification.builder()
                                .agenceId(paymentSyndicatProducerDto.getAgenceId())
                                .message(paymentSyndicatProducerDto.getMessage())
                                .receivedId(user.getUserDto().getId())
                                .userCreateNotification(paymentSyndicatProducerDto.getUserCreateNotification())
                                .relatedId(paymentSyndicatProducerDto.getRelatedId())
                                .senderUsername(paymentSyndicatProducerDto.getSenderUsername())
                                .build()
                );
            }
        }
    }

    @Override
    public void sendPayementsSyndicatNotificationForPayer(PaymentSyndicatProducerDto paymentSyndicatProducerDto)
    {
        UserResponse userResponse = userClient.byIdFeign(paymentSyndicatProducerDto.getPayerId(), paymentSyndicatProducerDto.getToken()).getBody();
        assert userResponse != null;

        saveFirebase(
                Notification.builder()
                        .agenceId(paymentSyndicatProducerDto.getAgenceId())
                        .message("You are create an payment syndicat")
                        .receivedId(paymentSyndicatProducerDto.getPayerId())
                        .userCreateNotification(paymentSyndicatProducerDto.getUserCreateNotification())
                        .relatedId(paymentSyndicatProducerDto.getRelatedId())
                        .senderUsername(paymentSyndicatProducerDto.getSenderUsername())
                        .build()
        );
    }

    @Override
    public void sendEvenementNotifications(EvenementProducerDto evenementProducerDto)
    {
        List<UserResponse> userDtos = userClient.getUsersByAgenceForNotifie(evenementProducerDto.getAgenceId(), evenementProducerDto.getToken()).getBody();
        User userCreateEvenement = userClient.byUsername(evenementProducerDto.getSenderUsername(), evenementProducerDto.getToken()).getBody();

        assert userDtos != null;
        assert userCreateEvenement != null;

        for (UserResponse user : userDtos)
        {
            if(!user.getUserDto().getUsername().equals(userCreateEvenement.getUsername()))
            {
                saveFirebase(
                        Notification.builder()
                                .agenceId(evenementProducerDto.getAgenceId())
                                .message(evenementProducerDto.getMessage())
                                .receivedId(user.getUserDto().getId())
                                .userCreateNotification(evenementProducerDto.getUserCreateNotification())
                                .relatedId(evenementProducerDto.getRelatedId())
                                .senderUsername(evenementProducerDto.getSenderUsername())
                                .build()
                );
            }
        }
    }

    @Override
    public void sendPlaintesNotifications(PlainteProducerDto plainteProducerDto)
    {
        List<UserResponse> userDtos = userClient.getUsersByAgenceForNotifie(plainteProducerDto.getAgenceId(), plainteProducerDto.getToken()).getBody();
        User userCreatePlainte = userClient.byUsername(plainteProducerDto.getSenderUsername(), plainteProducerDto.getToken()).getBody();

        assert userDtos != null;
        assert userCreatePlainte != null;

        for (UserResponse user : userDtos)
        {
            if(!user.getUserDto().getUsername().equals(userCreatePlainte.getUsername()))
            {
                saveFirebase(
                        Notification.builder()
                                .agenceId(plainteProducerDto.getAgenceId())
                                .message(plainteProducerDto.getMessage())
                                .userCreateNotification(plainteProducerDto.getUserCreatedNotification())
                                .receivedId(user.getUserDto().getId())
                                .relatedId(plainteProducerDto.getRelatedId())
                                .senderUsername(plainteProducerDto.getSenderUsername())
                                .build()
                );
            }
        }
    }

    @Override
    public void sendPlainteNotificationForCreator(PlainteProducerDto plainteProducerDto)
    {
        User user = userClient.byUsername(plainteProducerDto.getSenderUsername(), plainteProducerDto.getToken()).getBody();
        assert user != null;

        saveFirebase(
                Notification.builder()
                        .agenceId(plainteProducerDto.getAgenceId())
                        .message("You are create an complainte")
                        .userCreateNotification(plainteProducerDto.getUserCreatedNotification())
                        .receivedId(user.getId())
                        .relatedId(plainteProducerDto.getRelatedId())
                        .senderUsername(user.getUsername())
                        .build()
        );
    }

    @Override
    public void sendEvenementNotificationForCreator(EvenementProducerDto evenementProducerDto)
    {
        User user = userClient.byUsername(evenementProducerDto.getSenderUsername(), evenementProducerDto.getToken()).getBody();
        assert user != null;

        saveFirebase(
                Notification.builder()
                        .agenceId(evenementProducerDto.getAgenceId())
                        .message("You are create an complainte")
                        .receivedId(user.getId())
                        .userCreateNotification(evenementProducerDto.getUserCreateNotification())
                        .relatedId(evenementProducerDto.getRelatedId())
                        .senderUsername(user.getUsername())
                        .build()
        );
    }

    @Override
    public void deleteNotificationFirebase(String key)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference notificationsRef = database.getReference("notifications");
        log.debug("Notification ID: {}", key);

        notificationsRef.child(key).removeValueAsync();
        log.info("Notification deleted successfully");
    }

    @Override
    public void markAsSeenFirebase(String key)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference notificationsRef = database.getReference("notifications");
        log.debug("Notification ID: {}", key);

        Map<String, Object> noficationUpdated = new HashMap<>();
        noficationUpdated.put("seen", true);

        notificationsRef.child(key).updateChildrenAsync(
                noficationUpdated
        );
        log.info("Notification updated successfully");
    }

    public void validation(Notification notification, Long userId)
    {
        if(!notification.getReceivedId().equals(userId))
        {
            throw new ValidationException("You not have access in this notification.");
        }
    }
}
