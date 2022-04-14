package kg.peaksoft.ebookb4.api;

import kg.peaksoft.ebookb4.dto.request.AudioBookRequest;
import kg.peaksoft.ebookb4.service.AudioBookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

@RestController
@RequestMapping("/api/audioBook")
@AllArgsConstructor
public class AudioBookApi {

    private final AudioBookService audioBookService ;

    @PermitAll
    @PostMapping("/save")
    public ResponseEntity<?> saveAudioBook(AudioBookRequest audioBookRequest){
        return audioBookService.register(audioBookRequest);
    }


}
