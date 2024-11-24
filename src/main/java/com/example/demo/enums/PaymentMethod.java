package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentMethod {
	Ewallet("ewallet"), empty("empty"), Cash("cash");

	private  String name;
}
