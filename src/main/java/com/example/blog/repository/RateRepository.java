package com.example.blog.repository;

import com.example.blog.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RateRepository extends JpaRepository<Rate, Integer> {
}
