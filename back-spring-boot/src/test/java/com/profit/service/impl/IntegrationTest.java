package com.profit.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profit.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestGatewaySupport;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.nio.file.Files;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
public class IntegrationTest {

    private static final String URL = "/exchange/v1/calculate";
    private static final String DEFAULT_CHARSET = "Windows-1251";


    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;




    @Before
    public final void before() {
        if (this.mvc == null) {
            initializeMvc();
        }
        RestGatewaySupport gateway = new RestGatewaySupport();
        gateway.setRestTemplate(restTemplate);
    }

    private void initializeMvc() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                  .alwaysDo(print())
                                  .build();
    }

    @Test
    public void testSuccess() throws Exception {
        MvcResult result = mvc.perform(get(URL)
                .param("buyDate", "2001-11-10")
                .param("amount", "10"))
                .andExpect(status().isOk()).andReturn();
    }
}
