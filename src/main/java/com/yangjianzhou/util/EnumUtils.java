package com.yangjianzhou.util;

import com.yangjianzhou.dao.enums.EnumValue;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by yangjianzhou on 16-4-17.
 */
public class EnumUtils {

    public static <T extends Enum<T>> T parseEnum(Class<T> enumType, String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }

        T type;

        try {
            type = Enum.valueOf(enumType, name);
        } catch (Exception exp) {
            type = null;
        }
        return type;
    }


    public static <T extends Enum<T>> T parseEnum(Class<T> enumType, int value) {
        if(!EnumValue.class.isAssignableFrom(enumType)){
            throw new IllegalArgumentException();
        }
        try{
            Enum<T>[] values = enumType.getEnumConstants();
            for(Enum<T> item : values){
                if(((EnumValue) item).getValue()==value){
                    return (T)item ;
                }
            }
        }catch (Exception exp){

        }
        return  null ;
    }
}
