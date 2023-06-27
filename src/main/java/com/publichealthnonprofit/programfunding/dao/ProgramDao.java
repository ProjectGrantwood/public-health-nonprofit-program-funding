package com.publichealthnonprofit.programfunding.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.publichealthnonprofit.programfunding.entity.Program;

public interface ProgramDao extends JpaRepository<Program, Long> {
    Optional<Boolean> existsByProgramName(String programName);
}
