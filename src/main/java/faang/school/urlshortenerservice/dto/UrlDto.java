package faang.school.urlshortenerservice.dto;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlDto {

    @NotNull(message = "URL cannot be null")
    @URL(regexp = "^((https?|ftp):\\/\\/)?([0-9a-zA-Z\\-\\.]+)(?::(\\d+))?(\\/[^?#]*)?(?:\\?([^#]*))?(?:#(.*))?$")
    private String baseUrl;

}