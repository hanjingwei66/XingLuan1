package com.shuojie.domain.maps;


import lombok.Data;

import java.util.List;
@Data
public class Origin {

    private Integer orid;

    private String orName;

    private List<OriginLine> originAndLine;

}