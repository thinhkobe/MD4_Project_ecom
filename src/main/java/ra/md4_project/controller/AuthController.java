package ra.md4_project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.md4_project.exception.DataNotFound;
import ra.md4_project.exception.Locked;
import ra.md4_project.model.dto.request.FormLogin;
import ra.md4_project.model.dto.request.FormRegister;
import ra.md4_project.model.dto.respornWapper.EHttpStatus;
import ra.md4_project.model.dto.respornWapper.RespronWapper;
import ra.md4_project.service.userService.IUserService;

@RestController
@RequestMapping("/md4_project.com/v1/auth")
public class AuthController {
    @Autowired
    private IUserService iUserService;
    @PostMapping("/login")
    public ResponseEntity<?> doLogin(@RequestBody @Valid FormLogin formLogin) throws DataNotFound, Locked {
        return new ResponseEntity<>(new RespronWapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                iUserService.login(formLogin)
        ), HttpStatus.OK);
    }
     @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid FormRegister formRegister) throws DataNotFound {
         return new ResponseEntity<>(new RespronWapper<>(
                 EHttpStatus.SUCCESS,
                 HttpStatus.CREATED.name(),
                 HttpStatus.CREATED.value(),
                 iUserService.register(formRegister)
         ), HttpStatus.OK);
    }

}
