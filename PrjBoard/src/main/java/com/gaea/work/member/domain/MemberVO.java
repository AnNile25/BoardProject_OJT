package com.gaea.work.member.domain;

import com.gaea.work.cmn.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO extends DTO {
	
	private String memberId  ;//아이디
	private String memberName;//이름
	private String password  ;//비번
	private int    tel;		  //번호
	private String nicnName;  //닉네임
	private String email;	  //이메일
	private String joinDt;	  //가입일
	
	@Override
	public String toString() {
		return "MemberVO [memberId=" + memberId + ", memberName=" + memberName + ", password=" + password + ", tel="
				+ tel + ", nicnName=" + nicnName + ", email=" + email + ", joinDt=" + joinDt + ", toString()="
				+ super.toString() + "]";
	}	
	
}
