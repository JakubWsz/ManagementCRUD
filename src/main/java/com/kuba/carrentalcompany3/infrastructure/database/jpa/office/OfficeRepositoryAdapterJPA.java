package com.kuba.carrentalcompany3.infrastructure.database.jpa.office;

import com.kuba.carrentalcompany3.domain.employee.model.Employee;
import com.kuba.carrentalcompany3.domain.office.OfficeRepository;
import com.kuba.carrentalcompany3.domain.office.model.Office;
import com.kuba.carrentalcompany3.infrastructure.database.jpa.employee.entity.EmployeeDao;
import com.kuba.carrentalcompany3.infrastructure.database.jpa.office.entity.OfficeDao;
import org.springframework.core.convert.ConversionService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OfficeRepositoryAdapterJPA implements OfficeRepository {
    private final OfficeRepositoryJPA officeRepositoryJPA;
    private final ConversionService conversionService;

    public OfficeRepositoryAdapterJPA(OfficeRepositoryJPA officeRepositoryJPA, ConversionService conversionService) {
        this.officeRepositoryJPA = officeRepositoryJPA;
        this.conversionService = conversionService;
    }

    public OfficeDao getByDomainId(String domainId) {
        return officeRepositoryJPA.getByDomainId(domainId).get();
    }

    @Override
    public Office save(Office office) {
        OfficeDao officeDao = officeRepositoryJPA.save(conversionService.convert(office, OfficeDao.class));
        return conversionService.convert(officeDao, Office.class);
    }

    @Override
    public Optional<Office> getOffice(String id) {
        Optional<OfficeDao> officeDaoOptional = officeRepositoryJPA.getByDomainId(id);
        return Optional.of(conversionService.convert(officeDaoOptional, Office.class));

    }

    @Override
    public void update(Office office) {
        save(office);
    }

    @Override
    public List<Employee> getEmployeeList(String id) {
        return officeRepositoryJPA.getEmployeeListByOfficeDomainId(id).stream()
                 .map(employeeDao -> conversionService.convert(employeeDao,Employee.class))
                 .collect(Collectors.toList());
    }

    @Override
    public List<Employee> saveEmployee(String officeId, Employee employee) {
      Optional<OfficeDao> optionalOfficeDao = officeRepositoryJPA.getByDomainId(officeId);
        if (optionalOfficeDao.isPresent()){
           optionalOfficeDao.get().getEmployeeDaoList()
                    .add(conversionService.convert(employee,EmployeeDao.class));
           save(conversionService.convert(optionalOfficeDao.get(),Office.class));
    }
        return optionalOfficeDao.get().getEmployeeDaoList();
}
