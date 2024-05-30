package tech.theraven.customerhub.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import tech.theraven.customerhub.dto.CustomerDTO;
import tech.theraven.customerhub.entity.CustomerEntity;
import tech.theraven.customerhub.repository.CustomerRepository;
import tech.theraven.customerhub.validator.DataValidator;

@WebMvcTest(CustomerControllerImpl.class)
public class CustomerControllerImplTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private DataValidator dataValidator;

    @InjectMocks
    private CustomerControllerImpl customerController;

    @Test
    public void testReadAllCustomers_shouldReturnThreeCustomers_whenDataBaseFoundThreeCustomers() throws Exception {
        Long testTime = System.currentTimeMillis();
        List<CustomerEntity> customerEntityList = new ArrayList<CustomerEntity>();

        customerEntityList.add(CustomerEntity.builder()
                                             .withId(0L)
                                             .withCreated(testTime)
                                             .withUpdated(testTime)
                                             .withFullName("John Doe")
                                             .withEmail("john.doe@example.com")
                                             .withPhone("+1234567890")
                                             .withIsActive(true)
                                             .build());
        customerEntityList.add(CustomerEntity.builder()
                                             .withId(1L)
                                             .withCreated(testTime
                                                          + 1L)
                                             .withUpdated(testTime
                                                          + 1L)
                                             .withFullName("Lee Duyian")
                                             .withEmail("lee.duyian@example.com")
                                             .withPhone("+9876543210")
                                             .withIsActive(false)
                                             .build());
        customerEntityList.add(CustomerEntity.builder()
                                             .withId(2L)
                                             .withCreated(testTime
                                                          + 2L)
                                             .withUpdated(testTime
                                                          + 2L)
                                             .withFullName("Custom Punisher")
                                             .withEmail("custom.punisher@example.com")
                                             .withPhone("+1231231234")
                                             .withIsActive(true)
                                             .build());

        when(customerRepository.findAll()).thenReturn(customerEntityList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers")
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value(0L))
               .andExpect(jsonPath("$[0].fullName").value("John Doe"))
               .andExpect(jsonPath("$[0].email").value("john.doe@example.com"))
               .andExpect(jsonPath("$[0].phone").value("+1234567890"))
               .andExpect(jsonPath("$[1].id").value(1L))
               .andExpect(jsonPath("$[1].fullName").value("Lee Duyian"))
               .andExpect(jsonPath("$[1].email").value("lee.duyian@example.com"))
               .andExpect(jsonPath("$[1].phone").value("+9876543210"))
               .andExpect(jsonPath("$[2].id").value(2L))
               .andExpect(jsonPath("$[2].fullName").value("Custom Punisher"))
               .andExpect(jsonPath("$[2].email").value("custom.punisher@example.com"))
               .andExpect(jsonPath("$[2].phone").value("+1231231234"));
    }

    @Test
    public void testReadAllCustomers_shouldReturnStatus204_whenCustomersDidNotFound() throws Exception {
        List<CustomerEntity> customerEntityList = new ArrayList<CustomerEntity>();

        when(customerRepository.findAll()).thenReturn(customerEntityList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers")
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNoContent());
    }

    @Test
    public void testReadAllCustomers_shouldReturnStatus500_whenRepositoryThrowsException() throws Exception {
        when(customerRepository.findAll()).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers")
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is5xxServerError());
    }

    @Test
    public void testReadCustomer_ShouldReturnSpecificCustomer_whenDataBaseHasTheCustomersWithExpectedID() throws Exception {
        Long testTime = System.currentTimeMillis();
        Optional<CustomerEntity> expectedOptionalCustomerEntity = Optional.of(CustomerEntity.builder()
                                                                                            .withId(2L)
                                                                                            .withCreated(testTime)
                                                                                            .withUpdated(testTime)
                                                                                            .withFullName("John Doe")
                                                                                            .withEmail("john.doe@example.com")
                                                                                            .withPhone("+1234567890")
                                                                                            .withIsActive(true)
                                                                                            .build());

        when(customerRepository.findById(2L)).thenReturn(expectedOptionalCustomerEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/2")
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(2L))
               .andExpect(jsonPath("$.fullName").value("John Doe"))
               .andExpect(jsonPath("$.email").value("john.doe@example.com"))
               .andExpect(jsonPath("$.phone").value("+1234567890"));
    }

    @Test
    public void testReadCustomer_shouldReturnStatus404_whenCustomerDidNotFound() throws Exception {
        when(customerRepository.findById(2L)).thenReturn(Optional.ofNullable(null));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/2")
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound());
    }

    @Test
    public void testReadCustomer_shouldReturnStatus500_whenRepositoryThrowsException() throws Exception {
        when(customerRepository.findById(2L)).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/2")
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is5xxServerError());
    }

    @Test
    public void testCreateCustomer_shouldCreateCustomer_whenFieldsOfRequestAreValid() throws Exception {
        Long testTime = System.currentTimeMillis();
        CustomerEntity savedEntity = CustomerEntity.builder()
                                                   .withId(2L)
                                                   .withCreated(testTime)
                                                   .withUpdated(testTime)
                                                   .withFullName("John Doe")
                                                   .withEmail("john.doe@example.com")
                                                   .withPhone("+1234567890")
                                                   .withIsActive(true)
                                                   .build();

        when(dataValidator.validateEmailIsUnique("john.doe@example.com")).thenReturn(true);
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(savedEntity);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(objectMapper.writeValueAsString(CustomerDTO.builder()
                                                                                                  .withId(2L)
                                                                                                  .withFullName("John Doe")
                                                                                                  .withEmail("john.doe@example.com")
                                                                                                  .withPhone("+1234567890")
                                                                                                  .build())))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(2L))
               .andExpect(jsonPath("$.fullName").value("John Doe"))
               .andExpect(jsonPath("$.email").value("john.doe@example.com"))
               .andExpect(jsonPath("$.phone").value("+1234567890"));
    }

    @Test
    public void testCreateCustomer_shouldReturnStatus400_whenFieldOfFullNameOfRequestIsNotValid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(objectMapper.writeValueAsString(CustomerDTO.builder()
                                                                                                  .withId(1L)
                                                                                                  .withFullName("J")
                                                                                                  .withEmail("john@example.com")
                                                                                                  .withPhone("+1234567890")
                                                                                                  .build())))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateCustomer_shouldReturnStatus400_whenFieldOfEmailOfRequestIsValidButNotUnique() throws Exception {
        when(dataValidator.validateEmailIsUnique("john.doe@example.com")).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(objectMapper.writeValueAsString(CustomerDTO.builder()
                                                                                                  .withFullName("John Doe")
                                                                                                  .withEmail("john.doe@example.com")
                                                                                                  .withPhone("+0987776655")
                                                                                                  .build())))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateCustomer_shouldReturnStatus400_whenFieldOfEmailOfRequestIsNotValid() throws Exception {
        when(dataValidator.validateEmailIsUnique(anyString())).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(objectMapper.writeValueAsString(CustomerDTO.builder()
                                                                                                  .withFullName("John Doe")
                                                                                                  .withEmail("@example.com")
                                                                                                  .withPhone("+1234567890")
                                                                                                  .build())))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateCustomer_shouldReturnStatus400_whenFieldOfPhoneOfRequestIsNotValid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(objectMapper.writeValueAsString(CustomerDTO.builder()
                                                                                                  .withId(1L)
                                                                                                  .withFullName("John Doe")
                                                                                                  .withEmail("john.doe@example.com")
                                                                                                  .withPhone("+098")
                                                                                                  .build())))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateCustomer_shouldReturnStatus409_whenRepositoryThrowsException() throws Exception {
        when(dataValidator.validateEmailIsUnique("john.doe@example.com")).thenReturn(true);
        when(customerRepository.save(any(CustomerEntity.class))).thenThrow(new DataIntegrityViolationException("One of the constraints at the database level has been violated."));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(objectMapper.writeValueAsString(CustomerDTO.builder()
                                                                                                  .withId(2L)
                                                                                                  .withFullName("John Doe")
                                                                                                  .withEmail("john.doe@example.com")
                                                                                                  .withPhone("+1234567890")
                                                                                                  .build())))
               .andExpect(status().isConflict());
    }

    @Test
    public void testCreateCustomer_shouldReturnStatus500_whenRepositoryThrowsException() throws Exception {
        when(dataValidator.validateEmailIsUnique("john.doe@example.com")).thenReturn(true);
        when(customerRepository.save(any(CustomerEntity.class))).thenThrow(new RuntimeException("Database error"));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(objectMapper.writeValueAsString(CustomerDTO.builder()
                                                                                                  .withId(2L)
                                                                                                  .withFullName("John Doe")
                                                                                                  .withEmail("john.doe@example.com")
                                                                                                  .withPhone("+1234567890")
                                                                                                  .build())))
               .andExpect(status().isInternalServerError());
    }

    @Test
    public void testUpdateCustomer_shouldUpdateCustomer_whenFieldsOfRequestAreValid() throws Exception {
        Long testTime = System.currentTimeMillis();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(CustomerEntity.builder()
                                                                                   .withId(1L)
                                                                                   .withCreated(testTime)
                                                                                   .withUpdated(testTime)
                                                                                   .withFullName("John Doe")
                                                                                   .withEmail("john.doe@example.com")
                                                                                   .withPhone("+1234567890")
                                                                                   .withIsActive(true)
                                                                                   .build()));

        CustomerEntity savedCustomerEntity = CustomerEntity.builder()
                                                           .withId(1L)
                                                           .withCreated(testTime
                                                                        + 11_111L)
                                                           .withUpdated(testTime
                                                                        + 11_111L)
                                                           .withFullName("John Doenech")
                                                           .withEmail("john.doe@example.com")
                                                           .withPhone("+0987776655")
                                                           .withIsActive(true)
                                                           .build();
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(savedCustomerEntity);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/customers/1")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(objectMapper.writeValueAsString(CustomerDTO.builder()
                                                                                                  .withId(1L)
                                                                                                  .withFullName("John Doenech")
                                                                                                  .withEmail("john.doe@example.com")
                                                                                                  .withPhone("+0987776655")
                                                                                                  .build())))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.fullName").value("John Doenech"))
               .andExpect(jsonPath("$.email").value("john.doe@example.com"))
               .andExpect(jsonPath("$.phone").value("+0987776655"));
    }

    @Test
    public void testUpdateCustomer_shouldReturnStatus404_whenExpectedCustomerNotFound() throws Exception {
        when(customerRepository.findById(1L)).thenReturn(Optional.ofNullable(null));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/customers/1")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(objectMapper.writeValueAsString(CustomerDTO.builder()
                                                                                                  .withId(1L)
                                                                                                  .withFullName("John Doe")
                                                                                                  .withEmail("john.doe@example.com")
                                                                                                  .withPhone("+0987776655")
                                                                                                  .build())))
               .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateCustomer_shouldReturnStatus400_whenFieldOfEmailOfRequestIsValidButNotEquelFoundEmail() throws Exception {
        Long testTime = System.currentTimeMillis();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(CustomerEntity.builder()
                                                                                   .withId(1L)
                                                                                   .withCreated(testTime)
                                                                                   .withUpdated(testTime)
                                                                                   .withFullName("John Doe")
                                                                                   .withEmail("john.doetech@example.com")
                                                                                   .withPhone("+1234567890")
                                                                                   .withIsActive(true)
                                                                                   .build()));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/customers/1")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(objectMapper.writeValueAsString(CustomerDTO.builder()
                                                                                                  .withId(1L)
                                                                                                  .withFullName("John Doe")
                                                                                                  .withEmail("john.doe@example.com")
                                                                                                  .withPhone("+0987776655")
                                                                                                  .build())))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateCustomer_shouldReturnStatus400_whenFieldOfEmailOfRequestIsNotValid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/customers/1")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(objectMapper.writeValueAsString(CustomerDTO.builder()
                                                                                                  .withId(1L)
                                                                                                  .withFullName("John Doe")
                                                                                                  .withEmail("@example.com")
                                                                                                  .withPhone("+0987776655")
                                                                                                  .build())))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateCustomer_shouldReturnStatus400_whenFieldOfFullNameOfRequestIsNotValid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/customers/1")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(objectMapper.writeValueAsString(CustomerDTO.builder()
                                                                                                  .withId(1L)
                                                                                                  .withFullName("J")
                                                                                                  .withEmail("john.doe@example.com")
                                                                                                  .withPhone("+0987776655")
                                                                                                  .build())))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateCustomer_shouldReturnStatus400_whenFieldOfPhoneOfRequestIsNotValid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/customers/1")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(objectMapper.writeValueAsString(CustomerDTO.builder()
                                                                                                  .withId(1L)
                                                                                                  .withFullName("John Doe")
                                                                                                  .withEmail("john.doe@example.com")
                                                                                                  .withPhone("+098")
                                                                                                  .build())))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateCustomer_shouldReturnStatus500_whenRepositoryThrowsException() throws Exception {
        when(customerRepository.findById(1L)).thenThrow(new RuntimeException("Database error"));
        mockMvc.perform(MockMvcRequestBuilders.put("/api/customers/1")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(objectMapper.writeValueAsString(CustomerDTO.builder()
                                                                                                  .withId(1L)
                                                                                                  .withFullName("John Doe")
                                                                                                  .withEmail("john.doe@example.com")
                                                                                                  .withPhone("+1234567890")
                                                                                                  .build())))
               .andExpect(status().isInternalServerError());
    }

    @Test
    public void testDeleteCustomer_shouldReturnStatus200_whenRequestIsValid() throws Exception {
        Long testTime = System.currentTimeMillis();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(CustomerEntity.builder()
                                                                                   .withId(1L)
                                                                                   .withCreated(testTime)
                                                                                   .withUpdated(testTime)
                                                                                   .withFullName("John Doe")
                                                                                   .withEmail("john.doe@example.com")
                                                                                   .withPhone("+1234567890")
                                                                                   .withIsActive(true)
                                                                                   .build()));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/customers/1")
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @Test
    public void testDeleteCustomer_shouldReturnStatus404_whenCustomerDidNotFindWithRequestedID() throws Exception {
        when(customerRepository.findById(1L)).thenReturn(Optional.ofNullable(null));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/customers/1")
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteCustomer_shouldReturnStatus500_whenCustomerReposytoryGetException() throws Exception {
        when(customerRepository.findById(1L)).thenThrow(new RuntimeException());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/customers/1")
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is5xxServerError());
    }

}
