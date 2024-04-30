package org.example.chatservice.repository;

import org.example.chatservice.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findBySenderIdAndReceivedId(Long senderId, Long receivedId);
}
