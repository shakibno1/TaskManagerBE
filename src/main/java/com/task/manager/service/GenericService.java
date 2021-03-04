package com.task.manager.service;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface GenericService<T extends Object> {
	@Transactional(rollbackFor = Exception.class , propagation = Propagation.REQUIRED ,isolation =Isolation.DEFAULT)
	T save(T entity);
	@Transactional(rollbackFor = Exception.class , propagation = Propagation.REQUIRED ,isolation =Isolation.DEFAULT)
    T update(T entity);
  
    void delete(T entity);
  
    void delete(Long id);
    
    void deleteInBatch(List<T> entities);
    
    T find(Long id);
  
    List<T> findAll();
}
