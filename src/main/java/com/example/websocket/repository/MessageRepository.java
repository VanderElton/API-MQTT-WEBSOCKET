package com.example.websocket.repository;

import com.example.websocket.entity.MsgCallback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<MsgCallback, Integer> {
}
