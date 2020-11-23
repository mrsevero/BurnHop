package br.com.burnhop.model.dto;

import io.swagger.annotations.ApiModelProperty;

public class UpdatedGroupDto {

    @ApiModelProperty(value = "Uma String que representa o nome do usuário", example = "Nome Exemplo Teste")
    private String name;

    public UpdatedGroupDto() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
