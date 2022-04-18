package com.oikostechnologies.schedsys.repo;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.projection.UserDeptDTO;

public interface UserRepo extends JpaRepository<User, Long> {

	@Query("Select u from User u join u.userrole ur join ur.role r where r.rolename = :role")
	Page<User> getUsersByRole(@Param("role")String role, Pageable pageable);
	
//	@Query("Select u.id as id , d.id as deptid , t.id as taskid , user.firstname as firstname, d.deptname as deptname, t.taskname as taskname"
//			+ ", sum((case when td.done = true then 1 else 0 end)) as completed , count(td.id) as totaltask from User u join u.userdepartment ud join ud.department d join"
//			+ " d.tasks t join t.taskdetails td join u.userrole ur join ur.role r where r.id = :role group by"
//			+ " d.deptname, u.firstname, t.taskname")
//	List<UserDTO> getResult(@Param("role") long id);
//	
	@Query("Select u.firstname as firstname , d.deptname as deptname, t.taskname as taskname, count(td.id) as totaltask from User u"
			+ " join u.userdepartment ud join ud.department d join u.userrole ur join ur.role r join d.tasks t join t.taskdetails td"
			+ " where r.id = '2' group by u.firstname, d.deptname , t.taskname")
	List<UserDeptDTO> getResult(); // Get Managers and the tasks assigned to the department they belong as well as the number of task_detail under that task.
								  // Actually it should be supervisors cause' manager role is a main account role just like Superadmins, Masteradmin, executive
									// and RankFile, that means anyone can be a supervisor when inside the department who putted them in by the higher role
								  //   If manager creates his/her own dept he can only add people with roles lower than manager role which is currently rankfile
								 //    then he can assign a supervisor from the people he/she added to his/her dept that means there can only be one supervisor
								//     per department , and most likely the one who created the department is the supervisor (that made assigning supervisor useless lol)
	
	
							//
	
	@Query("Select u from User u join u.userrole ur join ur.role r order by :param")
	List<User> getAllByOrderBy(@Param("param") String param);
	
	User findByEmail(String email);
	
	Page<User> findByFirstnameContainingOrLastnameContaining(String firstname, String lastname , Pageable page);
	
	Page<User> findAllByFirstnameContaining(String firstname ,Pageable page);
	
	@Query("Select u from User u join u.company c where c.compname =:compname")
	List<User> getAllByCompanyname(@Param("compname")String compname);
	
	@Query("Select u from User u join u.userrole ur join ur.role r where r.rolename = 'SUPERADMIN'")
	User findSuperAdmin();
	
}

