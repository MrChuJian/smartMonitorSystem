package com.fjy.smartMonitorSystem.api;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fjy.smartMonitorSystem.model.Entity;
import com.fjy.smartMonitorSystem.model.Sensor;
import com.fjy.smartMonitorSystem.model.Vo.SensorVo;
import com.fjy.smartMonitorSystem.service.SensorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "传感器控制器", tags = { "传感器控制器" })
@Controller
@RequestMapping("/sensor")
public class SensorController {
	
	@Autowired
	private SensorService sensorService;

	@ApiOperation(value = "上传传感器数据", notes = "type:数据的类型(自定义,比如温度叫temperature)")
	@RequestMapping(value = "/{type}", method = RequestMethod.POST)
	public ResponseEntity<Entity<String>> save(@PathVariable String type,@RequestParam(required=true) Double data){
		Sensor sensor = new Sensor();
		sensor.setType(type);
		sensor.setData(data);
		sensor.setCreateTime(new Timestamp(System.currentTimeMillis()));
		if (sensorService.sava(sensor)) {
			return Entity.success(sensor.toString());
		};
		return Entity.failure(0, "保存失败哦");
	}
	
	@ApiOperation(value = "查询一段时间内的type传感器数据", notes = "type:问陈琦传了什么type \n\r unit:y M d h m s")
	@RequestMapping(value = "/{type}/last/{time}/{unit}", method = RequestMethod.GET)
	public ResponseEntity<Entity<List<Sensor>>> getByTimeAndType(@PathVariable String type,@PathVariable String unit,@PathVariable Integer time){
		SensorVo sensorVo = new SensorVo();
		sensorVo.setType(type);
		sensorVo.setTime(time);
		sensorVo.setUnit(unit);
		List<Sensor> sensors = sensorService.getByTimeAndType(sensorVo);
		return Entity.success(sensors);
	}
	
	@ApiOperation(value = "查询所有type传感器数据", notes = "type:问陈琦传了什么type \n\r")
	@RequestMapping(value = "/{type}", method = RequestMethod.GET)
	public ResponseEntity<Entity<List<Sensor>>> getAllByType(@PathVariable String type){
		List<Sensor> sensors = sensorService.getByType(type);
		return Entity.success(sensors);
	}
	
	@ApiOperation(value = "查询所有传感器数据", notes = "查询所有传感器数据")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Entity<List<Sensor>>> getAll(){
		List<Sensor> sensors = sensorService.getAll();
		return Entity.success(sensors);
	}
}
