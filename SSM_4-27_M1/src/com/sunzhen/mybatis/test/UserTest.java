package com.sunzhen.mybatis.test;

import com.sunzhen.mybatis.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class UserTest {

    SqlSessionFactory factory=null;

    @Before
    public void init() throws IOException {
        //1. 获取sqlSessionFactory对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        factory = builder.build(inputStream);
    }

    /*
        junit测试方法需遵守3大规范:
        1. public修饰
        2. void(无返回值)
        3. 参数为空
    */
    @Test
    public void testFindUserById() throws Exception {
        //2. 获取sqlsession
        SqlSession sqlSession = factory.openSession();
        //3. 使用sqlsession方法
        //    p1:statementId p2:指定的输入映射参数
        User user = sqlSession.selectOne("findUserById", 10);
        System.out.println(user);
        //4. 关闭sqlSession
        sqlSession.close();

    }

    @Test
    public void testFindUserByUsername() throws Exception {
        SqlSession sqlSession = factory.openSession();
        List<User> list = sqlSession.selectList("findUserByUsername", "张");
        System.out.println(list);
        sqlSession.close();
    }

    @Test
    public void testInsertUser() throws Exception {
        SqlSession sqlSession = factory.openSession();
        User user = new User();
        user.setAddress("家");
        user.setBirthday(new Date());
        user.setSex("1");
        user.setUsername("gengyang");
        sqlSession.insert("insertUser",user);
        System.out.println(user.getId());
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testUpdateUser(){
        SqlSession sqlSession = factory.openSession();
        User user=new User();
        user.setId(27);
        user.setAddress("老家");
        user.setBirthday(new Date());
        user.setSex("0");
        user.setUsername("孙桢");
        sqlSession.update("updateUser",user);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testDeleteUser(){
        SqlSession sqlSession = factory.openSession();
        sqlSession.delete("deleteUser",27);
        sqlSession.commit();
        sqlSession.close();
    }

}
