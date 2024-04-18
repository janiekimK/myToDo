package ch.kabashi.janie.MyToDo.employee;

import ch.kabashi.janie.MyToDo.base.MessageResponse;
import ch.kabashi.janie.MyToDo.storage.EntityNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> getEmployees() {
        return repository.findByOrderByNameAscFirstnameAsc();
    }

    public Employee getEmployee(Long id) {
        return repository.findById(id)

                .orElseThrow(() -> new EntityNotFoundException(id, Employee.class));
    }

    public Employee insertEmployee(Employee employee) {
        return repository.save(employee);
    }

    public Employee updateEmployee(Employee employee, Long id) {
        return repository.findById(id)
                .map(employeeOrig -> {
                    employeeOrig.setName(employee.getName());
                    employeeOrig.setFirstName(employee.getFirstName());
           
                    return repository.save(employeeOrig);
                })
                .orElseGet(() -> repository.save(employee));
    }

    public MessageResponse deleteEmployee(Long id) {
        repository.deleteById(id);
        return new MessageResponse("Employee " + id + " deleted");
    }
}
