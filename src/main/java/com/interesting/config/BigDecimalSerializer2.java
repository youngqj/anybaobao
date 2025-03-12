package com.interesting.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

public class BigDecimalSerializer2 extends JsonSerializer<BigDecimal>  {
    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // 四舍五入保留两位小数
        gen.writeString(value.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    }
}
