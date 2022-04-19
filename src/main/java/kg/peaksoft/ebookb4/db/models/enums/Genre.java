package kg.peaksoft.ebookb4.db.models.enums;

import org.springframework.security.core.GrantedAuthority;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 12/4/22
 */
public enum Genre implements GrantedAuthority {
    LITERATURE, ROMANCE, FANTASY, DETECTIVE, SCIENTIFIC, ADVENTURE,
    INTERNATIONAL_LITERATURE, BIOGRAPHY, DICTIONARY, POETRY, HORROR,
    JOURNAL, ENCYCLOPEDIA;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
