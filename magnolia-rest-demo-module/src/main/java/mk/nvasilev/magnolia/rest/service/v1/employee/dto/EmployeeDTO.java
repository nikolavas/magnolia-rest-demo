package mk.nvasilev.magnolia.rest.service.v1.employee.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 * EmployeeDTO.
 */
public class EmployeeDTO {
    private String id;
    private String firstName;
    private String lastName;
    private GenderDTO gender;
    private LocalDate dateOfBirth;
    private String department;

    private EmployeeDTO() {

    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public GenderDTO getGender() {
        return gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getDepartment() {
        return department;
    }

    public static final class Builder {
        private String id;
        private String firstName;
        private String lastName;
        private GenderDTO gender;
        private LocalDate dateOfBirth;
        private String department;

        private Builder(String id) {
            this.id = id;
        }

        public static Builder withId(String id) {
            return new Builder(id);
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withGender(GenderDTO gender) {
            this.gender = gender;
            return this;
        }

        public Builder withDateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder withDepartment(String department) {
            this.department = department;
            return this;
        }

        public EmployeeDTO createInstance() {
            EmployeeDTO instance = new EmployeeDTO();
            instance.id = id;
            instance.firstName = firstName;
            instance.lastName = lastName;
            instance.gender = gender;
            instance.dateOfBirth = dateOfBirth;
            instance.department = department;
            return instance;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof EmployeeDTO)) {
            return false;
        }
        EmployeeDTO employeeDTO = (EmployeeDTO) obj;
        return Objects.equals(id, employeeDTO.id);
    }
}
