package kg.peaksoft.ebookb4.enums;


import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,CLIENT,VENDOR;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
