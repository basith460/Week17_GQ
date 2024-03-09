package com.springrest.week17.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;





@Service
public class EmployeeService {
	
	@Autowired
	EmployeeRepository erepo;
	
	public void add(Employee employee) {
		erepo.save(employee);
	}
	
	public List<Employee> getAll(){
		return erepo.findAll();
	}

	public Employee getById(int id) {
		Optional<Employee> optional=erepo.findById(id);

		Employee temp=null;
		if(!optional.isEmpty()) {
			temp=optional.get();
		}
		return temp;
	}
	
	public Employee findById(int id) {
		if(erepo.findById(id).isEmpty()) {
			return null;
		}
		else {
			return erepo.findById(id).get();
		}
	}
	
	public void delete(Employee prod){
		erepo.delete(prod);
	}
	
	public List<Employee> filterByFirstName(String searchKey){
		
		Employee dummyEmployee=new Employee();
		dummyEmployee.setFirstName(searchKey);
		
		ExampleMatcher em=ExampleMatcher.matching().withMatcher("firstName",ExampleMatcher.GenericPropertyMatchers.contains()).withIgnorePaths("id","lastName","email");
		
		Example<Employee> example=Example.of(dummyEmployee,em);
		
		return erepo.findAll(example);
	}
	
	public List<Employee> getBySort(String column,Direction direction){
		return erepo.findAll(Sort.by(direction, column));
	}

	
}
