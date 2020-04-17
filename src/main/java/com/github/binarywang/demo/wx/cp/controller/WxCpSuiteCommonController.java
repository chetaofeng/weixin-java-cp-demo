package com.github.binarywang.demo.wx.cp.controller;

import com.github.binarywang.demo.wx.cp.config.WxCpConfiguration;
import com.github.binarywang.demo.wx.cp.config.WxCpProperties;
import me.chanjar.weixin.common.util.crypto.WxCryptUtil;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * 企业微信服务商第三方应用通用参数处理
 */
@RestController
@RequestMapping("/wx/cp/suite/common")
public class WxCpSuiteCommonController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private WxCpProperties config;

    // 用于接收跟应用无关的系统消息(如注册完成)。
    @GetMapping(produces = "text/plain;charset=utf-8")
    public String getCommonAuth(@RequestParam(name = "msg_signature", required = false) String signature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestParam(name = "echostr", required = false) String echostr) {
        this.logger.info("\n接收到来自微信服务器的认证消息：signature = [{}], timestamp = [{}], nonce = [{}], echostr = [{}]",
            signature, timestamp, nonce, echostr);

        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        WxCryptUtil wxCryptUtil = new WxCryptUtil(config.getCommonConfig().getToken(), config.getCommonConfig().getAesKey(), config.getCorpId());

        String retStr = wxCryptUtil.decrypt(echostr);
        this.logger.info("retStr:{}", retStr);

        return retStr;
    }

    @PostMapping(produces = "text/plain;charset=utf-8")
    public String getCommonCallback(
                                @RequestBody String requestBody,
                                @RequestParam(name = "msg_signature", required = false) String signature,
                                @RequestParam(name = "timestamp", required = false) String timestamp,
                                @RequestParam(name = "nonce", required = false) String nonce,
                                @RequestParam(name = "echostr", required = false) String echostr) {
        this.logger.info("\n接收微信请求：[signature=[{}], timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
            signature, timestamp, nonce, requestBody);

//        final WxCpService wxCpService = WxCpConfiguration.getCpService(agentId);
//        WxCpXmlMessage inMessage = WxCpXmlMessage.fromEncryptedXml(requestBody, wxCpService.getWxCpConfigStorage(),
//            timestamp, nonce, signature);
//        this.logger.debug("\n消息解密后内容为：\n{} ", JsonUtils.toJson(inMessage));
//        WxCpXmlOutMessage outMessage = this.route(agentId, inMessage);
//        if (outMessage == null) {
//            return "";
//        }
//
//        String out = outMessage.toEncryptedXml(wxCpService.getWxCpConfigStorage());
//        this.logger.debug("\n组装回复信息：{}", out);
        return "";
    }
}
