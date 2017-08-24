package com.example.administrator.girl.data.entity;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;

import java.io.Serializable;

/*
 * 项目名：   Girl
 * 包名:     com.example.administrator.girl.data.entity
 * 文件名:   Soul
 * 创建者:   LDW
 * 创建时间: 2017/8/10  16:03
 * 描述:    TODO
 */
public class Soul implements Serializable {
    //设置主键，自增 取名id，id和_id互通
    @PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)@Column("_id") public long id;
}
