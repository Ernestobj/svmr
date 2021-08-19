package edu.own.svmr;

import edu.own.svmr.mapper.EmployeeMapper;
import edu.own.svmr.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SvmrApplicationTests {

    @Autowired
    EmployeeMapper employeeMapper;

    @Test
    void contextLoads() {
        Employee employee = employeeMapper.selectByPrimaryKey(1);
        System.out.println(employee);
    }

}
