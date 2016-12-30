package com.malalaoshi.android.malapad.usercenter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.malalaoshi.android.core.AppContext;
import com.malalaoshi.android.malapad.data.entity.Lesson;
import com.malalaoshi.android.malapad.data.entity.User;

/**
 * Created by kang on 16/12/21.
 */

public class UserManager {
    /**
     * 登录成功
     */
    public static final String ACTION_LOGINED = "com.malalaoshi.android.account.ACTION_LOGINED";

    /**
     * 登出
     */
    public static final String ACTION_LOGOUT = "com.malalaoshi.android.account.ACTION_LOGOUT";

    private static UserManager instance = new UserManager();
    // 用户信息
    private String token;
    private String userId;
    private String phoneNo;
    private String role;
    private String name;
    private String school;
    private String schoolId;
    private Lesson lesson;

    private UserManager() {
        SharedPreferences userInfo = AppContext.getContext().getSharedPreferences("userInfo", 0);
        token = userInfo.getString("token", "");
        userId = userInfo.getString("userId", "");
        phoneNo = userInfo.getString("phoneNo", "");
        role = userInfo.getString("role", "");

        name = userInfo.getString("name", "");
        school = userInfo.getString("school", "");
        schoolId = userInfo.getString("schoolId", "");
        lesson = getLessonInfo();//userInfo.getString("lesson", "");
    }

    private Lesson getLessonInfo() {
        SharedPreferences userInfo = AppContext.getContext().getSharedPreferences("userInfo", 0);
        Lesson lesson = new Lesson();
        lesson.setId(userInfo.getLong("lessonId", -1L));
        lesson.setLessonNo(userInfo.getString("lessonNo", ""));
        lesson.setName(userInfo.getString("lessonName", ""));
        lesson.setLecturer(userInfo.getString("lessonLecturer", ""));
        lesson.setGrade(userInfo.getString("lessonGrade", ""));
        lesson.setSubject(userInfo.getString("lessonSubject", ""));
        return lesson;
    }

    private void setLessonInfo(Lesson lesson) {
        SharedPreferences userInfo = AppContext.getContext().getSharedPreferences("userInfo", 0);
        if (lesson!=null){
            userInfo.edit().putLong("lessonId", lesson.getId()).apply();
            userInfo.edit().putString("lessonNo", lesson.getLessonNo()).apply();
            userInfo.edit().putString("lessonName", lesson.getName()).apply();
            userInfo.edit().putString("lessonLecturer", lesson.getLecturer()).apply();
            userInfo.edit().putString("lessonGrade", lesson.getGrade()).apply();
            userInfo.edit().putString("lessonSubject", lesson.getSubject()).apply();
        }else{
            clearLessonInfo();
        }
    }

    private void clearLessonInfo(){
        SharedPreferences userInfo = AppContext.getContext().getSharedPreferences("userInfo", 0);
        userInfo.edit().putLong("lessonId", -1L).apply();
        userInfo.edit().putString("lessonNo", "").apply();
        userInfo.edit().putString("lessonName", "").apply();
        userInfo.edit().putString("lessonLecturer", "").apply();
        userInfo.edit().putString("lessonGrade", "").apply();
        userInfo.edit().putString("lessonSubject", "").apply();
    }

    public static UserManager getInstance() {
        return instance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        SharedPreferences userInfo = AppContext.getContext().getSharedPreferences("userInfo", 0);
        userInfo.edit().putString("token", token).apply();
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        SharedPreferences userInfo = AppContext.getContext().getSharedPreferences("userInfo", 0);
        userInfo.edit().putString("userId", userId).apply();
        this.userId = userId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        SharedPreferences userInfo = AppContext.getContext().getSharedPreferences("userInfo", 0);
        userInfo.edit().putString("phoneNo", phoneNo).apply();
        this.phoneNo = phoneNo;
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(token);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        SharedPreferences userInfo = AppContext.getContext().getSharedPreferences("userInfo", 0);
        userInfo.edit().putString("role", role).apply();
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        SharedPreferences userInfo = AppContext.getContext().getSharedPreferences("userInfo", 0);
        userInfo.edit().putString("name", name).apply();
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        SharedPreferences userInfo = AppContext.getContext().getSharedPreferences("userInfo", 0);
        userInfo.edit().putString("school", school).apply();
        this.school = school;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        SharedPreferences userInfo = AppContext.getContext().getSharedPreferences("userInfo", 0);
        userInfo.edit().putString("schoolId", schoolId).apply();
        this.schoolId = schoolId;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        setLessonInfo(lesson);
        this.lesson = lesson;
    }

    public void logout() {
        SharedPreferences userInfo = AppContext.getContext().getSharedPreferences("userInfo", 0);
        token = "";
        userInfo.edit().putString("token", "").apply();
        userId = "";
        userInfo.edit().putString("userId", "").apply();
        phoneNo = "";
        userInfo.edit().putString("phoneNo", "").apply();
        role = "";
        userInfo.edit().putString("role", "").apply();
        name = "";
        userInfo.edit().putString("studname", "").apply();
        school = "";
        userInfo.edit().putString("school", school).apply();
        schoolId = "";
        userInfo.edit().putString("schoolId", schoolId).apply();
        lesson = null;
        clearLessonInfo();
        //Login success broadcast
        Intent intent = new Intent(ACTION_LOGOUT);
        AppContext.getLocalBroadcastManager().sendBroadcast(intent);
        //发送退出通知
        //EventBus.getDefault().post(new BusEvent(BusEvent.BUS_EVENT_LOGOUT_SUCCESS));
    }



    /**
     * Success login, Update login info
     *
     * @param user Login user
     */
    public void login(User user) {
        setToken(user.getToken());
        setUserId(user.getUserId()+"");
        setName(user.getName());
        setPhoneNo(user.getPhone());
        setRole(user.getRole());
        setSchool(user.getSchool());
        setSchoolId(user.getSchoolId()+"");
        setLesson(user.getLesson());
        //Login success broadcast
        Intent intent = new Intent(ACTION_LOGINED);
        AppContext.getLocalBroadcastManager().sendBroadcast(intent);
        //发送登录成功通知
       // EventBus.getDefault().post(new BusEvent(BusEvent.BUS_EVENT_LOGIN_SUCCESS));
    }

}
