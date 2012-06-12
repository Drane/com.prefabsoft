package com.prefabsoft.web;

import com.prefabsoft.money.ItemTag;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebJson(jsonObject = ItemTag.class)
@Controller
@RequestMapping("/itemtags")
@RooWebScaffold(path = "itemtags", formBackingObject = ItemTag.class)
public class ItemTagController {
}
