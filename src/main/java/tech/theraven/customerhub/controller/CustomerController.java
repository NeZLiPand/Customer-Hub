package tech.theraven.customerhub.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import tech.theraven.customerhub.dto.CustomerDTO;

public interface CustomerController {

    ResponseEntity<List<CustomerDTO>> readAllCustomers();

    ResponseEntity<CustomerDTO> readCustomer(Long id);

    ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO);

    ResponseEntity<CustomerDTO> updateCustomer(@RequestBody @Valid CustomerDTO customerDTO,
                                               @PathVariable Long id);

    ResponseEntity<Void> deleteCustomer(@PathVariable Long id);
    
}
