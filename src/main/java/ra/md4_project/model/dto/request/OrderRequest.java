package ra.md4_project.model.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {


    @Size(max = 100)
    private String note;

    @NotBlank
    @Size(max = 100)
    private String receiveName;

    @NotBlank
    @Size(max = 255)
    private String receiveAddress;

    @NotBlank
    @Pattern(regexp = "(^$|[0-9]{10})")
    private String receivePhone;

    @NotNull
    private List<Long> shoppingCartId;
}