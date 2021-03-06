package com.eagle.community.annoucement.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

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

import com.eagle.community.annoucement.entity.Annoucement;
import com.eagle.community.annoucement.entity.Pagination;
import com.eagle.community.annoucement.service.AnnoucementService;
import com.eagle.community.cultural_recreation.entity.RecreationInfo;

/*
 * 处理访问通知公告的controller
 * */

@Controller
@RequestMapping(value = "/annoucement")
public class AnnoucementController {

	@Resource
	private AnnoucementService annoucementService;

	private static final Logger logger = LogManager
			.getLogger(AnnoucementController.class);

	/*
	 * 定义所有用户可以操访问的请求 包括各种查询
	 */

	// 根据通知公告的id查找并返回（以json的形式）
	@RequestMapping(value = "/{id}.json", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody Annoucement getAnnoucementJson(
			@PathVariable("id") long id) {
		logger.info("getAnnocementJson is invoked ");
		Annoucement an = annoucementService.getAnnoucement(id);
		an.setCount(an.getCount() + 1);
		annoucementService.updateAnnoucement(an);
		return an;
	}

	// 根据通知公告的id查找并返回（以视图的形式）
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public ModelAndView getAnnoucement(@PathVariable("id") Long id) {
		ModelAndView view = new ModelAndView("main/servicepart/serviceinfocontent");// 视图的名称自定义
		Annoucement an = annoucementService.getAnnoucement(id);
		an.setCount(an.getCount() + 1);
		annoucementService.updateAnnoucement(an);
		view.addObject("annoucement", an);
		return view;
	}

	// 获取指定条数的Annoucement，(供主页显示)
//	@RequestMapping(value = "/some/{num}", method = RequestMethod.GET)
//	@ResponseStatus(value = HttpStatus.OK)
//	public @ResponseBody List<Annoucement> getSomeJson(
//			@PathVariable("num") int num) {
//		logger.info("getSomeJson");
//		return Annoucement.getAnnoucements(num);
//	}
	
	// 请求主页文化娱乐的信息
	@RequestMapping(value = "/some/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
	public ModelAndView getConmunityService(@PathVariable("id") int id) {
		ModelAndView model = new ModelAndView("main/servicepart/service");
		List<Annoucement> list = annoucementService.getAnnoucements(id);
		model.addObject("Serviceinfo", list);
		return model;

	}
	

	// 请求一页的通知公告
	@RequestMapping(value = "/list/{currentPage}/{pageSize}")
	public ModelAndView getOnPageAnnoucement(
			@PathVariable("currentPage") int currentPage,
			@PathVariable("pageSize") int pageSize) {
		ModelAndView view = new ModelAndView("main/servicepart/listserviceinfo");// 视图的名字待定
		Pagination pagination = annoucementService.getOnePageAC(currentPage,
				pageSize, true);
		view.addObject("annoucement_pageInfo", pagination);
		return view;
	}

	/* 一下定义的操作请求都是管理员权限才能执行的操作 包括增加修改和删除以及跳转 */

	//添加服务信息
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String startAddCultureRecreation() {
		return "admin/service/addserviceinfo";
	}
	
	
	// 创建一个通知公告
	@RequiresRoles("admin")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Annoucement createAnnoucement(
			@RequestBody Annoucement an) {
		logger.info("createAnnoucement is invoked ");
		Date now = new Date(System.currentTimeMillis());
		an.setReleaseTime(now);
		return annoucementService.addAnnoucement(an);
	}

	// 更新一条已经存在的通知公告
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Annoucement updateAnnoucement(
			@RequestBody Annoucement an) {
		logger.info("updateAnnoucement is invoked !");
		return annoucementService.updateAnnoucement(an);
	}

	// 根据id删除一条已知的通知公告
//	@RequiresRoles("admin")
//	@RequestMapping(value = "/delete/{id}")
//	public String deleteAnnoucement(@PathVariable("id") long id) {
//		logger.info("deleteAnnoucement is invoked !");
//		annoucementService.deleteAnnoucement(id);
//		return "delsuccess";// 自定义的删除成功的返回视图
//	}
	
	// 根据id删除一条已知的文化娱信息
	@RequiresRoles(value = "admin")
	@RequestMapping(value = "/deleteseinfo/{id}", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public String deleteNews(@PathVariable("id") int id) {
		Annoucement an = annoucementService.getAnnoucement(id);
		if (an != null) {
			if (annoucementService.deleteAnnoucement(id)) {
				logger.info("delete annoucement success");
				return "delsuccess";
			}
			else {
				logger.info("delete annoucement failed");
				return "delfailure";
			}
		}

		return "delfailure";
	}

	
	// 查看的链接
		@RequiresRoles("admin")
		@RequestMapping(value = "/query", method = RequestMethod.GET)
		public String queryNews() {
			return "admin/service/queryallserviceinfo";
		}
		
		
	// 具体查看某一条新闻
	@RequiresRoles("admin")
	@RequestMapping(value = "/queryserviceinfo/{id}", method = RequestMethod.GET)
	public ModelAndView queryNews(@PathVariable("id") int id) {
		ModelAndView view = new ModelAndView("admin/service/serviceinfoquery");
		Annoucement an = annoucementService.getAnnoucement(id);
		view.addObject("queryseinfocontent", an);
		return view;
			}	
		
		
	//供管理员查看管理方便的分页请求
	@RequestMapping(value = "/query/{currentPage}/{pageSize}")
	public ModelAndView getOnPageAnnoucements(
			@PathVariable("currentPage") int currentPage,
			@PathVariable("pageSize") int pageSize) {
		ModelAndView view = new ModelAndView("admin/service/alistserviceinfo");//视图名称待定
		Pagination pagination =annoucementService.getOnePageAC(currentPage, pageSize, true);
		view.addObject("admin_annoucement_pageInfo", pagination);
		return view;
	}
	
	/*管理员管理操作的跳转部分*/
	

	public AnnoucementService getAnnoucementService() {
		return annoucementService;
	}

	public void setAnnoucementService(AnnoucementService annoucementService) {
		this.annoucementService = annoucementService;
	}

}
