package com.filk.dao.jdbc;

import com.filk.dao.ReviewDao;
import com.filk.entity.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:root-context.xml"})
public class JdbcReviewDaoTest {

    @Autowired
    private ReviewDao reviewDao;

    @Test
    public void getByMovieId() {
        List<Review> reviews = reviewDao.getByMovieId(5);

        assertNotNull(reviews);
        assertEquals(2, reviews.size());
        assertEquals(6, reviews.get(0).getId());
        assertEquals("У меня не найдётся слов, чтобы описать этот фильм. Не хочется быть банальной и говорить " +
                "какой он отличный, непредсказуемый и т. д., но от этого никуда не деться к сожалению — ведь он ШЕДЕВРАЛЬНЫЙ!",
                reviews.get(0).getText());

        assertNotNull(reviews.get(0).getUser());
        assertEquals(9, reviews.get(0).getUser().getId());
        assertEquals("Джесси Паттерсон", reviews.get(0).getUser().getName());
    }
}