import game.Chessboard;
import game.ChessSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by pbarbarski on 01/09/2016.
 */
public final class Chess {

    private Chess() {
    }

    public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException {
        System.out.println("Hello. It's a program for playing chess by Paweł Barbarski.");
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ChessSet chessSet = new ChessSet();
        Chessboard chessboard;
        boolean isChosen = false;
        Class[] chessboards = getChessboards();
        while (!isChosen) {
            System.out.println("Which chessboard would you like to play?\nPlease choose a number denoting one of the following:\n");
            System.out.println("0. Exit the program.");
            for (int i = 1; i <= chessboards.length; i++) {
                try {
                    System.out.println(Integer.toString(i) + ". " + chessboards[i - 1].getDeclaredField("description").get(null));
                } catch (NoSuchFieldException e) {
                    System.out.println(Integer.toString(i) + ". " + chessboards[i - 1].getName());
                }
            }
            try {
                int chessboardSymbol = Integer.parseInt(br.readLine());
                if (chessboardSymbol == 0) {
                    isChosen = true;
                } else if (chessboardSymbol >= 1 && chessboardSymbol <= chessboards.length) {
                    isChosen = true;
                    chessboard = (Chessboard) chessboards[chessboardSymbol - 1].newInstance();
                    chessboard.play(chessSet);
                } else {
                    isChosen = false;
                    System.out.println("Wrong number.");
                }
            } catch (NumberFormatException e) {
                isChosen = false;
                System.out.println("Wrong number.");
            }
        }
    }

    private static Class[] getChessboards() {
        String packageName = "chessboards";
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL resource = classLoader.getResource(packageName);
        File directory = new File(resource.getFile());
        ArrayList<Class> classes = new ArrayList<>();
        if (directory.exists()) {
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.getName().endsWith(".class")) {
                    try {
                        Class clazz = Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6));
                        if (Chessboard.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers())) {
                            classes.add(clazz);
                        }
                    } catch (ClassNotFoundException e) {
                    }
                }
            }
        }
        return classes.toArray(new Class[classes.size()]);
    }


}
