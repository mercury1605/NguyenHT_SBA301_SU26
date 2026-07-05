package se.fu.chapter12demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import se.fu.chapter12demo.dto.ApiResponse;
import se.fu.chapter12demo.exceptions.EmployeeNotFoundException;
import se.fu.chapter12demo.pojos.Employee;
import se.fu.chapter12demo.service.IEmployeeService;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee Operations", description = "CRUD with your Employee System")
public class EmployeeController {

    private final IEmployeeService employeeService;

    public EmployeeController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @Operation(summary = "Get employees list with paging and sorting")
    public ResponseEntity<ApiResponse<Page<Employee>>> getEmployees(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sortBy", defaultValue = "empId") String sortBy) {

        Page<Employee> employeePage = employeeService.getEmployeesWithPaging(page, size, sortBy);
        ApiResponse<Page<Employee>> response = new ApiResponse<>(true, "Fetched successfully", employeePage);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{empId}")
    @Operation(summary = "Get an employee by ID")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Employee found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Employee not found")
    })
    public ResponseEntity<ApiResponse<Employee>> getEmployeeById(@PathVariable("empId") String empId) {
        Employee employee = employeeService.getEmployeeById(empId);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee with ID " + empId + " does not exist."); 
        }
        ApiResponse<Employee> response = new ApiResponse<>(true, "Employee found", employee);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Create a new employee")
    public ResponseEntity<ApiResponse<Employee>> createEmployee(@Valid @RequestBody Employee employee) {
        Employee createdEmployee = employeeService.createEmployee(employee);
        ApiResponse<Employee> response = new ApiResponse<>(true, "Employee created successfully", createdEmployee);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an employee by index")
    public ResponseEntity<ApiResponse<Employee>> deleteEmployee(@PathVariable("id") int id) {
        Employee deletedEmployee = employeeService.deleteEmployee(id);
        if (deletedEmployee == null) {
            throw new EmployeeNotFoundException("Employee index " + id + " not found to delete.");
        }
        ApiResponse<Employee> response = new ApiResponse<>(true, "Employee deleted successfully", deletedEmployee);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{empId}")
    @Operation(summary = "Update an existing employee info")
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(
            @PathVariable("empId") String empId,
            @Valid @RequestBody Employee updatedEmployee) {

        Employee employee = employeeService.updateEmployee(empId, updatedEmployee);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee with ID " + empId + " not found to update.");
        }
        ApiResponse<Employee> response = new ApiResponse<>(true, "Employee updated successfully", employee);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    @Operation(summary = "Search employees by name or designation (case-insensitive)")
    public ResponseEntity<ApiResponse<List<Employee>>> searchEmployees(@RequestParam("keyword") String keyword) {
        List<Employee> results = employeeService.searchEmployees(keyword);
        ApiResponse<List<Employee>> response = new ApiResponse<>(true, "Search completed", results);
        return ResponseEntity.ok(response);
    }
}
