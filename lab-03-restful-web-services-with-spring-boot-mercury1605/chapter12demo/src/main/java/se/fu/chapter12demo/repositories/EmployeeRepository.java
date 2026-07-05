package se.fu.chapter12demo.repositories;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import se.fu.chapter12demo.pojos.Employee;
import java.util.*;

@Repository
public class EmployeeRepository implements IEmployeeRepository {
    private final List<Employee> store = new ArrayList<>();

    @PostConstruct
    public void init() {
        store.add(new Employee("E001", "Nguyen Van A", "Developer", 1200.0));
        store.add(new Employee("E002", "Tran Thi B", "Tester", 1100.0));
        store.add(new Employee("E003", "Le Van C", "Manager", 2500.0));
        store.add(new Employee("E004", "Pham Thi D", "Analyst", 1500.0));
        store.add(new Employee("E005", "Hoang Van E", "Architect", 3000.0));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(store);
    }

    @Override
    public Employee getEmployeeById(String empId) {
        return store.stream()
                .filter(e -> e.getEmpId().equals(empId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Employee create(Employee employee) {
        store.add(employee);
        return employee;
    }

    @Override
    public Employee delete(int id) {
        if (id >= 0 && id < store.size()) {
            return store.remove(id);
        }
        return null;
    }

    // Override findAll(Sort sort) - Sắp xếp thủ công dựa trên Sort Object của
    // Spring
    @Override
    public Iterable<Employee> findAll(Sort sort) {
        List<Employee> sortedList = new ArrayList<>(store);
        if (sort.isSorted()) {
            Sort.Order order = sort.iterator().next();
            String property = order.getProperty();

            Comparator<Employee> comparator = switch (property) {
                case "name" -> Comparator.comparing(Employee::getName);
                case "designation" -> Comparator.comparing(Employee::getDesignation);
                case "salary" -> Comparator.comparing(Employee::getSalary);
                default -> Comparator.comparing(Employee::getEmpId);
            };

            if (order.isDescending()) {
                comparator = comparator.reversed();
            }
            sortedList.sort(comparator);
        }
        return sortedList;
    }

    @Override
    public Page<Employee> findAll(Pageable pageable) {
        List<Employee> currentList = new ArrayList<>();
        findAll(pageable.getSort()).forEach(currentList::add);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), currentList.size());

        if (start > currentList.size()) {
            return new PageImpl<>(Collections.emptyList(), pageable, currentList.size());
        }

        List<Employee> pageContent = currentList.subList(start, end);
        return new PageImpl<>(pageContent, pageable, currentList.size());
    }

    @Override
    public Employee update(String empId, Employee updatedEmployee) {
        Employee existingEmployee = getEmployeeById(empId);
        if (existingEmployee != null) {
            existingEmployee.setName(updatedEmployee.getName());
            existingEmployee.setDesignation(updatedEmployee.getDesignation());
            existingEmployee.setSalary(updatedEmployee.getSalary());
            return existingEmployee;
        }
        return null;
    }

    @Override
    public List<Employee> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>(store);
        }
        String lowerKeyword = keyword.toLowerCase().trim();
        return store.stream()
                .filter(emp -> (emp.getName() != null && emp.getName().toLowerCase().contains(lowerKeyword)) ||
                        (emp.getDesignation() != null && emp.getDesignation().toLowerCase().contains(lowerKeyword)))
                .toList();
    }

}
