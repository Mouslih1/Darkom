package com.example.notificationservice.services.impl;

import com.example.evenementservice.dto.EvenementProducerDto;
import com.example.notificationservice.client.UserClient;
import com.example.notificationservice.dtos.NotificationDto;
import com.example.notificationservice.dtos.UserResponse;
import com.example.notificationservice.entities.Notification;
import com.example.notificationservice.entities.User;
import com.example.notificationservice.exception.NotFoundException;
import com.example.notificationservice.exception.ValidationException;
import com.example.notificationservice.repositories.NotificationRepository;
import com.example.notificationservice.services.INotificationService;
import com.example.paymentsyndecalservice.dtos.PaymentSyndicatProducerDto;
import com.example.plainte.dto.PlainteProducerDto;
import com.example.travauxservice.dto.TravauxProducerDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService implements INotificationService {

    private final NotificationRepository notificationRepository;
    private final ModelMapper modelMapper;
    private final UserClient userClient;
    private static final String NOTIFICATION_NOT_FOUND = "Notification not found with this id : ";

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
                notifyMembers(
                        Notification.builder()
                                .agenceId(travauxProducerDto.getAgenceId())
                                .message(travauxProducerDto.getMessage())
                                .receivedId(user.getUserDto().getId())
                                .senderUsername(travauxProducerDto.getSenderUsername())
                                .build()
                );
            }
        }
    }

    @Override
    public void sendTravauxNotificationForCreator(TravauxProducerDto travauxProducerDto)
    {
        User user = userClient.byUsername(travauxProducerDto.getSenderUsername(), travauxProducerDto.getToken()).getBody();
        assert user != null;

        notifyMembers(
                Notification.builder()
                        .agenceId(travauxProducerDto.getAgenceId())
                        .message("You are create an travaux")
                        .receivedId(user.getId())
                        .senderUsername(user.getUsername())
                        .build()
        );
    }

    public void notifyMembers(Notification notification)
    {
        notificationRepository.save(notification);
    }

    @Override
    public List<NotificationDto> allNotificationByMember(Long userId)
    {
        List<Notification> notifications = notificationRepository.findByReceivedId(userId);

        return notifications
                .stream()
                .map((element) -> modelMapper.map(element, NotificationDto.class))
                .toList();
    }

    @Override
    public void markNotificationAsSeen(Long notificationId, @RequestHeader("id") Long userId)
    {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new NotFoundException(NOTIFICATION_NOT_FOUND + notificationId));
        validation(notification, userId);
        notification.setSeen(true);
        notificationRepository.save(notification);
    }

    @Override
    public NotificationDto byId(Long id, @RequestHeader("id") Long userId)
    {
        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new NotFoundException(NOTIFICATION_NOT_FOUND + id));
        validation(notification, userId);
        notification.setSeen(true);
        notificationRepository.save(notification);
        return modelMapper.map(notification, NotificationDto.class);
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
                notifyMembers(
                        Notification.builder()
                                .agenceId(paymentSyndicatProducerDto.getAgenceId())
                                .message(paymentSyndicatProducerDto.getMessage())
                                .receivedId(user.getUserDto().getId())
                                .senderUsername(userResponse.getUserDto().getUsername())
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

        notifyMembers(
                Notification.builder()
                        .agenceId(paymentSyndicatProducerDto.getAgenceId())
                        .message("You are create an payment syndicat")
                        .receivedId(paymentSyndicatProducerDto.getPayerId())
                        .senderUsername(userResponse.getUserDto().getUsername())
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
                notifyMembers(
                        Notification.builder()
                                .agenceId(evenementProducerDto.getAgenceId())
                                .message(evenementProducerDto.getMessage())
                                .receivedId(user.getUserDto().getId())
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
                notifyMembers(
                        Notification.builder()
                                .agenceId(plainteProducerDto.getAgenceId())
                                .message(plainteProducerDto.getMessage())
                                .receivedId(user.getUserDto().getId())
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

        notifyMembers(
                Notification.builder()
                        .agenceId(plainteProducerDto.getAgenceId())
                        .message("You are create an complainte")
                        .receivedId(user.getId())
                        .senderUsername(user.getUsername())
                        .build()
        );
    }

    @Override
    public void sendEvenementNotificationForCreator(EvenementProducerDto evenementProducerDto)
    {
        User user = userClient.byUsername(evenementProducerDto.getSenderUsername(), evenementProducerDto.getToken()).getBody();
        assert user != null;

        notifyMembers(
                Notification.builder()
                        .agenceId(evenementProducerDto.getAgenceId())
                        .message("You are create an event")
                        .receivedId(user.getId())
                        .senderUsername(user.getUsername())
                        .build()
        );
    }

    public void validation(Notification notification, Long userId)
    {
        if(!notification.getReceivedId().equals(userId))
        {
            throw new ValidationException("You not have access in this notification.");
        }
    }
}
