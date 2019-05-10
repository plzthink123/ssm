package com.think123.service;

import com.think123.domain.SysLog;

import java.util.List;

public interface ISysLogService {
    public void save(SysLog sysLog) throws  Exception;

    List<SysLog> findAll() throws Exception;
}