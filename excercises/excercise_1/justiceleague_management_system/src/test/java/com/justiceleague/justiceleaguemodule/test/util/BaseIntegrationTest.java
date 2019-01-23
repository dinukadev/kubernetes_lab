package com.justiceleague.justiceleaguemodule.test.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * This class will have functionality required when running integration tests so
 * that invidivual classes do not need to implement the same functionality.
 *
 * @author dinuka
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    protected ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
    }


}
