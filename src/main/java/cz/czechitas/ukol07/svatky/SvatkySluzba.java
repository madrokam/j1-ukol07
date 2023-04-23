package cz.czechitas.ukol07.svatky;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.nio.file.Path;
import java.time.MonthDay;
import java.util.List;
import java.util.stream.Stream;

public class SvatkySluzba {

    private final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();
    private final Path cestaKDatum = Path.of("svatky.json");
    private final SeznamSvatku seznamSvatku;

    public SvatkySluzba() throws IOException {
        // TODO načíst seznam svátků ze souboru svatky.json
        seznamSvatku=objectMapper.readValue(cestaKDatum.toFile(),SeznamSvatku.class);
    }

    public List<String> vyhledatSvatkyDnes() {
        return vyhledatSvatkyKeDni(MonthDay.now());
    }

    public List<String> vyhledatSvatkyKeDni(MonthDay day) {
        // TODO
        // získat seznam svátků
        List<Svatek> listSvatku=seznamSvatku.getSvatky();
        // převést na Stream
        Stream<Svatek> streamSvatku=listSvatku.stream();
        // pomocí metody filter() vybrat jen ty, které odpovídají zadanému dni (porovnat MonthDay pomocí metodyequals())
        Stream<Svatek> nalezeneSvatky=streamSvatku.filter(x->x.getDen().equals(day));
        // pomocí metody map() získat z objektu jméno a pomocí toList() převést na List
        List<String> nalezenaJmenaSvatku=nalezeneSvatky.map(x->x.getJmeno()).toList();
        return nalezenaJmenaSvatku;
    }
}
