package pl.janksiegowy.tenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component( "currentTenantIdentifierResolver")
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {
    @Override
    public String resolveCurrentTenantIdentifier() {
        System.err.println( "TenantContext: "+ TenantContext.getTenantId());

        return Optional.ofNullable( TenantContext.getTenantId())
                .map( String::valueOf)
                .orElse( "BOOTSTRAP");
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
