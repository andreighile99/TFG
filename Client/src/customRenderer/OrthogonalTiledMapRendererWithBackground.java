
package customRenderer;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Clase render personalizada que permite la renderización de Map Objects de forma directa.
 *
 * @author Eduard Andrei Ghile
 */
public class OrthogonalTiledMapRendererWithBackground extends OrthogonalTiledMapRenderer {
    
    /**
     * Instantiates a new orthogonal tiled map renderer with background.
     *
     * @param map the map
     */
    public OrthogonalTiledMapRendererWithBackground(TiledMap map) {
        super(map);
    }


    
    @Override
    public void renderObject(MapObject object) {
        if(object instanceof TextureMapObject) {
            TextureMapObject textureObj = (TextureMapObject) object;
            batch.draw(textureObj.getTextureRegion(), textureObj.getX(), textureObj.getY(),
                    textureObj.getOriginX(), textureObj.getOriginY(), textureObj.getTextureRegion().getRegionWidth(), textureObj.getTextureRegion().getRegionHeight(),
                    textureObj.getScaleX(), textureObj.getScaleY(), textureObj.getRotation());
        }
    }
}
