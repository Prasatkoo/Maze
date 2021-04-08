import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33;

public class Main {

    public static void main(String[] args) throws Exception {
        String maze = text.load();

        String[] lines = maze.split("\n");
        int lenght = lines.length;
        float size = 2 / (float) lenght;
        char[][] zerosAndOnes = new char[lenght][lenght];
        {
            int i = 0;
            while (i < lenght) {
                for (int j = 0; j < lenght; j++) {
                    zerosAndOnes[i][j] = lines[i].charAt(j);
                }
                i++;
            }
        }
        GLFW.glfwInit();

        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        long window = GLFW.glfwCreateWindow(800, 600, "My first window", 0, 0);
        if (window == 0) {
            GLFW.glfwTerminate();
            throw new Exception("Can't open window");
        }
        GLFW.glfwMakeContextCurrent(window);

        GL.createCapabilities();
        GL33.glViewport(0, 0, 800, 600);

        GLFW.glfwSetFramebufferSizeCallback(window, (win, w, h) -> {
            GL33.glViewport(0, 0, w, h);
        });
        Shaders.initShaders();
        Square squareOne = new Square(0, 0, lenght);



        Square[][] squares = new Square[lenght][lenght];
        int i = 0;
        while (i < zerosAndOnes.length) {
            for (int j = 0; j < lenght; j++) {
                if (zerosAndOnes[i][j] == '1') {
                    squares[i][j] = new Square((j * size - 1), 1 - (i * size), size);
                } else {
                    squares[i][j] = null;
                }
            }
            i++;
        }

        while (!GLFW.glfwWindowShouldClose(window)) {

            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS)
                GLFW.glfwSetWindowShouldClose(window, true);

            GL33.glClearColor(0f, 0f, 0f, 1f);
            GL33.glClear(GL33.GL_COLOR_BUFFER_BIT);

            for (Square[] square : squares) {
                for (int j = 0; j < squares.length; j++) {
                    if (square[j] != null) square[j].render();
                }
            }
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
        GLFW.glfwTerminate();
    }

}
