package com.jk1091.weixin.message.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 消息基类（普通用户 -> 公众帐号）
 *
 * @author jphua
 * @date 2018-05-30 16:25
 **/
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class BaseMessage {
    /**
     * 接收方帐号（收到的OpenID）
     */
    private String ToUserName;
    /** 开发者微信号
     *
     */
    private String FromUserName;
    /**消息创建时间 （整型）
     *
     */
    private long CreateTime;
    /**消息类型（text/music/news）
     *
     */
    private String MsgType;
    /**位0x0001被标志时，星标刚收到的消息
     *
     */
    private int FuncFlag;
}
