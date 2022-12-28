package pl.janksiegowy.subdomain;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import pl.janksiegowy.shard.ShardRepository;
import pl.janksiegowy.tenant.TenantContext;

import javax.servlet.http.HttpServletRequest;

public class TenantRequestCondition implements RequestCondition<TenantRequestCondition> {

    private ShardRepository shardRepository;

    public TenantRequestCondition( ShardRepository shardRepository) {
        this.shardRepository = shardRepository;
    }

    @Override
    public TenantRequestCondition combine( TenantRequestCondition other) {
        return null;
    }

    @Override
    public TenantRequestCondition getMatchingCondition( HttpServletRequest request) {
        System.err.println( "getMatchingCondition: "+ request.getServerName().replaceAll("\\..*", ""));

        return shardRepository.findByName( request.getServerName().replaceAll("\\..*", ""))
                .map( shardJpa -> {
                    TenantContext.setShardId( shardJpa.getName());
                    return this;
                })
                .orElse( null);
    }

    @Override
    public int compareTo( TenantRequestCondition other, HttpServletRequest request) {
        return 0;
    }
}
