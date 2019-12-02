package com.shuojie.domain.maps;

import lombok.Data;

@Data
public class CurrentInfo {

    private Integer cuid;//当前点id

    private String lineName;//当前点name

    private String currentShijian;//切换时间

}