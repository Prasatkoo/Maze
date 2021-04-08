import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
    public class Square {
        private float[] vertices;
        private final int[] indices = {
                0, 1, 3,
                1, 2, 3,
        };
        private final int squareVaoId;
        private final int squareVboId;
        private final int squareEboId;

        public Square(float a, float b, float size) {
            float[] vertices = {
                    a + size, b, 0.0f,
                    a + size, b - size, 0.0f,
                    a, b - size, 0.0f,
                    a, b, 0.0f,
            };
            this.vertices = vertices;
            squareVaoId = GL33.glGenVertexArrays();
            squareEboId = GL33.glGenBuffers();
            squareVboId = GL33.glGenBuffers();


            GL33.glBindVertexArray(squareVaoId);
            GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, squareEboId);
            IntBuffer ib = BufferUtils.createIntBuffer(indices.length)
                    .put(indices)
                    .flip();

            GL33.glBufferData(GL33.GL_ELEMENT_ARRAY_BUFFER, ib, GL33.GL_STATIC_DRAW);
            GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, squareVboId);
            FloatBuffer fb = BufferUtils.createFloatBuffer(vertices.length)
                    .put(vertices)
                    .flip();

            GL33.glBufferData(GL33.GL_ARRAY_BUFFER, fb, GL33.GL_STATIC_DRAW);
            GL33.glVertexAttribPointer(0, 3, GL33.GL_FLOAT, false, 0, 0);
            GL33.glEnableVertexAttribArray(0);

            MemoryUtil.memFree(fb);
            MemoryUtil.memFree(ib);
        }

        public void render () {
            GL33.glUseProgram(Shaders.shaderProgramId);
            GL33.glBindVertexArray(squareVaoId);
            GL33.glDrawElements(GL33.GL_TRIANGLES, indices.length, GL33.GL_UNSIGNED_INT, 0);
        }
    }

