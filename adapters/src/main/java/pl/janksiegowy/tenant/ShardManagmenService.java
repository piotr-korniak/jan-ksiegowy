package pl.janksiegowy.tenant;

public interface ShardManagmenService {

    void createShard( String tenantId, String db, String password);

}
