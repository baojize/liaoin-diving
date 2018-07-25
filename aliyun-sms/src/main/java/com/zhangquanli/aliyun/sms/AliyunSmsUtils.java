package com.zhangquanli.aliyun.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * 阿里云短信工具类
 *
 * @author 张权立
 * @date 2018/06/07
 */
public class AliyunSmsUtils {

    /**
     * @param accessKeyId     密钥编号
     * @param accessKeySecret 密钥密码
     * @param signName        签名
     * @param phoneNumber     手机号码
     * @param templateCode    模板代码
     * @param templateParam   模板变量
     * @return 是否发送成功
     * @throws ClientException 请求失败抛出异常
     */
    public static boolean sendSms(String accessKeyId, String accessKeySecret, String signName, String phoneNumber, String templateCode, String templateParam) throws ClientException {
        // 设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        // 初始化ascClient需要的几个参数
        //短信API产品名称（短信产品名固定，无需修改）
        final String product = "Dysmsapi";
        //短信API产品域名（接口地址固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";

        // 初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        // 组装请求对象
        SendSmsRequest request = new SendSmsRequest();

        // 使用post提交
        request.setMethod(MethodType.POST);

        // 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,
        // 验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
        request.setPhoneNumbers(phoneNumber);

        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);

        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);

        // 可选:模板中的变量替换JSON串
        request.setTemplateParam(templateParam);

        // 请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        return (sendSmsResponse.getCode() != null) && ("OK".equals(sendSmsResponse.getCode()));
    }
}
