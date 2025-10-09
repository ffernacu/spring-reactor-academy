package pe.ffernacu.spring_reactor_academy.exception;

import java.time.LocalDateTime;

public record CustomErrorResponse(LocalDateTime dateTime, String message)
{
}
