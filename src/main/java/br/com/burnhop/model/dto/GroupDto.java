package br.com.burnhop.model.dto;

import br.com.burnhop.model.Groups;
import io.swagger.annotations.ApiModelProperty;

public class GroupDto {

    @ApiModelProperty(value = "Um inteiro que representa o identificador do grupo", example = "10")
    private int id;

    @ApiModelProperty(value = "Uma String que representa o nome do grupo", example = "Grupo Exemplo Teste")
    private String name;

    private UserDto admin;

    public GroupDto(){

    }

    public GroupDto(Groups group) {
        this.id = group.getId();
        this.name = group.getName();
        this.admin = new UserDto(group.getAdmin());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserDto getAdmin() {
        return admin;
    }

    public void setAdmin(UserDto admin) {
        this.admin = admin;
    }
}
