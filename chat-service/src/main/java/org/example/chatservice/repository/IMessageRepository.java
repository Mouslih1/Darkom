package org.example.chatservice.repository;

import org.example.chatservice.entities.Message;
import org.example.chatservice.entities.enums.StatusMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMessageRepository extends JpaRepository<Message, Long> {

    int countMessageBySenderIdAndReceivedIdAndStatusMessage(
            Long senderId,
            Long receivedId,
            StatusMessage statusMessage
    );
    List<Message> findBySenderIdAndReceivedIdAndStatusMessage(
            Long senderId,
            Long receivedId,
            StatusMessage statusMessage
    );
}
