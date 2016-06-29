package br.com.mytho.role.infra.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringInitializator extends AbstractAnnotationConfigDispatcherServletInitializer {
	
    @Override
    protected Class<?>[] getRootConfigClasses() {
    	return null;
    }
  
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { SpringMVCConfig.class };
    }
  
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
 
}