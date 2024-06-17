package com.gaea.work.qna.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gaea.work.cmn.GLog;
import com.gaea.work.member.MemberDao;
import com.gaea.work.member.MemberVO;

@RunWith(SpringJUnit4ClassRunner.class) // 스프링 테스트 컨텍스트 프레임웤그의 JUnit의 확장기능 지정
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
public class MemberDaoJUnitTest implements GLog {

	@Autowired
	MemberDao dao;

	MemberVO member01;
	MemberVO member02;
	MemberVO member03;

	MemberVO searchVO;

	@Autowired
	ApplicationContext context;

//	@Before
//	public void setUp() throws Exception {
//		member01 = new MemberVO("nile", "이한나", "4321_1", 86281627, "안나일", "dlgkssk1627@naver.com", "");
//		member02 = new MemberVO("gaea", "지어", "4321_2", 12345678, "하이", "", "");
//		member03 = new MemberVO("soft", "소프트", "4321_3", 87654321, "소프트", "", "");
//
//		searchVO = new MemberVO();
//		searchVO.setMemberId("hanna");
//	}
	
	@Test
	public void idDuplicateCheck() throws SQLException {
	    // 1. 데이터 삭제
	    dao.deleteMember(member01);
	    dao.deleteMember(member02);
	    dao.deleteMember(member03);
	    
	    // 2. 데이터 입력
	    int flag = dao.saveMember(member01);
	    assertEquals(1, flag);

	    flag = dao.saveMember(member02);
	    assertEquals(1, flag);

	    flag = dao.saveMember(member03);
	    assertEquals(1, flag);        
	    
//	    // idCheck: id가 있는 경우
//	    int idCheckCnt = dao.idDuplicateCheck(member02);
//	    assertEquals(1, idCheckCnt);
//	    
//	    // id가 없는 경우: 
//	    member02.setMemberId("unknown_user");
//	    idCheckCnt = dao.idDuplicateCheck(member02);
//	    assertEquals(0, idCheckCnt);        
	}

	@Ignore
	@Test
	public void doRetrieve() throws SQLException {
		// 1.
		dao.deleteMember(member01);
		dao.deleteMember(member02);
		dao.deleteMember(member03);

		// 2.
		int flag = dao.saveMember(member01);
		assertEquals(1, flag);

		flag = dao.saveMember(member02);
		assertEquals(1, flag);

		flag = dao.saveMember(member03);
		assertEquals(1, flag);

		List<MemberVO> list = dao.retrieveMember(searchVO);

		for (MemberVO vo : list) {
			LOG.debug(vo);
		}
	}

	@Ignore
	@Test
	public void update() throws SQLException {
		// 1. 데이터 삭제
		dao.deleteMember(member01);
		dao.deleteMember(member02);
		dao.deleteMember(member03);

		// 2. 데이터 등록
		int flag = dao.saveMember(member01);
		assertEquals(1, flag);

		flag = dao.saveMember(member02);
		assertEquals(1, flag);

		flag = dao.saveMember(member03);
		assertEquals(1, flag);

		// 3. 등록데이터 조회
		MemberVO vo01 = dao.selectOneMember(member02);

		String memberName = vo01.getMemberName() + "_U";
		String password = vo01.getPassword() + "99";
		String nickname = vo01.getNickName() + "_U";

		// 4. 3번 조회된 데이터를 수정
		vo01.setMemberName(memberName);
		vo01.setPassword(password);
		vo01.setNickName(nickname);

		// 5. update
		flag = dao.updateMember(vo01);
		assertEquals(1, flag);

		// 6. 수정데이터 조회
		MemberVO vs01 = dao.selectOneMember(vo01);

		// 7. 비교
		isSameMember(vs01, vo01);
	}

	@Ignore
	@Test
	public void addAndGet() throws SQLException {
		// 1.데이터 삭제
		dao.deleteMember(member01);
		dao.deleteMember(member02);
		dao.deleteMember(member03);

		// 2.등록
		int flag = dao.saveMember(member01);
		assertThat(flag, is(1));

		flag = dao.saveMember(member02);
		assertThat(flag, is(1));

		flag = dao.saveMember(member03);
		assertThat(flag, is(1));

		MemberVO outVO01 = dao.selectOneMember(member01);
		MemberVO outVO02 = dao.selectOneMember(member02);
		MemberVO outVO03 = dao.selectOneMember(member03);
		assertNotNull(outVO01);// Not Null이면 true
		assertNotNull(outVO02);// Not Null이면 true
		assertNotNull(outVO03);// Not Null이면 true

		// 데이터 동일 테스트
		isSameMember(member01, outVO01);
		isSameMember(member02, outVO02);
		isSameMember(member03, outVO03);
	}

	private void isSameMember(MemberVO memberVO, MemberVO outVO) {
		assertEquals(memberVO.getMemberId(), outVO.getMemberId());
		assertEquals(memberVO.getMemberName(), outVO.getMemberName());
		assertEquals(memberVO.getPassword(), outVO.getPassword());
		assertEquals(memberVO.getTel(), outVO.getTel());
		assertEquals(memberVO.getNickName(), outVO.getNickName());

	}

	@Test
	public void beans() {
		LOG.debug("[beans]dao:" + dao + "|coontext:" + context);
		assertNotNull(dao);
		assertNotNull(context);
	}

}
