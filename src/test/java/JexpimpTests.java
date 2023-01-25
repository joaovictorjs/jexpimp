import com.github.joaovictorjs.Jexpimp;
import com.github.joaovictorjs.NotJSONFileException;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.junit.jupiter.api.*;

import java.io.IOException;

class Mock {
    String name;
    String surname;

    Mock() {
        this.name = "joaovictorjs";
        this.surname = "jexpimp";
    }
    Mock(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JexpimpTests {
    @Test
    @Order(9)
    public void importTest() {
        try {
            Mock expected = new Mock();
            Mock res = Jexpimp.importJSON("mock.json", Mock.class);
            Assertions.assertTrue(expected.name.equals(res.name));
            Assertions.assertTrue(expected.surname.equals(res.surname));
        } catch (NotJSONFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
