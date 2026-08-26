package com.appointment.config;

import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class FienClientInterceptor implements RequestInterceptor{

	@Override
	public void apply(RequestTemplate template) {
		template.header("X-Secret-Key", "SSWuHijaMXSWyVtU2NERpKPGXF0hPN2");
	}

}
