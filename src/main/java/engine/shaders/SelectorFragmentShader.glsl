#version 150 core

in vec2 pass_textureCoords;

out vec4 out_Color;

uniform sampler2D modelTexture;

void main(void){

    vec4 textureColour = texture(modelTexture, pass_textureCoords);
    if (textureColour.a < 0.5) {
        discard;
    }

    out_Color = textureColour;

}