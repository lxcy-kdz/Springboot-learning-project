package com.yonyougov.elasticsearch.controller;

import com.yonyougov.elasticsearch.model.Employee;
import com.yonyougov.elasticsearch.service.IEmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author kongdezhi
 */
@RestController
@RequestMapping("employee")
public class EmployeeController {

    private IEmployeeService employeeService;

    public EmployeeController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/index/document")
    public Employee indexEmployee(@RequestBody Employee employee) {
        return employeeService.indexEmployee(employee);
    }

    @GetMapping("/search/{highlightColor}/{condition}/{page}/{size}")
    public Page<Employee> searchEmployee(@PathVariable String highlightColor, @PathVariable String condition, @PathVariable Integer page, @PathVariable Integer size) {
        return employeeService.searchEmployee(highlightColor, condition, page, size);
    }

}
