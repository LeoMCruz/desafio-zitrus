package com.example.desafio.service;

import com.example.desafio.domain.model.User;
import com.example.desafio.domain.repository.UserRepository;
import com.example.desafio.dto.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static final String ALGUM_LOGIN = "dsadsadsa";

    private static final String ALGUMA_SENHA = "5415154";

    private static final String SENHA_CRIPTOGRAFADA = "9999999";

    private static final Long ID_USUARIO_SALVO = 3L;

    @InjectMocks //somente na classe de testar
    private UserService userService;

    @Mock
    private  PasswordEncoder passwordEncoder;

    @Mock
    private  UserRepository userRepository;


    @Test
    public void deveSalvarNovoUsuario(){
        var userDTO= UserDTO.builder()
                .login(ALGUM_LOGIN)
                .senha(ALGUMA_SENHA)
                .build();

        Mockito.when(passwordEncoder.encode(ALGUMA_SENHA)).thenReturn(SENHA_CRIPTOGRAFADA);

        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer(context -> {
                    var user = context.getArgument(0, User.class);
                    Assertions.assertEquals(user.getLogin(), ALGUM_LOGIN);
                    Assertions.assertEquals(user.getSenha(), SENHA_CRIPTOGRAFADA);
                    Assertions.assertNull(user.getId());
                    user.setId(ID_USUARIO_SALVO);
                    return user;
                });

        var response = userService.salvarUser(userDTO);

        Assertions.assertEquals(response.getLogin(), ALGUM_LOGIN);
        Assertions.assertEquals(response.getId(), ID_USUARIO_SALVO);
        Assertions.assertNull(response.getSenha());

        Mockito.verify(passwordEncoder, Mockito.times(1)).encode(Mockito.any());
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verifyNoMoreInteractions(passwordEncoder, userRepository);
    }

}