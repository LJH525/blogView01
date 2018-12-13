package com.dao.inter;

import java.util.List;

public interface BaseDAO<T> {

    public boolean add(T obj);
    public boolean delete(String Id);
    public boolean update(T obj);
    public T queryById(String Id);
    public List<T> queryByConditions(String conditions);

}
