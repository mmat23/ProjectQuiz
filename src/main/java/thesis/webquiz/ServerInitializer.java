package thesis.webquiz;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import thesis.webquiz.config.*;

public class ServerInitializer extends AbstractAnnotationConfigDispatcherServletInitializer  {

	@Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{WebConf.class,SecurityConf.class,HibernateConf.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}