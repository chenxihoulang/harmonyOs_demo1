package com.chw.pktest;

import com.chw.pktest.db.BookStore;
import com.chw.pktest.db.User;
import ohos.aafwk.ability.*;
import ohos.aafwk.content.Intent;
import ohos.data.DatabaseHelper;
import ohos.data.dataability.DataAbilityUtils;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmMigration;
import ohos.data.orm.OrmPredicates;
import ohos.data.rdb.RdbStore;
import ohos.data.resultset.ResultSet;
import ohos.data.rdb.ValuesBucket;
import ohos.data.dataability.DataAbilityPredicates;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.rpc.MessageParcel;
import ohos.utils.net.Uri;
import ohos.utils.PacMap;

import java.io.*;
import java.util.ArrayList;

public class DataAbility extends Ability {

    private static final String DATABASE_NAME = "UserDataAbility.db";
    private static final String DATABASE_NAME_ALIAS = "UserDataAbility";
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.LOG_APP, 0xD00201, "Data_Log");
    private OrmContext ormContext = null;

    /**
     * 系统会在应用启动时调用onStart()方法创建Data实例
     */
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        DatabaseHelper manager = new DatabaseHelper(this);
        OrmMigration ormMigration1_2 = new OrmMigration(1, 2) {
            @Override
            public void onMigrate(RdbStore rdbStore) {

            }
        };

        ormContext = manager.getOrmContext(DATABASE_NAME_ALIAS, DATABASE_NAME, BookStore.class,ormMigration1_2);
    }

    /**
     * 查询数据库
     * @param uri 查询的目标路径
     * @param columns 查询的列名
     * @param predicates 查询条件
     * @return
     */
    @Override
    public ResultSet query(Uri uri, String[] columns, DataAbilityPredicates predicates) {
        if (ormContext == null) {
            HiLog.error(LABEL_LOG, "failed to query, ormContext is null");
            return null;
        }

        // 查询数据库
        OrmPredicates ormPredicates = DataAbilityUtils.createOrmPredicates(predicates, User.class);
        ResultSet resultSet = ormContext.query(ormPredicates, columns);
        if (resultSet == null) {
            HiLog.info(LABEL_LOG, "resultSet is null");
        }

        // 返回结果
        return resultSet;
    }

    /**
     * 向数据库中插入单条数据
     * @param uri 插入的目标路径
     * @param value 插入的数据值
     * @return 用于标识结果
     */
    @Override
    public int insert(Uri uri, ValuesBucket value) {
        HiLog.info(LABEL_LOG, "DataAbility insert");
        // 参数校验
        if (ormContext == null) {
            HiLog.error(LABEL_LOG, "failed to insert, ormContext is null");
            return -1;
        }

        // 构造插入数据
        User user = new User();
        user.setUserId(value.getInteger("userId"));
        user.setFirstName(value.getString("firstName"));
        user.setLastName(value.getString("lastName"));
        user.setAge(value.getInteger("age"));

        // 插入数据库
        boolean isSuccessful = ormContext.insert(user);
        if (!isSuccessful) {
            HiLog.error(LABEL_LOG, "failed to insert");
            return -1;
        }
        isSuccessful = ormContext.flush();
        if (!isSuccessful) {
            HiLog.error(LABEL_LOG, "failed to insert flush");
            return -1;
        }
        DataAbilityHelper.creator(this, uri).notifyChange(uri);
        int id = Math.toIntExact(user.getRowId());
        return id;
    }

    /**
     * 向数据库中插入多条数据,作用是提高插入多条重复数据的效率。该方法系统已实现，开发者可以直接调用
     * @param uri
     * @param values
     * @return
     */
    @Override
    public int batchInsert(Uri uri, ValuesBucket[] values) {
        return super.batchInsert(uri, values);
    }

    /**
     * 删除一条或多条数据
     * @param uri
     * @param predicates
     * @return
     */
    @Override
    public int delete(Uri uri, DataAbilityPredicates predicates) {
        if (ormContext == null) {
            HiLog.error(LABEL_LOG, "failed to delete, ormContext is null");
            return -1;
        }

        OrmPredicates ormPredicates = DataAbilityUtils.createOrmPredicates(predicates,User.class);
        int value = ormContext.delete(ormPredicates);
        DataAbilityHelper.creator(this, uri).notifyChange(uri);
        return value;
    }

    /**
     * 更新数据库
     * @param uri
     * @param value
     * @param predicates
     * @return
     */
    @Override
    public int update(Uri uri, ValuesBucket value, DataAbilityPredicates predicates) {
        if (ormContext == null) {
            HiLog.error(LABEL_LOG, "failed to update, ormContext is null");
            return -1;
        }

        OrmPredicates ormPredicates = DataAbilityUtils.createOrmPredicates(predicates,User.class);
        int index = ormContext.update(ormPredicates, value);
        HiLog.info(LABEL_LOG, "UserDataAbility update value:" + index);
        DataAbilityHelper.creator(this, uri).notifyChange(uri);
        return index;
    }

    /**
     * 批量操作数据库,该方法系统已实现，开发者可以直接调用。
     * @param operations
     * @return
     * @throws OperationExecuteException
     */
    @Override
    public DataAbilityResult[] executeBatch(ArrayList<DataAbilityOperation> operations) throws OperationExecuteException {
        return super.executeBatch(operations);
    }

    /**
     * 操作文件
     *
     * @param uri  uri为客户端传入的请求目标路径
     * @param mode mode为开发者对文件的操作选项，可选方式包含“r”(读), “w”(写), “rw”(读写)等
     */
    @Override
    public FileDescriptor openFile(Uri uri, String mode) {
        // 创建messageParcel
        MessageParcel messageParcel = MessageParcel.obtain();
        File file = new File(uri.getDecodedPathList().get(0)); //get(0)是获取URI完整字段中查询参数字段。
        if (!"rw".equals(mode)) {
            file.setReadOnly();
        }
        FileInputStream fileIs = null;
        try {
            fileIs = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FileDescriptor fd = null;
        try {
            fd = fileIs.getFD();
        } catch (Exception e) {
            HiLog.info(LABEL_LOG, "failed to getFD");
        }

        // 绑定文件描述符
        return messageParcel.dupFileDescriptor(fd);
    }

    @Override
    public String[] getFileTypes(Uri uri, String mimeTypeFilter) {
        return new String[0];
    }

    @Override
    public PacMap call(String method, String arg, PacMap extras) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}