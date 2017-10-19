package org.unimelb.postmanagement.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.unimelb.postmanagement.dao.PostDAO;
import org.unimelb.postmanagement.model.PaginationResult;
import org.unimelb.postmanagement.model.PostInfo;

@Controller
// Enable Hibernate Transaction.
@Transactional
// Need to use RedirectAttributes
@EnableWebMvc
public class AdminController {

	@Autowired
	private PostDAO postDAO;

	// Configurated In ApplicationContextConfig.
	@Autowired
	private ResourceBundleMessageSource messageSource;

	@InitBinder
	public void myInitBinder(WebDataBinder dataBinder) {
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		System.out.println("Target=" + target);
	}

	// GET: Show Login Page
	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String login(Model model) {

		return "login";
	}

	@RequestMapping(value = { "/allPosts" }, method = RequestMethod.GET)
	public String postList(Model model, //
			@RequestParam(value = "page", defaultValue = "1") String pageStr) {
		int page = 1;
		try {
			page = Integer.parseInt(pageStr);
		} catch (Exception e) {
		}
		final int MAX_RESULT = 5;
		final int MAX_NAVIGATION_PAGE = 10;

		PaginationResult<PostInfo> paginationResult //
				= postDAO.listAllPosts(page, MAX_RESULT, MAX_NAVIGATION_PAGE);

		model.addAttribute("paginationResult", paginationResult);
		return "allPosts";
	}
	
	@RequestMapping(value = { "/adminPost" }, method = RequestMethod.GET)
	public String postViewAdmin(Model model, @RequestParam("postId") String postId) {
		PostInfo postInfo = null;
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		if (postId != null) {
			postInfo = this.postDAO.getPostInfo(postId, userName);
		}
		if (postInfo == null) {
			return "redirect:/allPosts";
		}
		model.addAttribute("postInfo", postInfo);
		return "adminPost";
	}
	
	// POST: Save post
    @RequestMapping(value = { "/adminPost" }, method = RequestMethod.POST)
    // Avoid UnexpectedRollbackException (See more explanations)
    @Transactional(propagation = Propagation.NEVER)
    public String postSaveAdmin(Model model, //
            @ModelAttribute("postInfo") @Validated PostInfo postInfo, //
            BindingResult result, //
            final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "adminPost";
        }
        try {
            postDAO.update(postInfo);
        } catch (Exception e) {
            // Need: Propagation.NEVER?
            String message = e.getMessage();
            model.addAttribute("message", message);
            // Show product form.
            return "adminPost";
 
        }
        return "redirect:/allPosts";
    }
    
    @RequestMapping(value = { "/adminDelete" })
	public String postDelete(Model model, @RequestParam("postId") String postId) {
		if (postId != null) {
			postDAO.delete(postId);
		}
		return "redirect:/allPosts";
	}
}
