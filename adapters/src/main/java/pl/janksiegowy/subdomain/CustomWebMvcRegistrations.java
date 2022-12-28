package pl.janksiegowy.subdomain;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Component
public class CustomWebMvcRegistrations implements WebMvcRegistrations {

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        RequestMappingHandlerMapping handlerMapping= new SubdomainRequestMappingHandlerMapping();
        handlerMapping.setOrder( 0);
        return handlerMapping;
    }
}
