package com.github.binarywang.demo.wx.cp.utils;

import com.github.binarywang.demo.wx.cp.entity.WxCpSuiteXmlMessage;

public class MyUtils {

    public static void main(String[] args) {
        String xml = "<xml><ToUserName><![CDATA[ww16ae3a15cc859c8d]]></ToUserName><Encrypt><![CDATA[iMN8AJtpn+AJ71n1egi4cln1UAyr/BxeB6ugTPrBTBKd/3luHHGAHypS0A347DpVJJy7/+Xuv0ZJXSR1RlN80X1W7XC+hh+ZC3NrMQQpSXYlyo3vudKFsHGP1GUp/+UJ0PZbvavHYn1cNqEuM9UP/sNieJBQMFsVkTvsBVNzj+TP0bEHlmDYSuvMQj6LyAgpt5iIt1JgjsntV/KWffPJDc5y6yn4WHOobG/rC+E2ggKBozOLWsSJvUe+F1N2Da33BBwSdgUcQSQf8nk6PvhhFXq+4AmaP8ew3aW6Acr1XZXai9fWOXh5lsgoBka7L5reOYWpacTPageMIT3L5zuyVIyVaaUN+eSGHUjD+OMhtKrhMdm5ekieekZfWtZLnuyzV8ATt2uDdewAY6iZHESNwUc54BpWWkzq9eQun5eNwgE=]]></Encrypt><AgentID><![CDATA[1000004]]></AgentID></xml>]";
        WxCpSuiteXmlMessage tmp = WxCpSuiteXmlMessage.fromXml(xml);
        System.out.println(tmp);

    }
}
