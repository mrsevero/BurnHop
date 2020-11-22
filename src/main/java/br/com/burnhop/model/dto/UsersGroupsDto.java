package br.com.burnhop.model.dto;

import br.com.burnhop.model.Groups;
import br.com.burnhop.model.UsersGroups;
import io.swagger.annotations.ApiModelProperty;

public class UsersGroupsDto {

    @ApiModelProperty(value = "Um inteiro que representa o identificador do grupo", example = "10")
    private int id;

    private UserDto user;

    private GroupDto group;

    public UsersGroupsDto(){

    }

    public UsersGroupsDto(UsersGroups usersGroups) {
        this.id = usersGroups.getIdUsersGroups();
        this.user = new UserDto(usersGroups.getUser());
        this.group = new GroupDto(usersGroups.getGroup());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public GroupDto getGroup() {
        return group;
    }

    public void setGroup(GroupDto group) {
        this.group = group;
    }
}
