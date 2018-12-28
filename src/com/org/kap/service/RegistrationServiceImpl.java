package com.org.kap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.kap.dao.RegistrationDAO;
import com.org.kap.dto.CourseEntry;
import com.org.kap.dto.Course_Histry;
import com.org.kap.dto.Deduct_list;
import com.org.kap.dto.ExamCodeResult;
import com.org.kap.dto.ExamPaper;
import com.org.kap.dto.ExamSchedule;
import com.org.kap.dto.Exam_Result;
import com.org.kap.dto.GetQuestionList;
import com.org.kap.dto.GetRechargeHistory;
import com.org.kap.dto.GetResultHistory;
import com.org.kap.dto.Question_details;
import com.org.kap.dto.SearchExamCode;
import com.org.kap.dto.SetQuestion;
import com.org.kap.dto.Student_Results;
import com.org.kap.dto.TabQuestionMaster;
import com.org.kap.dto.User_Recharge;
import com.org.kap.dto.Userdata;
import com.org.kap.entity.User_Details;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	private RegistrationDAO registrationDAO;

	@Override
	public int submitregistrationdata(User_Details user) {
		return registrationDAO.submitregistrationdata(user);
	}

	
	@Override
	public int submitSetQuestionData(TabQuestionMaster questionMaster) {
		return registrationDAO.submitSetQuestionData(questionMaster);
	}

	@Override
	public int submitExamScheduleData(ExamSchedule examschedule) {
		return registrationDAO.submitExamScheduleData(examschedule);
	}

	@Override
	public Userdata authenticateUserDetails(Userdata userdata) {
		return registrationDAO.authenticateUserDetails(userdata);
	}

	@Override
	public int submitCourseEntryData(CourseEntry courseentry) {
		return registrationDAO.submitCourseEntryData(courseentry);
	}
	@Override
	public int updateCourseEntryData(CourseEntry courseentry) {
		return registrationDAO.updateCourseEntryData(courseentry);
	}
	@Override
	public List<CourseEntry> getCourseDetails(int insertedby){
		return registrationDAO.getCourseDetails(insertedby);
	}
	
	
	@Override
	public List<TabQuestionMaster> getTabQuestionMasterDetails(int insertedby){
		return registrationDAO.getTabQuestionMasters( insertedby);
	}
	
	
	
	@Override
	public ExamPaper submitExamPaperData(ExamPaper exampaper) {
		return registrationDAO.submitExamPaperData(exampaper);
	}
	
	@Override
	public SearchExamCode submitSearchExamCodeData(SearchExamCode searchexamcode) {
		return registrationDAO.submitSearchExamCodeData(searchexamcode);
	}
	
	@Override
	public List<Question_details> getquestion_answer(int insertedby, String papercode) {
		return registrationDAO.getquestion_answer(insertedby,papercode);
	}

	@Override
	public int submitresult(Exam_Result exampaper) {
		return registrationDAO.submitresult(exampaper);
	}

	@Override
	public List<User_Recharge> getRechargeUserList(int id) {
	
		return registrationDAO.getRechargeUserList(id);
	}

	@Override
	public int sumbmitRechargeUserList(User_Recharge recharge) {
		
		return registrationDAO.sumbmitRechargeUserList(recharge);
	}

	@Override
	public List<Student_Results> getstudentResult(int studentid) {
	
		return registrationDAO.getstudentResult(studentid);
	}

	@Override
	public int validateamount(String exampapercode) {
		
		return registrationDAO.validateamount(exampapercode);
	}

	@Override
	public int checkfinalsubmition(String exampapercode, int student_id) {
		
	return registrationDAO.checkfinalsubmition(exampapercode,student_id);
	}
	@Override
	public int get_no_of_attempt(String exampapercode, int student_id) {
		
	return registrationDAO.get_no_of_attempt(exampapercode,student_id);
	}
	
	@Override
	public int  deduct_amount(int student_id,int amount,int teacher_id) {
	return registrationDAO.deduct_amount(student_id,amount,teacher_id);
	}
	
	@Override
	public int checkdeductionHistory(int student_id, String examCode ) {
	return registrationDAO.checkdeductionHistory(student_id,examCode);
	}
	
	@Override
	public int insertdeductionhistory(int student_id,int amount,String purpose,String examcode) {
	return registrationDAO.insertdeductionhistory(student_id,amount,purpose,examcode);
	}
	
	
	@Override
	public int getbalance(int id) {
	return registrationDAO.getbalance(id);
	}
	
	@Override
	public int gettotalcoursecount(int id) {
	return registrationDAO.gettotalcoursecount(id);
	}
	
	@Override
	public int gettotalstudent(int id) {
	return registrationDAO.gettotalstudent(id);
	}
	
	@Override
	public int gettotalquestioncount(int id) {
	return registrationDAO.gettotalquestioncount(id);
	}
	

	@Override
	public int gettoexamshedulecount(int id) {
	return registrationDAO.gettoexamshedulecount(id);
	}

	@Override
	public List<Course_Histry> getcoursehistory(int id) {
	return registrationDAO.getcoursehistory(id);
	}


	@Override
	public int deleteCourseEntryData(int courseId) {
		
		return registrationDAO.deleteCourseEntryData(courseId);
	}


	@Override
	public List<GetQuestionList> getquestionhistory(int id) {
		
		return registrationDAO.getquestionhistory(id);
	}
	@Override
	public List<GetQuestionList> getquestion(int id) {
		
		return registrationDAO.getquestion(id);
	}
	
	@Override
	public int updateQuestion(int questionId,String xml,int length){
	return registrationDAO.updateQuestion(questionId,xml, length);
	}
	
	@Override
	public int  deletequestion(int questionId) {
		
		return registrationDAO.deletequestion(questionId);
	}
	public  List<ExamSchedule> examSchedulehistory(int id){
		return registrationDAO.examSchedulehistory(id);
	}
	@Override
	public int  deleteschedule(int questionId) {
		
		return registrationDAO.deleteschedule(questionId);
	}


	@Override
	public List<GetResultHistory> getResultHistory(int id) {
		return registrationDAO.getResultHistory(id);
	}


	@Override
	public List<ExamCodeResult> getExamcodeResul(String examCode) {
		return registrationDAO.getExamcodeResul(examCode);
	}


	@Override
	public List<GetRechargeHistory> getRechargeHistory(int id) {
		
		return registrationDAO.getRechargeHistory(id);
	}


	@Override
	public List<User_Details> getStudentList(int id) {
		
		return registrationDAO.getStudentList(id);
	}


	@Override
	public int gettotalteacher(int id) {
		
		return registrationDAO.gettotalteacher(id);
	}


	@Override
	public int gettotalMe(int id) {
		
		return  registrationDAO.gettotalMe(id);
	}


	@Override
	public List<User_Details> getTeacherMeList(int id,int serrchtype) {
		
		return registrationDAO.getTeacherMeList(id,serrchtype);
	}


	@Override
	public int gettotalparent(int id) {
		
		return registrationDAO.gettotalparent(id);
	}


	@Override
	public int getCompRechargeCount(int id) {
		
		return registrationDAO.getCompRechargeCount(id);
	}


	@Override
	public int getpaidRechargeCount(int id) {
	
		return registrationDAO.getpaidRechargeCount(id);
	}


	@Override
	public List<User_Recharge> getCompAndPaidRechargeList(int id,
			String rechargeType) {
		
		return registrationDAO.getCompAndPaidRechargeList(id, rechargeType);
	}


	@Override
	public int examcount(int id, int var) {
		
		return registrationDAO.examcount(id, var);
	}


	@Override
	public List<User_Details> getProfile(int id) {
		
		return registrationDAO.getProfile(id);
	}


	@Override
	public int updateprofile(User_Details user) {
		
		return registrationDAO.updateprofile(user);
	}


	@Override
	public List<Deduct_list> userlist(int id) {
		
		return registrationDAO.userlist(id);
	}


	@Override
	public int validateMobile(String mobile) {
		
		return registrationDAO.validateMobile(mobile);
	}


	@Override
	public String getAnswerSheet(String examcode, int studenyId) {
		return registrationDAO.getAnswerSheet(examcode, studenyId);
	}


	@Override
	public int getuserId(String name, String dob, String mobile) {
		
		return registrationDAO.getuserId(name, dob, mobile);
	}


	@Override
	public int changePassword(int userId, String Password) {
		
		return registrationDAO.changePassword(userId, Password);
	}
}
