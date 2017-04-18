package com.ymatou.productquery.infrastructure.mongodb;

import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;

import java.math.BigDecimal;

/**
 * Created by gejianhua on 2017/4/12.
 */
public class BigDecimalConverter extends TypeConverter implements SimpleValueConverter {

    public BigDecimalConverter() {
        super(BigDecimal.class);
    }

    @Override
    public Object encode(Object value, MappedField optionalExtraInfo) {
        BigDecimal val = (BigDecimal) value;
        if (val == null){
            return null;
        }
        String valStr = String.valueOf(val);
        return Double.valueOf(valStr);
    }

    @Override
    public Object decode(Class targetClass, Object fromDBObject,
                         MappedField optionalExtraInfo) {
        if (fromDBObject == null) {
            return null;
        }
        BigDecimal dec = new BigDecimal(String.valueOf(fromDBObject));
        return dec;
    }
}














































