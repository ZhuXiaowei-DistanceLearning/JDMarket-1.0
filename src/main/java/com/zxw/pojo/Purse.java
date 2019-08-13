package com.zxw.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by zxw on 2019/8/5.
 */
@Entity
public class Purse {
    private int id;
    private int userId;
    private Double balance;
    private Double recharge;
    private Double withdrawals;
    private Integer state;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "balance")
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Basic
    @Column(name = "recharge")
    public Double getRecharge() {
        return recharge;
    }

    public void setRecharge(Double recharge) {
        this.recharge = recharge;
    }

    @Basic
    @Column(name = "withdrawals")
    public Double getWithdrawals() {
        return withdrawals;
    }

    public void setWithdrawals(Double withdrawals) {
        this.withdrawals = withdrawals;
    }

    @Basic
    @Column(name = "state")
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purse purse = (Purse) o;
        return id == purse.id &&
                userId == purse.userId &&
                Objects.equals(balance, purse.balance) &&
                Objects.equals(recharge, purse.recharge) &&
                Objects.equals(withdrawals, purse.withdrawals) &&
                Objects.equals(state, purse.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, balance, recharge, withdrawals, state);
    }
}
