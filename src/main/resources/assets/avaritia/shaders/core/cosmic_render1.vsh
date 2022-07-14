#version 150

#moj_import <projection.glsl>

in vec3 Position;
in vec2 UV0;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform mat4 ModelViewProjMat;

out vec4 texProj0;
out vec2 texCoord0;
out vec4 position;

void main() {
	gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
	position = ModelViewProjMat * vec4(Position, 0.0);
	texCoord0 = UV0;
	texProj0 = projection_from_position(gl_Position);
}
