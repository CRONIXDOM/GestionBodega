package com.bodega.controlweb.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bodega.controlweb.interceptor.SesionInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	private SesionInterceptor sesionInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(sesionInterceptor)
				.excludePathPatterns("/login", "/logout", "/recuperar", "/recuperar/**", "/assets/**", "/css/**",
						"/js/**", "/error");
	}
}
