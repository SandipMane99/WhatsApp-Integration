package com.wi.templatemessageapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wi.templatemessageapi.entities.TemplateMessageApiEntity;
 
@Repository
public interface TemplateMessageApiRepository extends JpaRepository<TemplateMessageApiEntity, String> {

	@Query(value = "SELECT template_id FROM template_message_api_entity order by id ASC", nativeQuery = true)
	List getAllTemplateId();
	 
	@Query(value = "SELECT media_url from template_message_api_entity where template_id= :t_id", nativeQuery = true)
	String getMediaUrl(@Param("t_id") String template_id);
	
	@Query(value = "SELECT template_content from template_message_api_entity where template_id= :t_id", nativeQuery = true)
	String getTemplateContent(@Param("t_id") String template_id);

	@Query(value = "SELECT template_type from template_message_api_entity where template_id= :t_id", nativeQuery = true)
	String getTemplateType(@Param("t_id") String template_id);
}
