package com.yonyougov.elasticsearch.model;

import com.yonyougov.elasticsearch.constants.IndexConstants;
import com.yonyougov.elasticsearch.domain.annotation.GlobalSearch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.List;

/**
 * 员工索引实体
 *
 * @author kongdezhi
 */
@Data
@Builder
@Document(indexName = IndexConstants.EMPLOYEE_INDEX_NAME, type = IndexConstants.EMPLOYEE_INDEX_TYPE)
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable {

    private static final long serialVersionUID = -3760353265783896518L;

    @Id
    private String id;

    @GlobalSearch
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String firstName;

    @GlobalSearch
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String lastName;

    @Field(type = FieldType.Keyword)
    private Integer age;

    @GlobalSearch
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String about;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private List<String> interests;

}
