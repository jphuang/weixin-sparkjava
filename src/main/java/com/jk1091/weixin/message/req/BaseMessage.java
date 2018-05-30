package com.jk1091.weixin.message.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息基类（普通用户 -> 公众帐号）
 *
 * @author jphua
 * @date 2018-05-30 16:25
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseMessage {
    /**
     *开发者微信号
     */
    private String ToUserName;

    /**
     * 发送方帐号（一个OpenID）
     */
    private String FromUserName;
    /**
     *消息创建时间 （整型）
     */
    private long CreateTime;
    /**
     * 消息类型（text/image/location/link）
     */
    private String MsgType;
    /**
     * 消息id，64位整型
     */
    private long MsgId;
}
