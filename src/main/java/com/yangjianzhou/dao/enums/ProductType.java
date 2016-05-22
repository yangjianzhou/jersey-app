package com.yangjianzhou.dao.enums;

/**
 * Created by yangjianzhou on 16-4-13.
 */
public enum ProductType implements EnumValue{

    BOOK(1),
    CLOTH(2),
    FOOD(3),
    ;

    private int value ;

    private ProductType(int value){
        this.value = value ;
    }


    @Override
    public int getValue() {
        return  value;
    }
}
