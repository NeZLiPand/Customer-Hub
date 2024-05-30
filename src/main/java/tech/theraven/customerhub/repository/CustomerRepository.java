package tech.theraven.customerhub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tech.theraven.customerhub.entity.CustomerEntity;

@Repository
public interface CustomerRepository
    extends
        JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByEmail(String email);
}
