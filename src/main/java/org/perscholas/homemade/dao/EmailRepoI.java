package org.perscholas.homemade.dao;

import jakarta.transaction.Transactional;
import org.perscholas.homemade.models.Chef;
import org.perscholas.homemade.models.EmailDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional(rollbackOn = Exception.class)
public interface EmailRepoI extends JpaRepository<EmailDetails,Integer> {
}
