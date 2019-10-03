package com.example.websocket.repository;

import com.example.websocket.entity.CommandMqtt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandRepository extends JpaRepository<CommandMqtt, Integer> {
}
