package com.liaoin.diving.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_system_aliyun_sms", catalog = "diving")
@ApiModel("阿里云短信系统参数")
public class SystemAliyunSms implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty("主键")
    private Integer id;

    @Column(name = "access_key_id")
    @ApiModelProperty("密钥编号")
    private String accessKeyId;

    @Column(name = "access_key_secret")
    @ApiModelProperty("密钥密码")
    private String accessKeySecret;

    @Column(name = "sign_name")
    @ApiModelProperty("签名")
    private String signName;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = SystemSmsTemplate.class, mappedBy = "systemAliyunSms")
    @JsonIgnoreProperties({"systemAliyunSms"})
    @ApiModelProperty("短信模板系统参数列表")
    private Set<SystemSmsTemplate> systemSmsTemplateList = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public Set<SystemSmsTemplate> getSystemSmsTemplateList() {
        return systemSmsTemplateList;
    }

    public void setSystemSmsTemplateList(Set<SystemSmsTemplate> systemSmsTemplateList) {
        this.systemSmsTemplateList = systemSmsTemplateList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SystemAliyunSms that = (SystemAliyunSms) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
