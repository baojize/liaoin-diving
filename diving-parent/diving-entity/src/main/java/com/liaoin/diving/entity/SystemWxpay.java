package com.liaoin.diving.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 微信支付系统参数
 *
 * @author 张权立
 * @date 2018/6/11 14:31
 */
@Entity
@Table(name = "t_system_wxpay", catalog = "diving")
@ApiModel("微信支付系统参数")
public class SystemWxpay implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "app_id")
    @ApiModelProperty("应用编号")
    private String appId;
    @Column(name = "mch_id")
    @ApiModelProperty("商户编号")
    private String mchId;
    @Column(name = "`key`")
    @ApiModelProperty("密钥")
    private String key;
    @Column(name = "spbill_create_ip")
    @ApiModelProperty("本服务器ip")
    private String spbillCreateIp;
    @Column(name = "notify_url")
    @ApiModelProperty("回调地址")
    private String notifyUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
