package com.filk.dao.jdbc;

import com.filk.dao.CountryDao;
import com.filk.entity.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:root-context.xml"})
public class JdbcCountryDaoTest {

    @Autowired
    private CountryDao countryDao;

    @Test
    public void getByMovieId() {
        List<Country> countries = countryDao.getByMovieId(6);

        assertNotNull(countries);
        assertEquals(2, countries.size());
        assertEquals(1, countries.get(0).getId());
        assertEquals("США", countries.get(0).getName());
        assertEquals(3, countries.get(1).getId());
        assertEquals("Великобритания", countries.get(1).getName());
    }
}