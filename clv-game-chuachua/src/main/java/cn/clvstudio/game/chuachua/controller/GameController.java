package cn.clvstudio.game.chuachua.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Darnell
 *
 */
@Controller
public class GameController {
	@RequestMapping(value = "/index")
	public String index(ModelMap model,HttpServletRequest request) {

		return "index";
	}
	@RequestMapping(value = "/game")
	public String game(ModelMap model,HttpServletRequest request) {

		return "game";
	}
}
