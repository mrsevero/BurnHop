package br.com.burnhop.model.dto;

import br.com.burnhop.model.Groups;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

public class CreatedGroupDto {

    @ApiModelProperty(value = "Uma String que representa o nome do grupo", example = "Grupo Exemplo Teste")
    private String name;

    @ApiModelProperty(value = "Uma String que representa a descrição do grupo", example = "Descrição Teste")
    private String description;

    private int admin_id;

    public CreatedGroupDto(){

    }

    public Groups toGroup() {
        Groups group = new Groups();
        group.setName(this.name);
        group.setDescription(this.description);
        group.setCreatedGroupOn(new Timestamp(System.currentTimeMillis()));
        return group;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAdmin(){
        return this.admin_id;
    }

    public void setAdmin(int admin_id) {
        this.admin_id = admin_id;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
