package com.jk1091.weixin.message.resp;

import com.jk1091.weixin.util.MessageUtil;
import com.jk1091.weixin.utils.XmlUtils;
import lombok.Data;

import java.util.Date;

/**
 * 请求消息之文本消息
 *
 * @author jphua
 * @date 2018-05-30 16:28
 **/
@Data
public class TextMessage extends BaseMessage {
    /**
     *   消息内容
     */
    private String Content;


    public  String textMessageToXml() {
        return  new XmlUtils<TextMessage>().transferXml(this);
    }

    public TextMessage() {
    }

    public TextMessage(String toUserName, String fromUserName, String content) {
        setToUserName(toUserName);
        setToUserName(fromUserName);
        setCreateTime(System.currentTimeMillis());
        this.Content = content;
        setMsgType(MessageUtil.REQ_MESSAGE_TYPE_TEXT);
    }
}
