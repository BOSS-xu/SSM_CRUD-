package com.xhq.test;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhq.bean.Department;
import com.xhq.bean.Emplovee;
import com.xhq.dao.DepartmentMapper;
import com.xhq.dao.EmploveeMapper;

/**
 * ����dao��Ĺ���
 * @author BOS  S
 *	�Ƽ�spring����Ŀ�Ϳ���ʹ��spring�ĵ�Ԫ���飬�����Զ�ע��������Ҫ�����
 *		1������springTestģ��
 *		2��ContextConfigurationָ��Spring�����ļ���λ��
 *		3��ֱ��autowiredҪʹ�õ��������
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:applicationContext.xml"})
public class MapperTest {
	
	@Autowired
	DepartmentMapper departmentMapper;
	
	@Autowired
	EmploveeMapper emploveeMapper;
	
	@Autowired
	SqlSession sqlSession;
	
	/*
	 * ����DepartmentMapper
	 */
	@Test
	public void testCRUD() {
//		//1������SpringIOC����
//		ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
//		//2���������л�ȡmapper
//		DepartmentMapper bean = ioc.getBean(DepartmentMapper.class);
		
		System.out.println(departmentMapper);
		//1�����뼸������
//		departmentMapper.insertSelective(new Department(null,"���Բ�"));
//		departmentMapper.insertSelective(new Department(null,"������"));
		
		//2������Ա�����ݡ�����Ա������
		//emploveeMapper.insertSelective(new Emplovee(null, "jerry", "M", "jerry@xhq.com", 1));
		
		//3������������Ա����������ʹ�ÿ���ִ������������sqlSession
//		for() {
//			emploveeMapper.insertSelective(new Emplovee(null, "jerry", "M", "jerry@xhq.com", 1));
//		}
		EmploveeMapper mapper = sqlSession.getMapper(EmploveeMapper.class);
		for(int i=0;i<1000;i++) {
			String uid = UUID.randomUUID().toString().substring(0,5)+i;
			mapper.insertSelective(new Emplovee(null, uid, "M", uid+"@xhq.com", 1));
		}
		System.out.println("�������");
		
	}
}
