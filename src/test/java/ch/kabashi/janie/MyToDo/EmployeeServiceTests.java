package ch.kabashi.janie.MyToDo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.kabashi.janie.MyToDo.employee.Employee;
import ch.kabashi.janie.MyToDo.employee.EmployeeRepository;
import ch.kabashi.janie.MyToDo.employee.EmployeeService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmployeeServiceTests {

    private EmployeeService employeeService;
    private final EmployeeRepository employeeRepositoryMock = mock(EmployeeRepository.class);

    private final Employee employeeMock = mock(Employee.class);

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService(employeeRepositoryMock);
    }

    @Test
    void createEmployee() {
        when(employeeRepositoryMock.save(employeeMock)).thenReturn(employeeMock);
        employeeService.insertEmployee(employeeMock);
        verify(employeeRepositoryMock, times(1)).save(any());
    }

    @Test
    void findEmployee() {
        when(employeeRepositoryMock.findById(any())).thenReturn(Optional.ofNullable(employeeMock));
        Employee e = employeeService.getEmployee(any());
        verify(employeeRepositoryMock, times(1)).findById(any());
    }

    @Test
    void deleteEmployee() {
        employeeService.deleteEmployee(any());
        verify(employeeRepositoryMock, times(1)).deleteById(any());
    }
}