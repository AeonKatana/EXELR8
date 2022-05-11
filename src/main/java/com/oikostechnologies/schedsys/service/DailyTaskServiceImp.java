package com.oikostechnologies.schedsys.service;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.oikostechnologies.schedsys.entity.ActivityLog;
import com.oikostechnologies.schedsys.entity.DailyTask;
import com.oikostechnologies.schedsys.entity.Department;
import com.oikostechnologies.schedsys.entity.NotifyUser;
import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.model.DailyTaskModel;
import com.oikostechnologies.schedsys.model.PeopleModel;
import com.oikostechnologies.schedsys.repo.ActlogRepo;
import com.oikostechnologies.schedsys.repo.DailyTaskRepo;
import com.oikostechnologies.schedsys.repo.DepartmentRepo;
import com.oikostechnologies.schedsys.repo.NotifyUserRepo;
import com.oikostechnologies.schedsys.repo.UserRepo;

@Service
public class DailyTaskServiceImp implements DailyTaskService {
	
	@Autowired
	private DailyTaskRepo dailyrepo;

	@Autowired
	private UserRepo userrepo;
	
	@Autowired
	private NotifyUserRepo notifrepo;
	
	@Autowired
	private ActlogRepo actrepo;
	
	
	@Autowired
	private DepartmentRepo departmentRepo;
	
	@Override
	@Transactional
	public DailyTaskModel addMultiTask(DailyTaskModel model, User user) {
		User assign = user;
		
			
			if(model.getWho().size() > 0) {
				for(PeopleModel pm : model.getWho()) {
					
					User person = userrepo.findById(pm.getId()).orElse(null); 
						
					if(person != null) {
						DailyTask task = new DailyTask();
						task.setNote(model.getNote());
						task.setUntil(LocalDate.parse(model.getUntil()));
						task.setDescription(model.getTaskdetail());
						task.setStarteddate(LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")));
						task.setUser(person);
						task.setAssignedby(assign);
						task.setDepartment(departmentRepo.findById(model.getDeptid()).orElse(null));
						dailyrepo.save(task);
						
						User sa = userrepo.findSuperAdmin();
						NotifyUser superadmin = new NotifyUser();
						superadmin.setUserid(sa.getId());
						superadmin.setUsername(sa.fullname());
						superadmin.setDaily(task);
						notifrepo.save(superadmin);
					}
										
				}
				for(PeopleModel pm : model.getWho()) {
					User person = userrepo.findById(pm.getId()).orElse(null); 
					if(person != null) {
						ActivityLog actlog = new ActivityLog();
						actlog.setAction("has assigned a daily task for " + person.fullname());
						actlog.setUser(assign);
						actlog.setTarget(model.getTaskdetail());
						actlog.setTargetlink("#");
						actlog.setDate(LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")));
						actrepo.save(actlog);
					}
				}
				
			}else {
				DailyTask task = new DailyTask();
				task.setNote(model.getNote());
				task.setUntil(LocalDate.parse(model.getUntil()));
				task.setDescription(model.getTaskdetail());
				task.setStarteddate(LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")));
				task.setUser(assign);
				task.setAssignedby(assign);
				task.setDepartment(departmentRepo.findById(model.getDeptid()).orElse(null));
				dailyrepo.save(task);
				
				User sa = userrepo.findSuperAdmin();
				NotifyUser superadmin = new NotifyUser();
				superadmin.setUserid(sa.getId());
				superadmin.setUsername(sa.fullname());
				superadmin.setDaily(task);
				ActivityLog actlog = new ActivityLog();
				actlog.setAction("has created a daily task");
				actlog.setUser(assign);
				actlog.setTarget(model.getTaskdetail());
				actlog.setTargetlink("#");
				actlog.setDate(LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")));
				actrepo.save(actlog);
			}
			
		
		return null;
	}

	@Override
	public DailyTaskModel addMyTask(DailyTaskModel model, User user) {
		
		User assign = userrepo.findById(model.getUserid()).orElse(user);
		DailyTask task = new DailyTask();
		task.setNote(model.getNote());
		task.setUntil(LocalDate.parse(model.getUntil()));
		task.setStarteddate(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDateTime());
		task.setDescription(model.getTaskdetail());
		task.setUser(assign);
		task.setAssignedby(user);
		dailyrepo.save(task);
		User sa = userrepo.findSuperAdmin();  // Add Superadmin to be notified on default
		NotifyUser superadmin = new NotifyUser();
		superadmin.setUserid(sa.getId());
		superadmin.setUsername(sa.fullname());
		superadmin.setDaily(task);
		notifrepo.save(superadmin);
		ActivityLog compcreate = new ActivityLog(); // Create an activity log for this event
		if(model.getUserid() != user.getId()) {
			compcreate.setAction("has assigned a daily task for " + assign.fullname());
		}
		else {
			compcreate.setAction("has created a daily task");
		}
		compcreate.setTarget(model.getTaskdetail());
		compcreate.setTargetlink("#");
		compcreate.setUser(user);
		compcreate.setDate(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDateTime());
		
		actrepo.save(compcreate);
	return model;
	}
		
		
		
		
		
		
		

	

	@Override
	public List<DailyTask> getMyTasksByUserId(long id) {
		
		User user = userrepo.findById(id).orElse(null);
		List<DailyTask> mytasks = dailyrepo.findAllByUserAndDoneFalseOrderByStarteddateDesc(user);
		
		for(DailyTask dt : mytasks) {
			System.out.println("My Task :" + dt.getTitle());
		}
		
		return mytasks;
	}

	@Override
	public long countCompleted() {
		return dailyrepo.count();
	}
	
	@Override
	public long countDailyToday() {		
		return dailyrepo.countDailyToday(Date.valueOf( ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDate()));
	}

	@Override
	public long countCompanyDaily(String company) {
		
		return dailyrepo.countDailyToday(Date.valueOf(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDate()), company);
	}

	@Override
	public long countMyDaily(long id) {
		return dailyrepo.countDailyToday(Date.valueOf(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDate()), id);
	}

	@Override
	public long countOverdue() {
		return dailyrepo.countOverdue(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDate());
	}

	@Override
	public String markAsDone(User user,boolean status, long id) {
		DailyTask task = dailyrepo.findById(id).orElse(null);
		
		
		
		if(task == null) {
			return "An Error Occured, (This task is non-existent)";
		}
		else if(user.getId() == task.getUser().getId() || user.role().equalsIgnoreCase("SUPERADMIN") || ((user.companyname().equals(task.getDepartment().companyname()) &&  user.role().equalsIgnoreCase("MASTERADMIN")))){
			ActivityLog compcreate = new ActivityLog(); // Create an activity log for this event
			compcreate.setTarget(task.getDescription());
			compcreate.setTargetlink("/department/" + task.getDepartment().getDeptname() + "/trash");
			compcreate.setUser(user);
			compcreate.setDate(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDateTime());
			
			task.setDone(status);
			dailyrepo.save(task);
			if(task.getUntil().isBefore(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDate())){
				compcreate.setAction(" has completed a task but late");
				actrepo.save(compcreate);
				return "Task Submitted Late";
			}
			else {
				compcreate.setAction(" has completed a task on time");
				actrepo.save(compcreate);
				return "Task Submitted On Time!";
			}
			
		}
		else if(user.getId() != task.getUser().getId()) {
			throw new AccessDeniedException("Only SUPERADMIN and MASTERADMIN can only control other's tasks");
		}
		
		else {
			return "Nothing happened";
		}
		
		
	}

	@Override
	@Transactional
	public String deleteTask(User user, long taskid) {
		DailyTask task = dailyrepo.findById(taskid).orElse(null);
		if(task == null) {
			return "An Error Occured, (This task is non-existent)";
		}
		else if(user.getId() == task.getUser().getId() || user.role().equalsIgnoreCase("SUPERADMIN") || ((user.companyname().equals(task.getDepartment().companyname()) &&  user.role().equalsIgnoreCase("MASTERADMIN")))){
			ActivityLog compcreate = new ActivityLog(); // Create an activity log for this event
			compcreate.setAction(" has deleted a task");
			compcreate.setTarget(task.getDescription());
			compcreate.setTargetlink("#");
			compcreate.setUser(user);
			compcreate.setDate(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDateTime());
			actrepo.save(compcreate);
			dailyrepo.delete(task);
		}
		else if(user.getId() != task.getUser().getId()) {
			throw new AccessDeniedException("Only SUPERADMIN and MASTERADMIN can only control other's tasks");
		}
		
		
		
		return "Task Deleted!";
	}

	@Override
	public List<DailyTask> getAllTask() {
		return dailyrepo.findAllByDoneFalseOrderByIdDesc();
	}

	@Override
	public List<DailyTask> searchTask(String search) {
		
		return dailyrepo.searchTask(search);
	}


	@Override
	@Transactional
	public String editTask(long id, DailyTaskModel dailyedit, User user) {
		
		DailyTask task = dailyrepo.findById(id).orElse(null);
		if(task == null) {
			return "An error occured. Please refresh the page";
		}
		
		else {
				task.setNote(dailyedit.getNote());
				task.setUntil(LocalDate.parse(dailyedit.getUntil()));
				task.setStarteddate(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDateTime());
				task.setDescription(dailyedit.getTaskdetail());
				dailyrepo.save(task);
				
				ActivityLog editlog = new ActivityLog();
				editlog.setAction("has edited a task");
				editlog.setTarget(task.getDescription());
				editlog.setDate(LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")));
				editlog.setTargetlink("#");
				editlog.setUser(user);
				actrepo.save(editlog);
				}
		return "Task Updated!";
	}

	@Override
	public DailyTaskModel getTask(long id) {
		DailyTask task = dailyrepo.findById(id).orElse(null);
		DailyTaskModel model = new DailyTaskModel();
		model.setTitle(task.getTitle());
		model.setUntil(task.getUntil().toString());
		model.setTaskdetail(task.getDescription());
		
		return model;
	}

	@Override
	public long countOverdueByUser(long id) {
		return dailyrepo.countOverdueByUser(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDate(), id);
	}
	@Override
	public long countOverdueByCompany(String company) {
		return dailyrepo.countOverdueByCompany(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDate(), company);
	}

	@Override
	public long countOverdueByDepartment(String department) {
		// TODO Auto-generated method stub
		return dailyrepo.countOverdueByDepartment(department, ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDate());
	}
	@Override
	public long countDailyByDeparment(String department) {
		// TODO Auto-generated method stub
		return  dailyrepo.countDailyByDepartment(department);
	}
	@Override
	public long countDailyAssignedToMeBySomeoneElse(long id) {
		// TODO Auto-generated method stub
		return dailyrepo.countDailyAssignedToMeBySomeoneElse(id);
	}

	@Override
	public List<DailyTask> getAllOverdue() {
		// TODO Auto-generated method stub
		return dailyrepo.getAllByDoneFalseOrderByUntilDesc( ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDate());
	}

	@Override
	public List<DailyTask> getAllOverdueByUser(User user) {
		// TODO Auto-generated method stub
		return dailyrepo.getAllByDoneFalseAndUserOrderByUntilDesc( ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDate(), user.getId());
	}

	@Override
	public List<DailyTask> getAllOverdueByCompany(String company) {
		// TODO Auto-generated method stub
		return dailyrepo.getAllByDoneFalseAndCompanyOrderByUntilDesc(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDate(), company);
	}

	@Override
	public List<DailyTask> getAllDailyByUser(User user) {
		// TODO Auto-generated method stub
		return dailyrepo.getAllDailyByUser(Date.valueOf( ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDate()), user.getId());
	}

	@Override
	public List<DailyTask> getAllDailyByCompany(String company) {
		// TODO Auto-generated method stub
		return dailyrepo.getAllDailyByCompany(Date.valueOf( ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDate()), company);
	}

	@Override
	public String undoTask(User user, long id) {
		DailyTask task = dailyrepo.findById(id).orElse(null);
		if(task == null) {
			return "This task doesn't exist. Reloading...";
		}
		
		else if(user.getId() == task.getUser().getId() || user.role().equalsIgnoreCase("SUPERADMIN") || ((user.companyname().equals(task.getDepartment().companyname()) &&  user.role().equalsIgnoreCase("MASTERADMIN")))){
			task.setDone(false);
			dailyrepo.save(task);
			
			ActivityLog actlog = new ActivityLog();
			actlog.setAction("has undid a task completion");
			actlog.setTarget(task.getDescription());
			actlog.setDate(LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")));
			actlog.setTargetlink("/department/" + task.getDepartment().getDeptname());
			actlog.setUser(user);
			actrepo.save(actlog);
		}
		else if(user.getId() != task.getUser().getId()) {
			throw new AccessDeniedException("Only SUPERADMIN and MASTERADMIN can only control other's tasks");
		}
		
		return "Task undid";
	}

	@Override
	public String softDeleteTask(User user, long id) {
		DailyTask task = dailyrepo.findById(id).orElse(null);
		if(task == null) {
			return "This task doesn't exist. Reloading...";
		}
		
		else if(user.getId() == task.getUser().getId() || user.role().equalsIgnoreCase("SUPERADMIN") || ((user.companyname().equals(task.getDepartment().companyname()) &&  user.role().equalsIgnoreCase("MASTERADMIN")))){
			task.setDeleted(true);
			dailyrepo.save(task);
			
			
			ActivityLog actlog = new ActivityLog();
			actlog.setAction("has deleted a task temporarily");
			actlog.setTarget(task.getDescription());
			actlog.setDate(LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")));
			actlog.setTargetlink("/department/" + task.getDepartment().getDeptname() + "/trash");
			actlog.setUser(user);
			actrepo.save(actlog);
		}
		else if(user.getId() != task.getUser().getId()) {
			throw new AccessDeniedException("Only SUPERADMIN and MASTERADMIN can only control other's tasks");
		}
		return "Task deleted";
	}

	@Override
	public String undoDelete(User user, long id) {
		DailyTask task = dailyrepo.findById(id).orElse(null);
		if(task == null) {
			return "This task doesn't exist. Reloading...";
		}
		else if(user.getId() == task.getUser().getId() || user.role().equalsIgnoreCase("SUPERADMIN") || ((user.companyname().equals(task.getDepartment().companyname()) &&  user.role().equalsIgnoreCase("MASTERADMIN")))){
			task.setDeleted(false);
			dailyrepo.save(task);
			
			ActivityLog actlog = new ActivityLog();
			actlog.setAction("has undid a task deletion");
			actlog.setTarget(task.getDescription());
			actlog.setDate(LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")));
			actlog.setTargetlink("/department/" + task.getDepartment().getDeptname());
			actlog.setUser(user);
			actrepo.save(actlog);
		}
		else if(user.getId() != task.getUser().getId()) {
			throw new AccessDeniedException("Only SUPERADMIN and MASTERADMIN can only control other's tasks");
		}
		
		return "Task undid";
		
	}

	@Override
	public List<DailyTask> findAllDeletedTaskByDepartment(Department d) {
	
		return dailyrepo.findByDoneFalseAndDeletedTrueAndDepartment(d);
	}

	@Override
	public List<DailyTask> findAllDoneTaskByDepartment(Department d) {
		
		return dailyrepo.findByDoneTrueAndDeletedFalseAndDepartment(d);
	}
	

}
