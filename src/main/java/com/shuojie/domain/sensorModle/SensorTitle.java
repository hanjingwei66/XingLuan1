package com.shuojie.domain.sensorModle;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SensorTitle {
    @TableId
    private Long id;
    private byte version;//版本
    private  byte comand;//2 命令字
    private int jizhongqid;//6 集中器 ID
    private int jiedianid;//节点 ID
    private short duanid;//短 ID
    private byte tongdao;//通道0x01-0x04
    private byte snr;//SNR
    private byte rssi0;//RSSI[0]
    private byte rssi1;//12 RSSI[1]0x01:RSSI 为正数，0x00:RSSI 为负数
    private  byte nc;//13 NC
    private  byte nc1;//14 NC
    private  int time;//15 时间戳 0x01：掉线，0x00：在线
    private byte zdzxqk;//16 终端在线情况
    private  short num;//17 终端入网总数
    private short sensorDataLength;// 18 数据长度
    private String sensorData;//19 有效数据
}
