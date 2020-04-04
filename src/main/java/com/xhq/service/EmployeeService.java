package com.xhq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhq.bean.Emplovee;
import com.xhq.bean.EmploveeExample;
import com.xhq.bean.EmploveeExample.Criteria;
import com.xhq.dao.EmploveeMapper;

@Service
public class EmployeeService {
	
	@Autowired
	EmploveeMapper emploveeMapper;

	/*
	 * ��ѯ����Ա��
	 */
	public List<Emplovee> getAll() {
		// TODO Auto-generated method stub
		return emploveeMapper.selectByExampleWithDept(null);
	}

	/*
	 * Ա�����淽��
	 */
	public void saveEmp(Emplovee emplovee) {
		// TODO Auto-generated method stub
		emploveeMapper.insertSelective(emplovee);
	}

	/*
	 * ����Ա�����Ƿ����
	 *  return true����ǰ��������  false��������
	 */
	public boolean checkUser(String empName) {
		// TODO Auto-generated method stub
		EmploveeExample example = new EmploveeExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmpNameEqualTo(empName);
		long count = emploveeMapper.countByExample(example);
		return count==0;
	}

	/**
	 * ����Ա��id��ѯԱ��
	 * @param id
	 * @return
	 */
	public Emplovee getEmp(Integer id) {
		// TODO Auto-generated method stub
		Emplovee emplovee = emploveeMapper.selectByPrimaryKey(id);
		return emplovee;
	}

	/**
	 * Ա������
	 * @param emplovee
	 */
	public void updateEmp(Emplovee emplovee) {
		// TODO Auto-generated method stub
		emploveeMapper.updateByPrimaryKeySelective(emplovee);
	}

	/**
	 * Ա��ɾ��
	 * @param id
	 */
	public void deleteEmp(Integer id) {
		// TODO Auto-generated method stub
		emploveeMapper.deleteByPrimaryKey(id);
	}

	public void deleteBatch(List<Integer> ids) {
		// TODO Auto-generated method stub
		EmploveeExample example = new EmploveeExample();
		Criteria criteria = example.createCriteria();
		//delete from xxx where emp_id in(1,2,3)
		criteria.andEmpIdIn(ids);
		emploveeMapper.deleteByExample(example);
	}

}
