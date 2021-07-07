package com.chw.pktest.slice;

import com.chw.pktest.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.data.DatabaseHelper;
import ohos.data.rdb.*;
import ohos.data.resultset.ResultSet;
import ohos.data.resultset.TableResultSet;

/**
 * 关系型数据库
 */
public class DatabaseAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_ormdatabase);

        AbsRdbPredicates predicates = new RdbPredicates("");
        AbsRdbPredicates predicates1 = new RawRdbPredicates("");

        ResultSet resultSet = null;
        //判断结果集是否被移动过
        boolean started = resultSet.isStarted();
        //判断结果集当前位置是否在第一行
        boolean atFirstRow = resultSet.isAtFirstRow();
        //判断结果集当前位置是否在最后一行之后
        boolean ended = resultSet.isEnded();
        //判断结果集当前位置是否在最后一行
        boolean atLastRow = resultSet.isAtLastRow();
        resultSet.registerObserver(new DataObserver() {
            @Override
            public void onChange() {

            }
        });

        RdbStore rdbStore = null;
        try {
            rdbStore.beginTransaction();
            //处理数据
            rdbStore.markAsCommit();
        } finally {
            rdbStore.endTransaction();
        }

        rdbStore.beginTransactionWithObserver(new TransactionObserver() {
            @Override
            public void onBegin() {

            }

            @Override
            public void onCommit() {

            }

            @Override
            public void onRollback() {

            }
        });
        rdbStore.restore("non encrypt database file name");


        StoreConfig config = StoreConfig.newDefaultConfig("RdbStoreTest.db");
        DatabaseHelper helper = new DatabaseHelper(this);
        RdbStore store = helper.getRdbStore(config, 1, callback, null);

        ValuesBucket values = new ValuesBucket();
        values.putInteger("id", 1);
        values.putString("name", "zhangsan");
        values.putInteger("age", 18);
        values.putDouble("salary", 100.5);
        values.putByteArray("blobType", new byte[] {1, 2, 3});
        long id = store.insert("test", values);

        String[] columns = new String[] {"id", "name", "age", "salary"};
        RdbPredicates rdbPredicates = new RdbPredicates("test")
                .equalTo("age", 25)
                .orderByAsc("salary");
        ResultSet resultSet1 = store.query(rdbPredicates, columns);
        resultSet1.goToNextRow();


    }

    private static RdbOpenCallback callback = new RdbOpenCallback() {
        @Override
        public void onCreate(RdbStore store) {
            store.executeSql("CREATE TABLE IF NOT EXISTS test (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, age INTEGER, salary REAL, blobType BLOB)");
        }

        @Override
        public void onUpgrade(RdbStore store, int oldVersion, int newVersion) {
        }
    };

}
