package com.test;

import com.DAO.CityDAO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test5 {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context-mybatis.xml");
		SqlSessionFactory factory = (SqlSessionFactory) ctx.getBean("sessionFactory");
		SqlSession session = factory.openSession();
		CityDAO cityDAO = session.getMapper(CityDAO.class);

//		EventDAO eventDAO = session.getMapper(EventDAO.class);
//		Map<String, Object> map = new HashedMap();
//		map.put("name", "北京");
//		List<GroupBy> result = eventDAO.groupbySponsorLevel(map);
//		System.out.println(result.size());
//		for (GroupBy item : result) {
//			System.out.println(item.getType() + "\t" + item.getCount());
//		}

//		String ps = "123456";
//		ps = MD5Util.convertMD5(ps);
//		System.out.println(ps);
//		ps = MD5Util.convertMD5(ps);
//		System.out.println(ps);


//		WeatherURLDAO weatherURLDAO = session.getMapper(WeatherURLDAO.class);
//		List<WeatherURL> weatherURLS = weatherURLDAO.query("北京");
//		System.out.println(weatherURLS.size());

//		EmployeeDAO employeeDAO = session.getMapper(EmployeeDAO.class);
//		WeatherDAO weatherDAO = session.getMapper(WeatherDAO.class);
//		CityDAO cityDAO = session.getMapper(CityDAO.class);
//		EmployeeDAO employeeDAO = session.getMapper(EmployeeDAO.class);
//		List<Employee> employees = new ArrayList<Employee>();
//		Employee e1 = new Employee();
//		e1.setId(7);
//		e1.setName("王旭");
//		Employee e2 = new Employee();
//		e2.setId(4);
//		employees.add(e1);
//		employees.add(e2);
//		System.out.println(employeeDAO.login(e1));
//
//		PageHelper.startPage(1, 10);
//		employees = employeeDAO.pagingQuery(e1);
//		System.out.println(JSON.toJSONString(employees));
//		List<City> cities = cityDAO.getAllCity();
//		System.out.println(JSON.toJSONString(cities));
//		System.out.println(cities.get(0).getName());
//		System.out.println(JSON.toJSONString((Page) cities));
//
//		Weather weather = new Weather();
//		City c = new City();
//		c.setName("常州");
//		weather.setCity(c);
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		try {
//			weather.setDate(dateFormat.parse("2017-5-22")
//            );
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		List<Weather> weathers = weatherDAO.searchFiveDay(weather);
//
//		for(Weather w : weathers) {
//			System.out.println(w.getMaxTem());
		}

//		Employee e = new Employee();
//		e.setOnduty(true);
//		List<Employee> list =  employeeDAO.select(e);
//
//		for(Employee employee : list) {
//			System.out.println(JSON.toJSONString(employee));
//		}
//
//		System.out.println(JSON.toJSONString(list));

//		EventDAO eventDAO = session.getMapper(EventDAO.class);
//		List<Event> events = eventDAO.searchOfMonth();
//		for(Event ev : events) {
//			System.out.println(ev.getCity().getName());
//		}


}
