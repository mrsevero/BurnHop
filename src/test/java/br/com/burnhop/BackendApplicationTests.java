package br.com.burnhop;

import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;


import br.com.burnhop.model.Users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BackendApplicationTests {

	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

	@Test
	void contextLoads() {
	}

	/*
	void case1() throws Exception{
		String name = "Test1";
		String username = "test1";
		Date data_nasc = new Timestamp(System.currentTimeMillis());
		Timestamp created_on = Date.valueOf("01-01-2000");

		Users user = new Users(name, username, data_nasc, created_on);

		mockMvc.perform(post("/bookings")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isOk());
	}*/

	@Test
	void case2() throws Exception{
		String email = "xuao@bol.com";

		mockMvc.perform(get("/users/{id}", email))
                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

}
