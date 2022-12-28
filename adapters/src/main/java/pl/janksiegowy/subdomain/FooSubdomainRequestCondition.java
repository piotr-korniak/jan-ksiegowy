package pl.janksiegowy.subdomain;

import org.springframework.web.servlet.mvc.condition.RequestCondition;
import pl.janksiegowy.tenant.TenantContext;

import javax.servlet.http.HttpServletRequest;

public class FooSubdomainRequestCondition implements RequestCondition<FooSubdomainRequestCondition> {

    public FooSubdomainRequestCondition() {
        System.err.println( "FooSubdomainRequestCondition!");
    }

    @Override
    public FooSubdomainRequestCondition combine(FooSubdomainRequestCondition other) {
        return null;
    }

    @Override
    public FooSubdomainRequestCondition getMatchingCondition(HttpServletRequest request) {
        System.err.println( "SubdomainUtil: "+ request);
        //String subdomain = SubdomainUtil.extractSubdomain(request);
        System.err.println( "Foo: "+ request.getServerName());

        if( request.getServerName().startsWith( "eleutheria3")) {
            TenantContext.setShardId( "eleutheria3");
            return this;
        }
        return null;
    }

    @Override
    public int compareTo(FooSubdomainRequestCondition other, HttpServletRequest request) {
        return 0;
    }
}
