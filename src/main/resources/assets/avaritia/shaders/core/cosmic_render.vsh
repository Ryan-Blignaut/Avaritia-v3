#version 150

#moj_import <projection.glsl>

in vec3 Position;
in vec2 UV0;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

out vec4 texProj0;
out vec2 texCoord0;
out float vertexDistance;

out vec3 position;
out vec3 projection;
out vec3 normal;

vec3 fnormal()
{
	//Compute the normal
	vec3 normal = gl_NormalMatrix * gl_Normal;
	normal = normalize(normal);
	return normal;
}

void main() {
	gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

	position = (ModelViewMat * vec4(Position, 1.0)).xyz;

	fnormal = fnormal();

	texCoord0 = UV0;
	vertexDistance = length((ModelViewMat * vec4(Position, 1.0)).xyz);
	texProj0 = projection_from_position(gl_Position);
}
