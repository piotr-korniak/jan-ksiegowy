package pl.janksiegowy.entity;

public enum EntityType {
    C { // Contact
        @Override public <T> T accept(EntityTypeVisitor<T> visitor) {
            return visitor.visitContact();
        }
    },
    R { // Revenue
        @Override public <T> T accept(EntityTypeVisitor<T> visitor) {
            return visitor.visitRevenue();
        }

    },
    S { // Shareholders
        @Override public <T> T accept(EntityTypeVisitor<T> visitor) {
            return visitor.visitShareholders();
        }
    };

    public abstract <T> T accept(EntityTypeVisitor<T> visitor);

    interface EntityTypeVisitor<T> {
        T visitContact();
        T visitRevenue();
        T visitShareholders();
    }
}
