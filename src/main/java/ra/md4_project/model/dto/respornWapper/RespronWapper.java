package ra.md4_project.model.dto.respornWapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RespronWapper<T>{
    private EHttpStatus httpStatus;
    private String message;
    private Integer code;
    private T data;
}
