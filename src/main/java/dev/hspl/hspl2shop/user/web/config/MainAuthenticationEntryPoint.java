package dev.hspl.hspl2shop.user.web.config;

import dev.hspl.hspl2shop.common.web.ProblemMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@NullMarked
@RequiredArgsConstructor
public class MainAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;
    private final MessageSource messageSource;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        String problemKey = "unauthorized";

        String userFriendyMessage = messageSource.getMessage(
                problemKey, null,
                problemKey,
                Locale.of("fa", "IR")
        );

        var responseString = objectMapper.writeValueAsString(
                new ProblemMessage(problemKey, (short) 401,
                        userFriendyMessage != null ? userFriendyMessage : problemKey, null)
        );

        response.setCharacterEncoding(StandardCharsets.UTF_8);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(responseString);
    }
}
