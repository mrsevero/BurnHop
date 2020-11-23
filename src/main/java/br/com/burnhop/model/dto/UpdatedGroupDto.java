package br.com.burnhop.model.dto;

import io.swagger.annotations.ApiModelProperty;

public class UpdatedGroupDto {

    @ApiModelProperty(value = "Uma String que representa o nome do grupo", example = "Nome Exemplo Teste")
    private String name;

    @ApiModelProperty(value = "Uma String que representa a descrição do grupo", example = "Descrição Teste")
    private String description;

    @ApiModelProperty(value = "Uma String que representa o gênero do grupo", example = "Teste")
    private String genre;

    public UpdatedGroupDto() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
