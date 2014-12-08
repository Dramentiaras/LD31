package com.goudagames.ld31.render;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.goudagames.ld31.texture.Texture;
import com.goudagames.ld31.texture.TextureLibrary;
import com.goudagames.ld31.util.Vertex;

public class RenderEngine {

	public static RenderEngine instance;
	private static boolean initiated;
	
	private Matrix4f projection, model;
	
	private int vaoID, vboID, vboiID;
	private int texProgram, program;
	
	private Vertex[] quad;
	
	private float r, g, b, a;
	
	private int projLocation, modelLocation;
	
	public void renderQuad(float x, float y, float width, float height, float rotation) {
		
		GL20.glUseProgram(program);
		
		model = new Matrix4f();
		
		x = Math.round(x);
		y = Math.round(y);
		
		quad[0].setColor(r, g, b, a);
		quad[1].setColor(r, g, b, a);
		quad[2].setColor(r, g, b, a);
		quad[3].setColor(r, g, b, a);
		
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(quad.length * Vertex.elementCount);
		for (int i = 0; i < quad.length; i++) {
			vertexBuffer.put(quad[i].getElements());
		}
		vertexBuffer.flip();
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, Vertex.positionElementCount, GL11.GL_FLOAT, false, Vertex.stride, Vertex.positionByteOffset);
		GL20.glVertexAttribPointer(1, Vertex.colorElementCount, GL11.GL_FLOAT, false, Vertex.stride, Vertex.colorByteOffset);
		GL20.glVertexAttribPointer(2, Vertex.textureElementCount, GL11.GL_FLOAT, false, Vertex.stride, Vertex.textureByteOffset);
	
		model.translate(new Vector2f(x, y));
		model.rotate(rotation, new Vector3f(0f, 0f, 1f));
		model.scale(new Vector3f(width, height, 1f));
		
		FloatBuffer projBuf = BufferUtils.createFloatBuffer(16);
		projection.store(projBuf); projBuf.flip();
		GL20.glUniformMatrix4(projLocation, false, projBuf);
		
		FloatBuffer modelBuf = BufferUtils.createFloatBuffer(16);
		model.store(modelBuf); modelBuf.flip();
		GL20.glUniformMatrix4(modelLocation, false, modelBuf);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiID);
		GL20.glDisableVertexAttribArray(2);
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_BYTE, 0);
		
		GL20.glEnableVertexAttribArray(2);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void renderTexturedQuad(float x, float y, float width, float height, float rotation,
			float u, float v, float tw, float th, boolean normalize, String texture) {
		
		renderTexturedQuad(x, y, width, height, rotation, u, v, tw, th, normalize, TextureLibrary.get(texture));
	}
	
	public void renderTexturedQuad(float x, float y, float width, float height, float rotation,
			float u, float v, float tw, float th, boolean normalize, Texture texture) {
		
		GL20.glUseProgram(texProgram);
		
		x = Math.round(x);
		y = Math.round(y);
		
		if (normalize) {
			
			u /= texture.getWidth();
			v /= texture.getHeight();
			tw /= texture.getWidth();
			th /= texture.getHeight();
		}
		
		quad[0].setUV(u, v).setColor(r, g, b, a);
		quad[1].setUV(u, v + th).setColor(r, g, b, a);
		quad[2].setUV(u + tw, v + th).setColor(r, g, b, a);
		quad[3].setUV(u + tw, v).setColor(r, g, b, a);
		
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(quad.length * Vertex.elementCount);
		for (int i = 0; i < quad.length; i++) {
			vertexBuffer.put(quad[i].getElements());
		}
		vertexBuffer.flip();
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, Vertex.positionElementCount, GL11.GL_FLOAT, false, Vertex.stride, Vertex.positionByteOffset);
		GL20.glVertexAttribPointer(1, Vertex.colorElementCount, GL11.GL_FLOAT, false, Vertex.stride, Vertex.colorByteOffset);
		GL20.glVertexAttribPointer(2, Vertex.textureElementCount, GL11.GL_FLOAT, false, Vertex.stride, Vertex.textureByteOffset);
	
		model.translate(new Vector2f(x, y));
		model.rotate(rotation, new Vector3f(0f, 0f, 1f));
		model.scale(new Vector3f(width, height, 1f));
		
		FloatBuffer projBuf = BufferUtils.createFloatBuffer(16);
		projection.store(projBuf); projBuf.flip();
		GL20.glUniformMatrix4(projLocation, false, projBuf);
		
		FloatBuffer modelBuf = BufferUtils.createFloatBuffer(16);
		model.store(modelBuf); modelBuf.flip();
		GL20.glUniformMatrix4(modelLocation, false, modelBuf);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		texture.bind();
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiID);
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_BYTE, 0);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		model = new Matrix4f();
	}
	
	public void setRGBA(float r, float g, float b, float a) {
		
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public void setRGB(float r, float g, float b) {
		
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public void setAlpha(float a) {
		
		this.a = a;
	}
	
	public void setRed(float r) {
		
		this.r = r;
	}
	
	public void setGreen(float g) {
		
		this.g = g;
	}
	
	public void setBlue(float b) {
		
		this.b = b;
	}
	
	public static void init() {
		
		instance = new RenderEngine();
		initiated = true;
	}
	
	public static boolean isInitiated() {
		
		return initiated;
	}
	
	public static Matrix4f ortho(float left, float right, float bottom, float top, float near, float far) {
		
		Matrix4f result = new Matrix4f();
		
		result.m00 = 2f / (right - left);
		result.m11 = 2f / (top - bottom);
		result.m22 = -2f / (far - near);
		result.m30 = -((right + left) / (right - left));
		result.m31 = -((top + bottom) / (top - bottom));
		result.m32 = (far + near) / (far - near);
		result.m33 = 1f;
		
		return result;
	}
	
	private static String loadShaderSource(String path) {
		
		StringBuilder result = new StringBuilder("");
		
		try {
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(path)));
			String line;
			while ((line = reader.readLine()) != null) {
				
				result.append(line).append("\n");
			}
			reader.close();
		}
		catch (Exception ex) {
			
			System.err.println("Error when loading shader source");
			ex.printStackTrace();
		}
		
		return result.toString();
	}
	
	private RenderEngine() {
		
		model = new Matrix4f();
		projection = ortho(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);
		
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		
		vboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		GL20.glVertexAttribPointer(0, Vertex.positionElementCount, GL11.GL_FLOAT, false, Vertex.stride, Vertex.positionByteOffset);
		GL20.glVertexAttribPointer(1, Vertex.colorElementCount, GL11.GL_FLOAT, false, Vertex.stride, Vertex.colorByteOffset);
		GL20.glVertexAttribPointer(2, Vertex.textureElementCount, GL11.GL_FLOAT, false, Vertex.stride, Vertex.textureByteOffset);
		
		GL30.glBindVertexArray(0);
		
		r = g = b = a = 1f;
		
		byte[] index = new byte[] {0, 1, 2, 2, 3, 0};
		quad = new Vertex[] {
				new Vertex().setXY(-0.5f, 0.5f).setColor(r, g, b, a).setUV(0f, 1f),
				new Vertex().setXY(-0.5f, -0.5f).setColor(r, g, b, a).setUV(0f, 0f),
				new Vertex().setXY(0.5f, -0.5f).setColor(r, g, b, a).setUV(1f, 0f),
				new Vertex().setXY(0.5f, 0.5f).setColor(r, g, b, a).setUV(1f, 1f)
		};
		
		ByteBuffer indecies = BufferUtils.createByteBuffer(index.length);
		indecies.put(index);
		indecies.flip();
		
		vboiID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiID);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indecies, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		int vs = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		
		GL20.glShaderSource(vs, loadShaderSource("com/goudagames/ld31/shader/vert.glsl"));
		GL20.glCompileShader(vs);
		
		int fs = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		
		GL20.glShaderSource(fs, loadShaderSource("com/goudagames/ld31/shader/frag.glsl"));
		GL20.glCompileShader(fs);
		
		program = GL20.glCreateProgram();
		
		GL20.glAttachShader(program, vs);
		GL20.glAttachShader(program, fs);
		
		GL20.glBindAttribLocation(program, 0, "in_Position");
		GL20.glBindAttribLocation(program, 1, "in_Color");
		
		GL20.glLinkProgram(program);
		GL20.glValidateProgram(program);
		
		if (GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
			
			System.out.println("Error in program linking.");
		}
		
		int tvs = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		
		GL20.glShaderSource(tvs, loadShaderSource("com/goudagames/ld31/shader/tex_vert.glsl"));
		GL20.glCompileShader(tvs);
		
		int tfs = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		
		GL20.glShaderSource(tfs, loadShaderSource("com/goudagames/ld31/shader/tex_frag.glsl"));
		GL20.glCompileShader(tfs);
		
		texProgram = GL20.glCreateProgram();
		
		GL20.glAttachShader(texProgram, tvs);
		GL20.glAttachShader(texProgram, tfs);
		
		GL20.glBindAttribLocation(texProgram, 0, "in_Position");
		GL20.glBindAttribLocation(texProgram, 1, "in_Color");
		GL20.glBindAttribLocation(texProgram, 2, "in_TexCoord");
		
		GL20.glLinkProgram(texProgram);
		GL20.glValidateProgram(texProgram);
		
		int error = GL20.glGetProgrami(texProgram, GL20.GL_LINK_STATUS);
		if (error == GL11.GL_FALSE) {
			System.err.println("Error linking program!");
			System.err.println(GL20.glGetProgramInfoLog(texProgram, GL20.GL_INFO_LOG_LENGTH));
			return;
		}
		
		projLocation = GL20.glGetUniformLocation(texProgram, "projection");
		modelLocation = GL20.glGetUniformLocation(texProgram, "model");
		
		GL30.glBindVertexArray(vaoID);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		GL11.glClearColor(0f, 0f, 0f, 1f);
	}
}
