package com.wi.messagesendingapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wi.messagesendingapi.entities.MessageSendingApiEntity;

 
public interface MessageSendingApiRepository extends JpaRepository<MessageSendingApiEntity, Long> {

	MessageSendingApiEntity findById(int i);


	@Query(value = "SELECT status FROM message_sending_api_entity ORDER BY id DESC LIMIT :total_records", nativeQuery = true)
	List<String> getUpdatedStatus(@Param("total_records") int total_records);
	
}
