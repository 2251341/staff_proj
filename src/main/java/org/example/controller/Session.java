package org.example.controller;


// 사용자 세션을 관리하는 Session 클래스입니다.

public class Session {
    private static String currentUser = null;
    private static String currentUserType = null;


//     현재 로그인한 사용자의 세션 정보를 설정합니다. 사용자 이름과 유형을 입력받습니다.
//     @param username 현재 로그인한 사용자의 이름
//     @param userType 사용자의 유형 (예: "employee", "admin")

    public static void setCurrentUser(String username, String userType) {
        currentUser = username;
        currentUserType = userType;
    }


//     현재 사용자의 세션을 초기화합니다.

    public static void clearCurrentUser() {
        currentUser = null;
        currentUserType = null;
    }


//      현재 로그인한 사용자의 이름을 반환합니다.

    public static String getCurrentUser() {
        return currentUser;
    }


//현재 로그인한 사용자의 유형을 반환합니다.
//@return 현재 사용자의 유형

    public static String getCurrentUserType() {
        return currentUserType;
    }

//
//     사용자가 로그인했는지 확인합니다.
//     @return 사용자가 로그인했다면 true를, 아니라면 false를 반환합니다.
//
    public static boolean isUserLoggedIn() {
        return currentUser != null;
    }
}
