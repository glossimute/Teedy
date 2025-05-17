package com.sismics.docs.core.service;

import com.sismics.docs.core.constant.Constants;
import com.sismics.docs.core.dao.UserDao;
import com.sismics.docs.core.dao.UserRequestDao;
import com.sismics.docs.core.model.jpa.User;
import com.sismics.docs.core.model.jpa.UserRequest;

import java.util.List;

/**
 * Service for UserRequest.
 */
public class UserRequestService {

    private final UserRequestDao userRequestDao;

    public UserRequestService(UserRequestDao userRequestDao) {
        this.userRequestDao = userRequestDao;
    }

    public void submitRequest(UserRequest userRequest) {
        userRequestDao.create(userRequest);
    }

    public List<UserRequest> getPendingRequests() {
        return userRequestDao.findPending();
    }

    public boolean approve(String id) {
        UserRequest userRequest = userRequestDao.getById(id);
        if (userRequest == null || !"PENDING".equals(userRequest.getStatus())) {
            return false;
        }

        // 创建新用户
        User user = new User();
        String username = userRequest.getEmail();
        username = username.replace("@", "");
        user.setUsername(username);         // 邮箱作为用户名
        user.setPassword(username);         // 初始密码也是邮箱
        user.setEmail(userRequest.getEmail());
        user.setStorageQuota(1024L * 1024 * 1024);         // 默认1GB配额
        user.setRoleId(Constants.DEFAULT_USER_ROLE);
        user.setOnboarding(true);

        try {
            new UserDao().create(user, "system"); // 使用系统ID或管理员ID
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        // 更新请求状态
        userRequest.setStatus("APPROVED");
        userRequestDao.update(userRequest);
        return true;
    }

    public boolean reject(String id) {
        UserRequest request = userRequestDao.getById(id);
        if (request != null && "PENDING".equals(request.getStatus())) {
            request.setStatus("REJECTED");
            userRequestDao.update(request);
            return true;
        }
        return false;
    }
}
