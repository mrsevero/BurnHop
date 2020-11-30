package br.com.burnhop;

import java.sql.Date;
import java.sql.Timestamp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;


import br.com.burnhop.model.dto.CreatedLoginDto;
import br.com.burnhop.model.dto.CreatedUserDto;
import br.com.burnhop.model.dto.UpdatedUserDto;
import br.com.burnhop.model.Login;
import br.com.burnhop.model.Users;
import br.com.burnhop.repository.LoginRepository;
import br.com.burnhop.repository.UsersRepository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UsersResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private UsersRepository usersRepository;


    private CreatedUserDto makeUser(){
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
		createdUserDto.setDataNasc(data_nasc);

		return createdUserDto;
	}

	
    private CreatedUserDto makeUserLogin(){
		String name = "Test";
		String username = "test";
		String data_nasc = "2000-01-01";
		String email = "login@test.com";
		String password = "12345";
	
		CreatedUserDto createdUserDto = new CreatedUserDto();
		CreatedLoginDto createdLoginDto = new CreatedLoginDto();

		createdLoginDto.setEmail(email);
		createdLoginDto.setPassword(password);

		createdUserDto.setLogin(createdLoginDto);
		createdUserDto.setName(name);
		createdUserDto.setUsername(username);
		createdUserDto.setDataNasc(data_nasc);

		return createdUserDto;
	}

	private void saveUser(String name){
		Date data_nasc = Date.valueOf("2000-01-01");
		Timestamp created_on = new Timestamp(System.currentTimeMillis());
	
		Login login = new Login(name+"@email.com", "12345");
		Users newUser = new Users(name, name, data_nasc, created_on);
		newUser.setLogin(login);
		
		loginRepository.save(newUser.getLogin());
		usersRepository.save(newUser);
	}

	private int getUserId(String email){
		Users user = usersRepository.findByEmail(email);
		return user.getId();
	}

    @Test
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
	void testDeleteUser() throws Exception{
		saveUser("deleteuser");
		String id = String.valueOf(getUserId("deleteuser@email.com"));
		String notFoundId = "111111";

		mockMvc.perform(delete("/users")
				.queryParam("id", id))
				.andExpect(status().isOk());

		mockMvc.perform(delete("/users")
				.queryParam("id", notFoundId))
				.andExpect(status().isNotFound());

	}
  	
	@Test
    void testGetUserByEmail() throws Exception{
		saveUser("getuserbyemail");

		mockMvc.perform(get("/users/email/{email}", "getuserbyemail@email.com"))
                .andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	void testGetAllRequest() throws Exception{
		saveUser("getalluser");

		mockMvc.perform(get("/users/get-all"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	void testGetUserById() throws Exception{
		saveUser("getuserbyid");
		String id = String.valueOf(getUserId("getuserbyid@email.com"));
		int notFoundId = 1111;

		mockMvc.perform(get("/users/id/{id}", id))
                .andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
				
		mockMvc.perform(get("/users/id/{id}", notFoundId))
                .andExpect(status().isNotFound());
	}


	
	@Test
	void testSetImage() throws Exception{
		String image_path = "example/example.jpg";
		saveUser("setImage");
		String id = String.valueOf(getUserId("setImage@email.com"));
		String notFoundId = "1111111";

		mockMvc.perform(put("/users/image")
				.queryParam("id", id)
				.queryParam("imagePath", image_path))
                .andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));

		mockMvc.perform(put("/users/image")
				.queryParam("id", notFoundId)
				.queryParam("imagePath", image_path))
                .andExpect(status().isNotFound());
	}

	
	
	@Test
	void testLoginRequest() throws Exception{
		String email = "login@test.com";
		String password = "12345";
		String wrongPassword = "123";
		CreatedUserDto createdUserDto = makeUserLogin();

		mockMvc.perform(post("/users")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(createdUserDto)))
				.andExpect(status().isOk());

		mockMvc.perform(post("/users/login")
				.header("email", email)
				.header("password", password))
				.andExpect(status().isOk());
		
		mockMvc.perform(post("/users/login")
				.header("email", email)
				.header("password", wrongPassword))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void testUpdateuser() throws Exception{
		saveUser("updateUser");
		String id = String.valueOf(getUserId("updateUser@email.com"));
		String notFoundId = "111111";
		UpdatedUserDto updatedUserDto = new UpdatedUserDto();

		updatedUserDto.setEmail("updatedUser@email.com");
		updatedUserDto.setDataNasc("2000-01-01");
		updatedUserDto.setName("updatedUser");
		updatedUserDto.setUsername("updatedUser");

		mockMvc.perform(put("/users/update/{id}", id)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(updatedUserDto)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));

		mockMvc.perform(put("/users/update/{id}", notFoundId)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(updatedUserDto)))
				.andExpect(status().isNotFound());

	}
}