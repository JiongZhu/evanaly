package com.DAO;

import com.model.Employee;

import java.util.List;

/**
 * Created by wx6_2 on 2017/5/19.
 */
public interface EmployeeDAO {
    List<Employee> select(Employee e);
    List<Employee> pagingQuery(Employee employee);
    Employee login(Employee e);
    int insert(Employee e);
    int insertBatch(List<Employee> list);
    int update(Employee e);
    int delete(List<Employee> e);
    int updateByPhone(Employee employee);
}
