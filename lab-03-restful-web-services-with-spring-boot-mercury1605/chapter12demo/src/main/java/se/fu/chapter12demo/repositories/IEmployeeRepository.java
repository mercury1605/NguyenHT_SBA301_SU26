package se.fu.chapter12demo.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import se.fu.chapter12demo.pojos.Employee;
import java.util.List;

public interface IEmployeeRepository extends PagingAndSortingRepository<Employee, String> {
    List<Employee> getAllEmployees();

    Employee getEmployeeById(String empId);

    Employee create(Employee employee);

    Employee delete(int id);

    Employee update(String empId, Employee updatedEmployee);

    List<Employee> search(String keyword);
}
