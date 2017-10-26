package com.serpstat.restapi.dao.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

import com.serpstat.restapi.configuration.HibernateTestConfiguration;

@ContextConfiguration(classes = { HibernateTestConfiguration.class })
public abstract class EntityPopulatedDataDaoImplTest extends AbstractTransactionalTestNGSpringContextTests {
	@Autowired
	DataSource dataSource;

}
