package com.yonyougov.elasticsearch.service;

import com.yonyougov.elasticsearch.model.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author kongdezhi
 */
public interface IEmployeeService {

    /**
     * 索引一个员工文档
     *
     * @param employee 员工
     * @return 索引后的员工文档对象
     */
    Employee indexEmployee(Employee employee);


    /**
     * 条件查询员工
     *
     * @param condition 条件
     * @param page      当前页
     * @param size      每页显示数量
     * @return 结果集
     */
    Page<Employee> searchEmployee(String highlightColor,String condition, int page, int size);
}
