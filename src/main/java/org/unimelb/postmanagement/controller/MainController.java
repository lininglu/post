package org.unimelb.postmanagement.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.unimelb.postmanagement.model.CustomerInfo;
import org.unimelb.postmanagement.model.NewPost;
import org.unimelb.postmanagement.model.PaginationResult;
import org.unimelb.postmanagement.model.PostInfo;
import org.unimelb.postmanagement.util.Utils;
import org.unimelb.postmanagement.validator.CustomerInfoValidator;

@Controller
// Enable Hibernate Transaction.
@Transactional
// Need to use RedirectAttributes
@EnableWebMvc
public class MainController {

	@Autowired
	private PostDAO postDAO;

	@Autowired
	private CustomerInfoValidator customerInfoValidator;

	@InitBinder
	public void myInitBinder(WebDataBinder dataBinder) {
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		System.out.println("Target=" + target);

		// For Customer Form.
		// (@ModelAttribute("customerForm") @Validated CustomerInfo
		// customerForm)
		if (target.getClass() == CustomerInfo.class) {
			dataBinder.setValidator(customerInfoValidator);
		}

	}

	@RequestMapping("/403")
	public String accessDenied() {
		return "/403";
	}

	@RequestMapping("/")
	public String home() {
		return "index";
	}
	
	@RequestMapping(value = { "/accountInfo" }, method = RequestMethod.GET)
	public String accountInfo(Model model) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(userDetails.getPassword());
		System.out.println(userDetails.getUsername());
		System.out.println(userDetails.isEnabled());

		model.addAttribute("userDetails", userDetails);
		return "accountInfo";
	}

	// GET: Enter customer information.
	@RequestMapping(value = { "/newPost" }, method = RequestMethod.GET)
	public String CustomerForm(HttpServletRequest request, Model model) {

		NewPost newPost = Utils.getNewPostInSession(request);

		CustomerInfo customerInfo = newPost.getCustomerInfo();
		if (customerInfo == null) {
			customerInfo = new CustomerInfo();
		}

		model.addAttribute("customerForm", customerInfo);

		return "newPost";
	}

	// POST: Save customer information.
	@RequestMapping(value = { "/newPost" }, method = RequestMethod.POST)
	public String CustomerSave(HttpServletRequest request, //
			Model model, //
			@ModelAttribute("customerForm") @Validated CustomerInfo customerForm, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {

		// If has Errors.
		if (result.hasErrors()) {
			customerForm.setValid(false);
			// Forward to reenter customer info.
			return "newPost";
		}

		customerForm.setValid(true);
		NewPost newPost = Utils.getNewPostInSession(request);

		newPost.setCustomerInfo(customerForm);

		// Redirect to Confirmation page.
		return "redirect:/newPostConfirmation";
	}

	// GET: Review new post to confirm.
	@RequestMapping(value = { "/newPostConfirmation" }, method = RequestMethod.GET)
	public String postDetailConfirmationReview(HttpServletRequest request, Model model) {
		NewPost newPost = Utils.getNewPostInSession(request);

		// Cart have no products.
		if (!newPost.isValidCustomer()) {
			// Enter customer info.
			return "redirect:/newPost";
		}

		return "newPostConfirmation";
	}

	// POST: Send post (Save).
	@RequestMapping(value = { "/newPostConfirmation" }, method = RequestMethod.POST)
	// Avoid UnexpectedRollbackException (See more explanations)
	@Transactional(propagation = Propagation.NEVER)
	public String postDetailConfirmationSave(HttpServletRequest request, Model model) {
		NewPost newPost = Utils.getNewPostInSession(request);

		if (!newPost.isValidCustomer()) {
			// Enter customer info.
			return "redirect:/newPost";
		}
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		
		try {
			System.out.print("intry");
			postDAO.savePost(newPost, userName);
		} catch (Exception e) {
			e.printStackTrace();
			return "newPostConfirmation";
		}
		// Remove new post in sesion.
		Utils.removeNewPostInSession(request);

		// Store Last ordered post to session
		Utils.storeLastOrderedPostInSession(request, newPost);
		// Redirect to successful page.
		return "redirect:/newPostFinalize";
	}

	@RequestMapping(value = { "/newPostFinalize" }, method = RequestMethod.GET)
	public String postFinalize(HttpServletRequest request, Model model) {

		NewPost lastOrderedPost = Utils.getLastOrderedPostInSession(request);

		if (lastOrderedPost == null) {
			return "redirect:/newPost";
		}

		return "newPostFinalize";
	}
	
	@RequestMapping(value = { "/myPosts" }, method = RequestMethod.GET)
	public String myPosts(Model model, //
			@RequestParam(value = "page", defaultValue = "1") String pageStr) {
		int page = 1;
		try {
			page = Integer.parseInt(pageStr);
		} catch (Exception e) {
		}
		final int MAX_RESULT = 5;
		final int MAX_NAVIGATION_PAGE = 10;
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		PaginationResult<PostInfo> paginationResult //
				= postDAO.listPostInfo(page, MAX_RESULT, MAX_NAVIGATION_PAGE, userName);

		model.addAttribute("paginationResult", paginationResult);
		return "myPosts";
	}
	
	@RequestMapping(value = { "/post" }, method = RequestMethod.GET)
	public String postView(Model model, @RequestParam("postId") String postId) {
		PostInfo postInfo = null;
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		if (postId != null) {
			postInfo = this.postDAO.getPostInfo(postId, userName);
		}
		if (postInfo == null) {
			return "redirect:/myPosts";
		}
		model.addAttribute("postInfo", postInfo);
		return "post";
	}
	
	// POST: Save post
    @RequestMapping(value = { "/post" }, method = RequestMethod.POST)
    // Avoid UnexpectedRollbackException (See more explanations)
    @Transactional(propagation = Propagation.NEVER)
    public String postSave(Model model, //
            @ModelAttribute("postInfo") @Validated PostInfo postInfo, //
            BindingResult result, //
            final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "post";
        }
        try {
            postDAO.update(postInfo);
        } catch (Exception e) {
        		e.printStackTrace();
            // Need: Propagation.NEVER?
            String message = e.getMessage();
            model.addAttribute("message", message);
            // Show post form.
            return "post";
 
        }
        return "redirect:/myPosts";
    }
    
    @RequestMapping(value = { "/delete" })
	public String postDelete(Model model, @RequestParam("postId") String postId) {
		if (postId != null) {
			postDAO.delete(postId);
		}
		return "redirect:/myPosts";
	}
}
