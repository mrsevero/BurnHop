package br.com.burnhop.api.resource;

import br.com.burnhop.model.dto.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import br.com.burnhop.api.controller.GroupsController;
import br.com.burnhop.api.controller.UserController;
import br.com.burnhop.repository.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    public GroupsResource(GroupsRepository groupsRepository,
                          UsersRepository usersRepository,
                          UsersGroupsRepository usersGroupsRepository,
                          LoginRepository loginRepository,
                          PostsRepository postsRepository){
        this.groupsController = new GroupsController(groupsRepository, usersRepository, usersGroupsRepository);
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

    @PostMapping("/user")
    @ApiOperation(value = "Associar usuário a um grupo", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário associado com sucesso"),
            @ApiResponse(code = 404, message = "Id do usuário ou grupo inválido"),
            @ApiResponse(code = 409, message = "Usuário já está associado ao grupo"),
            @ApiResponse(code = 500, message = "Ocorreu um erro para processar a requisição")
    })
    public ResponseEntity<UsersGroupsDto> associateUser(@RequestBody AssociatedUserGroupDto userAssociated) throws NoSuchAlgorithmException {

        try {
            UserDto user = userController.getUserById(userAssociated.getUserId());

            if(user == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            GroupDto group = groupsController.getGroupById(userAssociated.getGroupId());

            if (group == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            UsersGroupsDto groupAssociated = groupsController.associateUserToGroup(userAssociated);

            if(groupAssociated == null)
                return new ResponseEntity<>(HttpStatus.CONFLICT);

            return new ResponseEntity<>(groupAssociated, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
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

    @GetMapping("/user/{id}")
    @ApiOperation(value = "Todos os Grupos relacionados ao Usuário", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Grupos retornados com sucesso"),
            @ApiResponse(code = 204, message = "Não existe nenhum grupo relacionado ao usuário"),
            @ApiResponse(code = 404, message = "Não existe nenhum usuário com id informado"),
            @ApiResponse(code = 500, message = "Ocorreu um erro para processar a requisição")
    })
    public ResponseEntity<ArrayList<GroupDto>> getAllGroupsByUser(
            @PathVariable(value = "id") int id){

        try {
            UserDto user = userController.getUserById(id);

            if(user == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            ArrayList<GroupDto> groups = groupsController.getAllGroupsByUser(user);

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

    @GetMapping("/name/{name}")
    @ApiOperation(value = "Retorna grupo baseado no nome")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Grupo com nome"),
            @ApiResponse(code = 404, message = "Grupo com nome não encontrado"),
            @ApiResponse(code = 500, message = "Ocorreu um erro para processar a requisição")
    })
    public ResponseEntity<GroupDto> getGroupByName(
            @PathVariable(value = "name") String name) {

        try {
            GroupDto group = groupsController.getGroupByName(name);

            if (group == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(group, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "Retorna Grupo atualizado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Grupo atualizado"),
            @ApiResponse(code = 404, message = "Nenhum grupo foi encontrado"),
            @ApiResponse(code = 409, message = "Grupo com este nome já existe"),
            @ApiResponse(code = 500, message = "Ocorreu um erro para processar a requisição")
    })
    public ResponseEntity<GroupDto> updateGroup(
            @RequestBody UpdatedGroupDto groupToUpdate,
            @PathVariable(value = "id") int id) {

        try {
            GroupDto group = groupsController.getGroupById(id);

            if(group == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            GroupDto updatedGroup = groupsController.updateGroup(id, group, groupToUpdate);

            if(updatedGroup == null)
                return new ResponseEntity<>(HttpStatus.CONFLICT);

            return new ResponseEntity<>(updatedGroup, HttpStatus.OK);
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
