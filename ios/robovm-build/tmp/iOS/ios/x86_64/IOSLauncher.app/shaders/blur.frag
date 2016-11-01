//declare uniforms
uniform float resolution;
uniform float radius;
uniform vec2 dir;

#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif
varying LOWP vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;

void main() {
	vec4 sum = vec4(0.0);

	vec2 tc = v_texCoords;

	float blur = radius/resolution;

	float hstep = dir.x;
	float vstep = dir.y;

	sum += texture2D(u_texture, vec2(tc.x - 4.0*blur*hstep, tc.y - 4.0*blur*vstep)) * 0.0162162162;
	sum += texture2D(u_texture, vec2(tc.x - 3.0*blur*hstep, tc.y - 3.0*blur*vstep)) * 0.0540540541;
	sum += texture2D(u_texture, vec2(tc.x - 2.0*blur*hstep, tc.y - 2.0*blur*vstep)) * 0.1216216216;
	sum += texture2D(u_texture, vec2(tc.x - 1.0*blur*hstep, tc.y - 1.0*blur*vstep)) * 0.1945945946;

	sum += texture2D(u_texture, vec2(tc.x, tc.y)) * 0.2270270270;

	sum += texture2D(u_texture, vec2(tc.x + 1.0*blur*hstep, tc.y + 1.0*blur*vstep)) * 0.1945945946;
	sum += texture2D(u_texture, vec2(tc.x + 2.0*blur*hstep, tc.y + 2.0*blur*vstep)) * 0.1216216216;
	sum += texture2D(u_texture, vec2(tc.x + 3.0*blur*hstep, tc.y + 3.0*blur*vstep)) * 0.0540540541;
	sum += texture2D(u_texture, vec2(tc.x + 4.0*blur*hstep, tc.y + 4.0*blur*vstep)) * 0.0162162162;

    //gl_FragColor = vec4(0.0, dir.x, dir.y, 1.0);
    gl_FragColor = v_color * vec4(sum.rgb, 1.0);
	//gl_FragColor = vec4(sum.rgb, 1.0);
}


/*void main()
{
  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
}*/