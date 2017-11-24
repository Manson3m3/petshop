package com.upa.petshop_intern.entity;

import javax.persistence.*;
import java.util.Arrays;

/**
 * Created by Yunhao.Cao on 2017/10/18.
 */
@Entity
@Table(name = "image_store", schema = "petshop", catalog = "")
public class ImageStore {
    private long id;
    private byte[] image;
    private String format;
    private String hash;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "image", nullable = false)
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Basic
    @Column(name = "format", nullable = false, length = 20)
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Basic
    @Column(name = "hash", nullable = false, length = 128)
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ImageStore that = (ImageStore) o;

        if (id != that.id)
            return false;
        if (!Arrays.equals(image, that.image))
            return false;
        if (format != null ? !format.equals(that.format) : that.format != null)
            return false;
        if (hash != null ? !hash.equals(that.hash) : that.hash != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + Arrays.hashCode(image);
        result = 31 * result + (format != null ? format.hashCode() : 0);
        result = 31 * result + (hash != null ? hash.hashCode() : 0);
        return result;
    }
}
