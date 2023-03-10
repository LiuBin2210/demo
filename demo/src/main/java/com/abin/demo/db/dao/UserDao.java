package com.abin.demo.db.dao;

import com.abin.demo.db.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * @Author: 啊斌
 * @Data:2023/2/8 20:03
 */
@Mapper
public interface UserDao {

    /**
     * 搜索用户权限
     *
     * @param userId 用户id
     * @return {@link Set}<{@link String}>
     */
    Set<String> searchUserPermissions(int userId);


    /**
     * 注册
     *
     * @param user 用户
     * @return int
     */
    int register(User user);

    /**
     * 登录
     *
     * @param param 参数
     * @return int
     */
    int login(HashMap param);

    /**
     * 更新密码
     *
     * @param param 参数
     * @return int
     */
    int updatePassword(HashMap param);

    /**
     * 搜索用户页面
     *
     * @param param 参数
     * @return {@link ArrayList}<{@link HashMap}>
     */
    ArrayList<HashMap> searchUserByPage(HashMap param);

    /**
     * 搜索用户数量
     *
     * @param param 参数
     * @return {@link Long}
     */
    Long searchUserCount(HashMap param);

    /**
     * 插入
     *
     * @param user 用户
     * @return int
     */
    int insert(User user);

    /**
     * 更新
     *
     * @param param 参数
     * @return int
     */
    int update(HashMap param);


    /**
     * 删除用户id
     *
     * @param ids id
     * @return int
     */
    int deleteUserByIds(Integer[] ids);

}
