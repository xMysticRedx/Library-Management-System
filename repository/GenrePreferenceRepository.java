package com.library.management.repository;

import com.library.management.model.GenrePreference;
import com.library.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenrePreferenceRepository extends JpaRepository<GenrePreference, Long> {
    List<GenrePreference> findByUser(User user);

    boolean existsById(Long id);
}