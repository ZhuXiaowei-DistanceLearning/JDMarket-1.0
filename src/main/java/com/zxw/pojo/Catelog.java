package com.zxw.pojo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Created by zxw on 2019/8/5.
 */
@Entity
public class Catelog {
    private int id;
    private String name;
    private Integer number;
    private Byte status;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "number")
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Basic
    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Catelog catelog = (Catelog) o;
        return id == catelog.id &&
                Objects.equals(name, catelog.name) &&
                Objects.equals(number, catelog.number) &&
                Objects.equals(status, catelog.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, number, status);
    }
}
