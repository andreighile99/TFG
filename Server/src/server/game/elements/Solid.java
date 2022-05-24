package server.game.elements;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Clase utilizada para definir los solidos en el servidor
 * 
 * @author Eduard Andrei Ghile
 *
 */
public class Solid{
	private float width;
	private float height;
	private Polygon colision;
	private float x;
	private float y;

	public Solid(float x, float y, float w, float h) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		float[] vertices= {x,y+0,x+w,y+0,x+w,y+h,x+0,y+h};
		colision=new Polygon(vertices);
		this.setSize(w, h);
	}

	private void setSize(float w, float h){
		this.width = w;
		this.height = h;
	}

	// Check if Polygon intersects with Another Polygon
	public boolean isCollision(Polygon p) {
		if (Intersector.overlapConvexPolygons(this.colision, p))
			return true;
		return false;
	}

	// Check if Polygon intersects Rectangle
	public boolean isCollision(Rectangle r) {
		Polygon rPoly = new Polygon(new float[] { 0, 0, r.width, 0, r.width,
				r.height, 0, r.height });
		rPoly.setPosition(r.x, r.y);
		if (Intersector.overlapConvexPolygons(this.colision, rPoly)){
			return true;
		}
		return false;
	}


	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Polygon getColision() {
		return colision;
	}

}
