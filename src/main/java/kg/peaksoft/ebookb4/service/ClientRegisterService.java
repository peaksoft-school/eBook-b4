package kg.peaksoft.ebookb4.service;

import kg.peaksoft.ebookb4.models.ClientRegister;
import kg.peaksoft.ebookb4.response.Response;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 12/4/22
 */
public interface ClientRegisterService {

    Response registerUser(ClientRegister clientRegister);



}
