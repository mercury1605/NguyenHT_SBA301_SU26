package se.fu.chapter12demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import se.fu.chapter12demo.pojos.Employee;
import se.fu.chapter12demo.repositories.IEmployeeRepository;

@Service
public class EmployeeService implements IEmployeeService {

    private final IEmployeeRepository employeeRepository;

    public EmployeeService(IEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

    @Override
    public Employee getEmployeeById(String empId) {
        return employeeRepository.getEmployeeById(empId);
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.create(employee);
    }

    @Override
    public Employee deleteEmployee(int id) {
        return employeeRepository.delete(id);
    }

    @Override
    public Page<Employee> getEmployeesWithPaging(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return employeeRepository.findAll(pageable);
    }

    // Thêm 2 hàm này vào cuối Class EmployeeService của bạn:

    @Override
    public Employee updateEmployee(String empId, Employee updatedEmployee) {
        return employeeRepository.update(empId, updatedEmployee);
    }

    @Override
    public List<Employee> searchEmployees(String keyword) {
        return employeeRepository.search(keyword);
    }
}
