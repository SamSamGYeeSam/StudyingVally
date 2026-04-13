package com.wanted.project.global.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.wanted.project")
public class ContextConfig {


    /* comment.
    *   modelMapper 란?
    *   Entity 클래스는 데이터의 무결성을 위해 Setter 사용을 지양한다.
    *   그렇다면 어떻게 DTO <-> Entity 값을 set 할까?
    *   여러가지 방법이 있는데 대표적인 방식으로 modelmapper 라는 것이 있다.
    *
    * */
    //가져와서 쓸 때
    @Bean
    public ModelMapper modelMapper() {
        //우리가 사용할 ModelMapper 설정하기
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                // private 필드에 접근할 수 있게 권한 부여
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
        return modelMapper;
    }
}
