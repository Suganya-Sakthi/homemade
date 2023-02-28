package org.perscholas.homemade.dao;

import org.perscholas.homemade.models.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthGroupRepoI extends JpaRepository<AuthGroup, Integer> {

    List<AuthGroup> findByEmail(String email);
}
