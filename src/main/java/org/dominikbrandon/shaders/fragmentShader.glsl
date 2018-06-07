#version 400 core

in vec2 passTextureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector;

out vec4 outColour;

uniform sampler2D textureSampler;
uniform vec3 lightColour;

void main(void) {
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);

    float nDot1 = dot(unitNormal, unitLightVector);
    float brightness = max(nDot1, 0.2);
    vec3 diffuse = brightness * lightColour;

    outColour = vec4(diffuse, 1.0) * texture(textureSampler, passTextureCoords);
}