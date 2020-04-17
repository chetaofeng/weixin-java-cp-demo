package com.github.binarywang.demo.wx.cp.controller;

import com.github.binarywang.demo.wx.cp.config.WxCpProperties;
import com.github.binarywang.demo.wx.cp.entity.WxCpSuiteXmlMessage;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.util.crypto.WxCryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 企业微信服务商第三方应用处理
 */
@Slf4j
@RestController
@RequestMapping("/suite/receive")
public class WxCpSuiteController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private WxCpProperties config;

    @PostMapping(produces = "application/xml; charset=UTF-8")
    public String postDataCallback(@PathVariable Integer corpId,@PathVariable String suiteId,
                                   @RequestBody String requestBody,
                                   @RequestParam("msg_signature") String signature,
                                   @RequestParam("timestamp") String timestamp,
                                   @RequestParam("nonce") String nonce) {
        log.info("\n接收微信请求：[signature=[{}], timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
            signature, timestamp, nonce, requestBody);


        WxCpProperties.SuiteAppConfig suiteAppConfig = config.getSuiteAppConfigs().get(0);
        WxCryptUtil wxCryptUtil = new WxCryptUtil(suiteAppConfig.getToken(), suiteAppConfig.getAesKey(), suiteAppConfig.getSuiteId());

        String plainText = wxCryptUtil.decrypt(signature, timestamp, nonce, requestBody);

        WxCpSuiteXmlMessage inMessage = WxCpSuiteXmlMessage.fromXml(plainText);
        log.info("第三方应用回调：angetId-{}",inMessage.getAgentId());

        return "success";
    }
}
