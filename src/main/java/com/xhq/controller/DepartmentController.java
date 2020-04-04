package com.xhq.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhq.bean.Department;
import com.xhq.bean.Msg;
import com.xhq.service.DepartmentService;

/**
 * 处理和部门有关的请求
 * @author BOSS
 *
 */
@Controller
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService ;
	
	/*
	 * 返回所有的部门信息
	 */
	@RequestMapping("/depts")
	@ResponseBody
	public Msg getDepts() {
		//查出的所有部门信息
		List<Department> list = departmentService.getDepts();
		return Msg.success().add("depts", list);
	}
	
}
