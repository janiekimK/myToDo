package ch.kabashi.janie.MyToDo;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import ch.kabashi.janie.MyToDo.employee.Employee;
import ch.kabashi.janie.MyToDo.employee.EmployeeRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class DBTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void insertEmployee() {
        Employee objTest1 = new Employee();
        objTest1.setName("Mustermann");
        objTest1.setFirstName("Max");
        objTest1 = this.employeeRepository.save(objTest1);
        Assertions.assertNotNull(objTest1.getId());

        Employee objTest2 = new Employee();
        objTest2.setName("Musterfrau");
        objTest2.setFirstName("Maxine");
        objTest2 = this.employeeRepository.save(objTest2);
        Assertions.assertNotNull(objTest2.getId());
    }
}
