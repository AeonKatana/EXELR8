package com.oikostechnologies.schedsys;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.oikostechnologies.schedsys.entity.Company;
import com.oikostechnologies.schedsys.entity.DailyTask;
import com.oikostechnologies.schedsys.entity.Department;
import com.oikostechnologies.schedsys.entity.NotifyUser;
import com.oikostechnologies.schedsys.entity.Role;
import com.oikostechnologies.schedsys.entity.Task;
import com.oikostechnologies.schedsys.entity.TaskDetail;
import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.entity.UserDepartment;
import com.oikostechnologies.schedsys.entity.UserRole;
import com.oikostechnologies.schedsys.entity.UserTask;
import com.oikostechnologies.schedsys.entity.view.Quickview;
import com.oikostechnologies.schedsys.model.QuickViewModel;
import com.oikostechnologies.schedsys.projection.UserDeptDTO;
import com.oikostechnologies.schedsys.repo.CompanyRepo;
import com.oikostechnologies.schedsys.repo.DailyTaskRepo;
import com.oikostechnologies.schedsys.repo.DepartmentRepo;
import com.oikostechnologies.schedsys.repo.NotifyUserRepo;
import com.oikostechnologies.schedsys.repo.QuickViewRepo;
import com.oikostechnologies.schedsys.repo.RoleRepo;
import com.oikostechnologies.schedsys.repo.TaskDetailRepo;
import com.oikostechnologies.schedsys.repo.TaskRepo;
import com.oikostechnologies.schedsys.repo.UserDepartmentRepo;
import com.oikostechnologies.schedsys.repo.UserRepo;
import com.oikostechnologies.schedsys.repo.UserRoleRepo;
import com.oikostechnologies.schedsys.repo.UserTaskRepo;
import com.oikostechnologies.schedsys.security.MyUserDetails;
import com.oikostechnologies.schedsys.service.DailyTaskService;
import com.oikostechnologies.schedsys.service.UserService;

@SpringBootTest
class SchedSysApplicationTests {
	
	@Autowired
	private RoleRepo rolerepo;
	
	@Autowired
	private UserRepo userrepo;
	
	@Autowired
	private UserRoleRepo userrolerepo;
	
	@Autowired
	private CompanyRepo comrepo;
	
	@Autowired
	private TaskRepo taskrepo;	
	
	@Autowired
	private DepartmentRepo deptrepo;
	
	@Autowired
	private TaskDetailRepo detailrepo;
	
	@Autowired
	private UserTaskRepo usertaskrepo;

	@Autowired
	private UserDepartmentRepo userdeptrepo;
	
	@Autowired
	private QuickViewRepo qrepo;
	
	@Autowired
	private DailyTaskRepo dailyrepo;
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private DailyTaskService dailyservice;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private NotifyUserRepo notifrepo;
	
	void contextLoads() {
		
		Company com = comrepo.findById(1L).orElse(null);
		
		
		Department department = deptrepo.findById(1L).orElse(null);
		
		
		Task task = Task.builder()
				.taskname("Build a Barn")
				.sdate(LocalDate.now())
				.edate(LocalDate.of(2022, 10, 25))
				.build();
		
		task.setDepartment(department);
		
		deptrepo.save(department);
		taskrepo.save(task);
		
		
	}
	
	void testCount() {
		
		System.out.println(detailrepo.countCompleted());
		
	}
	
	void createTaskForUser() {
		
		Task task = taskrepo.findById(2L).orElse(null);
		
		TaskDetail detail = TaskDetail.builder()
							.description("Count nails")
							.sdate(LocalDateTime.now())
							.edate(LocalDateTime.of(2022, 12, 25, 8, 30))
							.done(false)
							.task(task)
							.build();
		User user = userrepo.findById(2L).orElse(null);
		
		
		UserTask usertask = UserTask.builder()
							.user(user)
							.taskdetail(detail)
							.build();
		
		detailrepo.save(detail);
		usertaskrepo.save(usertask);
		
	}

	void createUser() {
		
		
		User user = User.builder()
					.firstname("Rean")
					.lastname("Schwarzer")
					.contactno(639564412627L)
					.email("rean@gmail.com")
					.password(passwordEncoder.encode("12345").toCharArray())
					.enabled(true)
					.build();
		
		Role role = Role.builder()
					.rolename("SUPERADMIN")  // Create a new role (this is already built in) but we created it since it doesnt exist yet
					.build();
		
		
		UserRole userrole = UserRole.builder() // Give the new user a role
						.role(role)  // MASTERADMIN Role
						.user(user) // Our Newly Created User
						.build();
		
		userrepo.save(user);
		rolerepo.save(role);
		
		userrolerepo.save(userrole);
		
		
		
		
	}
	
	
	void assignUserToDept() {
		
		User user = userrepo.findById(2L).orElse(null);
		Department d = deptrepo.findById(1L).orElse(null);
		
		UserDepartment ud = UserDepartment.builder()
							.department(d)
							.user(user)
							.build();
		
		userdeptrepo.save(ud);
		
	}
	
	void assignMultipleUserToTask() {
		
		long[] ids = {1L ,2L}; // IDs of a user got from @Mention or Checkbox using @RequestBody and AJAX, when assigning a task to someone
	
		Task task = taskrepo.findById(1L).orElse(null); // The Main Task 
		
		TaskDetail detail = TaskDetail.builder() // A new Task
				.description("Create an admin page")
				.sdate(LocalDateTime.now())
				.edate(LocalDateTime.of(2022, 5, 17, 22, 30))
				.done(false)
				.task(task)
				.build();
		
		detailrepo.save(detail);
		
		for(int i = 0 ; i < 2;i++) { 							// Iterate through array of IDs (the size depends on array size)		
			User user = userrepo.findById(ids[i]).orElse(null); // Finds all user with that id base from the array
			UserTask usertask = UserTask.builder()				
								.user(user) 
								.taskdetail(detail)       // Assign the users to this task
								.build();
			usertaskrepo.save(usertask); 
		}
		
		
		
	}
	
	void quickview() {
		
		List<QuickViewModel> models = new ArrayList<>(); // Table
		
		Role role = rolerepo.findById(1L).orElse(null);
		List<UserRole> useroles = userrolerepo.findAllByRole(role);
 		for(UserRole ur : useroles) {
			Set<UserDepartment> ud = ur.getUser().getUserdepartment();
			for(UserDepartment u : ud) {
				for(Task t : u.getDepartment().getTasks()) {
					QuickViewModel model = new QuickViewModel();
					model.setManager(u.username());
					model.setDepartment(u.departmentname());
					model.setTask(t.getTaskname());
					models.add(model);
				}
			}
		}
		
 		for(QuickViewModel m : models) {
 			System.out.println(m.getManager());
 			System.out.println(m.getDepartment());
 			System.out.println(m.getTask());
 		}
		
	}
	
	
//	void getMasterAdmins() {
//		
//		for(User user : userrepo.getUsersByRole(1L)) {
//			System.out.println(user.getFirstname() + " " + user.getLastname());
//			System.out.println(user.getUserrole().stream().findFirst().get().getRole().getRolename());
//		}
//		
//		
//	}

	void getTaskDeptNull() {
		
		for(Quickview view : qrepo.findAllByDeptidIsNull()){
			System.out.println(view.getTaskname());
		}
		
	}
	
	void getView() {
		
		List<UserDeptDTO> userdto = userrepo.getResult();
		for(UserDeptDTO u : userdto) {
			System.out.println(u.getFirstname() + " : " + u.getDeptname() + " : " + u.getTaskname() + " : " + u.getTotaltask());
		}
		
		
	}
	
	void getCompanyandMasterAdmin() {
		
//		User u = userrepo.findAll().stream().filter(x -> x.role().equals("MASTERADMIN")).findFirst().get();
//		System.out.println(u.fullname());
//		System.out.println(u.companyname());
		
//		User u = userrepo.getUsersByRole(1L).stream().findFirst().get();
//		System.out.println(u.getFirstname());
//		System.out.println(u.companyname());
//		System.out.println(u.getCompany().usercount());
		
	}
	
	void countDailiesForToday() {
		System.out.println(dailyservice.countDailyToday());
	}
	
	void getCompany() {
		
		for(Company c : comrepo.findByCompnameContaining("samplecompany", PageRequest.of(0, 1))) {
			System.out.println(c.getCompname());
			System.out.println(c.usercount());
			System.out.println(c.getUser().stream().filter(x -> x.role().equalsIgnoreCase("MASTERADMIN")).findFirst().get().fullname());
		}
		
	}

	void findByEmail() {
		User user = userrepo.findByEmail("rean@gmail.com");
		System.out.println(user.fullname());
	}

	void findPersonnelByCompanyName(){
		User myuser = userrepo.findByEmail("alibaba@gmail.com");
		MyUserDetails user = new MyUserDetails(myuser);
		System.out.println("Company Name : " + myuser.companyname());
		List<User> users = userservice.getAllByCompany(user);
		List<User> users2 = userrepo.getAllByCompanyname("Alibaba");
		System.out.println("Size : " + users.size());
		System.out.println("Size : " + users2.size());
	}
	
	void getAllUsers() {
		for(User u : userrepo.findAll(PageRequest.of(0, 10))) {
			System.out.println(u.role());
		}
	}
	
	void deleteUser() {
		 User user = userrepo.findById(10L).orElse(null);
		 userrepo.delete(user);
	}
	
	void deleteNotif() {
		DailyTask task = dailyrepo.findById(42L).orElse(null);
	}
	
	void findByCompanyName() {
		
		Company company = comrepo.findByCompname("Special Support Section");
		System.out.println("Company :" + company.getCompname());
		System.out.println("Master Admin :" + company.masteradmin());
		
	}
	
	
	
}
