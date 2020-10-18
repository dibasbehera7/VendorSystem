package com.dibasb;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordGenerator {

	public static void main(String args[]) {
		BCryptPasswordEncoder pass = new BCryptPasswordEncoder();
		System.out.println(pass.encode("llms@2020"));
	}
}
