package com.springrest.week17;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.week17.model.Employee;
import com.springrest.week17.model.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class EmpController {

	@Autowired
	EmployeeService eService;

	@PostMapping("/add/employee")
	public String addEmployee(@RequestParam int id,@RequestParam String firstName,@RequestParam String lastName,@RequestParam String email,Authentication authentication,SecurityContextHolder auth) {

		String acceptedRole="admin";
		boolean roleFound=false;

		Collection<?extends GrantedAuthority> grantedRoles=auth.getContext()
				.getAuthentication().getAuthorities();

		for(int i=0;i<grantedRoles.size();i++) {

			String role=grantedRoles.toArray()[i].toString();
			//System.out.println("my role "+ role);

			if(role.equalsIgnoreCase(acceptedRole)) {
				roleFound=true;
			}
		}
		if(roleFound) {
			Employee empfind=eService.getById(id);

			if(empfind!=null) {
				return "duplicate id";
			}
			else {
				Employee emp=new Employee(id,firstName,lastName,email);
				eService.add(emp);
				return "added-successfully";
			}
		}
		else {
			return "No Access-- User can't add employee";
		}

	}

	@GetMapping("/emp/showAllEmployee")
	public List<Employee> showAll(){
		return eService.getAll();

	}
	@GetMapping("emp/showById")
	public Employee showbyId(@RequestParam int id) {
		return eService.getById(id);
	}

	@PutMapping("emp/update")
	public String updateEmp(@RequestParam int id,@RequestParam String firstName,@RequestParam String lastName,@RequestParam String email,Authentication authentication,SecurityContextHolder auth) {
		String acceptedRole="admin";
		boolean roleFound=false;

		Collection<?extends GrantedAuthority> grantedRoles=auth.getContext()
				.getAuthentication().getAuthorities();

		for(int i=0;i<grantedRoles.size();i++) {

			String role=grantedRoles.toArray()[i].toString();
			if(role.equalsIgnoreCase(acceptedRole)) {
				roleFound=true;
			}
		}

		if(roleFound) {
			Employee empfind=eService.getById(id);

			if(empfind==null) {
				return "duplicate id-- no employee found in the given id";
			}
			else {
				Employee emp=new Employee(id,firstName,lastName,email);
				eService.add(emp);
				return "employee-updated successfully";
			}
		}else {
			return "No Access-- User can't Edit employee";
		}
	}

	@DeleteMapping("emp/delete")
	public String empDel(@RequestParam int id,Authentication authentication,SecurityContextHolder auth) {

		String acceptedRole="admin";
		boolean roleFound=false;

		Collection<?extends GrantedAuthority> grantedRoles=auth.getContext()
				.getAuthentication().getAuthorities();

		for(int i=0;i<grantedRoles.size();i++) {

			String role=grantedRoles.toArray()[i].toString();
			if(role.equalsIgnoreCase(acceptedRole)) {
				roleFound=true;
			}
		}
		if(roleFound) {
			Employee empfind=eService.getById(id);

			if(empfind==null) {
				return "duplicate id-- no employee found in the given id";
			}else {
				Employee empdel=new Employee(id,"","","");
				eService.delete(empdel);
				return "employe - deleted";
			}
		}
		else {
			return "user can't delete the employee-- Access Denied";
		}
	}

	@GetMapping("/emp/filterbyName")
	@Operation(summary="this will show all the data filtered with firstname")
	public List<Employee> empByFirstName(@RequestParam String firstName) {
		return eService.filterByFirstName(firstName);
	}

	@GetMapping("emp/getBySort")
	public List<Employee> sortbyname(@RequestParam String colums,int direction){
		if(direction==1) {
			return eService.getBySort(colums, Direction.ASC);
		}else {
			return eService.getBySort(colums, Direction.DESC);
		}
	}
}
