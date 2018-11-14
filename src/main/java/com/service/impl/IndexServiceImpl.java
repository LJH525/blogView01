package com.service.impl;

import com.service.inter.IndexService;
import com.util.ESClient;

import java.util.List;

public class IndexServiceImpl implements IndexService {


    private ESClient client ;
    @Override
    public void index(String id) {

    }

    @Override
    public void del(String id) throws Exception {

    }

    @Override
    public Object get() {
        return null;
    }

    @Override
    public void update(String id) throws Exception {

    }

    @Override
    public List<Object> mutiGet(String... ids) throws Exception {
        return null;
    }

    @Override
    public void bulk(String... ids) throws Exception {

    }

    @Override
    public void bulkProcesstor(String index, String type, String... ids) throws Exception {

    }
}
