package org.monkey.msd.cloud.common.handler.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.handlers.StrictFill;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.security.auth.Subject;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

/**
 * CustomizeMetaObjectHandler
 *
 * @author cc
 * @since 2025/3/3 15:04
 */
@Component
public class CustomizeMetaObjectHandler implements MetaObjectHandler {

    private static final String CREATE_BY = "createBy";

    private static final String CREATE_BY_ID = "createById";

    private static final String INSERT_TIME = "createTime";

    private static final String UPDATE_BY = "updateBy";

    private static final String UPDATE_BY_ID = "updateById";

    private static final String UPDATE_TIME = "updateTime";

    @Override
    public boolean openInsertFill(MappedStatement mappedStatement) {
        return MetaObjectHandler.super.openInsertFill(mappedStatement);
    }

    @Override
    public boolean openUpdateFill(MappedStatement mappedStatement) {
        return MetaObjectHandler.super.openUpdateFill(mappedStatement);
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        // 创建createTime与管理员
        this.insertDate(INSERT_TIME, metaObject);
        this.insertUserName(CREATE_BY, metaObject);
        this.insertUserId(CREATE_BY_ID, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新或创建updateTime
        this.updateDate(UPDATE_TIME, metaObject);
        this.updateUserName(UPDATE_BY, metaObject);
        this.updateUserId(UPDATE_BY_ID, metaObject);
    }

    @Override
    public MetaObjectHandler setFieldValByName(String fieldName, Object fieldVal, MetaObject metaObject) {
        return MetaObjectHandler.super.setFieldValByName(fieldName, fieldVal, metaObject);
    }

    @Override
    public Object getFieldValByName(String fieldName, MetaObject metaObject) {
        return MetaObjectHandler.super.getFieldValByName(fieldName, metaObject);
    }

    @Override
    public TableInfo findTableInfo(MetaObject metaObject) {
        return MetaObjectHandler.super.findTableInfo(metaObject);
    }

    @Override
    public <T, E extends T> MetaObjectHandler strictInsertFill(MetaObject metaObject, String fieldName, Class<T> fieldType, E fieldVal) {
        return MetaObjectHandler.super.strictInsertFill(metaObject, fieldName, fieldType, fieldVal);
    }

    @Override
    public <T, E extends T> MetaObjectHandler strictInsertFill(MetaObject metaObject, String fieldName, Supplier<E> fieldVal, Class<T> fieldType) {
        return MetaObjectHandler.super.strictInsertFill(metaObject, fieldName, fieldVal, fieldType);
    }

    @Override
    public MetaObjectHandler strictInsertFill(TableInfo tableInfo, MetaObject metaObject, List<StrictFill<?, ?>> strictFills) {
        return MetaObjectHandler.super.strictInsertFill(tableInfo, metaObject, strictFills);
    }

    @Override
    public <T, E extends T> MetaObjectHandler strictUpdateFill(MetaObject metaObject, String fieldName, Supplier<E> fieldVal, Class<T> fieldType) {
        return MetaObjectHandler.super.strictUpdateFill(metaObject, fieldName, fieldVal, fieldType);
    }

    @Override
    public <T, E extends T> MetaObjectHandler strictUpdateFill(MetaObject metaObject, String fieldName, Class<T> fieldType, E fieldVal) {
        return MetaObjectHandler.super.strictUpdateFill(metaObject, fieldName, fieldType, fieldVal);
    }

    @Override
    public MetaObjectHandler strictUpdateFill(TableInfo tableInfo, MetaObject metaObject, List<StrictFill<?, ?>> strictFills) {
        return MetaObjectHandler.super.strictUpdateFill(tableInfo, metaObject, strictFills);
    }

    @Override
    public MetaObjectHandler strictFill(boolean insertFill, TableInfo tableInfo, MetaObject metaObject, List<StrictFill<?, ?>> strictFills) {
        return MetaObjectHandler.super.strictFill(insertFill, tableInfo, metaObject, strictFills);
    }

    @Override
    public MetaObjectHandler fillStrategy(MetaObject metaObject, String fieldName, Object fieldVal) {
        return MetaObjectHandler.super.fillStrategy(metaObject, fieldName, fieldVal);
    }

    @Override
    public MetaObjectHandler strictFillStrategy(MetaObject metaObject, String fieldName, Supplier<?> fieldVal) {
        return MetaObjectHandler.super.strictFillStrategy(metaObject, fieldName, fieldVal);
    }
    // 创建时间字段
    private void insertDate(String filedName, MetaObject metaObject) {
        // 创建createTime
        if (this.isValue(filedName, metaObject)) {
            this.strictInsertFill(metaObject, filedName, Date.class, new Date());
            this.strictInsertFill(metaObject, filedName, Date::new, Date.class);
        }
    }

    // 更新时间字段
    private void updateDate(String filedName, MetaObject metaObject) {
        if (this.isValue(filedName, metaObject)) {
            Object fieldValByName = getFieldValByName(filedName, metaObject);
            if (fieldValByName != null) {
                this.setFieldValByName(filedName, new Date(), metaObject);
            }
            this.strictUpdateFill(metaObject, filedName, Date.class, new Date());
            this.strictUpdateFill(metaObject, filedName, Date::new, Date.class);
        }
    }

    // 创建管理员
    public void insertUserName(String filedName, MetaObject metaObject) {
        // 字段存在，值为空
        if (this.isValue(filedName, metaObject)
                && !ObjectUtils.isEmpty(getUserName())) {
            this.strictInsertFill(metaObject, filedName, String.class, getUserName());
            this.strictInsertFill(metaObject, filedName, this::getUserName, String.class);
        }
    }

    // 创建管理员
    public void insertUserId(String filedName, MetaObject metaObject) {
        // 字段存在，值为空
        if (this.isValue(filedName, metaObject) && !ObjectUtils.isNull(getUserId())) {
            this.strictInsertFill(metaObject, filedName, Long.class, getUserId());
            this.strictInsertFill(metaObject, filedName, this::getUserId, Long.class);
        }
    }

    // 更新管理员
    public void updateUserName(String filedName, MetaObject metaObject) {
        if (this.isValue(filedName, metaObject)
                && !ObjectUtils.isEmpty(getUserName())) {
            Object fieldValByName = getFieldValByName(filedName, metaObject);
            if (fieldValByName != null) {
                this.setFieldValByName(filedName, getUserName(), metaObject);
            }
            this.strictUpdateFill(metaObject, filedName, String.class, getUserName());
            this.strictUpdateFill(metaObject, filedName, this::getUserName, String.class);
        }
    }

    // 更新管理员
    public void updateUserId(String filedName, MetaObject metaObject) {
        if (this.isValue(filedName, metaObject)
                && !ObjectUtils.isNull(getUserId())) {
            Object fieldValByName = getFieldValByName(filedName, metaObject);
            if (fieldValByName != null) {
                this.setFieldValByName(filedName, getUserId(), metaObject);
            }
            this.strictUpdateFill(metaObject, filedName, Long.class, getUserId());
            this.strictUpdateFill(metaObject, filedName, this::getUserId, Long.class);
        }
    }

    // 值是否存在
    private boolean isValue(String filedName, MetaObject metaObject) {
        // 值是否存在
        boolean value = metaObject.hasGetter(filedName);
        // 字段存在，值为空
        if (value
                && ObjectUtils.isEmpty(metaObject.getValue(filedName))) {
            return true;
        }
        return false;
    }
    // 获取管理员
    public String getUserName() {
        String userName = null;
        try {
            userName = "admin";
        } catch (Exception e) {
        }
        return userName;
    }
    // 获取管理员
    public Long getUserId() {
        Long userId = null;
        try {
            userId = 1L;
        } catch (Exception e) {
        }
        return userId;
    }
}
