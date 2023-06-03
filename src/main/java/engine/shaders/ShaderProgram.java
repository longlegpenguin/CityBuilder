package engine.shaders;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.*;
import java.nio.FloatBuffer;

/**
 * Abstract class from which all shaders inherit.
 */
public abstract class ShaderProgram {

    private int programID;
    private int vertexShaderID;
    private int fragmentShaderID;

    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    /**
     * Receives the file name of the vertex and fragment GLSL file for a shader.
     * Proceeds to load the shader file from the method below and then create a shader program which runs on the GPU.
     * Shader program is attached and linked to the GPU and all attributes are bind as well as all uniform locations are found.
     * @param vertexFile
     * @param fragmentFile
     */
    public ShaderProgram(String vertexFile, String fragmentFile) {
        vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
        programID = GL20.glCreateProgram();
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);
        bindAttributes();
        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);
        getAllUniformLocations();
    }

    /**
     * Method uses a buffered reader and string builder to read the data from the GLSL file and then compiles the shader on the GPU.
     * @param file
     * @param type
     * @return the ID of the Shader program.
     */
    private static int loadShader(String file, int type) {
        StringBuilder shaderSource = new StringBuilder();
        InputStream is = ShaderProgram.class.getResourceAsStream(file);
        BufferedReader reader = null;
        if (is != null) {
            reader = new BufferedReader(new InputStreamReader(is));
        } else {
            System.out.println(file + "Baddddddddddddd");
        }

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch (Exception e) {
            System.err.println("Could not read file!" + file);
            e.printStackTrace();
            System.exit(-1);
        }

        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);

        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader");
            System.exit(-1);
        }

        return shaderID;

    }

    /**
     * Starts the program
     */
    public void start() {
        GL20.glUseProgram(programID);
    }

    /**
     * Stops the program
     */
    public void stop() {
        GL20.glUseProgram(0);
    }

    /**
     * Detaches and deletes the program from the GPU
     */
    public void cleanUp() {
        stop();
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
        GL20.glDeleteProgram(programID);
    }

    /**
     * Binds the attributes to the Shader
     * @param attribute
     * @param variableName
     */
    protected void bindAttribute(int attribute, String variableName) {
        GL20.glBindAttribLocation(programID, attribute, variableName);
    }

    /**
     * Finds the uniform variables in the shader
     * @param uniformName
     * @return
     */
    protected int getUniformLocation(String uniformName) {
        return GL20.glGetUniformLocation(programID, uniformName);
    }

    /**
     * Loads a float value into the shader.
     * @param location
     * @param value
     */
    protected void loadFloat(int location, float value) {
        GL20.glUniform1f(location, value);
    }

    /**
     * Loads a Vector3f into the shader.
     * @param location
     * @param vector
     */
    protected void loadVector(int location, Vector3f vector) {
        GL20.glUniform3f(location, vector.x, vector.y, vector.z);
    }

    /**
     * Loads a Vector2f into the shader
     * @param location
     * @param vector
     */
    protected void load2DVector(int location, Vector2f vector) {
        GL20.glUniform2f(location, vector.x, vector.y);
    }

    /**
     * Loads a boolean value into the shader.
     * @param location
     * @param value
     */
    protected void loadBoolean(int location, boolean value) {
        float toLoad = 0;
        if (value) {
            toLoad = 1;
        }
        GL20.glUniform1f(location, toLoad);
    }

    /**
     * Loads a Matrix4f into the shader
     * @param location
     * @param matrix
     */
    protected void loadMatrix(int location, Matrix4f matrix) {
        matrix.get(matrixBuffer);
        GL20.glUniformMatrix4fv(location, false, matrixBuffer);
    }

    /**
     * Binds all attributes on a per shader program basis
     */
    protected abstract void bindAttributes();

    /**
     * Gets all uniform loactions on a per shader program basis.
     */
    protected abstract void getAllUniformLocations();



}
