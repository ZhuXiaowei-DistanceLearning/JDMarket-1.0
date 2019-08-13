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
public class Reply {
    private int id;
    private Integer userId;
    private Integer atuserId;
    private Integer commetId;
    private String content;
    private String createAt;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "atuser_id")
    public Integer getAtuserId() {
        return atuserId;
    }

    public void setAtuserId(Integer atuserId) {
        this.atuserId = atuserId;
    }

    @Basic
    @Column(name = "commet_id")
    public Integer getCommetId() {
        return commetId;
    }

    public void setCommetId(Integer commetId) {
        this.commetId = commetId;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "create_at")
    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reply reply = (Reply) o;
        return id == reply.id &&
                Objects.equals(userId, reply.userId) &&
                Objects.equals(atuserId, reply.atuserId) &&
                Objects.equals(commetId, reply.commetId) &&
                Objects.equals(content, reply.content) &&
                Objects.equals(createAt, reply.createAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, atuserId, commetId, content, createAt);
    }
}
