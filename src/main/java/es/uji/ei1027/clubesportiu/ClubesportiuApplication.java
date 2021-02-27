package es.uji.ei1027.clubesportiu;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class ClubesportiuApplication implements CommandLineRunner {

	private static final Logger log = Logger.getLogger(ClubesportiuApplication .class.getName());

	public static void main(String[] args) {
		// Auto-configura l'aplicació
		new SpringApplicationBuilder(ClubesportiuApplication .class).run(args);
	}

	// Funció principal
	public void run(String... strings) throws Exception {
		log.info("Ací va el meu codi");
		log.info("Selecciona la nadadora Gemma Mengual");
		Nadador n1 = jdbcTemplate.queryForObject(
				"SELECT * FROM Nadador WHERE nom = 'Gemma Mengual'",
				new NadadorRowMapper());
		log.info(n1.toString());

		log.info("Selecciona la nadadora Gemma Mengual (amb paràmetre)");
		Nadador n2 = jdbcTemplate.queryForObject(
				"SELECT * FROM Nadador WHERE nom =?",
				new NadadorRowMapper(),
				"Gemma Mengual");
		log.info(n2.toString());

		log.info("Selecciona tots els nadadors");
		List<Nadador> nadadors = jdbcTemplate.query(
				"SELECT * FROM Nadador",
				new NadadorRowMapper());
		for (Nadador n: nadadors) {
			log.info(n.toString());
		}

		try {
			log.info("Selecciona una nadadora inexistent");
			Nadador n3 = jdbcTemplate.queryForObject(
					"SELECT * FROM Nadador WHERE nom = 'No estic'",
					new NadadorRowMapper());
			log.info(n3.toString());
		}
		catch(EmptyResultDataAccessException e) {
			log.info("No es troba a la base de dades");
		}

		try {
			log.info("Selecciona una nadadora inexistent");
			Nadador n3 = jdbcTemplate.queryForObject(
					"SELECT * FROM Nadador WHERE nom =?",
					new NadadorRowMapper(),
					"Gemma Mengual");
			log.info(n3.toString());
		}
		catch(EmptyResultDataAccessException e) {
			log.info("El nadador ? no existe");
		}

		log.info("Inserta una nova nadadora");
		jdbcTemplate.update(
				"INSERT INTO Nadador VALUES(?, ?, ?, ?, ?)",
				"Ariadna Edo", "XX1242", "Espanya", null, "Femení");
		log.info("I comprova que s'haja inserit correctament");

		mostraNadador("Gemma Mengual");



		//########################INSERCIÓ#######################################
		log.info("Inserta una nova nadadora");
		jdbcTemplate.update(
				"INSERT INTO Nadador VALUES(?, ?, ?, ?, ?)",
				"Ariadna Edo", "XX1242", "Espanya", null, "Femení");
		log.info("I comprova que s'haja inserit correctament");
		mostraNadador("Ariadna Edo");


		//#######################MODIFICACIÓ#######################################

		log.info("Actualitza l'edat de la nadadora Ariadna Edo a 21 anys");
		jdbcTemplate.update("UPDATE Nadador SET edat = 21 WHERE nom= Ariadna Edo ");
		log.info("I comprova que s'haja modificat correctament");
		mostraNadador("Ariadna Edo");

		//########################BORRAT###########################################
		log.info("Esborra la nadadora Ariadna Edo");
		jdbcTemplate.update("DELETE FROM Nadador WHERE Nom_nadador= Ariadna Edo");
		log.info("I comprova que s'haja esborrat correctament");
		mostraNadador("Ariadna Edo");


	}

	public void mostraNadador(String nomNadador){

		try {
			log.info("datos de un nadador");
			Nadador mostraNadador = jdbcTemplate.queryForObject(
					"SSELECT * FROM Nadador WHERE nom =?",
					new NadadorRowMapper(),
					nomNadador);
			log.info(mostraNadador.toString());
		}
		catch(EmptyResultDataAccessException e) {
			log.info("El nadador"+  nomNadador +" no es troba a la base de dades");
		}



	}


	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	// Plantilla per a executar operacions sobre la connexió
	private JdbcTemplate jdbcTemplate;

	// Crea el jdbcTemplate a partir del DataSource que hem configurat
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

}
