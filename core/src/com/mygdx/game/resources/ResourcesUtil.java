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
    public static Animation<TextureRegion> getAnimationByName(String filePath, String animationName) {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(filePath));
        Map<String, TextureAtlas.AtlasRegion> map = list2Map(atlas.getRegions());

        return new Animation<TextureRegion>(0.1f, getRegionsByName(map, animationName));
//        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("assassin-mage-viking/texture.atlas"));
//        Map<String, TextureAtlas.AtlasRegion> map = list2Map(atlas.getRegions());
//
//        return new Animation<TextureRegion>(0.1f, getRegionsByName(map, "PNG/Knight/Run/run"));
    }


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
    public static Array<TextureAtlas.AtlasRegion> getRegionsByName(Map<String, TextureAtlas.AtlasRegion> regionMap, final String name) {
        Array<TextureAtlas.AtlasRegion> result = new Array<>();
        int index = 0;
        TextureAtlas.AtlasRegion temp;

        Set<String> keySet = regionMap.keySet();
        for (String key : keySet) {
            if (key.startsWith(name)){
                result.add(regionMap.get(key));
            }
        }

        result.sort(new Comparator<TextureAtlas.AtlasRegion>() {
            @Override
            public int compare(TextureAtlas.AtlasRegion o1, TextureAtlas.AtlasRegion o2) {
                String nameO1 = o1.name;
                String nameO2 = o2.name;
                for (int i = 0; i < nameO1.length(); i++) {


                }

                return 0;
            }
        });

//        while ((temp = regionMap.get(name + "-0" + index)) != null) {
//            result.add(temp);
//            index++;
//        }

        return result;
    }
}
