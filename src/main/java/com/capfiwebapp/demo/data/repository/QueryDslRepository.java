package com.capfiwebapp.demo.data.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface QueryDslRepository<T> extends QuerydslPredicateExecutor<T> {
}
