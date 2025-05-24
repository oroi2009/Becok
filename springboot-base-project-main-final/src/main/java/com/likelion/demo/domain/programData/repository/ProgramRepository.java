package com.likelion.demo.domain.programData.repository;

import com.likelion.demo.domain.programData.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
}
