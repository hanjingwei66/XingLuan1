package com.shuojie.domain.sensorModle;

import com.shuojie.utils.snowFlake.SnowFlake;
import lombok.Data;

import java.text.SimpleDateFormat;

//=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis())
//=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis())
//= SnowFlake.nextId()
@Data
public class BaseEntity {
    private   Long id ;
    private  String startTime ;
    private  String endTime;

    public Long getId() {
        return id;
    }

    public void setId() {
        this.id = SnowFlake.nextId();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime() {
        this.startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime() {
        this.endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
    }
}
