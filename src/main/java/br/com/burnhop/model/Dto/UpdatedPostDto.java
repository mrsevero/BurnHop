package br.com.burnhop.model.Dto;

import io.swagger.annotations.ApiModelProperty;

public class UpdatedPostDto {

    @ApiModelProperty(value = "Uma String que representa o conteúdo do post", example = "Conteúdo teste")
    private String content;

    public UpdatedPostDto() {

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
