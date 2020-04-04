package com.xhq.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhq.bean.Emplovee;
import com.xhq.bean.Msg;
import com.xhq.service.EmployeeService;

/**
 * ����Ա��CRUD����
 * @author BOSS
 *
 */
@Controller
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	
	/**
	 * ������������һ
	 * ����ɾ����1-2-3
	 * ����ɾ����1
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/emp/{ids}",method = RequestMethod.DELETE)
	public Msg deleteEmpById(@PathVariable("ids")String ids) {
		//����ɾ��
		if(ids.contains("-")) {
			List<Integer> del_ids = new ArrayList<>();
			String[] str_ids = ids.split("-");
			//��װid�ļ���
			for (String string : str_ids) {
				del_ids.add(Integer.parseInt(string));
			}
			employeeService.deleteBatch(del_ids);
		}else {
			Integer id = Integer.parseInt(ids);
			employeeService.deleteEmp(id);
		}
		
		
		return Msg.success();
	}
	
	
	
	/**
	 * ���ֱ�ӷ���ajax=put������
	 * ��װ������
	 * ��������������
	 * ����Emplovee��װ����
	 * 
	 * ԭ��
	 * Tomcat
	 * 		���������е����ݡ���װһ��map
	 * 		request.getParameter����empName������������map��ȡֵ
	 * 		SpringMVC��װ���ݵ�ʱ��POJO�����ʱ�򣬶Ը�POJO��ÿ�����ݵ�ֵ��request.getPatameter�õ�
	 * AJAX����PUT����������Ѫ��
	 * 		Put�����������е�����request.getParameter�ò���
	 * 		Tomcatһ����PUT�����װ�������ص�����Ϊmap��ֻ��post��ʽ������ŷ�װΪmap
	 * 
	 * ����Ҫ֧��ֱ�ӷ���PUT����Ҫ��װ�������е�����
	 * 1��������HttpPutFormContenFilter,
	 * 2���������ã����������е����ݽ�����װ��һ��map��
	 * 3��request�����°�װ��request.getParameter()���������£��ͻ���Լ���װ��map��ȡ����
	 * 
	 * Ա�����·���
	 * @param emplovee
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/emp/{empId}",method = RequestMethod.PUT)
	public Msg saveEmp(Emplovee emplovee) {
		//System.out.println("��Ҫ����Ա�������ݣ�"+emplovee);
		employeeService.updateEmp(emplovee);
		return Msg.success();
	}
	
	/**
	 * ����id��ѯԱ��
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/emp/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Msg getEmp(@PathVariable("id")Integer id) {
		
		Emplovee emplovee = employeeService.getEmp(id);
		return Msg.success().add("emp", emplovee);
	}
	
	
	
	
	/**
	 * ����û����Ƿ���� 
	 * @param empName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkuser")
	public Msg checkuser(@RequestParam("empName")String empName) {
		//���ж��û����Ƿ��ǺϷ��ı��ʽ��
		String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
		if(!empName.matches(regx)) {
			return Msg.fail().add("va_msg", "�û���������6-16λ����ĸ����ϻ���2-5λ����");
		};
		
		//���ݿ��û����ظ�У��
		boolean b = employeeService.checkUser(empName);
		if(b) {
			return Msg.success();
		}else {
			return Msg.fail().add("va_msg", "�û���������");
		}
	}
	
	
	
	/*
	 * Ա������
	 * 1��֧��JSR303У��
	 * 2������Hibernate-Validator
	 */
	@RequestMapping(value="/emp",method=RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid Emplovee emplovee,BindingResult result) {
		if(result.hasErrors()) {
			//У��ʧ�ܣ�Ӧ�÷���ʧ�ܣ���ģ̬���з���У��ʧ�ܵĴ�����Ϣ
			Map<String, Object> map = new HashMap<>();
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				//System.out.println("������ֶ���"+fieldError.getField());
				//System.out.println("������Ϣ"+fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.fail().add("errorFields", map);
		}else {
			employeeService.saveEmp(emplovee);
			return Msg.success();
		}
	}
	
	
	
	/*
	 * ��Ҫ����jackson��
	 */
	@RequestMapping("emps")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value="pn",defaultValue = "1")Integer pn) {
		//�ⲻ��һ����ҳ��ѯ��
		//����PageHelper��ҳ���
		//�ڲ�ѯ֮ǰֻ��Ҫ����,����ҳ�룬�Լ�ÿҳ�Ĵ�С
		PageHelper.startPage(pn, 5);
		//startPage��������������ѯ����һ����ҳ��ѯ
		List<Emplovee> emps = employeeService.getAll();
		//ʹ��pageInfo��װ��ѯ��Ľ����ֻ��Ҫ��pageInfo����ҳ������ˡ�
		//��װ����ϸ�ķ�ҳ��Ϣ�����������ǲ�ѯ���������ݣ����Դ���������ʾ��ҳ��
		PageInfo page = new PageInfo(emps,5);
		return Msg.success().add("PageInfo",page);
	}
	
	/*
	 * ��ѯԱ�����ݣ���ҳ��ѯ��
	 */
	//@RequestMapping("/emps")
	public String getEmps(@RequestParam(value="pn",defaultValue = "1")Integer pn,
			Model model) {
		//�ⲻ��һ����ҳ��ѯ��
		//����PageHelper��ҳ���
		//�ڲ�ѯ֮ǰֻ��Ҫ����,����ҳ�룬�Լ�ÿҳ�Ĵ�С
		PageHelper.startPage(pn, 5);
		//startPage��������������ѯ����һ����ҳ��ѯ
		List<Emplovee> emps = employeeService.getAll();
		//ʹ��pageInfo��װ��ѯ��Ľ����ֻ��Ҫ��pageInfo����ҳ������ˡ�
		//��װ����ϸ�ķ�ҳ��Ϣ�����������ǲ�ѯ���������ݣ����Դ���������ʾ��ҳ��
		PageInfo page = new PageInfo(emps,5);
		model.addAttribute("pageInfo", page);
		
		return "list";
	}
	
}




