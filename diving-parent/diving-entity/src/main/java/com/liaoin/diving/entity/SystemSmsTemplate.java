package com.liaoin.diving.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_system_sms_template", catalog = "diving")
@ApiModel("短信模板系统参数")
public class SystemSmsTemplate implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键", hidden = true)
    private Integer id;

    @Column(name = "template_name")
    @ApiModelProperty("模板名称")
    private String templateName;
    @Column(name = "template_code")
    @ApiModelProperty("模板编号")
    private String templateCode;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = SystemAliyunSms.class)
    @JoinColumn(name = "system_aliyun_sms_id")
    @JsonIgnoreProperties({"systemSmsTemplateList"})
    @ApiModelProperty("阿里云短信系统参数")
    private SystemAliyunSms systemAliyunSms;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public SystemAliyunSms getSystemAliyunSms() {
        return systemAliyunSms;
    }

    public void setSystemAliyunSms(SystemAliyunSms systemAliyunSms) {
        this.systemAliyunSms = systemAliyunSms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SystemSmsTemplate that = (SystemSmsTemplate) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
