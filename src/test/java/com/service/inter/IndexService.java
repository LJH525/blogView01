package com.service.inter;

import java.util.List;

public interface IndexService {

    public void index(String id);

    public void del(String id)throws Exception;

    public Object get();

    public void update(String id) throws Exception;

    public List<Object> mutiGet(String ...ids) throws Exception;

    public void bulk(String ...ids) throws Exception;

    public void bulkProcesstor(String index,String type,String ...ids) throws Exception;
}
