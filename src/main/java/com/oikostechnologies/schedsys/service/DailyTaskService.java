package com.oikostechnologies.schedsys.service;

import java.util.List;

import com.oikostechnologies.schedsys.entity.DailyTask;
import com.oikostechnologies.schedsys.entity.Department;
import com.oikostechnologies.schedsys.entity.User;
import com.oikostechnologies.schedsys.model.DailyTaskModel;

public interface DailyTaskService {

	public DailyTaskModel addMyTask(DailyTaskModel model, User user);
	public List<DailyTask>
	getMyTasksByUserId(long id);
	public long countCompleted();
	public long countDailyToday();
	public long countCompanyDaily(String company);
	public long countMyDaily(long id);
	public long countDailyByDeparment(String department);
	public long countOverdue();
	public long countOverdueByUser(long id);
	public long countOverdueByCompany(String company);
	public long countOverdueByDepartment(String department);
	public long countDailyAssignedToMeBySomeoneElse(long id);
	public String markAsDone(User user,boolean status, long id);
	public String deleteTask(User user, long taskid);
	public List<DailyTask> getAllTask();
	public List<DailyTask> searchTask(String search);
	public String editTask(long id, DailyTaskModel dailyedit,User user);
	public DailyTaskModel getTask(long id);
	public DailyTaskModel addMultiTask(DailyTaskModel model, User user);
	public List<DailyTask> getAllOverdue();
	public List<DailyTask> getAllOverdueByUser(User user);
	public List<DailyTask> getAllOverdueByCompany(String company);
	public List<DailyTask> getAllDailyByUser(User user);
	public List<DailyTask> getAllDailyByCompany(String company);
	public String undoTask(User user, long id);
	public String softDeleteTask(User user, long id);
	public String undoDelete(User user, long id);
	public List<DailyTask> findAllDeletedTaskByDepartment(Department d);
	public List<DailyTask> findAllDoneTaskByDepartment(Department d);

} 
