package com.yangjianzhou.dto;

/**
 * Created by yangjianzhou on 16-4-13.
 */
public class UserDTO extends BaseDTO{

    /**
     * 用户名
     */
    private String name ;

    /**
     * 地址
     */
    private String address ;

    /**
     * 手机号
     */
    private String mobile ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
