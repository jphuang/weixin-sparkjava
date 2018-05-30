package com.jk1091.weixin.message.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求消息之文本消息
 *
 * @author jphua
 * @date 2018-05-30 16:28
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextMessage extends BaseMessage {
    /**
     *   消息内容
     */
    private String Content;
}
