package com.heima.item.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.benmanes.caffeine.cache.Cache;
import com.heima.item.pojo.Item;
import com.heima.item.pojo.ItemStock;
import com.heima.item.pojo.PageDTO;
import com.heima.item.service.IItemService;
import com.heima.item.service.IItemStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("item")
public class ItemController {

    @Autowired
    private IItemService itemService;
    @Autowired
    private IItemStockService stockService;

    @Autowired
    private Cache<Long,Item> itemCache;
    @Autowired
    private Cache<Long,ItemStock> stockCache;


    /**
     * 分页查询商品
     * @param page
     * @param size
     * @return
     */
    @GetMapping("list")
    public PageDTO queryItemPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size){
        // 分页查询商品
        Page<Item> result = itemService.query()
                .ne("status", 3)
                .page(new Page<>(page, size));

        // 查询库存
        List<Item> list = result.getRecords().stream().peek(item -> {
            ItemStock stock = stockService.getById(item.getId());
            item.setStock(stock.getStock());
            item.setSold(stock.getSold());
        }).collect(Collectors.toList());

        // 封装返回
        return new PageDTO(result.getTotal(), list);
    }

    /**
     * 保存商品
     * @param item
     */
    @PostMapping
    public void saveItem(@RequestBody Item item){
        itemService.saveItem(item);
    }

    /**
     * 更新商品
     * @param item
     */
    @PutMapping
    public void updateItem(@RequestBody Item item) {
        itemService.updateById(item);
    }

    /**
     * 更新商品库存
     * @param itemStock
     */
    @PutMapping("stock")
    public void updateStock(@RequestBody ItemStock itemStock){
        stockService.updateById(itemStock);
    }

    /**
     * 删除商品
     * @param id
     */
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id){
        itemService.update().set("status", 3).eq("id", id).update();
    }

    /**
     * 根据id查询商品（先去命中JVM缓存再找数据库）
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Item findById(@PathVariable("id") Long id){

        // 此处先去命中缓存再找数据库
        return itemCache.get(id, key -> itemService.query()
                .ne("status", 3).eq("id", id)
                .one()
        );

        /*
           传统方式直接查数据库
        return itemService.query()
                .ne("status", 3).eq("id", id)
                .one();
        */

    }

    /**
     * 根据id查询商品库存（先去命中JVM缓存再找数据库）
     * @param id
     * @return
     */
    @GetMapping("/stock/{id}")
    public ItemStock findStockById(@PathVariable("id") Long id){

        return stockCache.get(id,key -> stockService.getById(key));

        //return stockService.getById(id);


    }
}
