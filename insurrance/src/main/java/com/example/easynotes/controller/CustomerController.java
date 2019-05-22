package com.example.easynotes.controller;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Customer;
import com.example.easynotes.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by ThuongPham on 22/05/2019.
 */
@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @PostMapping("/customers")
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomerById(@PathVariable(value = "id") Integer customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));
    }

    @PutMapping("/customers/{id}")
    public Customer updateCustomer(@PathVariable(value = "id") Integer customerId,
                                   @Valid @RequestBody Customer customerDetails) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));
        customer.setName(customerDetails.getName());
        customer.setPhone(customerDetails.getPhone());
        customer.setAddress(customerDetails.getAddress());
        customer.setEmail(customerDetails.getEmail());
        Customer updatedCustomer = customerRepository.save(customerDetails);
        return updatedCustomer;
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable(value = "id") Integer customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));
        customerRepository.delete(customer);
        return ResponseEntity.ok().build();
    }
}
