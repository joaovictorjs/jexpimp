import com.github.joaovictorjs.Jexpimp;
import com.github.joaovictorjs.NotJSONFileException;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    @AfterAll
    public static void cleanup() {
        new File(FilenameUtils.separatorsToSystem("./mock.json")).delete();
    }

    @Test
    @Order(1)
    public void exportJSONTest() {
        try {
            boolean res = Jexpimp.exportJSON("./mock.json", new Mock(), false);
            Assertions.assertTrue(res);
        } catch (NotJSONFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(2)
    public void exportNonJSONTest() {
        Assertions.assertThrows(NotJSONFileException.class, () -> {
            Jexpimp.exportJSON("./mock.ini", new Mock(), false);
        });
    }

    @Test
    @Order(3)
    public void exportWhenDuplicatedTest() {
        try {
            boolean resFalse = Jexpimp.exportJSON("./mock.json", new Mock(), false);
            boolean resTrue = Jexpimp.exportJSON("./mock.json", new Mock(), true);
            Assertions.assertFalse(resFalse);
            Assertions.assertTrue(resTrue);
        } catch (NotJSONFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(4)
    public void exportNestedPathTest() {
        File root = new File(FilenameUtils.separatorsToSystem("./home"));
        String[] nested = {"a", "b", "c", "d", "e"};

        for (String n : nested) {
            root = new File(root, n);
        }

        root = new File(root, "mock.json");

        try {
            boolean res = Jexpimp.exportJSON(root.getPath(), new Mock(), false);
            Assertions.assertTrue(res);
            Assertions.assertTrue(root.exists());

            root.delete();

            File parent = new File(root.getParent());

            for (int i = 0; i < 6; i++) {
                parent.delete();
                parent = new File(parent.getParent());
            }
        } catch (NotJSONFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(5)
    public void importTest() {
        try {
            Mock expected = new Mock();
            Mock res = Jexpimp.importJSON("./mock.json", Mock.class);
            Assertions.assertTrue(expected.name.equals(res.name));
            Assertions.assertTrue(expected.surname.equals(res.surname));
        } catch (NotJSONFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
