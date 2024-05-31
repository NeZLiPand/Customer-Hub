package tech.theraven.custumerhub.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tech.theraven.custumerhub.model.entity.CustomerEntity;

@Repository
public interface CustomerRepository
    extends
        JpaRepository<CustomerEntity, Long> {

    String findByEmail(String email);
}
