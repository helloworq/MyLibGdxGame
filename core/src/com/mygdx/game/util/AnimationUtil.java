package com.mygdx.game.util;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class AnimationUtil {

    public static Map<String, TextureAtlas.AtlasRegion> list2Map(Array<TextureAtlas.AtlasRegion> regionArray) {
        Map<String, TextureAtlas.AtlasRegion> result = new HashMap<>();
        for (TextureAtlas.AtlasRegion region : regionArray) {
            result.put(region.name, region);
        }
        return result;
    }

    /**
     * 将给定的图层集合，提取出同名不同索引全部序列
     *
     * @param regionMap
     * @return
     */
    public static Array<TextureAtlas.AtlasRegion> getRegionsByName(Map<String, TextureAtlas.AtlasRegion> regionMap, String name) {
        Array<TextureAtlas.AtlasRegion> result = new Array<>();
        int index = 1;
        TextureAtlas.AtlasRegion temp;
        while ((temp = regionMap.get(name + index)) != null) {
            result.add(temp);
            index++;
        }

        return result;
    }
}
