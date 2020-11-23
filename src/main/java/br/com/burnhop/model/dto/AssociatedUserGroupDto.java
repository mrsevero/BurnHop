package br.com.burnhop.model.dto;

import io.swagger.annotations.ApiModelProperty;

public class AssociatedUserGroupDto {

    @ApiModelProperty(value = "Um inteiro que representa o id do usuário", example = "4")
    private int user_id;

    @ApiModelProperty(value = "Um inteiro que representa o id do grupo", example = "5")
    private int group_id;

    public AssociatedUserGroupDto(){

    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public int getGroupId() {
        return group_id;
    }

    public void setGroupId(int group_id) {
        this.group_id = group_id;
    }
}
