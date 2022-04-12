package kg.peaksoft.ebookb4.service;

import kg.peaksoft.ebookb4.dto.auth.AuthRequest;
import kg.peaksoft.ebookb4.dto.auth.AuthResponse;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 12/4/22
 */
public interface UserService {
    AuthResponse authenticate(AuthRequest authRequest);
}
