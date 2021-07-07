package com.chw.pktest.db;

import ohos.data.orm.OrmDatabase;
import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Database;
import ohos.data.rdb.RdbOpenCallback;

@Database(entities = {User.class, Book.class, AllDataType.class}, version = 2)
public abstract class BookStore extends OrmDatabase {
}
