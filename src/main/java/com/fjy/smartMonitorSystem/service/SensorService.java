package com.fjy.smartMonitorSystem.service;

import java.util.List;

import com.fjy.smartMonitorSystem.model.Location;
import com.fjy.smartMonitorSystem.model.Sensor;
import com.fjy.smartMonitorSystem.model.Vo.SensorVo;

public interface SensorService {
	public boolean sava(Sensor sensor);

	public List<Sensor> getByType(String type);

	public List<Sensor> getAll();

	public List<Sensor> getByTimeAndType(SensorVo sensorVo);

	public void saveLocation(Location location);

	public Location getLocation();
}
