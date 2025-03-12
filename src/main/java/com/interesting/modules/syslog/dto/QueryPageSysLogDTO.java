package com.interesting.modules.syslog.dto;

import lombok.Data;

@Data
public class QueryPageSysLogDTO {
    private Integer pageNo = 1;

    private Integer pageSize = 10;


}
