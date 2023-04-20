package com.wi.excelreadapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wi.excelreadapi.entities.Details;

@Repository
public interface UserRepository extends JpaRepository<Details, Long> {

}