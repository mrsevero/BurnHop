package br.com.burnhop;

import java.sql.Date;
import java.sql.Timestamp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.burnhop.model.Users;
import br.com.burnhop.model.Login;
import br.com.burnhop.model.Dto.CreatedLoginDto;
import br.com.burnhop.model.Dto.CreatedUserDto;
import br.com.burnhop.model.Dto.UserDto;
import br.com.burnhop.api.resource.UsersResource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;


import org.springframework.boot.test.mock.mockito.MockBean;

import org.mockito.MockitoAnnotations;
import org.mockito.Mock;


import br.com.burnhop.repository.LoginRepository;
import br.com.burnhop.repository.UsersRepository;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import br.com.burnhop.model.Dto.CreatedPostDto;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
class UsersResourceTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    
	CreatedUserDto makeUser(){
		String name = "Test";
		String username = "test";
		String data_nasc = "2000-01-01";
		String email = "test1@test.com";
		String password = "12345";
	
		CreatedUserDto createdUserDto = new CreatedUserDto();
		CreatedLoginDto createdLoginDto = new CreatedLoginDto();

		createdLoginDto.setEmail(email);
		createdLoginDto.setPassword(password);

		createdUserDto.setLogin(createdLoginDto);
		createdUserDto.setName(name);
		createdUserDto.setUsername(username);
		createdUserDto.setData_nasc(data_nasc);

		return createdUserDto;
	}
	
	
	@Test
	@Order(1)
	void testCreateUserRequest() throws Exception{

		CreatedUserDto createdUserDto = makeUser();

		mockMvc.perform(post("/users")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(createdUserDto)))
				.andExpect(status().isOk());
			
		mockMvc.perform(post("/users")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(createdUserDto)))
				.andExpect(status().isConflict());
	}
    
	
	@Test
	@Order(2)
    void testGetUserByEmail() throws Exception{
        String email = "test1@test.com";

		mockMvc.perform(get("/users/email/{email}", email))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

	@Test
	@Order(3)
	void testLoginRequest() throws Exception{

		CreatedUserDto createdUserDto = makeUser();

		String email = createdUserDto.getLogin().getEmail();
		String password = createdUserDto.getLogin().getPassword();;
		String wrongPassword = "123";

		mockMvc.perform(post("/users/login")
				.header("email", "test1@test.com")
				.header("password", "12345"))
				.andExpect(status().isOk());

		mockMvc.perform(post("/users/login")
				.header("email", email)
				.header("password", wrongPassword))
				.andExpect(status().isUnauthorized());	
	}
	
	@Test
    @Order(4)
    void testGetAllPostsWithPosts() throws Exception{
        mockMvc.perform(get("/posts/get-all"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(5)
    void testPostingRequest() throws Exception{
        String texto = "Posting";
        String user_email = "test1@test.com";
        
        CreatedPostDto createdPostDto = new CreatedPostDto();

        createdPostDto.setTexto(texto);
        createdPostDto.setUser_email(user_email);

		mockMvc.perform(post("/posts")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(createdPostDto)))
				.andExpect(status().isOk());
    }


    @Test
    @Order(6)
    void testGetAllPosts() throws Exception{
        mockMvc.perform(get("/posts/get-all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
}