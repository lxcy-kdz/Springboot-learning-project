package com.yonyougov.elasticsearch.repository;

import com.yonyougov.elasticsearch.model.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author kongdezhi
 */
public interface IEmployeeEsRepository extends ElasticsearchRepository<Employee,String> {
}
