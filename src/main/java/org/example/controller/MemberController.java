package org.example.controller;


import org.example.container.Container;
import org.example.dto.Member;

import java.util.Scanner;

public class MemberController extends Controller {
    private Scanner sc;
    private MemberService memberService;

    public MemberController(Scanner sc) {
        this.sc = sc;
        this.memberService = Container.memberService;
    }

    // 직원 등록 메서드입니다.
    public void registerEmployee() {
        // 새 직원을 등록하는 코드를 작성합니다.
    }

    // 직원 정보 수정 메서드입니다.
    public void updateEmployee() {
        // 직원 정보를 수정하는 코드를 작성합니다.
    }

    // 직원 삭제 메서드입니다.
    public void deleteEmployee() {
        // 직원을 삭제하는 코드를 작성합니다.
    }

    // 출퇴근 기록을 조회하는 메서드입니다.
    public void viewAttendance() {
        // 출퇴근 기록을 조회하는 코드를 작성합니다.
    }
    @Override
    public void doAction(String cmd, String actionMethodName) {
        switch (actionMethodName) {
            case "join":
                doJoin();
                break;
            default:
                System.out.println("존재하지 않는 명령어 입니다.");
                break;
        }
    }

    @Override
    public void makeTestData() {
        System.out.println("테스트를 위한 회원 데이터를 생성합니다.");

        memberService.join(new Member(Container.memberDao.getNewId(), Util.getNowDateStr(), "admin", "admin", "관리자"));
        memberService.join(new Member(Container.memberDao.getNewId(), Util.getNowDateStr(), "user1", "user1", "홍길동"));
        memberService.join(new Member(Container.memberDao.getNewId(), Util.getNowDateStr(), "user2", "user2", "홍길순"));
    }

    public void doJoin() {
        int id = Container.memberDao.getNewId();
        String regDate = Util.getNowDateStr();

        System.out.printf("로그인 아이디 : ");
        String loginId = sc.nextLine();

        System.out.printf("로그인 비밀번호 : ");
        String loginPw = sc.nextLine();

        System.out.printf("이름 : ");
        String name = sc.nextLine();

        Member member = new Member(id, regDate, loginId, loginPw, name);
        memberService.join(member);

        System.out.printf("%d번 직원이 생성되었습니다. 환영합니다!\n", id);
    }

    
}
