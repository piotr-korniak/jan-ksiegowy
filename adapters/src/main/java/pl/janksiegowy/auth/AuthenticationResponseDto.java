package pl.janksiegowy.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticationResponseDto {

    private final String token;
}
