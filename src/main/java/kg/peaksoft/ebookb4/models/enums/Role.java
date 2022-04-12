package kg.peaksoft.ebookb4.models.enums;

import org.springframework.security.core.GrantedAuthority;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 12/4/22
 */
public enum Role implements GrantedAuthority {
    CLIENT, VENDOR, ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
