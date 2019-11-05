package com.jiang.ping.weixin.message.resp;

import com.jiang.ping.weixin.util.MessageUtil;
import com.jiang.ping.weixin.utils.XmlUtils;
import lombok.Data;

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
        setFromUserName(fromUserName);
        setCreateTime(System.currentTimeMillis());
        this.Content = content;
        setMsgType(MessageUtil.REQ_MESSAGE_TYPE_TEXT);
    }
}
