package br.com.burnhop;

import java.sql.Date;
import java.sql.Timestamp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.burnhop.model.dto.CreatedPostDto;
import br.com.burnhop.model.Login;
import br.com.burnhop.model.Users;
import br.com.burnhop.model.Posts;
import br.com.burnhop.model.Content;
import br.com.burnhop.repository.LoginRepository;
import br.com.burnhop.repository.UsersRepository;
import br.com.burnhop.repository.PostsRepository;
import br.com.burnhop.repository.ContentRepository;

import org.junit.jupiter.api.Test;
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
class PostsResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private PostsRepository postsRepository;
	
	@Autowired
    private ContentRepository contentRepository;

	private void saveUser(String name){
		Date data_nasc = Date.valueOf("2000-01-01");
		Timestamp created_on = new Timestamp(System.currentTimeMillis());
	
		Login login = new Login(name+"@email.com", "12345");
		Users newUser = new Users(name, name, data_nasc, created_on);
		newUser.setLogin(login);
		
		loginRepository.save(newUser.getLogin());
		usersRepository.save(newUser);
    }

    private CreatedPostDto makePost(String email){
	
		CreatedPostDto createdPostDto = new CreatedPostDto();
        String texto = "texto de postagem";
        
		createdPostDto.setUserEmail(email);
		createdPostDto.setTexto(texto);

		return createdPostDto;
	}

    @Test
    void testPosting() throws Exception{
        saveUser("posting");
        CreatedPostDto createdPostDto = makePost("posting@email.com");

		mockMvc.perform(post("/posts")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(createdPostDto)))
                .andExpect(status().isOk());
    }

    
	@Test
	void testGetAllPostRequest() throws Exception{
		saveUser("getallposts");
		Users users = usersRepository.findByEmail("getallposts@email.com");

		Content content = new Content();
		content.setText("Getting all posts");

		Posts post = new Posts();
		post.setContent(content);
		post.setPostedOn(new Timestamp(System.currentTimeMillis()));
		post.setUsers(users);

		contentRepository.save(content);
        postsRepository.save(post);

		mockMvc.perform(get("/posts/get-all"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	void testGetPostsByUsers() throws Exception{
		saveUser("getpostbyusers");
		Users users = usersRepository.findByEmail("getpostbyusers@email.com");
		int id = users.getId();;

		Content content = new Content();
		content.setText("Getting posts by user");

		Posts post = new Posts();
		post.setContent(content);
		post.setPostedOn(new Timestamp(System.currentTimeMillis()));
		post.setUsers(users);

		contentRepository.save(content);
		postsRepository.save(post);
		
		mockMvc.perform(get("/posts/user/{id}", id))
                .andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
}
