package com.gaea.work.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {
	
	public String memberId  ; //아이디
	public String memberName; //이름
	public String password  ; //비번
	public int    tel;		  //번호
	public String nickName;   //닉네임
	public String email;	  //이메일
	public String joinDt;	  //가입일
	
	public String no;

	@Override
	public String toString() {
		return "MemberVO [memberId=" + memberId + ", memberName=" + memberName + ", password=" + password + ", tel="
				+ tel + ", nickName=" + nickName + ", email=" + email + ", joinDt=" + joinDt + ", no=" + no
				+ ", toString()=" + super.toString() + "]";
	}		
	
}
