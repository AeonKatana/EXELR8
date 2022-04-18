package com.oikostechnologies.schedsys.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oikostechnologies.schedsys.entity.ActivityLog;
import com.oikostechnologies.schedsys.entity.DailyTask;
import com.oikostechnologies.schedsys.entity.NotifyUser;
import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.model.DailyTaskModel;
import com.oikostechnologies.schedsys.model.PeopleModel;
import com.oikostechnologies.schedsys.repo.ActlogRepo;
import com.oikostechnologies.schedsys.repo.DailyTaskRepo;
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
	
	@Override
	public DailyTaskModel addMyTask(DailyTaskModel model, User user) {
		
	if(model.getWho().size() > 0) {
	for(PeopleModel who : model.getWho()) {
		
		DailyTask task = new DailyTask();
		task.setTitle(model.getTitle());
		task.setNote(model.getNote());
		task.setUntil(LocalDate.parse(model.getUntil()));
		task.setStarteddate(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDate());
		task.setDescription(model.getTaskdetail());
		task.setUser(userrepo.findById(who.getId()).orElse(user));
		task.setAssignedby(user);
		dailyrepo.save(task);
		User sa = userrepo.findSuperAdmin();  // Add Superadmin to be notified on default
		NotifyUser superadmin = new NotifyUser();
		superadmin.setUserid(sa.getId());
		superadmin.setUsername(sa.fullname());
		superadmin.setDaily(task);
		notifrepo.save(superadmin);
		
		for(PeopleModel pm : model.getMentions()) { // Get all personnels that was mentioned and save them
			NotifyUser mention = new NotifyUser();
			mention.setUserid(pm.getId());
			mention.setUsername(pm.getName());
			mention.setDaily(task);
			notifrepo.save(mention);
		}
		
		
		ActivityLog compcreate = new ActivityLog(); // Create an activity log for this event
		if(model.getWho().size() > 0) {
			compcreate.setAction("has assigned a daily task for " + who.getName());
		}
		else {
			compcreate.setAction("has created a daily task");
		}
		compcreate.setTarget(task.getTitle());
		compcreate.setTargetlink("#");
		compcreate.setUser(user);
		compcreate.setDate(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDateTime());
		
		actrepo.save(compcreate);
		}
	}else {
		DailyTask task = new DailyTask();
		task.setTitle(model.getTitle());
		task.setNote(model.getNote());
		task.setUntil(LocalDate.parse(model.getUntil()));
		task.setStarteddate(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDate());
		task.setDescription(model.getTaskdetail());
		task.setUser(user);
		task.setAssignedby(user);
		dailyrepo.save(task);
		User sa = userrepo.findSuperAdmin();  // Add Superadmin to be notified on default
		NotifyUser superadmin = new NotifyUser();
		superadmin.setUserid(sa.getId());
		superadmin.setUsername(sa.fullname());
		superadmin.setDaily(task);
		notifrepo.save(superadmin);
		
		for(PeopleModel pm : model.getMentions()) { // Get all personnels that was mentioned and save them
			NotifyUser mention = new NotifyUser();
			mention.setUserid(pm.getId());
			mention.setUsername(pm.getName());
			mention.setDaily(task);
			notifrepo.save(mention);
		}
		
		
		ActivityLog compcreate = new ActivityLog(); // Create an activity log for this event
		
		
		compcreate.setAction("has created a daily task");
		compcreate.setTarget(task.getTitle());
		compcreate.setTargetlink("#");
		compcreate.setUser(user);
		compcreate.setDate(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDateTime());
		
		actrepo.save(compcreate);
		}
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
		return dailyrepo.countDailyToday(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDate());
	}

	@Override
	public long countCompanyDaily(String company) {
		return dailyrepo.countDailyToday(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDate(), company);
	}

	@Override
	public long countMyDaily(long id) {
		return dailyrepo.countDailyToday(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDate(), id);
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
		else {
			ActivityLog compcreate = new ActivityLog(); // Create an activity log for this event
			compcreate.setTarget(task.getTitle());
			compcreate.setTargetlink("#");
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
		
		
	}

	@Override
	public String deleteTask(User user, long taskid) {
		DailyTask task = dailyrepo.findById(taskid).orElse(null);
		if(task == null) {
			return "An Error Occured, (This task is non-existent)";
		}
		else {
			
			ActivityLog compcreate = new ActivityLog(); // Create an activity log for this event
			compcreate.setAction(" has deleted a task");
			compcreate.setTarget(task.getTitle());
			compcreate.setTargetlink("#");
			compcreate.setUser(user);
			compcreate.setDate(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDateTime());
			actrepo.save(compcreate);
			dailyrepo.delete(task);
			
		}
		
		
		return "Task Deleted!";
	}

	@Override
	public List<DailyTask> getAllTask() {
		return dailyrepo.findAllByDoneFalseOrderByStarteddateDesc();
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

			
			
			ActivityLog edittask = new ActivityLog(); // Create an activity log for this event
			
			edittask.setAction("has edited a task");
			edittask.setTarget(task.getTitle());
			edittask.setTargetlink("#");
			edittask.setDate(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDateTime());
			edittask.setUser(user);
			actrepo.save(edittask);
			
			
			notifrepo.deleteAllByDaily(task); // Delete notif user in this task
			
			if(dailyedit.getWho().size() > 0) {
				for(PeopleModel who : dailyedit.getWho()) {
					task.setTitle(dailyedit.getTitle());
					task.setUntil(LocalDate.parse(dailyedit.getUntil()));
					task.setUser(userrepo.findById(who.getId()).orElse(null));
					task.setDescription(dailyedit.getTaskdetail());
				
					
					
				
				for(PeopleModel pm : dailyedit.getMentions()) { // Get all personnels that was mentioned and save them
					NotifyUser mention = new NotifyUser();
						mention.setUserid(pm.getId());
						mention.setUsername(pm.getName());
						mention.setDaily(task);
						notifrepo.save(mention);
					
				}
				User sa = userrepo.findSuperAdmin();
				NotifyUser superadmin = new NotifyUser();
				superadmin.setDaily(task);
				superadmin.setUsername(sa.fullname());
				superadmin.setUserid(sa.getId());
				
				notifrepo.save(superadmin);
				
				ActivityLog compcreate = new ActivityLog(); // Create an activity log for this event
				
				compcreate.setAction("changed assignation for a daily task to " + who.getName());
				compcreate.setTarget(task.getTitle());
				compcreate.setTargetlink("#");
				compcreate.setDate(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDateTime());
				compcreate.setUser(user);
				actrepo.save(compcreate);
				}
			}
			else {
				task.setTitle(dailyedit.getTitle());
				task.setNote(dailyedit.getNote());
				task.setUntil(LocalDate.parse(dailyedit.getUntil()));
				task.setStarteddate(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Manila")).toLocalDate());
				task.setDescription(dailyedit.getTaskdetail());
				task.setUser(user);
				dailyrepo.save(task);
				
				
				for(PeopleModel pm : dailyedit.getMentions()) { // Get all personnels that was mentioned and save them
					NotifyUser mention = new NotifyUser();
						mention.setUserid(pm.getId());
						mention.setUsername(pm.getName());
						mention.setDaily(task);
						notifrepo.save(mention);
					
				}
				
				User sa = userrepo.findSuperAdmin();
				NotifyUser superadmin = new NotifyUser();
				superadmin.setDaily(task);
				superadmin.setUsername(sa.fullname());
				superadmin.setUserid(sa.getId());
				
				notifrepo.save(superadmin);
			}
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
	

}
