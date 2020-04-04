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
	 * 查询所有员工
	 */
	public List<Emplovee> getAll() {
		// TODO Auto-generated method stub
		return emploveeMapper.selectByExampleWithDept(null);
	}

	/*
	 * 员工保存方法
	 */
	public void saveEmp(Emplovee emplovee) {
		// TODO Auto-generated method stub
		emploveeMapper.insertSelective(emplovee);
	}

	/*
	 * 检验员工名是否可用
	 *  return true代表当前姓名可用  false：不可用
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
	 * 按照员工id查询员工
	 * @param id
	 * @return
	 */
	public Emplovee getEmp(Integer id) {
		// TODO Auto-generated method stub
		Emplovee emplovee = emploveeMapper.selectByPrimaryKey(id);
		return emplovee;
	}

	/**
	 * 员工更新
	 * @param emplovee
	 */
	public void updateEmp(Emplovee emplovee) {
		// TODO Auto-generated method stub
		emploveeMapper.updateByPrimaryKeySelective(emplovee);
	}

	/**
	 * 员工删除
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
