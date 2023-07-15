package kz.aibat.moviereview.repository;

import kz.aibat.moviereview.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByAccessValue(String accessValue);
}
