package com.test;

import com.DAO.EmployeeDAO;
import com.DAO.PassengerFlowDAO;
import com.github.pagehelper.PageHelper;
import com.model.Employee;
import com.model.PassengerFlow;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.util.OtherUtil;

import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Test7 {
	static ApplicationContext ctx = new ClassPathXmlApplicationContext(
			"spring-*.xml");
	static EmployeeDAO employeeDAO = (EmployeeDAO) ctx.getBean("employeeDAO");
	public static void main(String[] args) {
		File f = new File("F:\\下载\\员工导入样表.xlsx");
		InputStream input = null;
		Workbook wb = null;
		boolean isE2007 = false; // 判断是否是excel2007格式
		if (f.getName().endsWith("xlsx"))
			isE2007 = true;
		try {
			input = new FileInputStream(f);
			// 根据文件格式(2003或者2007)来初始化
			if (isE2007)
				wb = new XSSFWorkbook(input);
			else
				wb = new HSSFWorkbook(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Sheet sheet = wb.getSheetAt(0); // 获得第一个表单
		Iterator<Row> rows = sheet.rowIterator(); // 获得第一个表单的迭代器
		rows.next();
		rows.next();
		int sum = 0;
		Object no;
		Object name;
		Object phone;
		Object email;
		Object authority;
		List<Employee> list = new ArrayList<Employee>();
		while (rows.hasNext()) {
			Row row = rows.next(); // 获得行数据
			sum++;
			System.out.println("Row #" + row.getRowNum()); // 获得行号从0开始
			try {
				// Iterator<Cell> cells = row.cellIterator(); //获得第一行的迭代器
				no = Double.valueOf(String.valueOf(getVal(row.getCell(0))))
						.longValue();
				name = String.valueOf(getVal(row.getCell(1)));
				phone = Double.valueOf(String.valueOf(getVal(row.getCell(2))))
						.longValue();
				email = String.valueOf(getVal(row.getCell(3)));
				authority = Double.valueOf(
						String.valueOf(getVal(row.getCell(4)))).intValue();
				System.out.println(no + "\t" + name + "\t" + phone + "\t"
						+ email + "\t" + authority);
				if (OtherUtil.validatePhone(phone.toString())
						&& OtherUtil.validateEmail(email.toString())
						&& OtherUtil.validateNo(no.toString())) {
					list.add(new Employee(no.toString(), name.toString(),
									phone.toString(), email.toString(),
									(Integer) authority));
				}

			} catch (Exception e) {
				System.out.println(e);
			}

		}
		int s = 0;
//		for (Employee e : list) {
//			try {
//				s += employeeDAO.insert(e);
//			} catch (Exception ex) {
//				System.out.println(ex);
//			}
//
//		}
		s += employeeDAO.insertBatch(list);
//		SqlSession session = (SqlSession) ctx.getBean("sqlSessionBatch");
		System.out.println("总计：" + sum + "\t成功：" + s);
		Map<String, Object> map = new HashedMap();
		map.put("success", s);
		map.put("total", sum);
		
	}
	static Object getVal(Cell cell) {
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
}





