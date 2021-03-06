package com.eagle.community.health_profile.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.eagle.community.health_profile.entity.HealthProfile;
import com.eagle.community.health_profile.service.HealthProfileService;

@Controller
@RequestMapping(value = "/healthProfile")
public class HealthProfileController {

	@Resource
	private HealthProfileService healthProfileService;

	private static final Logger logger = LogManager
			.getLogger(HealthProfileController.class);

	/* 用户可以操作的部分 */

	
	
	// 根据用户的id查看其健康档案信息，以json形式返回数据
	@RequestMapping(value = "/{id}.json", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody HealthProfile getProfileJson(
			@PathVariable("id") String userId) {
		logger.info("getProfileJson is invoked ");
		// 待定的用户权限控制

		return healthProfileService.getHealthProfile(userId);
	}

	// 根据用户的id查看其档案信息，并放回ModelAndView显示
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView getProfile(@PathVariable("id") String userId) {
		logger.info("getProfile is invoked ");
		System.out.println(userId);
		ModelAndView view = new ModelAndView("main/personpart/personhealthprofile");// 试图名陈待定
		HealthProfile profile = healthProfileService.getHealthProfile(userId);
		view.addObject("profile", profile);// model中的属性名等待自定义

		return view;
	}
	
	

	/* 具有管理员权限才能操作的部分 */

	@RequiresRoles(value = "admin")
	@RequestMapping(value = "/add/{userid}", method = RequestMethod.GET)
	public ModelAndView startAddHeathlProfile(@PathVariable("userid") String userid) {
		ModelAndView hp = new ModelAndView("admin/user/addhealthinfo");
		hp.addObject("userid",userid);
		System.out.println(userid);
		return hp;
	}
	
	// 为某个用户创建健康档案
	@RequiresRoles("admin")
	@RequestMapping(value = "/create/{userId}", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody HealthProfile createProfile(
			@PathVariable("userId") String userId,
			@RequestBody HealthProfile profile) {
		logger.info("createProfile is invoked !");
		// 待定的管理员权限控制部分
		System.out.println("////////////////////////////"+userId);
		HealthProfile healthProfile = healthProfileService.addHealthProfile(
				userId, profile);
		return healthProfile;
	}
	
	@RequiresRoles("admin")
	@RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
	public ModelAndView adminGetProfile(@PathVariable("id") String userId) {
		logger.info("getProfile is invoked ");
		System.out.println(userId);
		ModelAndView view = new ModelAndView("admin/user/healthinfoquery");// 试图名陈待定
		HealthProfile healthprofile = healthProfileService.getHealthProfile(userId);
		view.addObject("healthprofile", healthprofile);// model中的属性名等待自定义

		return view;
	}

	// 为某个用户更新健康档案信息,并以json形式返回更新过后的档案信息
	@RequiresRoles("admin")
	@RequestMapping(value = "/update/{userId}", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody HealthProfile updateProfile(
			@PathVariable("userId") String userId,
			@RequestBody HealthProfile profile) {
		logger.info("updatePforile is invoked ");

		// 待定的管理员权限实现部分

		String id = healthProfileService.updateHealthProfile(profile);
		HealthProfile healthProfile = healthProfileService.getHealthProfile(id);

		return healthProfile;
	}

	public HealthProfileService getHealthProfileService() {
		return healthProfileService;
	}

	public void setHealthProfileService(
			HealthProfileService healthProfileService) {
		this.healthProfileService = healthProfileService;
	}

}
