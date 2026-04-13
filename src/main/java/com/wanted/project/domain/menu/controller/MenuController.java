package com.wanted.project.domain.menu.controller;

import com.wanted.project.domain.menu.model.dto.CategoryDTO;
import com.wanted.project.domain.menu.model.dto.MenuDTO;
import com.wanted.project.domain.menu.model.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
// 필드에 final 키워드가 붙은 친구들을 자동으로 생성자 주입을 해준다.
@RequiredArgsConstructor
@RequestMapping("/menu/*")
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/{menuCode}")
    public ModelAndView findByMenuByPathVariable(@PathVariable int menuCode, ModelAndView mv) {

        MenuDTO findMenu = menuService.findMenuByMenuCode(menuCode);

        mv.addObject("result", findMenu);
        mv.setViewName("menu/detail");

        return mv;
    }

    @GetMapping("/querymethod")
    public void queryPage() {

    }

    @GetMapping("/search")
    public ModelAndView findByMenuPrice(@RequestParam int menuPrice, ModelAndView mv) {

        System.out.println("사용자가 입력한 menuPrice = " + menuPrice);

        List<MenuDTO> menuList = menuService.findMenuByPrice(menuPrice);

        mv.addObject("menuList", menuList);
        mv.addObject("price", menuPrice);
        mv.setViewName("menu/searchResult");
        return mv;
    }

    @GetMapping("/regist")
    public String regist() {
        return "menu/regist";
    }

    // 해당 메서드는 비동기 방식으로 페이지를 리턴하는 것이 아닌
    // 데이터만 리턴할 것이다.

    /* comment.
    *   @ResponseBody 를 붙이게 되면 웹 페이지에 Json 형태로 데이터를 리턴하게 된다
    *   Json은 JavaScript 객체 표기법으로 우리 Java 클래스와 비슷한 역할이라고 보면 된다.
    *   @ResponseBody 는 1개의 페이지에 여러 데이터를 표현할 때 1개의 핸들러메서드에서 여러 데이터를 넣는 것이 아닌
    *   비동기 형식으로 각 핸들러메서드에서 전달되는 값을 조합할 때 유용하게 사용된다.
    * */

    @GetMapping("/category")
    @ResponseBody //json 타입으로 반환함(페이지 반환이 아닌 데이터반환)
    public List<CategoryDTO> findCategoryList() {
        return menuService.findAllCategory();
    }

    @PostMapping("/regist")
    public ModelAndView registMenu(@ModelAttribute MenuDTO registMenu, ModelAndView mv) {
        System.out.println("메뉴 등록 시 화면에서 넘어오는 값 registMenu = " + registMenu);
        int menuCode = menuService.registNewMenu(registMenu);
        System.out.println("menuCode = " + menuCode);

        mv.setViewName("redirect:/menu/" + menuCode);
        return mv;
    }

    @GetMapping("/modify")
    public void modifyPage() {

    }

    @PostMapping("/modify")
    public ModelAndView modifyMenuName(@RequestParam int menuCode, @RequestParam String menuName, ModelAndView mv) {

        menuService.modifyMenuName(menuCode, menuName);

        mv.setViewName("redirect:/menu/" + menuCode);

        return mv;
    }

    @GetMapping("/list")
    public ModelAndView allMenu(ModelAndView mv) {
        List<MenuDTO> menuList = menuService.findAllMenu();

        return mv;

    }

//    @DeleteMapping("delete/{menuCode}")
//    @ResponseBody
//    public String deleteTestMethod(@RequestBody MemberDTO memberDTO) {
//        return menuCode + "번 메뉴 삭제 완료!";
//    }
}
