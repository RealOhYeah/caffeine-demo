package com.heima.item;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@MapperScan("com.heima.item.mapper")
@SpringBootApplication
public class ItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemApplication.class, args);
    }

    /**
     * @description: mybatis-plus分页插件
     * @return MybatisPlusInterceptor
     */
        @Bean
        public MybatisPlusInterceptor mybatisPlusInterceptor() {
            // 初始化核心插件
            MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
            // 添加分页插件
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
            return interceptor;
        }
}
