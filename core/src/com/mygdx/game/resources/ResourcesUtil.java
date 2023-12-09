package com.mygdx.game.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.util.AnimationUtil;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ResourcesUtil {

    /**
     * 由TexturePacker工具打包成单张图的Assets再次根据name进行合并，合并成单个动作的动画集合
     *
     * @return
     */
    public static Animation<TextureRegion> getAnimationByName(String filePath, String animationName, boolean flip) {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(filePath));
        Map<String, TextureAtlas.AtlasRegion> map = list2Map(atlas.getRegions());

        return new Animation<TextureRegion>(0.1f, getRegionsByName(map, animationName, flip));
//        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("assassin-mage-viking/texture.atlas"));
//        Map<String, TextureAtlas.AtlasRegion> map = list2Map(atlas.getRegions());
//
//        return new Animation<TextureRegion>(0.1f, getRegionsByName(map, "PNG/Knight/Run/run"));
    }


    private static Map<String, TextureAtlas.AtlasRegion> list2Map(Array<TextureAtlas.AtlasRegion> regionArray) {
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
    private static Array<TextureAtlas.AtlasRegion> getRegionsByName(Map<String, TextureAtlas.AtlasRegion> regionMap,
                                                                    final String name,
                                                                    boolean flip) {
        Array<TextureAtlas.AtlasRegion> result = new Array<>();
        Set<String> keySet = regionMap.keySet();
        for (String key : keySet) {
            if (key.startsWith(name)
                    && key.replace(name, "").charAt(0) == '-'
                    && Character.isDigit(key.replace(name + "-", "").charAt(0))) {
                TextureAtlas.AtlasRegion cur = regionMap.get(key);
                if (flip) {
                    cur.flip(true, false);
                }
                result.add(cur);
            }
        }

        result.sort(new Comparator<TextureAtlas.AtlasRegion>() {
            @Override
            public int compare(TextureAtlas.AtlasRegion o1, TextureAtlas.AtlasRegion o2) {
                return o1.name.compareToIgnoreCase(o2.name);
            }
        });

//        for (TextureAtlas.AtlasRegion r : result) {
//            System.out.print(r.name + "   ");
//        }
        return result;
    }
}
