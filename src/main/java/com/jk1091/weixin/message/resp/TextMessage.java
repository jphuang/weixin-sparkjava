package com.jk1091.weixin.message.resp;

import com.jk1091.weixin.utils.XmlUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 请求消息之文本消息
 *
 * @author jphua
 * @date 2018-05-30 16:28
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TextMessage extends BaseMessage {
    /**
     *   消息内容
     */
    private String Content;


    public  String textMessageToXml() {
        return  new XmlUtils<TextMessage>().transferXml(this);
    }

    public static void main(String[] args) {
        TextMessage textMessage = new TextMessage().setContent("content");
        System.out.println(textMessage.textMessageToXml());
    }
}
