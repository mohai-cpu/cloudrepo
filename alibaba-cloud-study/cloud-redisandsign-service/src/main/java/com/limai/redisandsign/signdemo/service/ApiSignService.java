package com.limai.redisandsign.signdemo.service;

import com.limai.utils.ReturnResult;

import javax.servlet.http.HttpServletRequest;

public interface ApiSignService {
    ReturnResult signTest(String username, HttpServletRequest request);
}
