package com.kuba.carrentalcompany3.infrastructure.converter.employee.jpa;

import com.kuba.carrentalcompany3.domain.employee.model.Employee;
import com.kuba.carrentalcompany3.infrastructure.database.jpa.employee.entity.EmployeeAddressDAO;
import com.kuba.carrentalcompany3.infrastructure.database.jpa.employee.entity.EmployeeDAO;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

public class EmployeeToEmployeeDAO implements Converter<Employee, EmployeeDAO> {
    private final ConversionService conversionService;

    public EmployeeToEmployeeDAO(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public EmployeeDAO convert(Employee employee) {
        return new  EmployeeDAO(
                employee.getDomainId(),
                employee.getFirstname(),
                employee.getLastname(),
                conversionService.convert(employee.getAddress(), EmployeeAddressDAO.class),
                employee.getPesel(),
                employee.getAccountNumber(),
                employee.getSalaryAmount(),
                employee.getTypeOfContract(),
                employee.getPosition(),
                employee.getOfficeId()
        );
    }
}
