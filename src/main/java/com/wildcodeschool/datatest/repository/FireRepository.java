package com.wildcodeschool.datatest.repository;

import com.wildcodeschool.datatest.entity.Fire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FireRepository extends JpaRepository<Fire, Long> {
}
