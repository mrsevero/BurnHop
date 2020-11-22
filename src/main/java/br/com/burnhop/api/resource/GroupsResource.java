package br.com.burnhop.api.resource;

import br.com.burnhop.model.dto.CreatedGroupDto;
import br.com.burnhop.model.dto.GroupDto;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import br.com.burnhop.repository.GroupsRepository;
import br.com.burnhop.repository.LoginRepository;
import br.com.burnhop.repository.PostsRepository;
import br.com.burnhop.repository.UsersRepository;
import br.com.burnhop.api.controller.GroupsController;
import br.com.burnhop.api.controller.UserController;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@CrossOrigin("*")
@RequestMapping("/groups")
public class GroupsResource {

    GroupsController groupsController;
    UserController userController;

    public GroupsResource(GroupsRepository groupsRepository, UsersRepository usersRepository, LoginRepository loginRepository, PostsRepository postsRepository){
        this.groupsController = new GroupsController(groupsRepository, usersRepository);
        this.userController = new UserController(loginRepository, usersRepository, postsRepository);
    }

    @PostMapping()
    @ApiOperation(value = "Criar um novo grupo", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Grupo criado com sucesso"),
            @ApiResponse(code = 409, message = "Grupo com esse nome já está cadastrado"),
            @ApiResponse(code = 500, message = "Ocorreu um erro para processar a requisição")
    })
    public ResponseEntity<GroupDto> createGroup(@RequestBody CreatedGroupDto newGroup) throws NoSuchAlgorithmException {

        try {
            GroupDto group = groupsController.createGroup(newGroup);
            if (group == null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(group, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-all")
    @ApiOperation(value = "Todos os Grupos", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Grupos retornados com sucesso"),
            @ApiResponse(code = 204, message = "Não existe nenhum grupo registrado"),
            @ApiResponse(code = 500, message = "Ocorreu um erro para processar a requisição")
    })
    public ResponseEntity<ArrayList<GroupDto>> getAllGroups(){

        try {
            ArrayList<GroupDto> groups = groupsController.getAllGroups();

            if(groups.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(groups, HttpStatus.OK);

        } catch (IllegalAccessError e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/id/{id}")
    @ApiOperation(value = "Retorna grupo baseado no id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Grupo com id"),
            @ApiResponse(code = 404, message = "Grupo com id não encontrado"),
            @ApiResponse(code = 500, message = "Ocorreu um erro para processar a requisição")
    })
    public ResponseEntity<GroupDto> getGroupById(
            @PathVariable(value = "id") int id) {

        try {
            GroupDto group = groupsController.getGroupById(id);

            if (group == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(group, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping()
    @ApiOperation(value = "Deleta grupo informado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Grupo deletado com sucesso"),
            @ApiResponse(code = 404, message = "Nenhum Grupo foi encontrado"),
            @ApiResponse(code = 500, message = "Ocorreu um erro para processar a requisição")
    })
    public ResponseEntity<String> deleteGroup(
            @RequestParam int id) {

        try {
            GroupDto group = groupsController.getGroupById(id);

            if(group == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            boolean deleted = groupsController.deleteGroup(id);

            if(deleted)
                return new ResponseEntity<>("Grupo deletado com sucesso", HttpStatus.OK);

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
