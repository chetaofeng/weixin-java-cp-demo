package com.github.binarywang.demo.wx.cp.controller;

import com.github.binarywang.demo.wx.cp.config.WxCpProperties;
import me.chanjar.weixin.common.util.crypto.WxCryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/wx/cp/platform")
public class WxCpPlatController {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Resource
  private WxCpProperties config;

  @GetMapping(produces = "text/plain;charset=utf-8")
  public String authGet(@RequestParam(name = "msg_signature", required = false) String signature,
                        @RequestParam(name = "timestamp", required = false) String timestamp,
                        @RequestParam(name = "nonce", required = false) String nonce,
                        @RequestParam(name = "echostr", required = false) String echostr) {
    this.logger.info("\n接收到来自微信服务器的认证消息：signature = [{}], timestamp = [{}], nonce = [{}], echostr = [{}]",
        signature, timestamp, nonce, echostr);

    if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
      throw new IllegalArgumentException("请求参数非法，请核实!");
    }

    WxCryptUtil wxCryptUtil =  new WxCryptUtil(config.getCommonConfig().getToken(),config.getCommonConfig().getAesKey(),config.getCorpId());

    String retStr =  wxCryptUtil.decrypt(echostr);
    this.logger.info("retStr:{}",retStr);

    return retStr;
  }
}
