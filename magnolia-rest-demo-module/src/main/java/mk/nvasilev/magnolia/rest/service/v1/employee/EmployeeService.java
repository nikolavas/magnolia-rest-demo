package mk.nvasilev.magnolia.rest.service.v1.employee;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import mk.nvasilev.magnolia.rest.service.v1.employee.dto.EmployeeDTO;
import mk.nvasilev.magnolia.rest.service.v1.employee.dto.GenderDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Basic employee service.
 */

public class EmployeeService {

    private Set<EmployeeDTO> employees = Sets.newLinkedHashSet();

    public EmployeeService() {
        employees.add(EmployeeDTO.Builder
                .withId(UUID.randomUUID().toString())
                .withFirstName("John")
                .withLastName("Doe")
                .withGender(GenderDTO.M)
                .withDateOfBirth(LocalDate.of(1987, 12, 26))
                .withDepartment("Marketing")
                .createInstance());
        employees.add(EmployeeDTO.Builder
                .withId(UUID.randomUUID().toString())
                .withFirstName("Jane")
                .withLastName("Doe")
                .withGender(GenderDTO.F)
                .withDateOfBirth(LocalDate.of(1981, 11, 12))
                .withDepartment("Marketing")
                .createInstance());
    }

    public Set<EmployeeDTO> getEmployees() {
        return employees;
    }

    public Optional<EmployeeDTO> getEmployee(String employeeId) {
        return employees.stream().filter(e -> e.getId().equals(employeeId)).findFirst();
    }

    public EmployeeDTO addEmployee(EmployeeDTO employee) {
        employees.add(employee);
        return employee;
    }

    public Optional<EmployeeDTO> editEmployee(String employeeId, EmployeeDTO employeeDTO) {
        final Optional<EmployeeDTO> employeeDTOOptional = getEmployee(employeeId);
        if(employeeDTOOptional.isPresent()) {
            deleteEmployee(employeeId);
            addEmployee(employeeDTO);
        }
        return getEmployee(employeeId);
    }

    public boolean deleteEmployee(String employeeId) {
        final Optional<EmployeeDTO> employeeDTOOptional = getEmployee(employeeId);
        if(employeeDTOOptional.isPresent()) {
            return employees.remove(employeeDTOOptional.get());
        } else {
            return false;
        }
    }
}
