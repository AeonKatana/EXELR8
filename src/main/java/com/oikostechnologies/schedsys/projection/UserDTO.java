package com.oikostechnologies.schedsys.projection;

import java.time.LocalDate;

public interface UserDTO {

	public long getId();
	public long getDeptid();
	public long getTaskid();
	public String getFirstname();
	public String getDeptname();
	public String getTaskname();
	public LocalDate getSdate();
	public LocalDate getEdate();
	public int getCompleted();
	public int getTotaltask();
	
}
