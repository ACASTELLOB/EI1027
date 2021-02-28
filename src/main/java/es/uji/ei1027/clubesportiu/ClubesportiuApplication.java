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




		//########################INSERCIÓ#######################################
		/*log.info("Inserta una nova nadadora");
		jdbcTemplate.update(
				"INSERT INTO Nadador VALUES(?, ?, ?, ?, ?)",
				"Ariadna es", "XX1243", "Espanya", null, "Femení");
		log.info("I comprova que s'haja inserit correctament");

		mostraNadador("Ariadna es");


		//#######################MODIFICACIÓ#######################################

		log.info("Actualitza l'edat de la nadadora Ariadna Edo a 21 anys");
		jdbcTemplate.update("UPDATE Nadador SET edat = 21 WHERE nom= 'Ariadna Edo' ");
		log.info("I comprova que s'haja modificat correctament");

		mostraNadador("Ariadna Edo");



		//########################BORRAT###########################################
		*/log.info("Esborra la nadadora Ariadna Edo");
		jdbcTemplate.update("DELETE FROM Nadador WHERE nom= 'Ariadna Edo'");
		log.info("I comprova que s'haja esborrat correctament");
		mostraNadador("Ariadna Edo");

	}

	public void mostraNadador(String nomNadador){

		try {
			log.info("mostrar nadador");
			Nadador mostraNadador = jdbcTemplate.queryForObject(
					"SELECT * FROM Nadador WHERE nom =?",
					new NadadorRowMapper(),
					nomNadador);
			log.info(mostraNadador.toString());
		}
		catch(EmptyResultDataAccessException e) {
			log.info("El nadador "+  nomNadador +" no es troba a la base de dades");
		}
		provaNadadorDao();


	}
	// Demana a Spring que ens proporcione una instància de NadadorDAO
	// mitjanjant injecció de dependencies
	@Autowired
	NadadorDao nadadorDao;

	void provaNadadorDao() {
		log.info("Provant NadadorDao");
		log.info("Tots els nadadors");

		for (Nadador n: nadadorDao.getNadadors()) {
			log.info(n.toString());
		}

		log.info("Dades de Gemma Mengual");
		Nadador n = nadadorDao.getNadador("Gemma Mengual");
		log.info(n.toString());


		Nadador aEdo = new Nadador();
		aEdo.setNom("Ariadna Edo");
		aEdo.setEdat(21);
		log.info("Nou: Ariadna Edo");
		nadadorDao.addNadador(aEdo);
		log.info(nadadorDao.getNadador("Ariadna Edo").toString());

		log.info("Actualitzat: Ariadna Edo");
		aEdo.setPais("Espanya");
		aEdo.setGenere("Femení");
		nadadorDao.updateNadador(aEdo);
		log.info(nadadorDao.getNadador("Ariadna Edo").toString());

		log.info("Esborrat: Ariadna Edo");
		nadadorDao.deleteNadador(aEdo);
		if (nadadorDao.getNadador("Ariadna Edo") == null) {
			log.info("Esborrada correctament");
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
