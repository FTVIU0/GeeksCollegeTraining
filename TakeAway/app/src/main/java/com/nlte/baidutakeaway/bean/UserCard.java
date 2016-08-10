package com.nlte.baidutakeaway.bean;

/**
 * Function：
 *
 * @author NLTE
 * @date 2016/7/13 0013 15:50
 */
public class UserCard {
    private int img;
    private String name;

    public UserCard(int img, String name) {
        this.img = img;
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
