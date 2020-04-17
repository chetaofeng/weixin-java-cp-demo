package com.github.binarywang.demo.wx.cp.controller;

import com.github.binarywang.demo.wx.cp.config.WxCpProperties;
import com.github.binarywang.demo.wx.cp.entity.WxCpSuiteXmlMessage;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.util.crypto.WxCryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 企业微信服务商第三方应用处理
 */
@Slf4j
@RestController
@RequestMapping("/wx/cp/suite/sass")
public class WxCpSuiteSassController {
    @Resource
    private WxCpProperties config;

    //数据回调URL
    //用于接收托管企业微信应用的用户消息和用户事件。推送事件时企业微信会自动将其替换为授权企业的corpid
    @GetMapping(value = "/{suiteId}/callback", produces = "text/plain;charset=utf-8")
    public String getDataCallbackAuth(@PathVariable String suiteId,
                                      @RequestParam(name = "msg_signature", required = false) String signature,
                                      @RequestParam(name = "timestamp", required = false) String timestamp,
                                      @RequestParam(name = "nonce", required = false) String nonce,
                                      @RequestParam(name = "echostr", required = false) String echostr) {
        log.info("\n接收到来自微信服务器的getDataCallback：signature = [{}], timestamp = [{}], nonce = [{}], echostr = [{}]",
            signature, timestamp, nonce, echostr);

        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        WxCpProperties.SuiteAppConfig suiteAppConfig = config.getSuiteAppConfigs().get(0);
        WxCryptUtil wxCryptUtil = new WxCryptUtil(suiteAppConfig.getToken(), suiteAppConfig.getAesKey(), suiteAppConfig.getSuiteId());

        String retStr = wxCryptUtil.decrypt(echostr);
        log.info("retStr:{}", retStr);

        return retStr;
    }

    @PostMapping(value = "/{suiteId}/callback",produces = "application/xml; charset=UTF-8")
    public String postDataCallback(@PathVariable String suiteId,
                                   @RequestBody String requestBody,
                                   @RequestParam("msg_signature") String signature,
                                   @RequestParam("timestamp") String timestamp,
                                   @RequestParam("nonce") String nonce) {
        log.info("\n接收微信请求：[signature=[{}], timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
            signature, timestamp, nonce, requestBody);


        WxCpProperties.SuiteAppConfig suiteAppConfig = config.getSuiteAppConfigs().get(0);
        WxCryptUtil wxCryptUtil = new WxCryptUtil(suiteAppConfig.getToken(), suiteAppConfig.getAesKey(), suiteAppConfig.getSuiteId());

        String plainText = wxCryptUtil.decrypt(signature, timestamp, nonce, requestBody);

//        WxCpSuiteXmlMessage inMessage = WxCpSuiteXmlMessage.fromXml(plainText);
//        log.info("第三方应用回调：angetId-{}",inMessage.getAgentId());


        return "success";
    }

    //指令回调URL
    //系统将会把此应用的授权变更事件以及ticket参数等推送给此URL,https://work.weixin.qq.com/api/doc/10982
    @GetMapping(value = "/{suiteId}/cmdCallback", produces = "text/plain;charset=utf-8")
    public String getCmdCallbackAuth( @PathVariable String suiteId,
                                      @RequestParam(name = "msg_signature", required = false) String signature,
                                      @RequestParam(name = "timestamp", required = false) String timestamp,
                                      @RequestParam(name = "nonce", required = false) String nonce,
                                      @RequestParam(name = "echostr", required = false) String echostr) {
        log.info("\n接收到来自微信服务器的getCmdCallbackAuth：signature = [{}], timestamp = [{}], nonce = [{}], echostr = [{}]",
            signature, timestamp, nonce, echostr);

        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        WxCpProperties.SuiteAppConfig suiteAppConfig = config.getSuiteAppConfigs().get(0);
        WxCryptUtil wxCryptUtil = new WxCryptUtil(suiteAppConfig.getToken(), suiteAppConfig.getAesKey(), suiteAppConfig.getSuiteId());

        String retStr = wxCryptUtil.decrypt(echostr);
        log.info("retStr:{}", retStr);

        return retStr;
    }

    @PostMapping(value = "/{suiteId}/cmdCallback",produces = "application/xml; charset=UTF-8")
    public String postCmdCallback( @PathVariable String suiteId,
                                   @RequestBody String requestBody,
                                   @RequestParam("msg_signature") String signature,
                                   @RequestParam("timestamp") String timestamp,
                                   @RequestParam("nonce") String nonce) {
        log.info("\n接收微信请求：[signature=[{}], timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
            signature, timestamp, nonce, requestBody);

        WxCpProperties.SuiteAppConfig suiteAppConfig = config.getSuiteAppConfigs().get(0);
        WxCryptUtil wxCryptUtil = new WxCryptUtil(suiteAppConfig.getToken(), suiteAppConfig.getAesKey(), suiteAppConfig.getSuiteId());

        String plainText = wxCryptUtil.decrypt(signature, timestamp, nonce, requestBody);
        log.debug("解密后的原始xml消息内容：{}", plainText);
//        WxCpSuiteXmlMessage inMessage = WxCpSuiteXmlMessage.fromXml(plainText);
//        String suiteTicket = inMessage.getSuiteTicket();
//        log.info(suiteTicket);

        return "success";
    }
}
