package com.org.kap.service;

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
import com.org.kap.dto.Student_Results;
import com.org.kap.dto.TabQuestionMaster;
import com.org.kap.dto.User_Recharge;
import com.org.kap.dto.Userdata;
import com.org.kap.entity.User_Details;

import java.util.List;

public interface RegistrationService {

	public Userdata authenticateUserDetails(Userdata userdata);
	
	public int submitregistrationdata(User_Details user);
	public int validateMobile(String mobile);
	public int updateprofile(User_Details user);
	public int submitSetQuestionData(TabQuestionMaster questionMaster);

	public int submitExamScheduleData(ExamSchedule examschedule);

	public int submitCourseEntryData(CourseEntry courseentry);
	
	public List<CourseEntry> getCourseDetails(int insertedby);
	
	public List<TabQuestionMaster> getTabQuestionMasterDetails(int insertedby);

	
	public int updateCourseEntryData(CourseEntry courseentry);
	public int deleteCourseEntryData(int courseId);
	public ExamPaper submitExamPaperData(ExamPaper exampaper);
	
	public SearchExamCode submitSearchExamCodeData(SearchExamCode searchexamcode);
	public List<Question_details> getquestion_answer(int insertedby, String papercode);
	public int submitresult(Exam_Result exampaper);
	public List<User_Recharge> getRechargeUserList(int id);
	public int sumbmitRechargeUserList(User_Recharge recharge);
	public List<Student_Results> getstudentResult(int studentid);
	public int validateamount(String exampapercode);
	public int checkfinalsubmition(String exampapercode,int student_id);
	public int get_no_of_attempt(String exampapercode,int student_id);
	public int deduct_amount(int student_id,int amount,int teacher_id);
	public int checkdeductionHistory(int student_id, String examCode );
	public int insertdeductionhistory(int student_id,int amount,String purpose,String examcode);
	public int getbalance(int id);
	public int gettotalcoursecount(int id);
	public int gettotalteacher(int id);
	public int gettotalMe(int id);
	public int gettotalstudent(int id);
	public int getCompRechargeCount(int id);
	public int getpaidRechargeCount(int id);
	public int gettotalquestioncount(int id);
	public int gettoexamshedulecount(int id);
	public  List<Course_Histry> getcoursehistory(int id);
	public  List<GetQuestionList> getquestionhistory(int id);
	public  List<GetQuestionList> getquestion(int id);
	public int updateQuestion(int questionId,String xml,int length);
	public int deletequestion(int questionId);
	public  List<ExamSchedule> examSchedulehistory(int id);
	public int deleteschedule(int questionId);
	
	public  List<GetResultHistory> getResultHistory(int id);
	
	public  List<GetRechargeHistory> getRechargeHistory(int id);
	
	public  List<ExamCodeResult> getExamcodeResul(String examCode);
	
	public  List<User_Details> getStudentList(int id);
	public  List<Deduct_list> userlist(int id);
	public  List<User_Details> getProfile(int id);
	public  List<User_Details> getTeacherMeList(int id,int serrchtype);
	public int gettotalparent(int id);
	
	public List<User_Recharge> getCompAndPaidRechargeList(int id,String rechargeType);
	public String getAnswerSheet(String examcode,int studenyId);
	public int getuserId(String name,String dob,String mobile);
	public int examcount(int id,int var);
	public int changePassword(int userId,String Password);
}
