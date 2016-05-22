package com.yangjianzhou.dao.typeHandler;

import com.ibatis.sqlmap.engine.type.TypeHandler;

/**
 * Created by yangjianzhou on 16-4-17.
 */
public  abstract  class BaseTypeHandler implements TypeHandler{

    @Override
    public boolean equals(Object object, String string) {
        if(object == null || string ==null){
            return object == string ;
        }

        Object castedObject = valueOf(string);

        return object.equals(castedObject);
    }
}
