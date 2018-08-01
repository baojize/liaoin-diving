package com.liaoin.diving.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_system_alipay", catalog = "diving")
@ApiModel("支付宝支付系统参数")
public class SystemAlipay implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "server_url")
    @ApiModelProperty("支付宝网关")
    private String serverUrl;
    @Column(name = "app_id")
    @ApiModelProperty("应用编号")
    private String appId;
    @Column(name = "private_key")
    @ApiModelProperty("应用私钥")
    private String privateKey;
    @Column(name = "alipay_public_key")
    @ApiModelProperty("支付宝公钥")
    private String alipayPublicKey;
    @Column(name = "subject")
    @ApiModelProperty("交易标题")
    private String subject;
    @Column(name = "notify_url")
    @ApiModelProperty("回调地址")
    private String notifyUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
