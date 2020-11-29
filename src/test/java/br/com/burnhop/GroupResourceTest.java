package br.com.burnhop;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.burnhop.model.dto.CreatedPostDto;
import br.com.burnhop.model.dto.CreatedGroupDto;
import br.com.burnhop.model.dto.AssociatedUserGroupDto;
import br.com.burnhop.model.Login;
import br.com.burnhop.model.Users;
import br.com.burnhop.model.Groups;
import br.com.burnhop.repository.LoginRepository;
import br.com.burnhop.repository.UsersRepository;
import br.com.burnhop.repository.GroupsRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class GroupResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
	private UsersRepository usersRepository;
	
	@Autowired
    private GroupsRepository groupsRepository;

    private CreatedGroupDto makeGroup(int id, String name){

        CreatedGroupDto createdGroupDto = new CreatedGroupDto();

		createdGroupDto.setName(name);
		createdGroupDto.setAdmin(id);

		return createdGroupDto;
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
	void testCreateGroup() throws Exception{
        saveUser("groups");
        int id = getUserId("groups@email.com");

		CreatedGroupDto createdGroupDto = makeGroup(id, "New Group");

		mockMvc.perform(post("/groups")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(createdGroupDto)))
				.andExpect(status().isOk());
			
		mockMvc.perform(post("/groups")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(createdGroupDto)))
				.andExpect(status().isConflict());
	}
	/*
	@Test
	void testAssociateUser() throws Exception{
		saveUser("adminassociateuser");
		Users admin = usersRepository.findByEmail("adminassociateuser@email.com");

		saveUser("associateuser");
		int id = getUserId("associateuser@email.com");

		Groups group = new Groups("AssociateUser", new Timestamp(System.currentTimeMillis()));
		group.setAdmin(admin);
		groupsRepository.save(group);
		group = groupsRepository.findByName("AssociateUser");
		int id_group = group.getId();

		AssociatedUserGroupDto associatedUserGroupDto = new AssociatedUserGroupDto();
		associatedUserGroupDto.setGroupId(id_group);
		associatedUserGroupDto.setUserId(id);

		mockMvc.perform(post("/groups/user")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(associatedUserGroupDto)))
				.andExpect(status().isOk());
	}
    
    @Test
    void testGetAllGroupsByUser() throws Exception{
        
    }*/
}