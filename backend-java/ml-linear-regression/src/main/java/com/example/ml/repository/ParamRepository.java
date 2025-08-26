package com.example.ml.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ml.model.LinearRegressionParams;

public interface ParamRepository extends JpaRepository<LinearRegressionParams, Long> {
}