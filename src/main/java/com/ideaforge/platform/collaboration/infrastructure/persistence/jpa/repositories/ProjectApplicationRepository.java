package com.ideaforge.platform.collaboration.infrastructure.persistence.jpa.repositories;

import com.ideaforge.platform.collaboration.domain.model.aggregates.ProjectApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectApplicationRepository extends JpaRepository<ProjectApplication, Long> {
    List<ProjectApplication> findByIdeaId(Long ideaId);
    List<ProjectApplication> findByApplicantProfileId(Long applicantProfileId);
    boolean existsByIdeaIdAndApplicantProfileId(Long ideaId, Long applicantProfileId);
}
