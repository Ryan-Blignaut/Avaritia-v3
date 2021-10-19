#version 150
#define M_PI 3.1415926535897932384626433832795

const int cosmic_texts_count = 10;
const int cosmic_text_num = 101;

uniform sampler2D Sampler0;
uniform float Time;

uniform float pitch;
uniform float yaw;
uniform float externalScale;

in vec4 vertexColor;
in vec2 texCoord0;
in vec3 sPos;

out vec4 fragColor;

uniform mat2 CosmicUVs[cosmic_texts_count];

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
    if (mask < 0.1) {
        discard;
    }
    int uvtiles = 16;

    // background colour
    vec4 col = vec4(0.1, 0.0, 0.0, 1.0);
    float pulse = mod(Time, 400) / 400.0;
    col.g = sin(pulse * M_PI * 2) * 0.075 + 0.225;
    col.b = cos(pulse * M_PI * 2) * 0.05 + 0.3;


    // get ray from camera to fragment
    vec4 dir = normalize(vec4(-sPos, 0));

    // rotate the ray to show the right bit of the sphere for the angle
    float sb = sin(pitch);
    float cb = cos(pitch);
    dir = normalize(vec4(dir.x, dir.y * cb - dir.z * sb, dir.y * sb + dir.z * cb, 0));

    float sa = sin(-yaw);
    float ca = cos(-yaw);
    dir = normalize(vec4(dir.z * sa + dir.x * ca, dir.y, dir.z * ca - dir.x * sa, 0));

    /*  for (int i=0; i<16; i++) {
          vec2 cosmictex = vec2(1.0, 1.0);
          float umin = testPoitions[0];
          float umax = testPoitions[1];
          float vmin = testPoitions[2];
          float vmax = testPoitions[3];
          col = texture(Sampler0, cosmictex);
      }*/
    //    vec2 cosmictex = vec2(1.0, 1.0);
    //    float umin = testPoitions[0];
    //    float umax = testPoitions[1];
    //    float vmin = testPoitions[2];
    //    float vmax = testPoitions[3];
    //    col = texture(Sampler0, vec2(.80, .40));

    col.a = mask;
    //    col = clamp(col, 0.0, 1.0);

    fragColor = col;

}
