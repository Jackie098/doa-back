package project.common.mappers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateTimeMapper {

    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("America/Sao_Paulo");

    // Converte Instant para o timezone informado (ou o padrão do sistema)
    public static ZonedDateTime toZonedDateTime(Instant instant, String timeZone) {
        ZoneId zoneId = timeZone == null ? DEFAULT_ZONE_ID : ZoneId.of(timeZone);
        return instant.atZone(zoneId);
    }

    // Converte Instant para o timezone padrão (Brasil)
    public static ZonedDateTime toDefaultZonedDateTime(Instant instant) {
        return instant.atZone(DEFAULT_ZONE_ID);
    }

    // Retorna data no formato ISO-8601 com timezone, como string
    public static String toIsoStringWithZone(Instant instant, String timeZone) {
        return toZonedDateTime(instant, timeZone).toString(); // ex: 2025-05-28T15:00-03:00
    }

    // Retorna apenas a data local (ex: para lógica de comparação de datas)
    public static LocalDate toLocalDate(Instant instant, String timeZone) {
        return toZonedDateTime(instant, timeZone).toLocalDate();
    }

    // Retorna true se a data for "hoje" no fuso informado
    public static boolean isToday(Instant instant, String timeZone) {
        LocalDate localDate = toLocalDate(instant, timeZone);
        return localDate.equals(LocalDate.now(ZoneId.of(timeZone)));
    }
}
