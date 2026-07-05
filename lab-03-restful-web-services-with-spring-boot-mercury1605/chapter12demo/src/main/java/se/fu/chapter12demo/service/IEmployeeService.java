package se.fu.chapter12demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import se.fu.chapter12demo.pojos.Employee;

public interface IEmployeeService {
    List<Employee> getAllEmployees();

    Employee getEmployeeById(String empId);

    Employee createEmployee(Employee employee);

    Employee deleteEmployee(int id);

    Page<Employee> getEmployeesWithPaging(int page, int size, String sortBy);

    Employee updateEmployee(String empId, Employee updatedEmployee);

    List<Employee> searchEmployees(String keyword);
}
