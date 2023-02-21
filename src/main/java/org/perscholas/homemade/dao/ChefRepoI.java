package org.perscholas.homemade.dao;


import org.perscholas.homemade.models.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefRepoI extends JpaRepository<Chef,Integer> {
}
