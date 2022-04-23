package kg.peaksoft.ebookb4.db.service;

import kg.peaksoft.ebookb4.db.models.enums.ERole;
import kg.peaksoft.ebookb4.dto.ResponseClient;
import java.util.List;

public interface AdminService {
    List<ResponseClient> findALlClients(ERole eRole);

    void deleteById(Long id);

}

