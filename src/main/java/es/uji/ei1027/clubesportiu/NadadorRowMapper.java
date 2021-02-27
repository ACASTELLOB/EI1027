package es.uji.ei1027.clubesportiu;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class NadadorRowMapper implements RowMapper<Nadador> {
    public Nadador mapRow(ResultSet rs, int rowNum) throws SQLException {
        Nadador nadador = new Nadador();
        nadador.setNom(rs.getString("nom"));
        nadador.setNumFederat(rs.getString("num_federat"));
        nadador.setEdat(rs.getInt("edat"));
        nadador.setPais(rs.getString("pais"));
        nadador.setGenere(rs.getString("genere"));
        return nadador;
    }
}
