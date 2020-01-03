package com.yonyougov.elasticsearch.service.impl;

import com.yonyougov.elasticsearch.domain.annotation.GlobalSearch;
import com.yonyougov.elasticsearch.model.Employee;
import com.yonyougov.elasticsearch.repository.IEmployeeEsRepository;
import com.yonyougov.elasticsearch.service.IEmployeeService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author kongdezhi
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private IEmployeeEsRepository employeeEsRepository;

    private ElasticsearchTemplate elasticsearchTemplate;

    public EmployeeServiceImpl(IEmployeeEsRepository employeeEsRepository, ElasticsearchTemplate elasticsearchTemplate) {
        this.employeeEsRepository = employeeEsRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Employee indexEmployee(Employee employee) {
        return employeeEsRepository.save(employee);
    }

    @Override
    public Page<Employee> searchEmployee(String highlightColorString, String condition, int page, int size) {
        HighlightBuilder.Field nameField = new HighlightBuilder
                .Field("*")
                .preTags("<span style='color:" + highlightColorString + "'>")
                .postTags("</span>").requireFieldMatch(false);
        Class employeeClazz = Employee.class;
        Field[] fields = employeeClazz.getDeclaredFields();
        String[] queryFields = Stream.of(fields).filter(field ->
                Optional.ofNullable(field.getAnnotation(GlobalSearch.class)).isPresent() ? field.getAnnotation(GlobalSearch.class).isGlobalSearch() : false
        ).map(field -> field.getName()).toArray(String[]::new);
        //多字段查询，可同时在name和description查询 对应实体类中的属性名
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(condition, queryFields))
                .withPageable(PageRequest.of(page - 1, size))
                .withHighlightFields(nameField)
                .build();
        AggregatedPage<Employee> employees = elasticsearchTemplate.queryForPage(nativeSearchQuery, Employee.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                List<Employee> result = new ArrayList<>();
                SearchHits hits = searchResponse.getHits();
                if (hits.getHits().length <= 0) {
                    return new AggregatedPageImpl<>((new ArrayList<T>()));
                }
                for (SearchHit searchHit : hits) {
                    Employee employee = new Employee();
                    //设置ID
                    employee.setId(searchHit.getId());
                    try {
                        for (Field field : fields) {
                            if (field.getName().equals("serialVersionUID")){
                                continue;
                            }
                            if (field.getName().equals("id")) {
                                //设置ID
                                employee.setId(searchHit.getId());
                                continue;
                            }
                            GlobalSearch globalSearchAnnotation = field.getAnnotation(GlobalSearch.class);
                            field.setAccessible(true);
                            if (globalSearchAnnotation != null) {
                                if (globalSearchAnnotation.isHighlightDisplay()) {
                                    //设置高亮的name
                                    HighlightField nameHighlight = searchHit.getHighlightFields().get(field.getName());
                                    if (nameHighlight != null) {
                                        Class<?> type = field.getType();
                                        if(type.equals(Integer.class)){
                                            field.set(employee, Integer.valueOf(nameHighlight.fragments()[0].toString()));
                                        }else {
                                            field.set(employee, nameHighlight.fragments()[0]);
                                        }
                                    } else {
                                        //没有高亮的name
                                        field.set(employee, searchHit.getSourceAsMap().get(field.getName()));
                                    }
                                } else {
                                    field.set(employee, searchHit.getSourceAsMap().get(field.getName()));
                                }
                            } else {
                                field.set(employee, searchHit.getSourceAsMap().get(field.getName()));
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    result.add(employee);
                }
                if (result.size() > 0) {
                    return new AggregatedPageImpl<>((List<T>) result);
                }
                return new AggregatedPageImpl<>((new ArrayList<T>()));
            }
        });
        return employees;
    }
}
