package org.perscholas.homemade.dao;


import jakarta.transaction.Transactional;
import org.perscholas.homemade.models.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional(rollbackOn = Exception.class)
public interface ChefRepoI extends JpaRepository<Chef,Integer>  {
    Optional<Chef> findByEmail(String email);
}
