package com.liaoin.diving.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 环信系统参数
 *
 * @author 张权立
 * @date 2018/6/19 11:48
 */
@Entity
@Table(name = "t_system_huan_xin", catalog = "diving")
@ApiModel("环信系统参数")
public class SystemHuanXin implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "app_key")
    @ApiModelProperty("应用标识")
    private String appKey;
    @Column(name = "org_name")
    @ApiModelProperty("企业标识")
    private String orgName;
    @Column(name = "app_name")
    @ApiModelProperty("应用名称")
    private String appName;
    @Column(name = "client_id")
    @ApiModelProperty("客户编号")
    private String clientId;
    @Column(name = "client_secret")
    @ApiModelProperty("客户密钥")
    private String clientSecret;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
