package kg.peaksoft.ebookb4.db.models.entity.dto.request;

import kg.peaksoft.ebookb4.db.models.notEntities.CustomPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;


/**
 * Author: Zhanarbek Abdurasulov
 * Date: 20/4/22
 */
@Data
@AllArgsConstructor
public class CustomPageRequest<B> {

    List<B> content;

    CustomPage customPage;

    public CustomPageRequest(Page<B> page) {
        this.content = page.getContent();
        this.customPage = new CustomPage(page.getTotalElements(),
                page.getTotalPages(), page.getNumber(), page.getSize());
    }

}
