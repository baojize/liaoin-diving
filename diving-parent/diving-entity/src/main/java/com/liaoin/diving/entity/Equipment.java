package com.liaoin.diving.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "t_equipment", catalog = "diving")
@ApiModel("装备实体类")
@Getter
@Setter
public class Equipment implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "name")
    @ApiModelProperty("装备名称")
    private String name;

    @Column(name = "is_delete")
    @ApiModelProperty(value = "是否删除：1删除", hidden = true)
    private String isDelete;

   /* @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"pointsHistoryList", "contentList", "equipmentList", "orderList", "addressList", "labelList", "focusList", "followList"})
    @ApiModelProperty("用户")
    private User user;*/

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Brand.class)
    @JoinColumn(name = "brand_id")
    @ApiModelProperty("品牌")
    private Brand brand;



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Equipment equipment = (Equipment) o;
        return Objects.equals(id, equipment.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
