package tech.theraven.customerhub.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import tech.theraven.customerhub.entity.CustomerEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testFindByEmail_shouldReturnTrue_whenEmailExists() {
        CustomerEntity customer = CustomerEntity.builder()
                                                .withFullName("John Doe")
                                                .withEmail("john.doe@example.com")
                                                .withPhone("+123456789")
                                                .withIsActive(true)
                                                .build();
        customerRepository.save(customer);

        Optional<CustomerEntity> foundEntityByEmail = customerRepository.findByEmail(customer.getEmail());
        assertTrue(foundEntityByEmail.isPresent());
        assertEquals(customer.getEmail(),
                     foundEntityByEmail.get()
                                       .getEmail());
    }

    @Test
    public void testFindByEmail_shouldReturnNull_whenEmailNotFound() {
        Optional<CustomerEntity> foundEntityByEmail = customerRepository.findByEmail("nonexistent@example.com");
        assertTrue(foundEntityByEmail.isEmpty());
    }

}
