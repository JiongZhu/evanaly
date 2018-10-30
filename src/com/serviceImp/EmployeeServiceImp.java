package com.serviceImp;

import com.DAO.EmployeeDAO;
import com.github.pagehelper.PageHelper;
import com.model.Employee;
import com.service.EmployeeService;
import com.util.OtherUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Description:员工服务层实现类
 * @Author:wx6_2
 * @Date:2017/5/20
 **/
@Service
public class EmployeeServiceImp implements EmployeeService {
    @Autowired
    private EmployeeDAO employeeDAO;

    public Employee hasPhone(Employee employee) {
        List<Employee> employees = employeeDAO.select(employee);
        if(employees.size() > 0) {
            return employees.get(0);
        }
        return null;
    }

    public boolean updateByPhone(Employee employee) {
        if(employeeDAO.updateByPhone(employee) > 0)
            return true;
        return false;
    }

    public Map<String, Object> importInfo(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        InputStream input = null;
        Workbook wb = null;
        boolean isE2007 = false;    //判断是否是excel2007格式
        if (fileName.endsWith("xlsx"))
            isE2007 = true;
        try {
            input = file.getInputStream();
            //根据文件格式(2003或者2007)来初始化
            if (isE2007)
                wb = new XSSFWorkbook(input);
            else
                wb = new HSSFWorkbook(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sheet sheet = wb.getSheetAt(0);     //获得第一个表单
        Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器
        rows.next();
        int sum = 0;
        Object  no;
        Object name;
        Object phone;
        Object email;
        Object authority;
        List<Employee> list = new ArrayList<Employee>();
        while (rows.hasNext()) {
            Row row = rows.next();  //获得行数据
            sum++;
            System.out.println("Row #" + row.getRowNum());  //获得行号从0开始
            try{
//                Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器
                no = Double.valueOf(String.valueOf(getVal(row.getCell(0)))).longValue();
                name = String.valueOf(getVal(row.getCell(1)));
                phone = Double.valueOf(String.valueOf(getVal(row.getCell(2)))).longValue();
                email = String.valueOf(getVal(row.getCell(3)));
                authority = Double.valueOf(String.valueOf(getVal(row.getCell(4)))).intValue();
                System.out.println(no+"\t"+name+"\t"+phone+"\t"+email+"\t"+authority);
                if(OtherUtil.validatePhone(phone.toString())&&OtherUtil.validateEmail(email.toString())&&OtherUtil.validateNo(no.toString())){
                    list.add(new Employee(no.toString(), name.toString(), phone.toString(), email.toString(), (Integer) authority));
                }

            }catch (Exception e){
                System.out.println(e);
            }


        }
        int s = 0;
        for (Employee e: list  ) {
            try {
                s+=employeeDAO.insert(e);
            }catch (Exception ex){
                System.out.println(ex);
            }

        }
        System.out.println("总计："+sum+"\t成功："+s);
        Map<String,Object> map = new HashedMap();
        map.put("success",s);
        map.put("total",sum);
        return map;
    }

    private Object getVal(Cell cell) {
        Object o = null;
        switch (cell.getCellType()) {   //根据cell中的类型来输出数据
            case HSSFCell.CELL_TYPE_NUMERIC:
                o = cell.getNumericCellValue();
                break;
            case HSSFCell.CELL_TYPE_STRING:
                o = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                o = cell.getBooleanCellValue();
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                o = cell.getCellFormula();
                break;
            default:
                break;
        }
        return o;
    }

    public Employee login(Employee employee) {
        return employeeDAO.login(employee);
    }

    public List<Employee> pagingQuery(int page, Employee employee) {
        PageHelper.startPage(page, 8);
		List<Employee> employees = employeeDAO.pagingQuery(employee);

		return employees;
    }

    public boolean noValidate(Employee e) {
        List<Employee> employees = employeeDAO.select(e);

        if(employees.size() == 0)
            return true;
        return false;
    }

    public boolean insert(Employee employee) {
        int result = employeeDAO.insert(employee);

        if(result > 0)
            return true;
        return false;
    }
    public boolean update(Employee employee) {
        int result = employeeDAO.update(employee);

        if(result > 0)
            return true;
        return false;
    }

    public boolean delete(List<Employee> employees) {
        int result = employeeDAO.delete(employees);
        if(result > 0)
            return true;
        return false;
    }
}
