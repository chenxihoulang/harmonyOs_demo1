package com.chw.pktest.slice;

import com.chw.pktest.db.BookStore;
import com.chw.pktest.db.User;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.data.DatabaseHelper;
import ohos.data.orm.*;
import ohos.data.rdb.RdbStore;
import ohos.data.rdb.ValuesBucket;

import java.util.List;

/**
 * ORM关系型数据库
 */
public class ORMDatabaseAbilitySlice extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);

        DatabaseHelper helper = new DatabaseHelper(this);
        // context入参类型为ohos.app.Context，注意不要使用slice.getContext()来获取context，请直接传入slice，否则会出现找不到类的报错。
        OrmContext context = helper.getOrmContext("BookStore", "BookStore.db", BookStore.class);

        OrmContext context1 = helper.getOrmContext("BookStore",
                "BookStore.db",
                BookStore.class,
                new TestOrmMigration12());

        User user = new User();
        user.setFirstName("Zhang");
        user.setLastName("San");
        user.setAge(29);
        user.setBalance(100.51);
        boolean isSuccessed = context.insert(user);
        //只有在flush()接口被调用后才会持久化到数据库中
        isSuccessed = context.flush();

        // 更新数据
        OrmPredicates predicates = context.where(User.class);
        predicates.equalTo("age", 29);
        List<User> users = context.query(predicates);
        User user0 = users.get(0);
        user0.setFirstName("Li");
        context.update(user0);
        context.flush();

        // 删除数据
        OrmPredicates predicates1 = context.where(User.class);
        predicates1.equalTo("age", 29);
        List<User> users1 = context.query(predicates1);
        User user1 = users1.get(0);
        context.delete(user1);
        context.flush();

        //通过传入谓词的接口来更新和删除数据，方法与OrmObject对象的接口类似，只是无需flush就可以持久化到数据库中
        ValuesBucket valuesBucket = new ValuesBucket();
        valuesBucket.putInteger("age", 31);
        valuesBucket.putString("firstName", "ZhangU");
        valuesBucket.putString("lastName", "SanU");
        valuesBucket.putDouble("balance", 300.51);
        OrmPredicates update = context.where(User.class)
                .equalTo("userId", 1);
        context.update(update, valuesBucket);

        OrmPredicates query = context.where(User.class).equalTo("lastName", "San");
        List<User> users2 = context.query(query);



        // 调用registerEntityObserver方法注册一个观察者observer。
        MyOrmObjectObserver observer = new MyOrmObjectObserver();
        //数据库表名
        String entityName="user";
        context.registerEntityObserver(entityName, observer);

        OrmContext context2 = helper.getOrmContext("OrmBackup", "OrmBackup.db", BookStore.class);
        context2.backup("OrmBackup001.db");
        context2.close();

        helper.deleteRdbStore("OrmBackup.db");
    }

    private static class TestOrmMigration12 extends OrmMigration {
        // 此处用于配置数据库版本迁移的开始版本和结束版本，super(startVersion, endVersion)即数据库版本号从1升到2。
        public TestOrmMigration12() {
            super(1, 2);
        }
        @Override
        public void onMigrate(RdbStore store) {
            store.executeSql("ALTER TABLE `Book` ADD COLUMN `addColumn12` INTEGER");
        }
    }

    // 定义一个观察者类。
    private class MyOrmObjectObserver implements OrmObjectObserver {
        @Override
        public void onChange(OrmContext changeContext, AllChangeToTarget subAllChange) {
            // 用户可以在此处定义观察者行为
        }
    }
}
