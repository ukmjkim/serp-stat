package com.serpstat.restapi.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.serpstat.restapi.dao.AbstractDao;
import com.serpstat.restapi.dao.DeviceDao;
import com.serpstat.restapi.model.Device;

@Repository("deviceDao")
public class DeviceDaoImpl extends AbstractDao<Integer, Device> implements DeviceDao {
	public Device findById(int id) {
		return getByKey(id);
	}

	@SuppressWarnings("unchecked")
	public List<Device> findAll() {
		Criteria criteria = createEntityCriteria();
		criteria.addOrder(Order.asc("name"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<Device>) criteria.list();
	}

	public void save(Device device) {
		persist(device);
	}

	public void deleteById(int id) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", id));
		Device device = (Device) criteria.uniqueResult();
		delete(device);
	}
}
