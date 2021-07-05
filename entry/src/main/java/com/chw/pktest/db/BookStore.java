package com.chw.pktest.db;

import ohos.data.orm.OrmDatabase;
import ohos.data.orm.OrmObject;
import ohos.data.rdb.RdbOpenCallback;

public class BookStore extends OrmDatabase {
    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public RdbOpenCallback getHelper() {
        return null;
    }
}
