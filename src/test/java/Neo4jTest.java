
import BD.UsuarioNeo4j;
import Entidades.Persona;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Neo4jTest {
    private UsuarioNeo4j dao;

    @Before
    public void setUp() {
        this.dao = new UsuarioNeo4j();
    }

    @Test
    public void crearPersona() {
        Persona persona = new Persona("300000", "Jerry", "Smith");
        this.dao.create(persona);

        Persona persona2 = new Persona("300001", "Morty", "Smith");
        this.dao.create(persona2);

        this.dao.crearRelacionEsHijoDe(persona, persona2);
    }

    @After
    public void delete(){
        this.dao.deleteAll();
    }

}
