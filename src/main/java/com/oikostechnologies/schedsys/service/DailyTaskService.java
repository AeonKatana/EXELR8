package com.oikostechnologies.schedsys.service;

import java.time.LocalDate;
import java.util.List;

import com.oikostechnologies.schedsys.entity.DailyTask;
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
	public long countOverdue();
	public String markAsDone(User user,boolean status, long id);
	public String deleteTask(User user, long taskid);
	public List<DailyTask> getAllTask();
	public List<DailyTask> searchTask(String search);
	public String editTask(long id, DailyTaskModel dailyedit,User user);
	public DailyTaskModel getTask(long id);
}
