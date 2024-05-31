package tech.theraven.custumerhub.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tech.theraven.custumerhub.model.dto.CustomerDTO;
import tech.theraven.custumerhub.model.entity.CustomerEntity;
import tech.theraven.custumerhub.model.repository.CustomerRepository;
import tech.theraven.custumerhub.validator.DataValidator;

@RestController
public class CustomerControllerImpl
    implements
        CustomerController {

    private final CustomerRepository customerRepository;
    private final DataValidator dataValidator;

    public CustomerControllerImpl(CustomerRepository customerRepository,
                                  DataValidator dataValidator) {
        super();
        this.customerRepository = customerRepository;
        this.dataValidator = dataValidator;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> readAllCustomers() {
        try {
            List<CustomerEntity> optionalListFoundCustomerEntities = customerRepository.findAll();
            if (!optionalListFoundCustomerEntities.isEmpty()) {
                List<CustomerDTO> foundListCustomerDTO = new ArrayList<>();
                for (CustomerEntity cE : optionalListFoundCustomerEntities) {
                    foundListCustomerDTO.add(CustomerDTO.builder()
                                                        .withId(cE.getId())
                                                        .withFullName(cE.getFullName())
                                                        .withEmail(cE.getEmail())
                                                        .withPhone(cE.getPhone())
                                                        .build());
                }
                return new ResponseEntity<>(foundListCustomerDTO,
                                            HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> readCustomer(@PathVariable Long id) {
        try {
            Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(id);
            if (optionalCustomerEntity.isPresent()) {
                CustomerEntity foundCustomerEntity = optionalCustomerEntity.get();
                return new ResponseEntity<>(CustomerDTO.builder()
                                                       .withId(foundCustomerEntity.getId())
                                                       .withFullName(foundCustomerEntity.getFullName())
                                                       .withEmail(foundCustomerEntity.getEmail())
                                                       .withPhone(foundCustomerEntity.getPhone())
                                                       .build(),
                                            HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        try {
            if (dataValidator.validateEmailIsUnique(customerDTO.getEmail())) {
                CustomerEntity savedEntity = customerRepository.save(CustomerEntity.builder()
                                                                                   .withCreated(System.currentTimeMillis())
                                                                                   .withUpdated(System.currentTimeMillis())
                                                                                   .withFullName(customerDTO.getFullName())
                                                                                   .withEmail(customerDTO.getEmail())
                                                                                   .withPhone(customerDTO.getPhone())
                                                                                   .withIsActive(true)
                                                                                   .build());
                return new ResponseEntity<>(CustomerDTO.builder()
                                                       .withId(savedEntity.getId())
                                                       .withFullName(savedEntity.getFullName())
                                                       .withEmail(savedEntity.getEmail())
                                                       .withPhone(savedEntity.getPhone())
                                                       .build(),
                                            HttpStatus.CREATED);
            }
            return new ResponseEntity<>(customerDTO,
                                        HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> upsertCustomer(@RequestBody @Valid CustomerDTO customerDTO,
                                                      @PathVariable Long id) {
        try {
            Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(id);
            if (optionalCustomerEntity.orElseThrow() != null) {
                CustomerEntity foundCustomerEntity = optionalCustomerEntity.get();
                if (foundCustomerEntity.getEmail()
                                       .equals(customerDTO.getEmail())) {
                    CustomerEntity savedEntity = customerRepository.save(CustomerEntity.builder()
                                                                                       .withId(id)
                                                                                       .withCreated(System.currentTimeMillis())
                                                                                       .withUpdated(System.currentTimeMillis())
                                                                                       .withFullName(customerDTO.getFullName())
                                                                                       .withEmail(customerDTO.getEmail())
                                                                                       .withPhone(customerDTO.getPhone())
                                                                                       .withIsActive(true)
                                                                                       .build());
                    return new ResponseEntity<>(CustomerDTO.builder()
                                                           .withId(savedEntity.getId())
                                                           .withFullName(savedEntity.getFullName())
                                                           .withEmail(savedEntity.getEmail())
                                                           .withPhone(savedEntity.getPhone())
                                                           .build(),
                                                HttpStatus.CREATED);
                }
            }
            return new ResponseEntity<>(customerDTO,
                                        HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        try {
            Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(id);
            if (optionalCustomerEntity.isPresent()) {
                CustomerEntity foundCustomerEntity = optionalCustomerEntity.get();
                customerRepository.save(CustomerEntity.builder()
                                                      .withId(id)
                                                      .withCreated(foundCustomerEntity.getCreated())
                                                      .withUpdated(System.currentTimeMillis())
                                                      .withFullName(foundCustomerEntity.getFullName())
                                                      .withEmail(foundCustomerEntity.getEmail())
                                                      .withPhone(foundCustomerEntity.getPhone())
                                                      .withIsActive(false)
                                                      .build());
                return HttpStatus.OK;
            }
            return HttpStatus.NOT_FOUND;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}
