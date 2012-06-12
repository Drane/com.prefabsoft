// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.prefabsoft.money;

import com.prefabsoft.money.ItemTag;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect ItemTag_Roo_Json {
    
    public String ItemTag.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static ItemTag ItemTag.fromJsonToItemTag(String json) {
        return new JSONDeserializer<ItemTag>().use(null, ItemTag.class).deserialize(json);
    }
    
    public static String ItemTag.toJsonArray(Collection<ItemTag> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<ItemTag> ItemTag.fromJsonArrayToItemTags(String json) {
        return new JSONDeserializer<List<ItemTag>>().use(null, ArrayList.class).use("values", ItemTag.class).deserialize(json);
    }
    
}