package com.serviceImp;

import com.DAO.EventDAO;
import com.DAO.PassengerFlowDAO;
import com.github.pagehelper.PageHelper;
import com.model.EventSf;
import com.model.PassengerFlow;
import com.model.Route;
import com.service.PassengerFlowService;
import com.util.DateUtil;
import com.util.RouteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Service
public class PassengerFlowServiceImp implements PassengerFlowService {
	@Autowired
	private PassengerFlowDAO passengerFlowDAO;
	@Autowired
	private EventDAO eventDAO;
	/**
	 * 计算客流量业务操作
	 */
	public void caclPassengerFlow() {
		//按地区 时间分组 查找不同事件的人流影响
		List<EventSf> eList = eventDAO.searchEventSf(new Date(System.currentTimeMillis()-24L*3600*1000*7));
		int size = eList.size();
		if(size==0)
			return;
		
		//根据时间 地点计算影响人流量
		EventSf e  = eList.get(0);
		Date date = e.getDate();
		PassengerFlow pf = new PassengerFlow();
		pf.setDate(date);
		addPnums(pf, e);
		int city = e.getCity();
		for (int i = 1; i <size; i++) {
			e = eList.get(i);
			date = e.getDate();
			//如果是同一天同一地点突然 累加人数
			if(pf.getDate().equals(date)&&city == e.getCity()){
				addPnums(pf,e);//累加人数
				continue;
			}
			//保存
			doInsert(pf,city);
			//创建新的对象
			pf = new PassengerFlow();
			pf.setDate(date);
			addPnums(pf, e);
			city = e.getCity();
		}
		
		
	}

	public List<PassengerFlow> searchPF(Map<String, Object> map) {
		int page = (Integer) map.get("page");
		PageHelper.startPage(page, 8);
		List<PassengerFlow> list = passengerFlowDAO.search(map);
		return list;
	}

	public PassengerFlow initPFChart(Map<String, Object> map) {
		PassengerFlow passengerFlow = passengerFlowDAO.groupByType(map);
		return passengerFlow;
	}

	//保存到数据库
	private void doInsert(PassengerFlow pf ,int city) {
		List<Route> routes = RouteUtil.getRouteList(city); 
		if(routes==null)
			return;
		PassengerFlow pfTmp ;
		int size = routes.size();
		Date d = DateUtil.getOffsetDate(pf.getDate(), -1);
		Route r;
		//根据比例分散到各个路线上
		for (int i = 0; i < size; i++) {
			r = routes.get(i);
			pfTmp = new PassengerFlow();
			pfTmp.setDate(d);
			pfTmp.setShowPnums((int)(pf.getShowPnums()*r.getWeight()));
			pfTmp.setConcertPnums((int)(pf.getConcertPnums()*r.getWeight()));
			pfTmp.setSportPnums((int)(pf.getSportPnums()*r.getWeight()));
			pfTmp.setPolityPnums((int)(pf.getPolityPnums()*r.getWeight()));
			pfTmp.setRoute(r);
			pfTmp.setTotal(pfTmp.getShowPnums()+pfTmp.getConcertPnums()+pfTmp.getSportPnums()+pfTmp.getPolityPnums());
			//如果存在,更新
			if(passengerFlowDAO.searchByRouteDate(pfTmp).size()>0)
				passengerFlowDAO.updateByRouteDate(pfTmp);
			else
				passengerFlowDAO.insert(pfTmp);
		}
	}
	/**
	 * 根据e中事件类型 增加pf中对应影响人数
	 */
	private void addPnums(PassengerFlow pf, EventSf e) {
		if(e.getType()==1)//展会
			pf.setShowPnums(pf.getShowPnums()+e.getPnums());
		else if(e.getType()==2)//演唱会
			pf.setConcertPnums(pf.getConcertPnums()+e.getPnums());
		else if(e.getType()==3)//体育赛事
			pf.setSportPnums(pf.getSportPnums()+e.getPnums());
		else//政治事件
			pf.setPolityPnums(pf.getPolityPnums()+e.getPnums());
	}
	
	
}
