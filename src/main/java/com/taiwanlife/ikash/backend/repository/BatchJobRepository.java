package com.taiwanlife.ikash.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taiwanlife.ikash.backend.entity.ikash.BatchJob;

public interface BatchJobRepository extends JpaRepository<BatchJob, String>{

}
