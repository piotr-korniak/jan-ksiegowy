package pl.janksiegowy.tenant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class TenantContext {

    private String tenant;
    private String shard;
    private TenantContext() {}
    private static final InheritableThreadLocal<TenantContext> currentTenant= new InheritableThreadLocal<>(){
        @Override protected TenantContext initialValue() {
            return new TenantContext();
        }};

    public static void setShardId( String shard) {
        log.debug( "Setting shardId: "+ shard);
        currentTenant.get()
                .setShard( shard)
                .setTenant( null);
    }

    public static void setTenantId(  String shard, String tenant) {
        log.debug( "Setting shardId: "+ shard+ " and tenantId: "+ tenant);
        currentTenant.get()
                .setShard( shard)
                .setTenant( tenant);
    }

    private TenantContext setShard( String shard) {
        this.shard= shard;
        return this;
    }

    private TenantContext setTenant( String tenant) {
        this.tenant= tenant;
        return this;
    }

    public static String getTenantId() {
        return currentTenant.get()!= null? currentTenant.get().tenant: null;
    }
    public static String getShardId() {
        return currentTenant.get()!= null? currentTenant.get().shard : null;
    }

    public static void clear(){
        currentTenant.remove();
    }


}
