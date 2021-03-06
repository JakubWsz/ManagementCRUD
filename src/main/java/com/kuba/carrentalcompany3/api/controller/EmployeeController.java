package com.kuba.carrentalcompany3.api.controller;

import com.kuba.carrentalcompany3.api.dto.FieldUpdateDTO;
import com.kuba.carrentalcompany3.api.dto.employee.EmployeeView;
import com.kuba.carrentalcompany3.api.dto.employee.request.CreateEmployeeRequest;
import com.kuba.carrentalcompany3.api.dto.employee.request.UpdateEmployeeRequest;
import com.kuba.carrentalcompany3.domain.Address;
import com.kuba.carrentalcompany3.domain.employee.model.EmployeeFieldType;
import com.kuba.carrentalcompany3.domain.employee.EmployeeService;
import com.kuba.carrentalcompany3.domain.employee.model.Employee;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final ConversionService conversionService;
    private final EmployeeService employeeService;

    public EmployeeController(ConversionService conversionService, EmployeeService employeeService) {
        this.conversionService = conversionService;
        this.employeeService = employeeService;
    }

    @PostMapping("/create")
    public ResponseEntity<EmployeeView> createEmployee(@RequestBody CreateEmployeeRequest createEmployeeRequest) {
        Employee employee = employeeService.createEmployee(
                createEmployeeRequest.getFirstname(),
                createEmployeeRequest.getLastname(),
                conversionService.convert(createEmployeeRequest.getAddressDTO(), Address.class),
                createEmployeeRequest.getPesel(),
                createEmployeeRequest.getAccountNumber(),
                createEmployeeRequest.getSalaryAmount(),
                createEmployeeRequest.getContractType(),
                createEmployeeRequest.getPosition(),
                createEmployeeRequest.getOfficeId());
        return new ResponseEntity<>(conversionService.convert(employee, EmployeeView.class), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/remove")
    public void removeEmployee(@PathVariable String id) {
        employeeService.removeEmployee(id);
    }

    @PatchMapping("/{id}/update-data")
    public void updateEmployee(@RequestBody UpdateEmployeeRequest updateEmployeeRequest, @PathVariable String id) {
        Map<EmployeeFieldType, String> fieldUpdates = updateEmployeeRequest.getUpdateEmployee().stream()
                .collect(Collectors.toMap(FieldUpdateDTO::getFieldType, FieldUpdateDTO::getNewValue));
        employeeService.updateEmployee(fieldUpdates, id);
    }
}
