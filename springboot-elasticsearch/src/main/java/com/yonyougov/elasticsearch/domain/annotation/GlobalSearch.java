package com.yonyougov.elasticsearch.domain.annotation;

import java.lang.annotation.*;

/**
 * @author kongdezhi
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GlobalSearch {

    boolean isGlobalSearch() default true;

    boolean isHighlightDisplay() default true;
}
