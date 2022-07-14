#version 150

#moj_import <matrix.glsl>

//mask texture location
uniform sampler2D Sampler0;
uniform float Time;
uniform float Pitch;
uniform float Yaw;
uniform float ExternalScale;
uniform mat2 CosmicUVs[10];

//The texture co-oords of the icon to render
in vec2 texCoord0;
in vec3 position;

out vec4 fragColor;

#define M_PI 3.1415926535897932384626433832795

const int cosmiccount = 10;
const int cosmicoutof = 101;

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

void main() {
	float mask = texture(Sampler0, texCoord0).r;
/*	if (mask <= 0) {
		discard;
	}*/
	if (mask < 0.1) {
		discard;
	}

	float oneOverExternalScale = 1.0/ExternalScale;

	int uvtiles = 16;

	// background colour
	vec4 col = vec4(0.1, 0.0, 0.0, 1.0);

	float pulse = mod(Time, 400)/400.0;

	col.g = sin(pulse * M_PI * 2) * 0.075 + 0.225;
	col.b = cos(pulse * M_PI * 2) * 0.05 + 0.3;

	vec4 dir = normalize(vec4(-position, 0));
	float sb = sin(Pitch);
	float cb = cos(Pitch);
	dir = normalize(vec4(dir.x, dir.y * cb - dir.z * sb, dir.y * sb + dir.z * cb, 0));
	float sa = sin(-Yaw);
	float ca = cos(-Yaw);
	dir = normalize(vec4(dir.z * sa + dir.x * ca, dir.y, dir.z * ca - dir.x * sa, 0));
	vec4 ray;

	for (int i=0; i<16; i++) {
		int mult = 16-i;
		// get semi-random stuff
		int j = i + 7;
		float rand1 = (j * j * 4321 + j * 8) * 2.0F;
		int k = j + 1;
		float rand2 = (k * k * k * 239 + k * 37) * 3.6F;
		float rand3 = rand1 * 347.4 + rand2 * 63.4;

		// random rotation matrix by random rotation around random axis
		vec3 axis = normalize(vec3(sin(rand1), sin(rand2), cos(rand3)));

		// apply
		ray = dir * rotationMatrix(axis, mod(rand3, 2*M_PI));

		// calcuate the UVs from the final ray
		float rawu = 0.5 + (atan(ray.z, ray.x)/(2*M_PI));
		float rawv = 0.5 + (asin(ray.y)/M_PI);

		// get UV scaled for layers and offset by Time;
		float scale = mult*0.5 + 2.75;
		float u = rawu * scale * ExternalScale;
		//float v = (rawv + Time * 0.00006) * scale * 0.6;
		float v = (rawv + Time * 0.0002 * oneOverExternalScale) * scale * 0.6 * ExternalScale;
		vec2 tex = vec2(u, v);

		// tile position of the current uv
		int tu = int(mod(floor(u*uvtiles), uvtiles));
		int tv = int(mod(floor(v*uvtiles), uvtiles));

		// get pseudorandom variants
		int position = ((1777541 * tu) + (7649689 * tv) + (3612703 * (i+31)) + 1723609) ^ 50943779;
		int symbol = int(mod(position, cosmicoutof));
		int rotation = int(mod(pow(tu, float(tv)) + tu + 3 + tv*i, 8));
		bool flip = false;
		if (rotation >= 4) {
			rotation -= 4;
			flip = true;
		}

		// if it's an icon, then add the colour!
		if (symbol >= 0 && symbol < cosmiccount) {

			vec2 cosmictex = vec2(1.0, 1.0);
			vec4 tcol = vec4(1.0, 0.0, 0.0, 1.0);

			// get uv within the tile
			float ru = clamp(mod(u, 1.0)*uvtiles - tu, 0.0, 1.0);
			float rv = clamp(mod(v, 1.0)*uvtiles - tv, 0.0, 1.0);

			if (flip) {
				ru = 1.0 - ru;
			}

			float oru = ru;
			float orv = rv;

			// rotate uvs if necessary
			if (rotation == 1) {
				oru = 1.0-rv;
				orv = ru;
			} else if (rotation == 2) {
				oru = 1.0-ru;
				orv = 1.0-rv;
			} else if (rotation == 3) {
				oru = rv;
				orv = 1.0-ru;
			}

			// get the iicon uvs for the tile
			float umin = CosmicUVs[symbol][0][0];
			float umax = CosmicUVs[symbol][1][0];
			float vmin = CosmicUVs[symbol][0][1];
			float vmax = CosmicUVs[symbol][1][1];

			// interpolate based on tile uvs
			cosmictex.x = umin * (1.0-oru) + umax * oru;
			cosmictex.y = vmin * (1.0-orv) + vmax * orv;

			tcol = texture(Sampler0, cosmictex);

			// set the alpha, blending out at the bunched ends
			float a = tcol.r * (0.5 + (1.0/mult) * 1.0) * (1.0-smoothstep(0.15, 0.48, abs(rawv-0.5)));

			// get fancy colours
			float r = (mod(rand1, 29.0)/29.0) * 0.3 + 0.4;
			float g = (mod(rand2, 35.0)/35.0) * 0.4 + 0.6;
			float b = (mod(rand1, 17.0)/17.0) * 0.3 + 0.7;

			// mix the colours
			//col = col*(1-a) + vec4(r,g,b,1)*a;
			col =  vec4(r, g, b, 1);
		}
	}
	//	vec3 shade = light.rgb * (lightmix) + vec3(1.0-lightmix,1.0-lightmix,1.0-lightmix);
	//	col.rgb *= shade;

	// apply mask
	col.a *= mask * .4;

	col = clamp(col, 0.0, 1.0);

	fragColor = col;

	//	vec4 ray = normalize(vec4(-position, 0));
	//	float rawu = 0.5 + (atan(ray.z, ray.x)/(2*M_PI));
	//	for (int i = 0; i < 4; i++) {
	//	}
	//	vec2 cosmictex = vec2(renderPositions[0][0][1], renderPositions[0][0][1]);
	//	fragColor = texture(Sampler0, texCoord0 + cosmictex);
	//	fragColor = textureProjLodOffset(Sampler0, renderPositions[0], 1.0, vec2(0, 0));

	//	float umin = CosmicUVs[1][0][0];
	//	float umax = CosmicUVs[1][1][0];
	//	float vmin = CosmicUVs[1][0][1];
	//	float vmax = CosmicUVs[1][1][1];

	//	float oru = 0.0;
	//	float orv = 0.0;
	//	cosmictex.x = umin * (1.0-rawu) + umax * rawu;
	//	cosmictex.y = vmin * (1.0-orv) + vmax * orv;

	//	fragColor = texture(Sampler0, vec2(texCoord0.x, texCoord0.y));
}
