package com.org.kap.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.xml.sax.SAXException;
import com.org.kap.dto.AnsherSheet;
import com.org.kap.dto.CourseEntry;
import com.org.kap.dto.Course_Histry;
import com.org.kap.dto.Deduct_list;
import com.org.kap.dto.ExamCodeResult;
import com.org.kap.dto.ExamQuestions;
import com.org.kap.dto.ExamSchedule;
import com.org.kap.dto.Exam_Result;
import com.org.kap.dto.GetQuestionList;
import com.org.kap.dto.GetRechargeHistory;
import com.org.kap.dto.GetResultHistory;
import com.org.kap.dto.Question_details;
import com.org.kap.dto.Student_Results;
import com.org.kap.dto.TabQuestionMaster;
import com.org.kap.dto.User_Recharge;
import com.org.kap.dto.Userdata;
import com.org.kap.entity.User_Details;
import com.org.kap.service.RegistrationService;

@Controller
public class ApplicationController {
	
	private static 	org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ApplicationController.class);
	 
	private static SimpleDateFormat inSDF = new SimpleDateFormat("dd/mm/yyyy");
	private static SimpleDateFormat outSDF = new SimpleDateFormat("yyyy-mm-dd");
	private static List<ExamQuestions> questions = new ArrayList<ExamQuestions>();
	private static int totalmarks = 0;
	private static int passmarks = 0;
	private static int duration = 0;
	private static String xml;
	private static String papercode;
	private static String papername;
	private static List<ExamQuestions> questionsview = new ArrayList<ExamQuestions>();
	@Autowired
	private RegistrationService registrationService;
	@Autowired
	GenerateXml generate;

	@RequestMapping(value = "home.do", method = RequestMethod.GET)
	public String homepage(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Redirect to home.jsp");
		
		try {
			model.addAttribute("userdata", new Userdata());
			model.put("errormsg", request.getParameter("errormsg"));
		} catch (Exception e) {
			logger.info("Exception in home.do " + e);

		}
		return "home";
	}

	@RequestMapping(value = "loginProcess.do", method = RequestMethod.POST)
	public String loginpage(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("loginProcess.do");
		String errormsg = "Incorrect User id or Password";
		Userdata returnObj = null;

		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			Userdata userdata = new Userdata();
			userdata.setPhoneNo(username);
			userdata.setPassword(password);
			returnObj = registrationService.authenticateUserDetails(userdata);
			if (returnObj != null) {
				String position = "";
				if (returnObj.getUserlogintype() == 1) {
					position = "Admin";
				} else if (returnObj.getUserlogintype() == 2) {
					position = "ME";
				} else if (returnObj.getUserlogintype() == 3) {
					position = "Teacher";
				} else if (returnObj.getUserlogintype() == 4) {
					position = "Parents";
				} else {
					position = "Student";
				}
				session = null;
				request.getSession(false).invalidate();
				session = request.getSession(true);
				session.setAttribute("Logged", "Yes");
				session.setAttribute("userId", returnObj.getTumId());
				session.setAttribute("userfullname", returnObj.getUserName()
						+ "(" + position + ")");
				session.setAttribute("MobileNo", returnObj.getPhoneNo());
				session.setAttribute("logintype", returnObj.getUserlogintype());
				return "redirect:userhome.do";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:home.do?errormsg=" + errormsg;
	}

	@RequestMapping(value = "setQuestion.do", method = RequestMethod.GET)
	public String setQuestion(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			logger.info("Inside setQuestion.do");
			List<CourseEntry> courseEntryList = registrationService
					.getCourseDetails(Integer.parseInt(session.getAttribute(
							"userId").toString()));

			model.addAttribute("courseEntryList", courseEntryList);
		} catch (Exception e) {
			logger.info("Exception in setQuestion" + e);
		}
		return "setQuestion";
	}

	@RequestMapping(value = "examSchedule.do", method = RequestMethod.GET)
	public String examSchedule(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			questions.clear();
			logger.info("Inside examSchedule.do");
			List<TabQuestionMaster> tabQuestionMasterList = registrationService
					.getTabQuestionMasterDetails(Integer.parseInt(session
							.getAttribute("userId").toString()));
			model.addAttribute("tabQuestionMasterList", tabQuestionMasterList);
		} catch (Exception e) {
			logger.info("Exception in examSchedule" + e);
		}
		return "examSchedule";
	}

	@RequestMapping(value = "courseEntry.do", method = RequestMethod.GET)
	public String courseEntry(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {

			questions.clear();
			logger.info("Inside courseEntry.do");
			model.addAttribute("courseEntryData", new CourseEntry());
		} catch (Exception e) {
			logger.info("Exception in courseEntry" + e);
		}
		return "courseEntry";
	}

	@RequestMapping(value = "examPaper.do", method = RequestMethod.GET)
	public String examPaper(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {

			logger.info("Inside examPaper.do");
		} catch (Exception e) {
			logger.info("Exception in examPaper" + e);
		}
		return "examPaper";
	}

	@RequestMapping(value = "searchExamCode.do", method = RequestMethod.POST)
	public String searchExamCode(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model)
			throws ParseException {
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			questions.clear();
			logger.info("Inside searchExamCode.do");
			String examCode = request.getParameter("examcode");
			List<Question_details> questionlist = registrationService
					.getquestion_answer(Integer.parseInt(session.getAttribute(
							"userId").toString()), examCode);
			if (questionlist.size() == 0) {
				session.setAttribute("msg", "1");
				return "startExam";
			} else {
				int blance = registrationService.validateamount(examCode);
				if (blance >= 5) {
					Date date = new Date();
					String modifiedDate = new SimpleDateFormat("yyyy-MM-dd")
							.format(date);
					logger.info(modifiedDate);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String enddate = questionlist.get(0).getEnd_date();
					Date end = sdf.parse(enddate);
					Date curr = sdf.parse(modifiedDate);
					if (end.compareTo(curr) > 0) {
						model.addAttribute("questionlist", questionlist);
						xml = questionlist.get(0).getQuestion_answer_xml();
						return "questionDetails";
					} else {
						session.setAttribute("msg", "2");
						return "startExam";
					}

				} else {
					session.setAttribute("msg", "3");
					return "startExam";
				}
			}
		} catch (Exception e) {
			logger.info("Exception in searchExamCode" + e);
		}
		return "startExam";
	}

	@RequestMapping(value = "result.do", method = RequestMethod.GET)
	public String result(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			logger.info("Inside result.do");
		} catch (Exception e) {
			logger.info("Exception in result" + e);
		}
		return "result";
	}

	@RequestMapping(value = "startExam.do", method = RequestMethod.GET)
	public String startExam(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			logger.info("Inside startExam.do");
		}

		catch (Exception e) {
			logger.info("Exception in startExam" + e);
		}
		return "startExam";
	}

	@RequestMapping(value = "studentResult.do", method = RequestMethod.GET)
	public String studentResult(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			logger.info("Inside studentResult.do");
		}

		catch (Exception e) {
			logger.info("Exception in studentResult" + e);
		}
		return "studentResult";
	}

	@RequestMapping(value = "registeruser.do", method = RequestMethod.GET)
	public String Addmeget(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			logger.info("Inside registeruser .do");
		}

		catch (Exception e) {
			logger.info("Exception in registeruser" + e);
		}
		return "registrationForAll";
	}

	@RequestMapping(value = "userhome.do", method = RequestMethod.GET)
	public String Adminhome(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		String redirectPage = "";
		try {

			redirectPage = "homeforall";

			logger.info("Inside userhome.do");
		}

		catch (Exception e) {
			logger.info("Exception in userhome" + e);
		}
		return redirectPage;
	}

	@RequestMapping(value = "finalexam.do", method = RequestMethod.POST)
	public String finalexam(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model)
			throws ParserConfigurationException, IOException, SAXException,
			org.xml.sax.SAXException {
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		logger.info("Inside finalexam.do");
		try {
			String duration1 = request.getParameter("duration");
			String totalmarks1 = request.getParameter("totalmarks");
			String passmarks1 = request.getParameter("passmarks");
			String examcode = request.getParameter("papercode");
			String papername1 = request.getParameter("papername");
			duration = Integer.parseInt(duration1);
			totalmarks = Integer.parseInt(totalmarks1);
			passmarks = Integer.parseInt(passmarks1);
			papercode = examcode;
			papername = papername1;
			int checkfinalattempt = registrationService
					.checkfinalsubmition(examcode, Integer.parseInt(session
							.getAttribute("userId").toString()));
			logger.info(checkfinalattempt);
			if (checkfinalattempt > 0) {
				session.setAttribute("msg", "4");
				model.addAttribute("usrmsg",
						"You have already attend this Exam.");
				return "startExam";
			} else {
				int no_of_attempt = registrationService.get_no_of_attempt(
						examcode, Integer.parseInt(session.getAttribute(
								"userId").toString()));
				if (no_of_attempt == 3) {
					session.setAttribute("msg", "4");
					model.addAttribute("usrmsg",
							"Your Total Attempt has been finished.");
					return "startExam";
				} else {
					questions.clear();
					questions = generate.parsexml(xml);
					if (no_of_attempt == 2) {
						int deduct_amount = registrationService.deduct_amount(
								Integer.parseInt(session.getAttribute("userId")
										.toString()), 5, 0);
						registrationService.insertdeductionhistory(Integer
								.parseInt(session.getAttribute("userId")
										.toString()), 5,
								"Exam Fee of Exam Code:" + examcode, examcode);
						logger.info(deduct_amount);
					}
					session.setAttribute("duration", duration);
					session.setAttribute("totalmarks", totalmarks);
					session.setAttribute("passmarks", passmarks);
					session.setAttribute("papercode", papercode);
					session.setAttribute("papername", papername);
					session.setAttribute("noofquestions", questions.size());
					model.addAttribute("totalquestions", questions);
					model.addAttribute("totalnoofquestions", questions.size());

					return "takeExam";
				}
			}
		} catch (Exception e) {
			logger.info("Exception in userhome" + e);
		}
		return "startExam";
	}

	@RequestMapping(value = "/submitquestion.do", method = RequestMethod.POST)
	public String submitans(HttpServletRequest request, HttpSession session,
			Model model) {
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			// ArrayList<String> slno=new ArrayList<String>();
			ArrayList<String> question = new ArrayList<String>();
			ArrayList<String> opt1 = new ArrayList<String>();
			ArrayList<String> opt2 = new ArrayList<String>();
			ArrayList<String> opt3 = new ArrayList<String>();
			ArrayList<String> opt4 = new ArrayList<String>();
			ArrayList<String> rightans = new ArrayList<String>();
			ArrayList<String> stuAns = new ArrayList<String>();
			int totalrightans = 0;
			int totalwrongans = 0;
			int totalquestion = questions.size();
			for (int i = 1; i <= totalquestion; i++) {
				String studentquestionsslnumber = request
						.getParameter("sl" + i);
				String studentquestions = request.getParameter("question" + i);
				String studentaption1 = request.getParameter("studentoption1"
						+ i);
				String studentaption2 = request.getParameter("studentoption2"
						+ i);
				String studentaption3 = request.getParameter("studentoption3"
						+ i);
				String studentaption4 = request.getParameter("studentoption4"
						+ i);
				String studentaptionanswers = request.getParameter("testans"
						+ i);
				String finalans = request.getParameter("finalans" + i);
				question.add(studentquestions);
				opt1.add(studentaption1);
				opt2.add(studentaption2);
				opt3.add(studentaption3);
				opt4.add(studentaption4);
				rightans.add(finalans);
				stuAns.add(studentaptionanswers);
				if (studentaptionanswers != null) {
					if (studentaptionanswers.equals(finalans)) {
						totalrightans = totalrightans + 1;
					} else {
						totalwrongans = totalwrongans + 1;
					}

				} else {
					totalwrongans = totalwrongans + 1;

				}

			}
			logger.info("Total Right: " + totalrightans);
			logger.info("Total Wrong: " + totalwrongans);

			String studentxml = generate.generatexmlforStudentanswer(question,
					opt1, opt2, opt3, opt4, rightans, stuAns);
			logger.info(studentxml);
			String remarks = "";
			int onequestionmarks = totalmarks / question.size();
			int totalstudentmarks = (onequestionmarks * totalrightans);
			if (totalstudentmarks >= passmarks) {
				remarks = "Pass";
			} else {
				remarks = "Fail";
			}
			model.addAttribute("totalmarks", totalmarks);
			model.addAttribute("passedmarks", passmarks);
			model.addAttribute("obtainedmarks", totalstudentmarks);
			Exam_Result exampaper = new Exam_Result();
			exampaper.setObtainedmarks(totalstudentmarks);
			exampaper.setPapercode(papercode);
			exampaper.setPassmarks(passmarks);
			exampaper.setTotalmarks(totalmarks);
			exampaper.setRemarks(remarks);
			exampaper.setResultXml(studentxml);
			exampaper.setStudentid(Integer.parseInt(session.getAttribute(
					"userId").toString()));
			int checkfeepaid = registrationService
					.checkdeductionHistory(Integer.parseInt(session
							.getAttribute("userId").toString()), papercode);
			if (checkfeepaid == 0) {
				registrationService.deduct_amount(Integer.parseInt(session
						.getAttribute("userId").toString()), 5, 0);
				registrationService.insertdeductionhistory(Integer
						.parseInt(session.getAttribute("userId").toString()),
						5, "Exam Fee of Exam Code:" + papercode, papercode);
			}
			registrationService.submitresult(exampaper);
			model.addAttribute("remarks", remarks);
			questions.clear();
		} catch (Exception e) {
			logger.info("Exception in submitquestion" + e);
		}
		return "studentFinalresult";
	}

	@RequestMapping(value = "submitsetQuestion.do", method = RequestMethod.POST)
	public String submitsetQuestion(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model,
			@ModelAttribute("questionMaster") TabQuestionMaster questionMaster) {
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			logger.info("Inside submitsetQuestion");
			String coursename = request.getParameter("coursename");
			String questionspapername = request
					.getParameter("questionPaperName");
			String[] questions = request.getParameterValues("questions");
			String[] option1 = request.getParameterValues("Option1");
			String[] option2 = request.getParameterValues("Option2");
			String[] option3 = request.getParameterValues("Option3");
			String[] option4 = request.getParameterValues("Option4");
			String[] rightanswers = request.getParameterValues("answer");
			String lanuuage = request.getParameter("questionlanguage");
			String xml = generate.generatexml(questions, option1, option2,
					option3, option4, rightanswers);
			TabQuestionMaster questionpaper = new TabQuestionMaster();
			questionpaper.setCourseid(Integer.parseInt(coursename));
			questionpaper.setQuestionPaperName(questionspapername);
			questionpaper.setQuestionAnswerXml(xml);
			questionpaper.setInsertedBy(Integer.parseInt(session.getAttribute(
					"userId").toString()));
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
			String strDate = sdf.format(cal.getTime());
			String papercode = coursename + questionpaper.getInsertedBy()
					+ strDate;
			questionpaper.setQuestionPaperCode(papercode);
			questionpaper.setTqmId(questions.length);
			questionpaper.setLanguage(lanuuage);
			int i = registrationService.submitSetQuestionData(questionpaper);
			if (i == 1) {
				model.addAttribute("question",
						"Question Seccessfully set by Paper Code " + papercode);
				session.setAttribute("msg", "0");
			} else {
				session.setAttribute("msg", "1");
			}
		} catch (Exception e) {
			logger.info("Exception in submitsetQuestion" + e);
		}
		return "setQuestion";
	}

	@RequestMapping(value = "submitexamSchedule.do", method = RequestMethod.POST)
	public String submitexamSchedule(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		logger.info("Inside submitexamSchedule");
		try {
			String papercode = request.getParameter("exampapercode");
			String startdate = formatDate(request.getParameter("examstartdate"));
			String enddate = formatDate(request.getParameter("examenddate"));
			String total = request.getParameter("totalmarks");
			String passmarks = request.getParameter("passmarks");
			String duration = request.getParameter("duration");
			String id[] = papercode.split("@");
			ExamSchedule examSchedule = new ExamSchedule();
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
			String strDate = sdf.format(cal.getTime());
			String examcode = generate.generatEexamCode(strDate + "|"
					+ papercode + "|"
					+ session.getAttribute("userId").toString());
			examSchedule.setExamcode(examcode);
			examSchedule.setDuration(duration);
			examSchedule.setEndDate(enddate);
			examSchedule.setExamScheduleCode(id[0]);
			examSchedule.setMasks(passmarks);
			examSchedule.setStartDate(startdate);
			examSchedule.setTotalMasks(total);
			examSchedule.setTesId(Integer.parseInt(id[1]));
			examSchedule.setInserted_by(Integer.parseInt(session.getAttribute(
					"userId").toString()));
			;
			int i = registrationService.submitExamScheduleData(examSchedule);
			if (i == 1) {
				session.setAttribute("msg", "0");
				model.addAttribute("usrmsg",
						"Successfully Schedule with Exam Code: " + examcode);
			} else {
				session.setAttribute("msg", "1");
			}
			List<TabQuestionMaster> tabQuestionMasterList = registrationService
					.getTabQuestionMasterDetails(Integer.parseInt(session
							.getAttribute("userId").toString()));
			model.addAttribute("tabQuestionMasterList", tabQuestionMasterList);
		} catch (Exception e) {
			logger.info("Exception in submitexamSchedule" + e);
		}
		return "examSchedule";
	}

	@RequestMapping(value = "submitcourseEntry.do", method = RequestMethod.POST)
	public String submitcourseEntry(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			logger.info("Inside submitcourseEntry");
			CourseEntry courseEntry = new CourseEntry();
			courseEntry.setInsertedBy(Integer.parseInt(session.getAttribute(
					"userId").toString()));
			courseEntry.setCourseName(request.getParameter("coursename"));
			courseEntry.setDescription(request
					.getParameter("coursedescription"));
			int returnObject = registrationService
					.submitCourseEntryData(courseEntry);
			if (returnObject == 2) {
				session.setAttribute("msg", "1");
			} else if (returnObject == 1) {
				session.setAttribute("msg", "0");
			} else {
				session.setAttribute("msg", "2");
			}
		} catch (Exception e) {
			logger.info("Exception in submitcourseEntry" + e);
		}

		return "courseEntry";
	}

	@RequestMapping(value = "submituser.do", method = RequestMethod.POST)
	public String submituser(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			logger.info("Inside submituser post");
			String usertype = request.getParameter("usertype");

			String fullname = request.getParameter("fullname");
			String gender = request.getParameter("gender");
			String mobile = request.getParameter("mobile");
			String password = request.getParameter("password");
			String address = request.getParameter("assress");
			String email = request.getParameter("email");
			String pin = request.getParameter("pincode");
			String dob = request.getParameter("dob");
			int responsedata = 0;
			int logintype = 0;
			if (usertype.equals("me")) {
				logintype = 2;
			} else if (usertype.equals("teacher")) {
				logintype = 3;

			} else if (usertype.equals("parent")) {
				logintype = 4;
			} else {
				logintype = 5;
			}
			User_Details user = new User_Details();
			user.setAddress(address);
			user.setBalance_amount(0);
			user.setDob(dob);
			user.setEmail(email);
			user.setFullname(fullname);
			user.setGender(gender);
			user.setLogintype(logintype);
			user.setMobno(mobile);
			user.setPassword(password);
			user.setPin(pin);
			user.setRegistered_by(Integer.parseInt(session.getAttribute(
					"userId").toString()));
			int mobilecount=registrationService.validateMobile(mobile);
			if(mobilecount==0){
			responsedata = registrationService.submitregistrationdata(user);
			if (responsedata == 1) {
				session.setAttribute("msg", "0");
			} else {
				session.setAttribute("msg", "1");
			}
			}
			else{
				session.setAttribute("msg", "2");	
			}
			logger.info("Inside submituser.do");
		} catch (Exception e) {
			logger.info("Exception in submituser" + e);
		}
		return "registrationForAll";

	}

	@RequestMapping(value = "logout.do", method = RequestMethod.GET)
	public String logout(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Inside Logout");
		if (!session.isNew()) {
			Enumeration<?> e = session.getAttributeNames();
			while (e.hasMoreElements()) {
				String attrName = (String) e.nextElement();
				session.removeAttribute(attrName);
				logger.info("#Session Attribute (" + attrName
						+ ") is removed from Current Session");
			}
			session.invalidate();

		}

		return "home";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/sessionOut.do")
	public String sessionOUT(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		if (!session.isNew()) {
			Enumeration<?> e = session.getAttributeNames();
			while (e.hasMoreElements()) {
				String attrName = (String) e.nextElement();
				session.removeAttribute(attrName);
				logger.info("#Session Attribute (" + attrName
						+ ") is removed from Current Session");
			}
			session.invalidate();

		}
		return "sessionOUT";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/userrecharge.do")
	public String recharge(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Inside recharge");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			int usertype = Integer.parseInt(session.getAttribute("logintype")
					.toString());
			int usertypeId = 0;
			if (usertype == 1) {
				usertypeId = 0;
			} else {
				usertypeId = Integer.parseInt(session.getAttribute("userId")
						.toString());
			}
			List<User_Recharge> uselist = registrationService
					.getRechargeUserList(usertypeId);
			int balance = registrationService.getbalance(usertypeId);
			model.addAttribute("balance", balance);
			model.addAttribute("list", uselist);
		} catch (Exception e) {
			logger.info("Exception in userrecharge" + e);
		}
		return "userRecharge";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/submitrecharge.do")
	public String submitrecharge(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Inside submitrecharge");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			int usertype = Integer.parseInt(session.getAttribute("logintype")
					.toString());
			User_Recharge recharge = new User_Recharge();
			recharge.setUserId(Integer.parseInt(request.getParameter("userid")));
			recharge.setRechargeAmt(Integer.parseInt(request
					.getParameter("rechargeamount")));
			recharge.setRechargemode((request.getParameter("rechargemode")));
			recharge.setRechargetype((request.getParameter("rechargetype")));
			recharge.setRechargedBy(Integer.parseInt(session.getAttribute(
					"userId").toString()));
			recharge.setUsertype(usertype);
			int i = registrationService.sumbmitRechargeUserList(recharge);
			int usertypeId = 0;
			if (usertype == 1) {
				usertypeId = 0;
			} else {
				usertypeId = Integer.parseInt(session.getAttribute("userId")
						.toString());
			}
			List<User_Recharge> uselist = registrationService
					.getRechargeUserList(usertypeId);
			model.addAttribute("list", uselist);
			if (i == 1) {
				session.setAttribute("msg", "0");
			} else {
				session.setAttribute("msg", "2");
			}
			int balance = registrationService.getbalance(usertypeId);
			model.addAttribute("balance", balance);
		} catch (Exception e) {
			logger.info("Exception in submitrecharge" + e);
		}
		return "userRecharge";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/studentsresult.do")
	public String student_result(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Inside student_result");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			List<Student_Results> studentreslist = registrationService
					.getstudentResult(Integer.parseInt(session.getAttribute(
							"userId").toString()));
			model.addAttribute("questionlist", studentreslist);
		} catch (Exception e) {
			logger.info("Exception in studentsresult" + e);
		}
		return "student_result";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/teacherdashboard.do")
	public String dashboardforTeacher(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Inside teacherdashboard");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			int balance = registrationService.getbalance(Integer
					.parseInt(session.getAttribute("userId").toString()));
			int coursecount = registrationService.gettotalcoursecount(Integer
					.parseInt(session.getAttribute("userId").toString()));
			int questioncount = registrationService
					.gettotalquestioncount(Integer.parseInt(session
							.getAttribute("userId").toString()));
			int examshedulecount = registrationService
					.gettoexamshedulecount(Integer.parseInt(session
							.getAttribute("userId").toString()));
			int studentcount = registrationService.gettotalstudent(Integer
					.parseInt(session.getAttribute("userId").toString()));
			model.addAttribute("balance", balance);
			model.addAttribute("totalcoursecount", coursecount);
			model.addAttribute("totalquestioncount", questioncount);
			model.addAttribute("totalexamschedulecount", examshedulecount);
			model.addAttribute("studentcount", studentcount);
		} catch (Exception e) {
			logger.info("Exception in teacherdashboard" + e);
		}
		return "dashboardforTeacher";
	}

	public static String formatDate(String inDate) {
		String outDate = "";
		if (inDate != null) {
			try {
				Date date = inSDF.parse(inDate);
				outDate = outSDF.format(date);
			} catch (Exception e) {
				logger.info("Unable to format date: " + inDate
						+ e.getMessage());
				e.printStackTrace();
			}
		}
		return outDate;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getcoursehistory.do")
	public String getcoursehistory(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Inside getcoursehistory");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			List<Course_Histry> coursehistory = registrationService
					.getcoursehistory(Integer.parseInt(session.getAttribute(
							"userId").toString()));
			model.addAttribute("coursehistory", coursehistory);
		} catch (Exception e) {
			logger.info("Exception in getcoursehistory" + e);
		}
		return "courseHistory";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/editcoursehistory.do")
	public String editcoursehistory(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Inside editcoursehistory");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			String courseId = request.getParameter("cousrseId");
			String coursename = request.getParameter("cousename");
			String coursedesc = request.getParameter("coursedesc");
			model.addAttribute("courseId", courseId);
			model.addAttribute("coursename", coursename);
			model.addAttribute("coursedesc", coursedesc);
		} catch (Exception e) {
			logger.info("Exception in editcoursehistory" + e);
		}

		return "editCourseHistory";
	}

	@RequestMapping(value = "updatecourseEntry.do", method = RequestMethod.POST)
	public String updatecourseEntry(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			logger.info("Inside updatecourseEntry");
			CourseEntry courseEntry = new CourseEntry();
			courseEntry.setCourseId(Integer.parseInt(request
					.getParameter("courseId")));
			courseEntry.setCourseName(request.getParameter("coursename"));
			courseEntry.setDescription(request
					.getParameter("coursedescription"));
			int returnObject = registrationService
					.updateCourseEntryData(courseEntry);
			if (returnObject == 1) {
				session.setAttribute("msg", "0");
			} else {
				session.setAttribute("msg", "1");
			}
		} catch (Exception e) {
			logger.info("Exception in updatecourseEntry" + e);
		}

		return "courseEntry";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/deletecoursehistory.do")
	public String deletecoursehistory(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Inside editcoursehistory");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			String courseId = request.getParameter("cousrseId");
			registrationService.deleteCourseEntryData(Integer
					.parseInt(courseId));
			List<Course_Histry> coursehistory = registrationService
					.getcoursehistory(Integer.parseInt(session.getAttribute(
							"userId").toString()));
			model.addAttribute("coursehistory", coursehistory);
		} catch (Exception e) {
			logger.info("Exception in deletecoursehistory" + e);
		}
		return "courseHistory";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/getquestionhistory.do")
	public String getquestionhistory(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Inside getquestionhistory");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			List<GetQuestionList> questionhistory = registrationService
					.getquestionhistory(Integer.parseInt(session.getAttribute(
							"userId").toString()));
			model.addAttribute("questionhistory", questionhistory);
		} catch (Exception e) {
			logger.info("Exception in getquestionhistory" + e);
		}
		return "questionHistory";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/viewquestion.do")
	public String viewquestion(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model)
			throws ParserConfigurationException, IOException, SAXException {
		logger.info("Inside viewquestion");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			int questionId = Integer.parseInt(request
					.getParameter("questionId"));
			List<GetQuestionList> question = registrationService
					.getquestion(questionId);
			questionsview.clear();
			questionsview = generate.parsexml(question.get(0).getQuestionxml());
			model.addAttribute("questionsview", questionsview);
			model.addAttribute("questionId", questionId);
			model.addAttribute("papaename", question.get(0).getPaperName());
			model.addAttribute("papaecode", question.get(0).getPapercodeName());
			model.addAttribute("lang", question.get(0).getLanguage());
		} catch (Exception e) {
			logger.info("Exception in viewquestion" + e);
		}
		return "viewQuestion";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/editquestion.do")
	public String editquestion(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model)
			throws ParserConfigurationException, IOException, SAXException {
		logger.info("Inside editquestion");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			int questionId = Integer.parseInt(request
					.getParameter("questionId"));
			List<GetQuestionList> question = registrationService
					.getquestion(questionId);
			questionsview.clear();
			questionsview = generate.parsexml(question.get(0).getQuestionxml());
			model.addAttribute("questionsview", questionsview);
			model.addAttribute("questionId", questionId);
			model.addAttribute("papaename", question.get(0).getPaperName());
			model.addAttribute("papaecode", question.get(0).getPapercodeName());
			model.addAttribute("lang", question.get(0).getLanguage());
			model.addAttribute("questionlength", questionsview.size());
			
		} catch (Exception e) {
			logger.info("Exception in editquestion" + e);
		}
		return "editQuestion";

	}

	@RequestMapping(method = RequestMethod.POST, value = "/updateQuestion.do")
	public String updateQuestion(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model)
			throws ParserConfigurationException, IOException, SAXException {
		logger.info("Inside updateQuestion");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			int questionId = Integer.parseInt(request
					.getParameter("questionId"));
			String paperCode = (request.getParameter("coursename"));
			String[] questions = request.getParameterValues("questions");
			String[] option1 = request.getParameterValues("Option1");
			String[] option2 = request.getParameterValues("Option2");
			String[] option3 = request.getParameterValues("Option3");
			String[] option4 = request.getParameterValues("Option4");
			String[] rightanswers = request.getParameterValues("answer");
			String xml = generate.generatexml(questions, option1, option2,
					option3, option4, rightanswers);
			int responseval = registrationService.updateQuestion(questionId,
					xml, questions.length);
			if (responseval == 1) {
				model.addAttribute("question",
						"Question Seccessfully set by Paper Code " + paperCode);
				session.setAttribute("msg", "0");
			} else {
				session.setAttribute("msg", "1");
			}
		} catch (Exception e) {
			logger.info("Exception in updateQuestion" + e);
		}
		return "setQuestion";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/deletequestion.do")
	public String deletequestion(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Inside deletequestion");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			int questionId = Integer.parseInt(request
					.getParameter("questionId"));
			registrationService.deletequestion(questionId);
			List<GetQuestionList> questionhistory = registrationService
					.getquestionhistory(Integer.parseInt(session.getAttribute(
							"userId").toString()));
			model.addAttribute("questionhistory", questionhistory);
		} catch (Exception e) {
			logger.info("Exception in deletequestion" + e);
		}
		return "questionHistory";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/examSchedulehistory.do")
	public String examSchedulehistory(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Inside examSchedulehistory");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			List<ExamSchedule> examschedule = registrationService
					.examSchedulehistory(Integer.parseInt(session.getAttribute(
							"userId").toString()));
			model.addAttribute("examschedule", examschedule);
		} catch (Exception e) {
			logger.info("Exception in examSchedulehistory" + e);
		}
		return "examSchedulehistory";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/deletescheduleexam.do")
	public String deletescheduleexam(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Inside deletescheduleexam");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			int scheduleId = Integer.parseInt(request
					.getParameter("scheduleId"));
			registrationService.deleteschedule(scheduleId);
			List<ExamSchedule> examschedule = registrationService
					.examSchedulehistory(Integer.parseInt(session.getAttribute(
							"userId").toString()));
			model.addAttribute("examschedule", examschedule);
		} catch (Exception e) {
			logger.info("Exception in deletescheduleexam" + e);
		}
		return "examSchedulehistory";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/fetchresult.do")
	public String fetchresult(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Inside fetchresult");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			List<GetResultHistory> examlist = registrationService
					.getResultHistory(Integer.parseInt(session.getAttribute(
							"userId").toString()));
			model.addAttribute("examlist", examlist);
		} catch (Exception e) {
			logger.info("Exception in fetchresult" + e);
		}
		return "resultList";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/studentdashboard.do")
	public String studentDashboard(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Inside studentdashboard");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			int totalexam = registrationService
					.examcount(Integer.parseInt(session.getAttribute("userId")
							.toString()), 2);
			int passexam = registrationService
					.examcount(Integer.parseInt(session.getAttribute("userId")
							.toString()), 1);
			int failexam = registrationService
					.examcount(Integer.parseInt(session.getAttribute("userId")
							.toString()), 0);
			model.addAttribute("totalexam", totalexam);
			model.addAttribute("passexam", passexam);
			model.addAttribute("failexam", failexam);
		} catch (Exception e) {
			logger.info("Exception in studentdashboard" + e);
		}
		return "dashboardForStudent";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/admindashboard.do")
	public String adminDashboard(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Inside admindashboard");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			int totalme = registrationService.gettotalMe(0);
			int gettotalteacher = registrationService.gettotalteacher(0);
			int parentcount = registrationService.gettotalparent(0);
			int studentcount = registrationService.gettotalstudent(0);
			int paidcount = registrationService.getpaidRechargeCount(Integer
					.parseInt(session.getAttribute("userId").toString()));
			int copmCount = registrationService.getCompRechargeCount(Integer
					.parseInt(session.getAttribute("userId").toString()));
			model.addAttribute("totalme", totalme);
			model.addAttribute("gettotalteacher", gettotalteacher);
			model.addAttribute("parentcount", parentcount);
			model.addAttribute("studentcount", studentcount);
			model.addAttribute("paidcount", paidcount);
			model.addAttribute("copmCount", copmCount);
		} catch (Exception e) {
			logger.info("Exception in admindashboard" + e);
		}
		return "AdminDashboard";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/viewresultbyexamcode.do")
	public String getExamcodeResult(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Inside viewresultbyexamcode");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			String examcode = request.getParameter("examCode");
			List<ExamCodeResult> examcodeResult = registrationService
					.getExamcodeResul(examcode);
			model.addAttribute("examcodeResult", examcodeResult);
			model.addAttribute("examcode", examcode);
		} catch (Exception e) {
			logger.info("Exception in viewresultbyexamcode" + e);
		}
		return "examCodeResult";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/getRechargeHistory.do")
	public String getRechargeHistory(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Inside getRechargeHistory");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			List<GetRechargeHistory> rechargeHistory = registrationService
					.getRechargeHistory(Integer.parseInt(session.getAttribute(
							"userId").toString()));
			model.addAttribute("rechargehistory", rechargeHistory);
		} catch (Exception e) {
			logger.info("Exception in getRechargeHistory" + e);
		}
		return "rechargeHistory";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/getStudentList.do")
	public String getStudentList(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Inside getStudentList");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			List<User_Details> studentList = registrationService
					.getStudentList(Integer.parseInt(session.getAttribute(
							"userId").toString()));
			model.addAttribute("studentList", studentList);
		} catch (Exception e) {
			logger.info("Exception in getStudentList" + e);
		}
		return "Student_list";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/Medashboard.do")
	public String meDashboard(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Inside Medashboard");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {

			int balance = registrationService.getbalance(Integer
					.parseInt(session.getAttribute("userId").toString()));
			int totalme = registrationService.gettotalMe(Integer
					.parseInt(session.getAttribute("userId").toString()));
			int gettotalteacher = registrationService.gettotalteacher(Integer
					.parseInt(session.getAttribute("userId").toString()));
			int parentcount = registrationService.gettotalparent(Integer
					.parseInt(session.getAttribute("userId").toString()));
			model.addAttribute("balance", balance);
			model.addAttribute("totalme", totalme);
			model.addAttribute("gettotalteacher", gettotalteacher);
			model.addAttribute("parentcount", parentcount);
		} catch (Exception e) {
			logger.info("Exception in Medashboard" + e);
		}
		return "meDashboard";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getTeacherMeList.do")
	public String getTeacherMeList(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Inside getTeacherMeList");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {

			int usertype = Integer.parseInt(request.getParameter("serachType"));
			List<User_Details> userlist = registrationService
					.getTeacherMeList(Integer.parseInt(session.getAttribute(
							"userId").toString()), usertype);
			model.addAttribute("userlist", userlist);
			if (usertype == 2) {
				model.addAttribute("usermsg", "ME List");
			} else if (usertype == 3) {
				model.addAttribute("usermsg", "Teacher List");
			} else {
				model.addAttribute("usermsg", "Parents List");
			}
		} catch (Exception e) {
			logger.info("Exception in getTeacherMeList" + e);
		}
		return "TeacherOrMeList";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/admingetTeacherMeList.do")
	public String admingetTeacherMeList(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Inside admingetTeacherMeList");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			int usertype = Integer.parseInt(request.getParameter("serachType"));
			List<User_Details> userlist = registrationService.getTeacherMeList(
					0, usertype);
			model.addAttribute("userlist", userlist);
			if (usertype == 2) {
				model.addAttribute("usermsg", "ME List");
			} else if (usertype == 3) {
				model.addAttribute("usermsg", "Teacher List");
			} else if (usertype == 4) {
				model.addAttribute("usermsg", "Parents List");
			} else {
				model.addAttribute("usermsg", "Student List");
			}
		} catch (Exception e) {
			logger.info("Exception in admingetTeacherMeList" + e);
		}
		return "TeacherOrMeList";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/adminrechargelist.do")
	public String adminrechargelist(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Inside adminrechargelist");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			String search = request.getParameter("serachType");
			List<User_Recharge> rechargelist = registrationService
					.getCompAndPaidRechargeList(Integer.parseInt(session
							.getAttribute("userId").toString()), search);
			if (search.equals("Complementary")) {
				model.addAttribute("msg", "Complementary Recharge List");
			} else {
				model.addAttribute("msg", "Paid Recharge List");
			}
			model.addAttribute("rechargelist", rechargelist);
		} catch (Exception e) {
			logger.info("Exception in adminrechargelist" + e);
		}
		return "Recharge_List";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/updateProfile.do")
	public String updateProfile(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Inside updateProfile");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			List<User_Details> profile = registrationService.getProfile(Integer
					.parseInt(session.getAttribute("userId").toString()));
			model.addAttribute("profile", profile);
		} catch (Exception e) {
			logger.info("Exception in updateProfile" + e);
		}
		return "updateProfile";

	}

	@RequestMapping(value = "updateprofile.do", method = RequestMethod.POST)
	public String updateprofile(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			logger.info("Inside updateprofile post");
			String fullname = request.getParameter("fullname");
			String gender = request.getParameter("gender");
			String mobile = request.getParameter("mobile");
			String password = request.getParameter("password");
			String address = request.getParameter("assress");
			String email = request.getParameter("email");
			String pin = request.getParameter("pincode");
			String dob = request.getParameter("dob");
			int responsedata = 0;
			User_Details user = new User_Details();
			user.setAddress(address);
			user.setDob(dob);
			user.setEmail(email);
			user.setFullname(fullname);
			user.setGender(gender);
			user.setMobno(mobile);
			user.setPassword(password);
			user.setPin(pin);
			user.setRegistered_by(Integer.parseInt(session.getAttribute(
					"userId").toString()));
			responsedata = registrationService.updateprofile(user);
			if (responsedata == 1) {
				session.setAttribute("msg", "0");
			} else {
				session.setAttribute("msg", "1");
			}
			List<User_Details> profile = registrationService.getProfile(Integer
					.parseInt(session.getAttribute("userId").toString()));
			model.addAttribute("profile", profile);
			logger.info("Inside updateprofile.do");
		} catch (Exception e) {
			logger.info("Exception in updateProfile" + e);
		}
		return "updateProfile";

	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/teacherDeduction.do")
	public String teacherDeduction(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Inside teacherDeduction");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			List<Deduct_list> userlist = registrationService.userlist(Integer
					.parseInt(session.getAttribute("userId").toString()));
			model.addAttribute("userlist", userlist);
		} catch (Exception e) {
			logger.info("Exception in teacherDeduction" + e);
		}
		return "deduction_history";

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/meDeduction.do")
	public String meDeduction(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Inside meDeduction");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			List<Deduct_list> userlist = registrationService.userlist(Integer
					.parseInt(session.getAttribute("userId").toString()));
			model.addAttribute("userlist", userlist);
		} catch (Exception e) {
			logger.info("Exception in teacherDeduction" + e);
		}
		return "mededuction";

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getStudentAnswersheet.do")
	public String getStudentAnswersheet(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Inside getStudentAnswersheet");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			 List<AnsherSheet> anshersheet = new ArrayList<AnsherSheet>();
			String examcode=request.getParameter("examCode");
			String studentId=request.getParameter("studentId");
			String xml=registrationService.getAnswerSheet(examcode,Integer.parseInt(studentId));
			anshersheet=generate.parsexmlAnswersheet(xml);
			model.addAttribute("examcode", examcode);
			logger.info(examcode+" "+studentId);
			model.addAttribute("answersheet", anshersheet);
			
		} catch (Exception e) {
			logger.info("Exception in getStudentAnswersheet" + e);
		}
		return "AnswerSheet";

	}
	@RequestMapping(method = RequestMethod.GET, value = "/getStudentAnswer.do")
	public String getStudentAnswer(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ModelMap model) {
		logger.info("Inside getStudentAnswer");
		if (session.getAttribute("userId") == null) {
			logger.info(".... session time out ....");
			return "redirect:sessionOut.do";
		}
		try {
			 List<AnsherSheet> anshersheet = new ArrayList<AnsherSheet>();
			String examcode=request.getParameter("examCode");
			
			String xml=registrationService.getAnswerSheet(examcode,Integer.parseInt(session.getAttribute("userId").toString()));
			anshersheet=generate.parsexmlAnswersheet(xml);
			model.addAttribute("examcode", examcode);
			model.addAttribute("answersheet", anshersheet);
			
		} catch (Exception e) {
			logger.info("Exception in getStudentAnswer" + e);
		}
		return "AnswerSheet";

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/forgotpassword.do")
	public String forgotpassword(HttpServletRequest request,
			HttpServletResponse response,HttpSession session, ModelMap model) {
		logger.info("Inside forgotpassword");
		try {
		model.addAttribute("check", "1");	

		} catch (Exception e) {
			logger.info("Exception in forgotpassword" + e);
		}
		return "forgot";

	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/submitforgotpassword.do")
	public String submitforgotpassword(HttpServletRequest request,
			HttpServletResponse response,HttpSession session, ModelMap model) {
		logger.info("Inside submitforgotpassword");
		try {
			String fullname=request.getParameter("fullname");
			String mobile=request.getParameter("mobile");
			String dob=request.getParameter("dob");
			int userId=registrationService.getuserId(fullname, dob, mobile)	;
			logger.info(userId);
			if(userId==0){
			model.addAttribute("check", "1");
			session.setAttribute("msg", "1");
			}
			else{
			model.addAttribute("check", "2");
			model.addAttribute("userId", userId);
			}
			
			
		} catch (Exception e) {
			logger.info("Exception in submitforgotpassword" + e);
		}
		return "forgot";

	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/changepassword.do")
	public String changepassword(HttpServletRequest request,
			HttpServletResponse response,HttpSession session, ModelMap model) {
		logger.info("Inside changepassword");
		try {
			String userId=request.getParameter("userId");
			String password=request.getParameter("newPassword");
			int responsedata=registrationService.changePassword(Integer.parseInt(userId), password);
			logger.info(responsedata);
			if(responsedata==1){
			model.addAttribute("check", "1");
			session.setAttribute("msg", "0");
			}
			else{
			model.addAttribute("check", "1");
			session.setAttribute("msg", "2");
			}
			
			
		} catch (Exception e) {
			logger.info("Exception in changepassword" + e);
		}
		return "forgot";

	}
	
}
