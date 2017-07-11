import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by rhaveson on 7/11/2017.
 */
public class FileIOSample {

    public static void main(final String args[]) {

        SimpleClass simpleClass = new SimpleClass(2, "Ryan Haveson");
        System.out.printf("Initial value is: %s\n", simpleClass);


        Path path = Paths.get("simpleClass.ser");

        // Check to see if the file exists
        if(!Files.exists(path)) {
            // The file does not exist
            System.out.print("Saving class....");
            saveClass(path, simpleClass);
            System.out.println("Saved class");
        } else {
            System.out.println("The class has already been saved earlier");
        }

        System.out.println("changing some values");
        simpleClass.setMyInt(12);
        simpleClass.setMyString("Booby");
        System.out.printf("New value is: %s\n", simpleClass);

        System.out.print("loading the class...");
        simpleClass = loadClass(path);
        System.out.println("loaded the class");

        System.out.printf("After loading, the new value is: %s\n", simpleClass);
    }

    static void saveClass(Path path, SimpleClass simpleClass) {
        try (ObjectOutputStream objectStream = new ObjectOutputStream(Files.newOutputStream(path))) {
            objectStream.writeObject(simpleClass);
        } catch( IOException e) {
            e.printStackTrace();
        }
    }

    static SimpleClass loadClass(Path path) {
        SimpleClass simpleClass = null;
        try( ObjectInputStream objectStream = new ObjectInputStream(Files.newInputStream(path))) {
            simpleClass = (SimpleClass) objectStream.readObject();
        } catch (IOException e ) {
            e.printStackTrace();
        } catch (ClassNotFoundException ce) {
            ce.printStackTrace();
        }

        return simpleClass;
    }
}
