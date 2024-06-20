package com.gaea.work.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberVO {
	
	public String memberId  ; //아이디
	public String memberName; //이름
	public String password  ; //비번
	public String tel;		  //번호
	public String nickName;   //닉네임
	public String email;	  //이메일
	public String joinDt;	  //가입일
	public String address;
	
	public String no;
	
}
