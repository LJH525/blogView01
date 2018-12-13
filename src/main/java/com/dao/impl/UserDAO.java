package com.dao.impl;

import com.bean.BlogUser;
import com.dao.inter.IUserDAO;
import com.util.DBUtils;

import java.util.List;

public class UserDAO implements IUserDAO {
    @Override
    public boolean add(BlogUser obj) {
        return false;
    }

    @Override
    public boolean delete(String Id) {
        return false;
    }

    @Override
    public boolean update(BlogUser obj) {
        return false;
    }

    @Override
    public BlogUser queryById(String Id) {
        return null;
    }

    @Override
    public List<BlogUser> queryByConditions(String conditions) {
        return null;
    }

    public  void insert(Object oj){
        DBUtils.getSqlSession().insert("",oj);
    }


}
