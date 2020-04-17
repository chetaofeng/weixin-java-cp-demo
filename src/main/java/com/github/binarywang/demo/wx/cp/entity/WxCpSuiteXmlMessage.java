package com.github.binarywang.demo.wx.cp.entity;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.util.xml.XStreamCDataConverter;

import java.io.Serializable;

@Data
@Slf4j
@XStreamAlias("xml")
public class WxCpSuiteXmlMessage  implements Serializable {
    private static final long serialVersionUID = -1l;

    ///////////////////////
    // 以下都是微信推送过来的消息的xml的element所对应的属性
    ///////////////////////

    @XStreamAlias("SuiteId")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String suiteId;

    @XStreamAlias("SuiteTicket")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String suiteTicket;

    @XStreamAlias("InfoType")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String infoType;

    @XStreamAlias("ToUserName")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String toUserName;

    @XStreamAlias("FromUserName")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String fromUserName;

    @XStreamAlias("AgentID")
    private Integer agentId;

    @XStreamAlias("Encrypt")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String encrypt;

    @XStreamAlias("AuthCode")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String authCode;

    @XStreamAlias("TimeStamp")
    private Long timeStamp;


    public static WxCpSuiteXmlMessage fromXml(String xml) {
        //修改微信变态的消息内容格式，方便解析
        xml = xml.replace("</PicList><PicList>", "");
        XStream xstream = new XStream(new StaxDriver());
        xstream.processAnnotations(WxCpSuiteXmlMessage.class);
        xstream.autodetectAnnotations(true);

        final WxCpSuiteXmlMessage xmlMessage = (WxCpSuiteXmlMessage)xstream.fromXML(xml);
        return xmlMessage;
    }
}
