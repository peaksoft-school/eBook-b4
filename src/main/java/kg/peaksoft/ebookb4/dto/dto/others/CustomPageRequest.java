package kg.peaksoft.ebookb4.dto.dto.others;

import kg.peaksoft.ebookb4.db.models.others.CustomPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomPageRequest<B> {

    List<B> content;

    CustomPage customPage;

    public  CustomPageRequest(Page<B> page) {
        this.content = page.getContent();
        this.customPage = new CustomPage(page.getTotalElements(),
                page.getTotalPages(), page.getNumber(), page.getSize());
    }

}
