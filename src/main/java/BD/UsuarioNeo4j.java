package BD;

import java.util.List;

import Entidades.Persona;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.Values;

public class UsuarioNeo4j {

    private Driver driver;

    public UsuarioNeo4j() {
        //this.driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "password" ) );
        this.driver = GraphDatabase.driver( "http://localhost:7474", AuthTokens.basic( "neo4j", "neo4j" ) );
    }

    public void create(Persona persona) {
        Session session = this.driver.session();

        try {
            //CREATE (n:Persona {dni:{elDni}, name:  {elNombre}, surname: {elApellido}})
            String query = "MERGE (n:Persona {dni: {elDni}}) " +
                    "SET n.name = {elNombre} " +
                    "SET n.surname = {elApellido}";
            session.run(query, Values.parameters("elNombre", persona.getNombre(),
                    "elDni", persona.getDni(),
                    "elApellido", persona.getApellido()));

        } finally {
            session.close();
        }
    }

    public void crearRelacionEsHijoDe(Persona padre, Persona hijo) {
        Session session = this.driver.session();

        try {
            String query = "MATCH (padre:Persona {dni: {elDniPadre}}) " +
                    "MATCH (hijo:Persona {dni: {elDniHijo}}) " +
//					"MERGE (padre)-[:padreDe]->(hijo) " + //CREATE
                    "MERGE (hijo)-[:hijoDe]->(padre)"; //CREATE
            session.run(query, Values.parameters("elDniPadre", padre.getDni(),
                    "elDniHijo", hijo.getDni()));

        } finally {
            session.close();
        }
    }

    public List<Persona> getHijosDe(Persona padre) {
        Session session = this.driver.session();

        try {
            String query = "MATCH (padre:Persona {dni: {elDniPadre}}) " +
                    "MATCH (hijo)-[:hijoDe]->(padre) " +
                    "RETURN hijo";
            StatementResult result = session.run(query, Values.parameters("elDniPadre", padre.getDni()));

            //Similar a list.stream().map(...)
            return result.list(record -> {
                Value hijo = record.get(0);
                String dni = hijo.get("dni").asString();
                String nombre = hijo.get("nombre").asString();
                String apellido = hijo.get("apellido").asString();
                return new Persona(dni, nombre, apellido);
            });


        } finally {
            session.close();
        }
    }

    public void crearRelacionEsAmigoDe(Persona p, Persona amigo) {
        Session session = this.driver.session();

        try {
            String query = "MATCH (p:Persona {dni: {elDniPersona}}) " +
                    "MATCH (amigo:Persona {dni: {elDniAmigo}}) " +
                    "MERGE (p)-[:AmigoDe]->(amigo)"; //CREATE
            session.run(query, Values.parameters("elDniPersona", p.getDni(),
                    "elDniAmigo", amigo.getDni()));

        } finally {
            session.close();
        }

    }

    public List<Persona> getAmigosDe(Persona p) {
        Session session = this.driver.session();

        try {
            String query = "MATCH (p:Persona {dni: {elDniPersona}}) " +
                    "MATCH (p)-[:AmigoDe]->(amigo) " +
                    "RETURN amigo";
            StatementResult result = session.run(query, Values.parameters("elDniPersona", p.getDni()));

            //Similar a list.stream().map(...)
            return result.list(record -> {
                Value amigo = record.get(0);
                String dni = amigo.get("dni").asString();
                String nombre = amigo.get("nombre").asString();
                String apellido = amigo.get("apellido").asString();
                return new Persona(dni, nombre, apellido);
            });


        } finally {
            session.close();
        }
    }

    public void deleteAll(){
        Session session = this.driver.session();

        try {
            String query = "MATCH (a) DETACH DELETE a";
            session.run(query);


        } finally {
            session.close();
        }

    }

}
