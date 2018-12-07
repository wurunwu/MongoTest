package com.wu.test;

import com.wu.pojo.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Pattern;

/**
 * springboot整合MongoDB测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoNewTest {

    @Resource
    private MongoTemplate mongoTemplate;

    //测试插入一条数据
    @Test
    public void insertOneTest(){
        System.out.println("测试单个*******************"+mongoTemplate);
        Student stu=new Student(2,"狂战","1666");
        mongoTemplate.save(stu);
        System.out.println("插入成功**************************");
    }

    //查询所有
    @Test
    public void findAll(){
        System.out.println("进入查询所有**********************");
        List<Student> stuList=mongoTemplate.findAll(Student.class);
        for(Student stu:stuList){
            System.out.println("当前对象:"+stu);
        }
    }

    //按名字查询
    @Test
    public void findByName(){
        System.out.println("进入按姓名查询单个对象**************************");
        Query query=new Query();
        query.addCriteria(Criteria.where("sname").is("张三"));
        query.addCriteria(Criteria.where("pwd").is(null));
        Student stu=mongoTemplate.findOne(query,Student.class);
        System.out.println("要查找的对象为："+ stu);
    }

    //模糊查询+排序分页
    @Test
    public void findByPage(){
        System.out.println("进入模糊分页查询***********************");
        String name="三";
        Integer pageIndex=0;
        Integer pageSize=3;
        Query query=new Query();
        //模糊查询 创建一个条件
        Criteria criteria=new Criteria();
        //制定格式
        Pattern pattern=Pattern.compile("^.*"+name+".*$",Pattern.CASE_INSENSITIVE);
        criteria.andOperator(Criteria.where("sname").regex(pattern));
        query.addCriteria(criteria);
        //排序
        query.with(new Sort(Sort.Direction.DESC,"age"));
        //分页
        query.skip((pageIndex-1)*pageSize);    //从那条记录开始
        query.limit(pageSize);             //取多少条记录
        long count=mongoTemplate.count(query,Student.class);  //总记录数
        System.out.println("count:"+count);
        List<Student> students = mongoTemplate.find(query, Student.class);
        students.forEach(System.out::print);
    }

    //修改
    @Test
    public void updateTest(){
        //编辑筛选条件
        System.out.println("进入修改*************************************");
        Query query=new Query(Criteria.where("sid").is(1));
        //编辑要修改的内容
        Update update=new Update().set("pwd","666");
        //更新查询返回结果集的第一条
        //mongoTemplate.updateFirst(query,update,Student.class);
        //更新查询返回结果集的所有
        mongoTemplate.updateMulti(query,update,Student.class);
    }

    //删除对象
    @Test
    public void deleteTest(){
        System.out.println("进入删除***************************");
        Query query=new Query(Criteria.where("sid").is(1));
        //mongoTemplate.remove(query,Student.class);   //删除单个
        //mongoTemplate.findAllAndRemove(query,Student.class);
        mongoTemplate.findAllAndRemove(query,"student");
    }
}
