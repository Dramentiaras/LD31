#version 330 core

uniform sampler2D texture;

in vec4 color;
in vec2 texCoord;

out vec4 out_Color;

void main(void) {
	vec4 texColor = texture2D(texture, texCoord);
	out_Color = texColor * color;
}