#version 150

#moj_import <matrix.glsl>

#define M_PI 3.1415926535897932384626433832795
#define COSMIC_COUNT 2

uniform sampler2D Sampler0;
uniform sampler2D Sampler1;

uniform float GameTime;
uniform float Time;
uniform float Pitch;
uniform float Yaw;

uniform int EndPortalLayers;

//uniform float CosmicUVs[40];

uniform mat2 CosmicUVs[10];

in vec4 texProj0;
in vec2 texCoord0;

in vec4 position;

//https://gist.github.com/neilmendoza/4512992
mat4 rotationMatrix(vec3 axis, float angle)
{

	axis = normalize(axis);
	float s = sin(angle);
	float c = cos(angle);
	float oc = 1.0 - c;

	return mat4(oc * axis.x * axis.x + c, oc * axis.x * axis.y - axis.z * s, oc * axis.z * axis.x + axis.y * s, 0.0,
	oc * axis.x * axis.y + axis.z * s, oc * axis.y * axis.y + c, oc * axis.y * axis.z - axis.x * s, 0.0,
	oc * axis.z * axis.x - axis.y * s, oc * axis.y * axis.z + axis.x * s, oc * axis.z * axis.z + c, 0.0,
	0.0, 0.0, 0.0, 1.0);
}

out vec4 fragColor;
float rand2d(vec2 x) {
	return fract(sin(mod(dot(x, vec2(12.9898, 78.233)), 3.14)) * 43758.5453);
}
void main() {
	float mask = texture(Sampler0, texCoord0).r;
	if (mask <= 0) {
		discard;
	}
	vec4 col = vec4(0.1, 0.0, 0.0, 1.0);


	float pulse = mod(Time, 400) / 400.0;
	col.g = sin(pulse*M_PI*2) * 0.075 + 0.225;
	col.b = cos(pulse*M_PI*2) * 0.05 + 0.3;

	vec4 dir = normalize(vec4(position.xyz, 0));

	/*float sb = sin(Pitch);
	float cb = cos(Pitch);
	dir = normalize(vec4(dir.x, dir.y * cb - dir.z * sb, dir.y * sb + dir.z * cb, 0));

	float sa = sin(-Yaw);
	float ca = cos(-Yaw);
	dir = normalize(vec4(dir.z * sa + dir.x * ca, dir.y, dir.z * ca - dir.x * sa, 0));*/

	// get semi-random stuff
	for (int i=0; i<16; i++) {
		int j = i + 7;
		float rand1 = (j * j * 4321 + j * 8) * 2.0;
		int k = j + 1;
		float rand2 = (k * k * k * 239 + k * 37) * 3.6;
		float rand3 = rand1 * 347.4 + rand2 * 63.4;

		// random rotation matrix by random rotation around random axis
		vec3 axis = normalize(vec3(sin(rand1), sin(rand2), cos(rand3)));

		// apply
		vec4 ray = dir * rotationMatrix(axis, mod(rand3, 2*M_PI));

		float sb = sin(Pitch);
		float cb = cos(Pitch);
		dir = normalize(vec4(dir.x, dir.y * cb - dir.z * sb, dir.y * sb + dir.z * cb, 0));

		float sa = sin(-Yaw);
		float ca = cos(-Yaw);
		dir = normalize(vec4(dir.z * sa + dir.x * ca, dir.y, dir.z * ca - dir.x * sa, 0));

		float rawu = 0.5 + (atan(ray.z, ray.x) / (2 * M_PI));
		float rawv = 0.5 + (asin(ray.y) / M_PI);
		int mult = 16-i;
		float scale = mult*0.5 + 2.75;
		float u = rawu * scale * (1/1);
		float v = (rawv + GameTime * 0.0002 * (1/1)) * scale * 0.6 * (1/1);

		int uvtiles = 16;
		int tu = int(mod(floor(u*uvtiles), uvtiles));
		int tv = int(mod(floor(v*uvtiles), uvtiles));

		float ru = clamp(mod(u, 1.0)*uvtiles - tu, 0.0, 1.0);
		float rv = clamp(mod(v, 1.0)*uvtiles - tv, 0.0, 1.0);
		int symbol = int(rand2d(vec2(tu, tv + i * 10.0)) * 101);
		if (symbol >= 0 && symbol < 10) {

			float umin = CosmicUVs[symbol][0][0];
			float umax = CosmicUVs[symbol][1][0];
			float vmin = CosmicUVs[symbol][0][1];
			float vmax = CosmicUVs[symbol][1][1];

			vec4 tcol = vec4(1.0, 0.0, 0.0, 1.0);

			vec2 cosmictex;
			cosmictex.x = umin * (1.0-ru) + umax * ru;
			cosmictex.y = vmin * (1.0-rv) + vmax * rv;

			// set the alpha, blending out at the bunched ends
			tcol = texture(Sampler0, cosmictex);
			float a = tcol.r * (0.5 + (1.0/mult) * 1.0) * (1.0-smoothstep(0.15, 0.48, abs(rawv-0.5)));


			// get fancy colours
			float r = (mod(rand1, 29.0)/29.0) * 0.3 + 0.4;
			float g = (mod(rand2, 35.0)/35.0) * 0.4 + 0.6;
			float b = (mod(rand1, 17.0)/17.0) * 0.3 + 0.7;

			// mix the colours
			col = col + vec4(r, g, b, 1)*a;
		}
	}
	col = clamp(col, 0.0, 1.0);
	col.a *= mask;

	fragColor = col;

}
