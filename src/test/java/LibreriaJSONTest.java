import io.stefano.Libreria;
import io.stefano.LibreriaConcreta;
import io.stefano.implementazioni.LibreriaJSON;
import io.stefano.implementazioni.LibreriaPersistente;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Test LibreriaJSON")
public class LibreriaJSONTest extends LibreriaTest{
    @Override
    public LibreriaPersistente createLibreriaPersistente() {
        return LibreriaJSON.INSTANCE;
    }

    @Override
    public Libreria createLibreria(LibreriaPersistente libreriaPersistente) {
        return new LibreriaConcreta(libreriaPersistente);
    }
}
