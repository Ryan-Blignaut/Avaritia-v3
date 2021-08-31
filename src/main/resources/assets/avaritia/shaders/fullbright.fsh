#version 150

uniform float clipThreshold;

uniform sampler2D Sampler0;

in vec2 texCoord0;
in vec2 texCoord2;
in vec4 vertexColor;
in float clip_distance;

out vec4 fragColor;

void main(){
    vec4 color = texture(Sampler0, texCoord0) * vertexColor ;
    fragColor = vec4(0.0, 1.0, 1.0, 1.0);
}
