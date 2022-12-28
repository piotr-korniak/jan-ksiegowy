package pl.janksiegowy.base.support;

import pl.janksiegowy.base.DomainObjectId;

import javax.validation.constraints.NotNull;
import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDDomainObjectId extends SimpleValueObject<UUID> implements DomainObjectId {

    /**
     * Creates a new {@code UUIDDomainObjectId} containing a random type 4 UUID.
     *
     * @see UUID#randomUUID()
     */
    public UUIDDomainObjectId() {

        super( UUID.randomUUID());
    }

    /**
     * Creates a new {@code UUIDDomainObjectId} from the given UUID.
     *
     * @param uuid the UUID.
     */
    public UUIDDomainObjectId( @NotNull UUID uuid) {
        super(uuid);
    }

    /**
     * Creates a new {@code UUIDDomainObjectId} from the given string.
     *
     * @param uuid the UUID as a string.
     * @see UUID#fromString(String)
     */
    public UUIDDomainObjectId(@NotNull String uuid) {
        super(UUID.fromString(uuid));
    }

    /**
     * Creates a new {@code UUIDDomainObjectId} from the given byte array.
     *
     * @param uuid the UUID as a 16-byte big-endian array.
     * @see UUID#UUID(long, long)
     */
    public UUIDDomainObjectId(@NotNull byte[] uuid) {
        super(bytesToUUID(uuid));
    }

    private static @NotNull UUID bytesToUUID(@NotNull byte[] uuid) {
        var buf = ByteBuffer.wrap(uuid);
        return new UUID(buf.getLong(), buf.getLong());
    }

    /**
     * Returns the UUID as a byte array.
     *
     * @return a 16-byte big-endian array.
     */
    public @NotNull byte[] toBytes() {
        System.err.println( "Tędy przechodzimy???");
        var buf = ByteBuffer.wrap(new byte[16]);
        var uuid = unwrap();
        buf.putLong(uuid.getMostSignificantBits());
        buf.putLong(uuid.getLeastSignificantBits());
        return buf.array();
    }

}
