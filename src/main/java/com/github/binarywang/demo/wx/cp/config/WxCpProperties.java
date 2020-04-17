package com.github.binarywang.demo.wx.cp.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import com.github.binarywang.demo.wx.cp.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "wechat.cp")
public class WxCpProperties {

  private String corpId;

  private List<AppConfig> appConfigs;

  private List<SuiteAppConfig> suiteAppConfigs;

  private CommonConfig commonConfig;

  @Getter
  @Setter
  public static class AppConfig {
    /**
     * 设置微信企业应用的AgentId
     */
    private Integer agentId;

    /**
     * 设置微信企业应用的Secret
     */
    private String secret;

    /**
     * 设置微信企业号的token
     */
    private String token;

    /**
     * 设置微信企业号的EncodingAESKey
     */
    private String aesKey;

  }

    @Getter
    @Setter
    public static class CommonConfig {
        private String providerSecret;
        private String token;
        private String aesKey;
    }

    @Getter
    @Setter
    public static class SuiteAppConfig {
        private String suiteId;
        private String secret;
        private String token;
        private String aesKey;
    }

  @Override
  public String toString() {
    return JsonUtils.toJson(this);
  }
}
