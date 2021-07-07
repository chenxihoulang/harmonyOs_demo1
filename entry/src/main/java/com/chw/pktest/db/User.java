package com.chw.pktest.db;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.Index;
import ohos.data.orm.annotation.PrimaryKey;

@Entity(tableName = "user",
        ignoredColumns = {
                "ignoredColumn1",
                "ignoredColumn2"
        },
        indices = {@Index(value = {"firstName", "lastName"}, name = "name_index", unique = true)}
)
public class User extends OrmObject {
    // 此处将userId设为了自增的主键。注意只有在数据类型为包装类型时，自增主键才能生效。
    @PrimaryKey(autoGenerate = true)
    private Integer userId;

    private String firstName;
    private String lastName;
    private int age;
    private double balance;

    private int ignoredColumn1;
    private int ignoredColumn2;

    private int mSex;
    private boolean isMan;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getIgnoredColumn1() {
        return ignoredColumn1;
    }

    public void setIgnoredColumn1(int ignoredColumn1) {
        this.ignoredColumn1 = ignoredColumn1;
    }

    public int getIgnoredColumn2() {
        return ignoredColumn2;
    }

    public void setIgnoredColumn2(int ignoredColumn2) {
        this.ignoredColumn2 = ignoredColumn2;
    }

    public int getSex() {
        return mSex;
    }

    public void setSex(int sex) {
        mSex = sex;
    }

    public boolean isMan() {
        return isMan;
    }

    public void setMan(boolean man) {
        isMan = man;
    }
}
