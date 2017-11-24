package com.upa.petshop_intern.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Tao.Jiang on 2017/10/13.
 */
public class UserImagePK implements Serializable {
    private long userId;
    private int imageNumber;

    @Column(name = "user_id", nullable = false)
    @Id
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Column(name = "image_number", nullable = false)
    @Id
    public int getImageNumber() {
        return imageNumber;
    }

    public void setImageNumber(int imageNumber) {
        this.imageNumber = imageNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserImagePK that = (UserImagePK) o;

        if (userId != that.userId) return false;
        if (imageNumber != that.imageNumber) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + imageNumber;
        return result;
    }
}
