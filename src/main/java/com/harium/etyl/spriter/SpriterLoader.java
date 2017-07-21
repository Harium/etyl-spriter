package com.harium.etyl.spriter;

import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.FileReference;
import com.brashmonkey.spriter.Loader;
import com.harium.etyl.layer.ImageLayer;
import com.harium.etyl.layer.StaticLayer;
import com.harium.etyl.loader.image.ImageLoader;
import com.harium.etyl.util.PathHelper;

import java.io.IOException;
import java.io.InputStream;

public class SpriterLoader extends Loader<ImageLayer> {

    public boolean loadAsStream = true;
    public static boolean asyncLoad = false;

    /**
     * Creates a loader with the given Spriter data.
     *
     * @param data the generated Spriter data
     */
    public SpriterLoader(Data data) {
        super(data);
    }

    @Override
    protected ImageLayer loadResource(FileReference ref) {

        String pathPrefix;

        if (super.root == null || super.root.isEmpty()) {
            pathPrefix = "";
        } else {
            pathPrefix = super.root + java.io.File.separator;
        }

        String path = pathPrefix + data.getFile(ref).name;

        try {
            if (loadAsStream) {
                //Avoid this line in Android
                InputStream stream = null;
                if (!asyncLoad) {
                    stream = PathHelper.loadAsset(path);
                }
                StaticLayer layer = ImageLoader.getInstance().loadImage(stream, path);

                ImageLayer imageLayer = new ImageLayer();
                imageLayer.cloneLayer(layer);

                return imageLayer;
            } else {
                return new ImageLayer(path, true);
            }

        } catch (IOException e) {
            System.err.println("Asset not found: " + path);
            e.printStackTrace();
        }

        return null;
    }
}
