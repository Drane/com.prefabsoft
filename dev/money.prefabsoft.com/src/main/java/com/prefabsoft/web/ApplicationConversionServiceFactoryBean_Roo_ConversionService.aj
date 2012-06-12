// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.prefabsoft.web;

import com.prefabsoft.money.ItemTag;
import com.prefabsoft.web.ApplicationConversionServiceFactoryBean;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;

privileged aspect ApplicationConversionServiceFactoryBean_Roo_ConversionService {
    
    declare @type: ApplicationConversionServiceFactoryBean: @Configurable;
    
    public Converter<ItemTag, String> ApplicationConversionServiceFactoryBean.getItemTagToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.prefabsoft.money.ItemTag, java.lang.String>() {
            public String convert(ItemTag itemTag) {
                return new StringBuilder().append(itemTag.getName()).toString();
            }
        };
    }
    
    public Converter<Long, ItemTag> ApplicationConversionServiceFactoryBean.getIdToItemTagConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.prefabsoft.money.ItemTag>() {
            public com.prefabsoft.money.ItemTag convert(java.lang.Long id) {
                return ItemTag.findItemTag(id);
            }
        };
    }
    
    public Converter<String, ItemTag> ApplicationConversionServiceFactoryBean.getStringToItemTagConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.prefabsoft.money.ItemTag>() {
            public com.prefabsoft.money.ItemTag convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), ItemTag.class);
            }
        };
    }
    
    public void ApplicationConversionServiceFactoryBean.installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getItemTagToStringConverter());
        registry.addConverter(getIdToItemTagConverter());
        registry.addConverter(getStringToItemTagConverter());
    }
    
    public void ApplicationConversionServiceFactoryBean.afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
    
}
