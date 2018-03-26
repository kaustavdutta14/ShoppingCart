package com.amazon.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.amazon.domain.Conn;
import com.amazon.domain.Item;
import com.amazon.domain.SummaryOfAllItems;
import com.amazon.service.Summary.SummaryService;
import com.amazon.service.item.ItemService;
import com.amazon.service.login.LoginService;

public class ControllerServlet extends HttpServlet {

	private static final String USERNAME_TEXTBOX = "username";
	private static final String LOGIN_BUTTON = "Login";
	private static final String BUTTON_CLICKED = "action";
	private static final String CURRENT_PAGE = "page";
	private static final String BACK_TO_CART_BUTTON = "Back";
	private static final String ADD_TO_CART_BUTTON = "Add to Cart";
	private static final String CHECKOUT_BUTTON = "Checkout";
	private static final String PAYMENT_BUTTON = "Payment";
	private static final String LOGOUT = "Logout";
	private static final String HELP = "Help";
	private static final String BACK = "Back";

	private static final long serialVersionUID = 4818685114397399943L;

	private LoginService loginService;
	private ItemService itemService;
	private SummaryService summaryService;
	private HttpSession session;

	private List<Item> cart;

	@Override
	public void init() throws ServletException {
		loginService = new LoginService();
		itemService = new ItemService();
		summaryService = new SummaryService();

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String nextPage = "/login.jsp";
		String page = request.getParameter(CURRENT_PAGE);
		String action = request.getParameter("action");

		if (page != null) {
			if (page.equalsIgnoreCase("login")) {
				if (action.equalsIgnoreCase(LOGIN_BUTTON)) {
					String username = request.getParameter(USERNAME_TEXTBOX);
					String password = request.getParameter("password");
					boolean isValidUser = loginService.authenticate(username, password);
					if (isValidUser) {
						nextPage = "/cart.jsp";

						session = request.getSession(true);
						session.setAttribute("items", itemService.getItems());
						cart = new ArrayList<>();
					} else {
						nextPage = "/login.jsp";
						request.setAttribute("invalidUser", "Invalid username or password.");
					}
				}
			} else if (page.equalsIgnoreCase("cart")) {

				if (action.equalsIgnoreCase(HELP))
					nextPage = "/help.jsp";

				if (action.equalsIgnoreCase(LOGOUT)) {
					nextPage = "/login.jsp";
					session.invalidate();
				}
				if (action.equalsIgnoreCase(ADD_TO_CART_BUTTON)) {
					session.removeAttribute("cart");

					String[] selectedItemIDs = request.getParameterValues("chkItem");
					PreparedStatement ps;
					Connection conn = Conn.startConnection();
					if (selectedItemIDs != null && selectedItemIDs.length > 0) {
						for (String selectedItemID : selectedItemIDs) {
							Integer quantity = Integer.parseInt(request.getParameter("qty" + selectedItemID));
							Integer id = Integer.parseInt(selectedItemID);
							Item item = null;
							PreparedStatement preps;
							try {
								preps = (PreparedStatement) conn
										.prepareStatement("select name,price from item where id=?");
								preps.setString(1, selectedItemID);
								ResultSet rs = preps.executeQuery();
								while (rs.next()) {
									String name = rs.getString("name");
									Float unitPrice = rs.getFloat("price");
									item = new Item(id, name, unitPrice, quantity);
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
							cart.add(item);
						}

						session.setAttribute("items", itemService.getItems());
						session.setAttribute("cart", cart);
						request.setAttribute("addToCartMessage",
								selectedItemIDs.length + " Items added to cart sucessfully");
					}
					nextPage = "/cart.jsp";

				} else if (action.equalsIgnoreCase(CHECKOUT_BUTTON)) {

					List<Item> cartItems = (List<Item>) session.getAttribute("cart");
					if (cartItems != null) {
						SummaryOfAllItems summaryOfAllItems = summaryService.getSummary(cartItems);
						session.setAttribute("summaryOfAllItems", summaryOfAllItems);
						nextPage = "/summary.jsp";
					} else
						nextPage = "/cart.jsp";
				}

			} else if (page.equalsIgnoreCase("summary")) {
				if (action.equalsIgnoreCase(HELP))
					nextPage = "/help.jsp";

				if (action.equalsIgnoreCase("payment")) {
					nextPage = "/thankyou.jsp";
				}
				if (action.equalsIgnoreCase("back"))
					nextPage = "/cart.jsp";

				if (action.equalsIgnoreCase("logout")) {
					nextPage = "/login.jsp";
					session.invalidate();
				}
			}

			else if (page.equalsIgnoreCase("thankyou")) {
				if (action.equalsIgnoreCase(LOGOUT)) {
					nextPage = "/login.jsp";
					session.invalidate();
				}
			}

			else if (page.equalsIgnoreCase(HELP)) {
				if (action.equalsIgnoreCase(BACK)) {
					nextPage = "/cart.jsp";
				}
			}

			RequestDispatcher rd = request.getRequestDispatcher(nextPage);
			rd.forward(request, response);

		}

	}
}
