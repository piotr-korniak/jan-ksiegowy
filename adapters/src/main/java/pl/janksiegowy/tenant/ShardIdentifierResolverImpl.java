package pl.janksiegowy.tenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component( "shardIdentifierResolver")
public class ShardIdentifierResolverImpl implements CurrentTenantIdentifierResolver {
    @Override
    public String resolveCurrentTenantIdentifier() {
        System.err.println( "ShardContext: public");
        return "public";
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
