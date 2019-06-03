package com.glw.miaosha.access;

import com.alibaba.fastjson.JSON;
import com.glw.miaosha.doman.MsUser;
import com.glw.miaosha.redis.AccessKey;
import com.glw.miaosha.redis.RedisService;
import com.glw.miaosha.result.CodeMsg;
import com.glw.miaosha.result.Result;
import com.glw.miaosha.service.MsUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求接口限流实现类
 * Create by glw
 * 2019/6/3 17:05
 */
@Service
public class AccessInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    MsUserService msUserService;

    @Autowired
    RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            MsUser user = getUser(request, response);

            HandlerMethod hm = (HandlerMethod)handler;
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCoutn();
            boolean needLogin = accessLimit.needLogin();

            String key =request.getRequestURI();
            if (needLogin) {
                if (user == null) {
                    render(response, CodeMsg.SESSION_ERROR);
                    return false;
                }
                key += "_" + user.getId();
            } else {
                // 啥也不做
            }
            // 限流
            AccessKey accessKey = AccessKey.updateKey(seconds);
            Integer count = redisService.get(accessKey, key, Integer.class);
            if (count == null) {
                redisService.set(accessKey, key, 1);
            } else if (count < maxCount) {
                redisService.incr(accessKey, key);
            } else {
                render(response, CodeMsg.ACCESS_LIMIT_REACHED);
                return false;
            }

        }
        return true;
    }

    /**
     * 写入响应错误码
     * @param response
     * @param cm
     * @throws Exception
     */
    private void render(HttpServletResponse response, CodeMsg cm) throws Exception {
        response.setContentType("application/json;charset=UTF-8");      // 防止乱码
        ServletOutputStream out = response.getOutputStream();
        String codeStr = JSON.toJSONString(Result.error(cm));
        out.write(codeStr.getBytes("UTF-8"));
        out.flush();
        out.close();
    }

    private MsUser getUser(HttpServletRequest request, HttpServletResponse response) {
        String paramToken = request.getParameter(MsUserService.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request, MsUserService.COOKIE_NAME_TOKEN);
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return null;
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        return msUserService.getByToken(response, token);
    }

    private String getCookieValue(HttpServletRequest request, String cookieNameToken) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length <= 0) {
            return null;
        }
        for(Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieNameToken)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
