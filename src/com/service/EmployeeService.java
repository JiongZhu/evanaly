package com.service;

import com.model.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Created by wx6_2 on 2017/5/20.
 */
public interface EmployeeService {
    Employee login(Employee employee);
    List<Employee> pagingQuery(int page, Employee employee);
    boolean noValidate(Employee e);
    boolean insert(Employee employee);
    boolean update(Employee employee);
    boolean delete(List<Employee> employees);
    Employee hasPhone(Employee employee);
    boolean updateByPhone(Employee employee);
    Map<String,Object> importInfo(MultipartFile file);
}
