package no.trinnvis.dabih.db;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitJoinColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitJoinTableNameSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl;

/**
 * Created by Administrator on 2016/7/9.
 */
public class ImplicitNamingStrategyImpl extends ImplicitNamingStrategyLegacyJpaImpl {

    private static final long serialVersionUID = -4673069398939723967L;

    @Override
    public Identifier determineJoinColumnName(ImplicitJoinColumnNameSource source) {
        String name;
        if (source.getNature() == ImplicitJoinColumnNameSource.Nature.ELEMENT_COLLECTION || source.getAttributePath() == null) {
            name = source.getReferencedTableName().getText();
        } else {
            name = transformAttributePath(source.getAttributePath());
        }

        return toIdentifier(name, source.getBuildingContext());
    }

    @Override
    public Identifier determineJoinTableName(ImplicitJoinTableNameSource source) {
        final String ownerPortion = source.getOwningPhysicalTableName();
        final String ownedPortion = source.getAssociationOwningAttributePath().getProperty();
        return toIdentifier(ownerPortion + "_" + ownedPortion, source.getBuildingContext());
    }
}
